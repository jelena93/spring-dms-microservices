/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.controller;

import gateway.dto.Activity;
import gateway.dto.Company;
import gateway.dto.Descriptor;
import gateway.dto.Document;
import gateway.dto.DocumentType;
import gateway.dto.MessageDto;
import gateway.dto.User;
import gateway.service.CompanyService;
import gateway.service.DocumentService;
import gateway.service.DocumentTypeService;
import gateway.service.ProcessService;
import gateway.service.UserService;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author jelena
 */
@Controller
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private DocumentTypeService documentTypeService;
    @Autowired
    ProcessService processService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public ModelAndView save(Principal principal) {
        ModelAndView mv = new ModelAndView("add_document");
        List<DocumentType> documentTypes = documentTypeService.findAll();
        User loggedUser = userService.findOne(principal.getName());
        Company company = companyService.findOne(loggedUser.getCompanyId());
        mv.addObject("documentTypes", documentTypes);
        mv.addObject("action_type_processes_search", "add_document");
        mv.addObject("company", company);
        return mv;
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ModelAndView search(Principal principal) throws IOException {
        User user = userService.findOne(principal.getName());
        Document documents = documentService.findAllByCompanyId(user.getCompanyId());
        ModelAndView mv = new ModelAndView("search_documents");
        mv.addObject("documents", documents);
        return mv;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ModelAndView save(Principal principal, String inputOutput, MultipartFile file,
            long docType, HttpServletRequest request, long activityID, Long existingDocumentID) {
        Activity activity = processService.findOneActivity(activityID);
        DocumentType documentType = documentTypeService.findOne(docType);
        List<Descriptor> descriptors = documentType.getDescriptors();
        List<Descriptor> newDescriptors = new ArrayList<>();
        for (Descriptor descriptor : descriptors) {
            if (descriptor.getValue() == null) {
                String key = descriptor.getDescriptorKey();
                String value = request.getParameter(key).trim();
                descriptor.setValue(value);
                Descriptor newDescriptor = new Descriptor(key, descriptor.getValue(), docType, descriptor.getDescriptorType());
                newDescriptors.add(newDescriptor);
            }
        }
        Document document = new Document();
        boolean found = false;
        if (existingDocumentID != null) {
            List<Document> documents;
            if (inputOutput.equals("input")) {
                documents = activity.getInputList();
            } else {
                documents = activity.getInputList();
            }
            for (Document doc : documents) {
                if (Objects.equals(existingDocumentID, doc.getId())) {
                    document = doc;
                    found = true;
                    break;
                }
            }
        }
        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        try {
            document.setFileContent(file.getBytes());
        } catch (IOException ex) {
            ModelAndView mv = new ModelAndView("add_document");
            mv.addObject("message", new MessageDto(MessageDto.MESSAGE_TYPE_ERROR, ex.getMessage()));
            return mv;
        }
        document.setDescriptors(newDescriptors);
        if (!found) {
            if (inputOutput.equals("input")) {
                activity.getInputList().add(document);
            } else {
                activity.getOutputList().add(document);
            }
        }
//        activityService.save(activity);
        ModelAndView mv = new ModelAndView("add_document");
        List<DocumentType> documentTypes = documentTypeService.findAll();
        mv.addObject("documentTypes", documentTypes);
        mv.addObject("action_type_processes_search", "add_document");
        User loggedUser = userService.findOne(principal.getName());
        Company company = companyService.findOne(loggedUser.getCompanyId());
        mv.addObject("company", company);
        if (found) {
            mv.addObject("message", new MessageDto(MessageDto.MESSAGE_TYPE_SUCCESS, "Document successfully edited"));
        } else {
            mv.addObject("message", new MessageDto(MessageDto.MESSAGE_TYPE_SUCCESS, "Document successfully added"));
        }
        return mv;
    }

    @RequestMapping(path = "/document/display/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> showFile(@PathVariable("id") long id/*, @RequestHeader HttpHeaders headers*/) {
        Document document = documentService.findOne(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(document.getFileType()));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + document.getFileName());
        headers.setContentLength(document.getFileContent().length);
        return new ResponseEntity<>(document.getFileContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(path = "/document/{id}", method = RequestMethod.GET)
    public ModelAndView showDocument(@PathVariable("id") long id) throws Exception {
        Document document = documentService.findOne(id);
        if (document == null) {
            throw new Exception("There is no document with id " + id);
        }
        return new ModelAndView("document_info", "document", document);
    }

    @RequestMapping(path = "/document/download/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") long id) {
        Document document = documentService.findOne(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(document.getFileType()));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getFileName());
        headers.setContentLength(document.getFileContent().length);
        return new ResponseEntity<>(document.getFileContent(), headers, HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}

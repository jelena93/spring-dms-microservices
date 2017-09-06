/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.controller;

import gateway.model.Company;
import gateway.dto.Descriptor;
import gateway.model.Document;
import gateway.dto.DocumentType;
import gateway.dto.MessageDto;
import gateway.dto.User;
import gateway.model.Activity;
import gateway.service.CompanyService;
import gateway.service.DocumentService;
import gateway.service.ProcessService;
import gateway.service.UserService;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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

@Controller
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    ProcessService processService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public ModelAndView save(Principal principal) {
        ModelAndView mv = new ModelAndView("add_document");
        User loggedUser = userService.findOne(principal.getName());
        Company company = companyService.findOne(loggedUser.getCompanyId());
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
        System.out.println("activity:" + activity);
        DocumentType documentType = documentService.findOneDocumentType(docType);
        System.out.println("documentType:" + documentType);
        List<Descriptor> newDescriptors = new ArrayList<>();
        for (Descriptor descriptor : documentType.getDescriptors()) {
            if (descriptor.getValue() == null) {
                String key = descriptor.getDescriptorKey();
                String value = request.getParameter(key).trim();
                Descriptor newDescriptor = new Descriptor(key, descriptor, value, docType, descriptor.getDescriptorType());
                newDescriptors.add(newDescriptor);
                System.out.println("novi desc" + newDescriptor);
            }
        }
        List<Long> newDescriptorsIds = documentService.save(newDescriptors);
        Document document = new Document();
        boolean found = false;
//        if (existingDocumentID != null) {
//            List<Document> documents;
//            if (inputOutput.equals("input")) {
//                documents = activity.getInputList();
//            } else {
//                documents = activity.getInputList();
//            }
//            for (DocumentDto doc : documents) {
//                if (Objects.equals(existingDocumentID, doc.getId())) {
//                    document = doc;
//                    found = true;
//                    break;
//                }
//            }
//        }
        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        try {
            document.setFileContent(file.getBytes());
        } catch (IOException ex) {
            ModelAndView mv = new ModelAndView("add_document");
            mv.addObject("message", new MessageDto(MessageDto.MESSAGE_TYPE_ERROR, ex.getMessage()));
            return mv;
        }
        User loggedUser = userService.findOne(principal.getName());
        Company company = companyService.findOne(loggedUser.getCompanyId());
        document.setCompanyId(company.getId());
        document.setDescriptors(newDescriptorsIds);
        documentType.getDescriptors().addAll(newDescriptors);
        System.out.println("new doc" + documentType);
        System.out.println("document" + document);
        document = documentService.save(document);
        if (!found) {
            if (inputOutput.equals("input")) {
                activity.getInputList().add(document.getId());
            } else {
                activity.getOutputList().add(document.getId());
            }
        }
        System.out.println("activity" + activity);
        processService.save(activity);
        ModelAndView mv = new ModelAndView("add_document");
        List<DocumentType> documentTypes = documentService.findAllDocumentTypes();
        mv.addObject("documentTypes", documentTypes);
        mv.addObject("company", company);
//        if (found) {
//            mv.addObject("message", new MessageDto(MessageDto.MESSAGE_TYPE_SUCCESS, "Document successfully edited"));
//        } else {
        mv.addObject("message", new MessageDto(MessageDto.MESSAGE_TYPE_SUCCESS, "Document successfully added"));
//        }
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

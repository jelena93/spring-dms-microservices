/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.controller;

import document.dto.MessageDto;
import document.repository.DocumentRepository;
import document.model.Document;
import document.model.DocumentType;
import document.repository.DescriptorRepository;
import document.repository.DocumentTypeRepository;
import java.util.List;
import javax.management.Descriptor;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/")
public class DocumentServiceController {

    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    private DescriptorRepository descriptorRepository;
    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @RequestMapping(value = "document-type", method = RequestMethod.GET)
    public List<DocumentType> getDocumentTypes() {
        List<DocumentType> documentTypes = documentTypeRepository.findAll();
        return documentTypes;
    }

    @RequestMapping(value = "/document-types")
    public List<DocumentType> findDocumentTypesByIds(@RequestBody List<Long> ids) {
        List<DocumentType> documentTypes = documentTypeRepository.findByIdIn(ids);
        for (DocumentType documentType : documentTypes) {
            System.out.println("doc" + documentType);
        }
        return documentTypes;
    }

    @RequestMapping(value = "/document-type/{id}", method = RequestMethod.GET)
    public DocumentType findByDocumentType(@PathVariable("id") Long id) {
        return documentTypeRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateDocumentType(@RequestBody DocumentType documentType) {
        System.out.println("updating " + documentType);
        documentTypeRepository.save(documentType);
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<document.model.Descriptor> addDescriptors(@RequestBody List<document.model.Descriptor> descriptors) {
        System.out.println("adding " + descriptors);
        return descriptorRepository.save(descriptors);
    }

    @RequestMapping(value = "/{company}/search", method = RequestMethod.GET)
    public List<Document> search(@PathVariable("company") Long company, String query) {
        if (query == null || query.isEmpty()) {
            return documentRepository.findAll();
        }
        return documentRepository.findAll();
//        return documentRepository.findByDescriptorsDescriptorKey(query);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Document addDocument(@RequestBody Document document) {
        return documentRepository.save(document);
    }

    @RequestMapping(value = "/ids")
    public List<Document> findDocumentByIds(@RequestBody List<Long> ids) {
        List<Document> documents = documentRepository.findByIdIn(ids);
        return documents;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Document findDocument(@PathVariable("id") long id) {
        return documentRepository.findOne(id);
    }

    @RequestMapping(value = "/search/{companyId}", method = RequestMethod.GET)
    public List<Document> getDocumentTypes(@PathVariable("companyId") Long companyId, String query) {
        if (query == null || query.isEmpty()) {
            return documentRepository.findByCompanyId(companyId);
        }
        return documentRepository.findByCompanyId(companyId);
    }

    @RequestMapping(path = "/validation", method = RequestMethod.POST)
    public ResponseEntity<MessageDto> checkIfDocumentExists(HttpServletRequest request, long docType, long activityID, String inputOutput) throws Exception {
//        DocumentType documentType = documentTypeRepository.findOne(docType);
//        List<Descriptor> descriptors = documentType.getDescriptors();
//        List<Descriptor> existingDescriptors = descriptorRepository.findByDocumentType(docType);
        int numberOfIdenticalDescriptors = 0;
        Long existingDocumentID = null;
//        for (Descriptor descriptor : descriptors) {
//            String key = descriptor.getDescriptorKey();
//            String value = request.getParameter(key).trim();
//            descriptor.setValue(value);
//            if (descriptor.getValue() == null) {
//                throw new Exception("Value for descriptor " + descriptor.getDescriptorKey()
//                        + "  is not correct. Expecting descriptor of type " + descriptor.getDescriptorType().getStringMessageByParamClass() + ".");
//            }
//            Descriptor newDescriptor = new Descriptor(key, descriptor.getValue(), docType, descriptor.getDescriptorType());
//            Long id = checkIfFileAlreadyAdded(existingDescriptors, newDescriptor, activityID, inputOutput);
//            if (id == null) {
//                continue;
//            } else if (existingDocumentID == null) {
//                existingDocumentID = id;
//            } else if (!Objects.equals(id, existingDocumentID)) {
//                continue;
//            }
//            numberOfIdenticalDescriptors += 1;
//        }
//        if (numberOfIdenticalDescriptors == descriptors.size() && existingDocumentID != null) {
//            return new ResponseEntity<>(new MessageDto(MessageDto.MESSAGE_TYPE_QUESTION, existingDocumentID + ""), HttpStatus.OK);
//        }
        return new ResponseEntity<>(new MessageDto(MessageDto.MESSAGE_TYPE_SUCCESS, "ok"), HttpStatus.OK);
    }

    private Long checkIfFileAlreadyAdded(List<Descriptor> existingDescriptors, Descriptor newDescriptor, long activityID, String inputOutput) {
        if (existingDescriptors == null || existingDescriptors.isEmpty()) {
            return null;
        }
//        for (Descriptor existingDescriptor : existingDescriptors) {
//            if (existingDescriptor.getValue() == null) {
//                continue;
//            }
//            if (existingDescriptor.equals(newDescriptor)
//                    || ((newDescriptor.getValue() instanceof Date && (existingDescriptor.getValue() instanceof Date)) && isTheSameDate(existingDescriptor, newDescriptor))) {
//                Activity activity = activityRepository.findOne(activityID);
//                if (inputOutput.equals("input")) {
//                    for (Document document : activity.getInputList()) {
//                        if (document.getDescriptors().contains(existingDescriptor)) {
//                            return document.getId();
//                        } else if (inputOutput.equals("output")) {
//                            for (Document d : activity.getOutputList()) {
//                                if (d.getDescriptors().contains(existingDescriptor)) {
//                                    return document.getId();
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDto> handleError(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>(new MessageDto(MessageDto.MESSAGE_TYPE_ERROR, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

//    private boolean isTheSameDate(Descriptor existingDescriptor, Descriptor newDescriptor) {
//        Date d1 = (Date) existingDescriptor.getValue();
//        Date d2 = (Date) newDescriptor.getValue();
//        return d1.equals(d2);
//    }
}

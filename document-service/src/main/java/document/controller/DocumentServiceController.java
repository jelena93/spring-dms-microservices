/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.controller;

import document.repository.DocumentTypeRepository;
import document.dto.MessageDto;
import document.repository.DescriptorRepository;
import document.repository.DocumentRepository;
import document.domain.Document;
import document.domain.Descriptor;
import document.domain.DocumentType;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
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
    DocumentTypeRepository documentTypeRepository;
    @Autowired
    DescriptorRepository descriptorRepository;

    @RequestMapping(value = "/{company}/search", method = RequestMethod.GET)
    public List<Document> search(@PathVariable("company") Long company, String query) {
        if (query == null || query.isEmpty()) {
            return documentRepository.findAll();
        }
        return documentRepository.findByDescriptorsDescriptorKey(query);
    }

    @RequestMapping(value = "/document-types", method = RequestMethod.GET)
    public List<DocumentType> getDocumentTypes() {
        return documentTypeRepository.findAll();
    }

    @RequestMapping(value = "/document-types/{id}", method = RequestMethod.GET)
    public DocumentType findDocumentType(@PathVariable("id") long id) {
        return documentTypeRepository.findOne(id);
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
        DocumentType documentType = documentTypeRepository.findOne(docType);
        List<Descriptor> descriptors = documentType.getDescriptors();
        List<Descriptor> existingDescriptors = descriptorRepository.findByDocumentType(docType);
        int numberOfIdenticalDescriptors = 0;
        Long existingDocumentID = null;
        for (Descriptor descriptor : descriptors) {
            String key = descriptor.getDescriptorKey();
            String value = request.getParameter(key).trim();
            descriptor.setValue(value);
            if (descriptor.getValue() == null) {
                throw new Exception("Value for descriptor " + descriptor.getDescriptorKey()
                        + "  is not correct. Expecting descriptor of type " + descriptor.getDescriptorType().getStringMessageByParamClass() + ".");
            }
            Descriptor newDescriptor = new Descriptor(key, descriptor.getValue(), docType, descriptor.getDescriptorType());
            Long id = checkIfFileAlreadyAdded(existingDescriptors, newDescriptor, activityID, inputOutput);
            if (id == null) {
                continue;
            } else if (existingDocumentID == null) {
                existingDocumentID = id;
            } else if (!Objects.equals(id, existingDocumentID)) {
                continue;
            }
            numberOfIdenticalDescriptors += 1;
        }
        if (numberOfIdenticalDescriptors == descriptors.size() && existingDocumentID != null) {
            return new ResponseEntity<>(new MessageDto(MessageDto.MESSAGE_TYPE_QUESTION, existingDocumentID + ""), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDto(MessageDto.MESSAGE_TYPE_SUCCESS, "ok"), HttpStatus.OK);
    }

    private Long checkIfFileAlreadyAdded(List<Descriptor> existingDescriptors, Descriptor newDescriptor, long activityID, String inputOutput) {
        if (existingDescriptors == null || existingDescriptors.isEmpty()) {
            return null;
        }
        for (Descriptor existingDescriptor : existingDescriptors) {
            if (existingDescriptor.getValue() == null) {
                continue;
            }
            if (existingDescriptor.equals(newDescriptor)
                    || ((newDescriptor.getValue() instanceof Date && (existingDescriptor.getValue() instanceof Date)) && isTheSameDate(existingDescriptor, newDescriptor))) {
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
            }
        }
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

    private boolean isTheSameDate(Descriptor existingDescriptor, Descriptor newDescriptor) {
        Date d1 = (Date) existingDescriptor.getValue();
        Date d2 = (Date) newDescriptor.getValue();
        return d1.equals(d2);
    }

}

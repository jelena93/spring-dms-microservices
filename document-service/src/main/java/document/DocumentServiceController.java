/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author jelenas
 */
@RestController
@RequestMapping("/")
public class DocumentServiceController {

    @Autowired
    DocumentRepository documentRepository;

    @RequestMapping(value = "/{company}/search", method = RequestMethod.GET)
    public List<Document> search(@PathVariable("company") Long company, String query) {
        if (query == null || query.isEmpty()) {
            return documentRepository.findAll();
        }
        return documentRepository.findByDescriptorsDescriptorKey(query);
    }

    @RequestMapping(path = "/validation", method = RequestMethod.POST)
    public ResponseEntity<MessageDto> validation(Authentication authentication, HttpServletRequest request, MultipartHttpServletRequest req, long docType, long activityID, String inputOutput) throws Exception {
//        MultipartFile file = req.getFile("file");
//        UserDto userDto = (UserDto) authentication.getPrincipal();
//        User user = userService.findOne(userDto.getUsername());
//        DocumentDto document;
//        boolean sameName = false;
//        Long documentID = null;
//        List<DocumentDto> documents = findByName(user.getCompany().getId(), file.getOriginalFilename(), 1, 1);
//        System.out.println("@@@@@@ documents sameName " + documents);
//        if (!documents.isEmpty()) {
//            documentID = documents.get(0).getId();
//            sameName = true;
//        }
//        DocumentType documentType = documentTypeService.find(docType);
//        int numberOfExistingDescripotrs = 0;
//        int numberOfDefaultDescripotrs = 0;
//        for (Descriptor descriptor : documentType.getDescriptors()) {
//            if (descriptor.getValue() == null) {
//                String key = descriptor.getDescriptorKey();
//                String value = request.getParameter(key).trim();
//                descriptor.setValue(value);
//                if (descriptor.getValue() == null) {
//                    throw new Exception("Value for descriptor " + descriptor.getDescriptorKey()
//                            + "  is not correct. Expecting descriptor of type "
//                            + descriptor.getDescriptorType().getStringMessageByParamClass() + ".");
//                }
//                numberOfDefaultDescripotrs++;
//                document = findByDescriptors(user.getCompany().getId(), key, docType, descriptor.convertValueToString(), 10, 1);
//                if (document != null) {
//                    numberOfExistingDescripotrs++;
//                    documentID = document.getId();
//                }
//            }
//        }
//        if (sameName) {
//            return new ResponseEntity<>(new MessageDto(MessageDto.MESSAGE_TYPE_QUESTION,
//                    "Document with same name already exists. Do you want to rewrite it?", documentID, MessageDto.MESSAGE_ACTION_EDIT), HttpStatus.OK);
//        }
//        if (numberOfDefaultDescripotrs == numberOfExistingDescripotrs) {
//            return new ResponseEntity<>(new MessageDto(MessageDto.MESSAGE_TYPE_QUESTION,
//                    "Document with same descriptors already exists. Do you want to rewrite it?", documentID, MessageDto.MESSAGE_ACTION_EDIT), HttpStatus.OK);
//        }
        return new ResponseEntity<>(new MessageDto(MessageDto.MESSAGE_TYPE_SUCCESS, "ok"), HttpStatus.OK);
    }
}

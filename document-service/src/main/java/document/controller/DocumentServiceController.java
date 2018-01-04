package document.controller;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

import com.fasterxml.jackson.databind.ObjectMapper;
import document.command.DocumentCmd;
import document.domain.Document;
import document.dto.DocumentDto;
import document.elasticsearch.DocumentIndexer;
import document.elasticsearch.service.DocumentService;
import document.mapper.DocumentMapper;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@RestController
@RequestMapping("/")
public class DocumentServiceController {

    @Autowired
    DocumentMapper documentMapper;
    @Autowired
    DocumentIndexer documentIndexer;
    @Autowired
    DocumentService documentService;

    @GetMapping("/search")
    public List<DocumentDto> search(@RequestParam Long ownerId, @RequestParam(required = false) String query)
            throws IOException {
        SearchResponse searchResponse = documentService.searchDocumentsForOwner(ownerId, query, 10, 1);
        List<DocumentDto> documents = new ArrayList<>();
        System.out.println(searchResponse);
        ObjectMapper mapper = new ObjectMapper();
        for (SearchHit hit : searchResponse.getHits()) {
            documents.add(mapper.readValue(hit.getSourceAsString(), DocumentDto.class));
        }
        return documents;
    }

    @GetMapping("/all")
    public List<DocumentDto> all() throws IOException {
        SearchResponse searchResponse = documentService.getAllDocuments();
        List<DocumentDto> documents = new ArrayList<>();
        System.out.println(searchResponse);
        ObjectMapper mapper = new ObjectMapper();
        for (SearchHit hit : searchResponse.getHits()) {
            documents.add(mapper.readValue(hit.getSourceAsString(), DocumentDto.class));
        }
        return documents;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "multipart/form-data")
    public String addDocument(HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DocumentCmd documentCmd = mapper.readValue(request.getParameter("documentCmd"), DocumentCmd.class);
        System.out.println(documentCmd);
        Document document = documentMapper.mapToEntity(documentCmd);
        Part filePart = request.getPart("file");
        try {
            SearchResponse sr = documentService.getMaxId();
            Max max = sr.getAggregations().get("id");
            System.out.println("max: " + max.getValue());
            if (max.getValue() < 0) {
                document.setId(1L);
            } else {
                document.setId((long) max.getValue() + 1);
            }
        } catch (Exception e) {
            document.setId(1L);
            System.out.println("addDocument, no index - " + e.getMessage());
        }
        document.setContent(
                Base64.getUrlEncoder().encodeToString(StreamUtils.copyToByteArray(filePart.getInputStream())));
        documentIndexer.indexDocument(document);
        System.out.println(document);
        return document.getId() + "";
    }
    //    @PostMapping
    //    public String addDocument(@RequestParam MultipartFile file, @RequestParam Long ownerId,
    //                                   @RequestParam(required = false) List<Descriptor> descriptors) throws
    // Exception {
    //        Document document = new Document();
    //        try {
    //            SearchResponse sr = documentService.getMaxId();
    //            Max max = sr.getAggregations().get("latest");
    //            document.setId((long) max.getValue());
    //        } catch (Exception e) {
    //            document.setId(1L);
    //            System.out.println(e.getMessage());
    //        }
    //
    //        document.setOwnerId(ownerId);
    //        document.setFileName(file.getOriginalFilename());
    //        document.setDescriptors(descriptors);
    //        document.setContent(Base64.getUrlEncoder().encodeToString(file.getBytes()));
    //        documentIndexer.indexDocument(document);
    //        return " You successfully uploaded " + file.getOriginalFilename() + "!";
    //    }

    //    @RequestMapping(method = RequestMethod.POST)
    //    public List<Descriptor> addDescriptors(@RequestBody List<Descriptor>
    // descriptors) {
    //        System.out.println("adding " + descriptors);
    //        return descriptorRepository.save(descriptors);
    //    }

    //    @RequestMapping(value = "/{company}/search", method = RequestMethod.GET)
    //    public List<Document> search(@PathVariable("company") Long company, String query) {
    //        if (query == null || query.isEmpty()) {
    //            return documentRepository.findAll();
    //        }
    //        return documentRepository.findAll();
    ////        return documentRepository.findByDescriptorsDescriptorKey(query);
    //    }
    //
    //    @RequestMapping(value = "/add", method = RequestMethod.POST)
    //    public Document addDocument(@RequestBody Document descriptor) {
    //        return documentRepository.save(descriptor);
    //    }
    //
    //    @RequestMapping(value = "/ids")
    //    public List<Document> findDocumentByIds(@RequestBody List<Long> ids) {
    //        List<Document> documents = documentRepository.findByIdIn(ids);
    //        return documents;
    //    }

    //    @RequestMapping(value = "/search/{companyId}", method = RequestMethod.GET)
    //    public List<Document> getDocumentTypes(@PathVariable("companyId") Long companyId, String query) {
    //        if (query == null || query.isEmpty()) {
    //            return documentRepository.findByCompanyId(companyId);
    //        }
    //        return documentRepository.findByCompanyId(companyId);
    //    }

    //    @RequestMapping(path = "/validation", method = RequestMethod.POST)
    //    public ResponseEntity<MessageDto> checkIfDocumentExists(HttpServletRequest request, long docType, long
    // activityID, String inputOutput) throws Exception {
    //        DocumentType documentType = documentTypeRepository.findOne(docType);
    //        List<Descriptor> descriptors = documentType.getDescriptors();
    //        List<Descriptor> existingDescriptors = descriptorRepository.findByDocumentType(docType);
    //        int numberOfIdenticalDescriptors = 0;
    //        Long existingDocumentID = null;
    //        for (Descriptor descriptor : descriptors) {
    //            String key = descriptor.getDescriptorKey();
    //            String value = request.getParameter(key).trim();
    //            descriptor.setValue(value);
    //            if (descriptor.getValue() == null) {
    //                throw new Exception("Value for descriptor " + descriptor.getDescriptorKey()
    //                        + "  is not correct. Expecting descriptor of type " + descriptor.getDescriptorType()
    // .getStringMessageByParamClass() + ".");
    //            }
    //            Descriptor newDescriptor = new Descriptor(key, descriptor.getValue(), docType, descriptor
    // .getDescriptorType());
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
    //            return new ResponseEntity<>(new MessageDto(MessageDto.MESSAGE_TYPE_QUESTION, existingDocumentID +
    // ""), HttpStatus.OK);
    //        }
    //        return new ResponseEntity<>(new MessageDto(MessageDto.MESSAGE_TYPE_SUCCESS, "ok"), HttpStatus.OK);
    //    }

    //    private Long checkIfFileAlreadyAdded(List<Descriptor> existingDescriptors, Descriptor newDescriptor, long
    // activityID, String inputOutput) {
    //        if (existingDescriptors == null || existingDescriptors.isEmpty()) {
    //            return null;
    //        }
    //        for (Descriptor existingDescriptor : existingDescriptors) {
    //            if (existingDescriptor.getValue() == null) {
    //                continue;
    //            }
    //            if (existingDescriptor.equals(newDescriptor)
    //                    || ((newDescriptor.getValue() instanceof Date && (existingDescriptor.getValue() instanceof
    // Date)) && isTheSameDate(existingDescriptor, newDescriptor))) {
    //                Activity activity = activityRepository.findOne(activityID);
    //                if (inputOutput.equals("input")) {
    //                    for (Document descriptor : activity.getInputList()) {
    //                        if (descriptor.getDescriptors().contains(existingDescriptor)) {
    //                            return descriptor.getId();
    //                        } else if (inputOutput.equals("output")) {
    //                            for (Document d : activity.getOutputList()) {
    //                                if (d.getDescriptors().contains(existingDescriptor)) {
    //                                    return descriptor.getId();
    //                                }
    //                            }
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //        return null;
    //    }

    //    @ExceptionHandler(Exception.class)
    //    public ResponseEntity<MessageDto> handleError(Exception ex, WebRequest request) {
    //        ex.printStackTrace();
    //        return new ResponseEntity<>(new MessageDto(MessageDto.MESSAGE_TYPE_ERROR, ex.getMessage()),
    //                                    HttpStatus.BAD_REQUEST);
    //    }

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
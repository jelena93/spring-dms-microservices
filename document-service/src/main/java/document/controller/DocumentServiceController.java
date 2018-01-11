package document.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import document.command.DocumentCmd;
import document.domain.Document;
import document.dto.DocumentDto;
import document.elasticsearch.DocumentIndexer;
import document.elasticsearch.service.DocumentService;
import document.mapper.DocumentMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@RestController
@RequestMapping("/")
public class DocumentServiceController {

    private final DocumentMapper documentMapper;
    private final DocumentIndexer documentIndexer;
    private final DocumentService documentService;

    @Autowired
    public DocumentServiceController(DocumentMapper documentMapper, DocumentIndexer documentIndexer,
                                     DocumentService documentService) {
        this.documentMapper = documentMapper;
        this.documentIndexer = documentIndexer;
        this.documentService = documentService;
    }

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
        System.out.println(searchResponse.getHits());
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

    @GetMapping(path = "/{ownerId}/{documentId}")
    public String showFile(@PathVariable long ownerId, @PathVariable long documentId) throws IOException {
        SearchResponse searchResponse = documentService.findOne(ownerId, documentId);
        ObjectMapper mapper = new ObjectMapper();
        for (SearchHit hit : searchResponse.getHits()) {
            DocumentDto documentDto = mapper.readValue(hit.getSourceAsString(), DocumentDto.class);
            return documentDto.getAttachment().getContent();
        }
        return null;
    }
    //    @RequestMapping(path = "/document/{id}", method = RequestMethod.GET)
    //    public ResponseEntity<byte[]> showFile(@PathVariable("id") long id) {
    //        Document document = documentService.findOne(id);
    //        if (document == null) {
    //            throw new CustomException("There is no document with id " + id, "400");
    //        }
    //        HttpHeaders header = new HttpHeaders();
    //        header.setContentType(MediaType.valueOf(document.getFileType()));
    //        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + document.getFileName());
    //        header.setContentLength(document.getFileContent().length);
    //        return new ResponseEntity<>(document.getFileContent(), header, HttpStatus.OK);
    //    }
    //    @RequestMapping(path = "/document/download/{id}", method = RequestMethod.GET)
    //    public ResponseEntity<byte[]> downloadFile(@PathVariable long ownerId, @PathVariable long documentId) {
    //        Document document = documentService.findOne(id);
    //        HttpHeaders header = new HttpHeaders();
    //        header.setContentType(MediaType.valueOf(document.getFileType()));
    //        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getFileName());
    //        header.setContentLength(document.getFileContent().length);
    //        return new ResponseEntity<>(document.getFileContent(), header, HttpStatus.OK);
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
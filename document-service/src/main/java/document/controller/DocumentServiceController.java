package document.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import document.command.DocumentCmd;
import document.domain.Document;
import document.dto.DocumentDto;
import document.elasticsearch.DocumentIndexer;
import document.elasticsearch.service.DocumentService;
import document.mapper.DocumentMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<DocumentDto> search(@RequestParam Long ownerId, @RequestParam(required = false) String query) {
        List<DocumentDto> documents = new ArrayList<>();
        try {
            SearchResponse searchResponse = documentService.searchDocumentsForOwner(ownerId, query, 10, 1);
            System.out.println(searchResponse);
            ObjectMapper mapper = new ObjectMapper();
            for (SearchHit hit : searchResponse.getHits()) {
                documents.add(mapper.readValue(hit.getSourceAsString(), DocumentDto.class));
            }
            System.out.println(searchResponse.getHits());
        } catch (Exception e) {
            System.out.println("search " + e.getMessage());
        }
        return documents;
    }

    @GetMapping("/all")
    public List<DocumentDto> all() {
        List<DocumentDto> documents = new ArrayList<>();
        try {
            SearchResponse searchResponse = documentService.getAllDocuments();
            System.out.println(searchResponse);
            ObjectMapper mapper = new ObjectMapper();
            for (SearchHit hit : searchResponse.getHits()) {
                documents.add(mapper.readValue(hit.getSourceAsString(), DocumentDto.class));
            }
        } catch (Exception e) {
            System.out.println("all " + e.getMessage());
        }

        return documents;
    }

    @PostMapping()
    public ResponseEntity<String> addDocument(HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DocumentCmd documentCmd = mapper.readValue(request.getParameter("documentCmd"), DocumentCmd.class);
        System.out.println(documentCmd);
        Document document = documentMapper.mapToEntity(documentCmd);
        Part filePart = request.getPart("file");
        document.setFile(ByteStreams.toByteArray(filePart.getInputStream()));
        document.setContent(
                Base64.getUrlEncoder().encodeToString(StreamUtils.copyToByteArray(filePart.getInputStream())));
        documentIndexer.indexDocument(document);
        System.out.println("saved " + document);
        return new ResponseEntity<>(String.valueOf(document.getId()), HttpStatus.OK);
    }

    @GetMapping("/download/{ownerId}/{documentId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable long ownerId, @PathVariable long documentId)
            throws IOException {
        SearchResponse searchResponse = documentService.findOne(ownerId, documentId);
        ObjectMapper mapper = new ObjectMapper();
        DocumentDto documentDto = new DocumentDto();
        if (searchResponse.getHits().getHits().length > 0) {
            documentDto = mapper
                    .readValue(searchResponse.getHits().getHits()[0].getSourceAsString(), DocumentDto.class);
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(documentDto.getAttachment().getContentType()));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + documentDto.getFileName());
        header.setContentLength(documentDto.getFile().length);
        return new ResponseEntity<>(documentDto.getFile(), header, HttpStatus.OK);
    }

    @GetMapping(path = "/{ownerId}/{documentId}")
    public ResponseEntity<byte[]> showFile(@PathVariable long ownerId, @PathVariable long documentId)
            throws IOException {
        SearchResponse searchResponse = documentService.findOne(ownerId, documentId);
        DocumentDto documentDto = new DocumentDto();
        ObjectMapper mapper = new ObjectMapper();
        if (searchResponse.getHits().getHits().length > 0) {
            documentDto = mapper
                    .readValue(searchResponse.getHits().getHits()[0].getSourceAsString(), DocumentDto.class);
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(documentDto.getAttachment().getContentType()));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + documentDto.getFileName());
        header.setContentLength(documentDto.getFile().length);
        return new ResponseEntity<>(documentDto.getFile(), header, HttpStatus.OK);
    }

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

}
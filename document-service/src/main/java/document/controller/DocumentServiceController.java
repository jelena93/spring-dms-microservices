package document.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import document.command.DocumentCmd;
import document.command.DocumentValidationCmd;
import document.domain.Descriptor;
import document.domain.Document;
import document.dto.DocumentDto;
import document.dto.MessageDto;
import document.elasticsearch.DocumentIndexer;
import document.elasticsearch.service.DocumentService;
import document.mapper.DocumentMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/")
public class DocumentServiceController {

    private final DocumentMapper documentMapper;
    private final DocumentIndexer documentIndexer;
    private final DocumentService documentService;

    @Autowired
    public DocumentServiceController(
            DocumentMapper documentMapper, DocumentIndexer documentIndexer, DocumentService documentService) {
        this.documentMapper = documentMapper;
        this.documentIndexer = documentIndexer;
        this.documentService = documentService;
    }

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam Long ownerId, @RequestParam(required = false) String query,
                                      @RequestParam int limit,
                                      @RequestParam int page,
                                      OAuth2Authentication oAuth2Authentication) throws Exception {
        checkUser(ownerId, oAuth2Authentication);
        Map<String, Object> map = new HashMap<>();
        try {
            SearchResponse searchResponse = documentService.searchDocumentsForOwner(ownerId, query, limit, page);
            List<DocumentDto> dtos = mapToDocumentList(searchResponse);
            map.put("documents", dtos);
            map.put("total", searchResponse.getHits().getTotalHits());
        } catch (IndexNotFoundException e) {
            System.out.println("search error" + e.getMessage());
        }
        return map;
    }

    @GetMapping("/{ownerId}/all")
    public List<DocumentDto> all(@PathVariable long ownerId, OAuth2Authentication oAuth2Authentication) throws Exception {
        checkUser(ownerId, oAuth2Authentication);
        try {
            return mapToDocumentList(documentService.findAll(ownerId));
        } catch (IndexNotFoundException e) {
            System.out.println("all " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @PostMapping()
    public ResponseEntity<String> addDocument(HttpServletRequest request, OAuth2Authentication oAuth2Authentication) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DocumentCmd documentCmd = mapper.readValue(request.getParameter("documentCmd"), DocumentCmd.class);
        System.out.println(documentCmd);
        checkUser(documentCmd.getOwnerId(), oAuth2Authentication);
        Document document = documentMapper.mapToEntity(documentCmd);
        Part filePart = request.getPart("file");
        document.setFile(ByteStreams.toByteArray(filePart.getInputStream()));
        document.setContent(
                Base64.getUrlEncoder().encodeToString(StreamUtils.copyToByteArray(filePart.getInputStream())));
        documentIndexer.save(document);
        System.out.println("saved " + document);
        return new ResponseEntity<>(String.valueOf(document.getId()), HttpStatus.OK);
    }

    @GetMapping("/download/{ownerId}/{documentId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable long ownerId,
                                               @PathVariable long documentId,
                                               OAuth2Authentication oAuth2Authentication) throws Exception {
        checkUser(ownerId, oAuth2Authentication);
        SearchResponse searchResponse = documentService.findOne(ownerId, documentId);
        ObjectMapper mapper = new ObjectMapper();
        DocumentDto documentDto = new DocumentDto();
        if (searchResponse.getHits().getHits().length > 0) {
            documentDto = mapper.readValue(
                    searchResponse.getHits().getHits()[0].getSourceAsString(), DocumentDto.class);
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(documentDto.getAttachment().getContentType()));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + documentDto.getFileName());
        header.setContentLength(documentDto.getFile().length);
        return new ResponseEntity<>(documentDto.getFile(), header, HttpStatus.OK);
    }

    @GetMapping(path = "/{ownerId}/{documentId}")
    public ResponseEntity<byte[]> showFile(@PathVariable long ownerId,
                                           @PathVariable long documentId,
                                           OAuth2Authentication oAuth2Authentication) throws Exception {
        checkUser(ownerId, oAuth2Authentication);
        SearchResponse searchResponse = documentService.findOne(ownerId, documentId);
        DocumentDto documentDto = new DocumentDto();
        ObjectMapper mapper = new ObjectMapper();
        if (searchResponse.getHits().getHits().length > 0) {
            documentDto = mapper.readValue(
                    searchResponse.getHits().getHits()[0].getSourceAsString(), DocumentDto.class);
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(documentDto.getAttachment().getContentType()));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + documentDto.getFileName());
        header.setContentLength(documentDto.getFile().length);
        return new ResponseEntity<>(documentDto.getFile(), header, HttpStatus.OK);
    }

    @PostMapping("/validation")
    public MessageDto validation(HttpServletRequest request) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Part filePart = request.getPart("file");
            DocumentValidationCmd documentValidationCmd = new DocumentValidationCmd();
            documentValidationCmd.setOwnerId(Long.valueOf(request.getParameter("ownerId")));
            documentValidationCmd.setDocumentTypeId(Long.valueOf(request.getParameter("documentTypeId")));
            documentValidationCmd.setFileName(filePart.getSubmittedFileName());
            documentValidationCmd.setDescriptors(
                    Arrays.asList(mapper.readValue(request.getParameter("descriptors"), Descriptor[].class)));
            System.out.println("validation  " + documentValidationCmd.toString());
            //@TODO same name -
            boolean sameName = false;
            Long documentId = null;
            List<DocumentDto> documentsWithSameName = mapToDocumentList(
                    documentService.findByName(documentValidationCmd.getOwnerId(), documentValidationCmd.getFileName()));
            System.out.println("documents sameName " + documentsWithSameName);
            if (!documentsWithSameName.isEmpty()) {
                //ne mora id
                documentId = documentsWithSameName.get(0).getId();
                return new MessageDto("Document with same name already exists. Do you want to rewrite it?", documentId);
            }
            //@TODO same descriptors
            List<DocumentDto> documentsWithSameDescriptors = mapToDocumentList(
                    documentService.findDocumentsForOwnerByDescriptors(documentValidationCmd, 1, 1));
            if (!documentsWithSameDescriptors.isEmpty()) {
                documentId = documentsWithSameDescriptors.get(0).getId();
                return new MessageDto("Document with same descriptors already exists. Do you want to save rewrite it?",
                        documentId);
            }
            return new MessageDto("ok", null);
        } catch (IndexNotFoundException ex) {
            return new MessageDto("ok", null);
        }
    }

    private static List<DocumentDto> mapToDocumentList(SearchResponse searchResponse) throws IOException {
        List<DocumentDto> documents = new ArrayList<>();
        System.out.println(searchResponse);
        ObjectMapper mapper = new ObjectMapper();
        for (SearchHit hit : searchResponse.getHits()) {
            documents.add(mapper.readValue(hit.getSourceAsString(), DocumentDto.class));
        }
        System.out.println("total hits: " + searchResponse.getHits().getTotalHits());
        return documents;
    }

    private static void checkUser(Long ownerId, OAuth2Authentication oAuth2Authentication) throws Exception {
        Map<String, Object> details = (Map<String, Object>) oAuth2Authentication.getUserAuthentication().getDetails();
        Map<String, Object> principal = (Map<String, Object>) details.get("principal");
        System.out.println(principal.get("companyId"));
        if (ownerId != Long.valueOf(principal.get("companyId").toString())) {
            throw new Exception("Not allowed");
        }
    }
}
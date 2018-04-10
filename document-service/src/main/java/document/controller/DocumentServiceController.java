package document.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import document.command.DocumentCmd;
import document.domain.Document;
import document.dto.DocumentDto;
import document.elasticsearch.DocumentIndexer;
import document.elasticsearch.ElasticSearchMapper;
import document.elasticsearch.service.DocumentService;
import document.mapper.DocumentMapper;
import document.validator.DocumentValidator;
import org.apache.commons.codec.binary.Base64;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.IndexNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class DocumentServiceController {

    private final DocumentMapper documentMapper;
    private final DocumentIndexer documentIndexer;
    private final DocumentService documentService;
    private final DocumentValidator documentValidator;

    @Autowired
    public DocumentServiceController(
            DocumentMapper documentMapper, DocumentIndexer documentIndexer, DocumentService documentService,
            DocumentValidator documentValidator) {
        this.documentMapper = documentMapper;
        this.documentIndexer = documentIndexer;
        this.documentService = documentService;
        this.documentValidator = documentValidator;
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
            List<DocumentDto> dtos = ElasticSearchMapper.mapToDocumentList(searchResponse);
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
            return ElasticSearchMapper.mapToDocumentList(documentService.findAll(ownerId));
        } catch (IndexNotFoundException e) {
            System.out.println("all " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @PostMapping
    public ResponseEntity<Long> addDocument(HttpServletRequest request, OAuth2Authentication oAuth2Authentication) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DocumentCmd documentCmd = mapper.readValue(request.getParameter("documentCmd"), DocumentCmd.class);
        System.out.println(documentCmd);
        checkUser(documentCmd.getOwnerId(), oAuth2Authentication);

        Document document = documentMapper.mapToEntity(documentCmd);

        Part filePart = request.getPart("file");
        document.setFile(ByteStreams.toByteArray(filePart.getInputStream()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        InputStream input = filePart.getInputStream();
        int read = -1;
        while ((read = input.read()) != -1) {
            output.write(read);
        }
        input.close();
        String content = Base64.encodeBase64String(output.toByteArray());
        document.setContent(content);

        documentValidator.validate(document);

        documentIndexer.save(document);
        System.out.println("sending " + document.getId());
        return new ResponseEntity<>(document.getId(), HttpStatus.OK);
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

    private static void checkUser(Long ownerId, OAuth2Authentication oAuth2Authentication) throws Exception {
        Map<String, Object> details = (Map<String, Object>) oAuth2Authentication.getUserAuthentication().getDetails();
        Map<String, Object> principal = (Map<String, Object>) details.get("principal");
        System.out.println(principal.get("companyId"));
        if (ownerId != Long.valueOf(principal.get("companyId").toString())) {
            throw new Exception("Not allowed");
        }
    }
}
package descriptor.controller;

import descriptor.domain.Descriptor;
import descriptor.domain.DocumentType;
import descriptor.dto.DescriptorDto;
import descriptor.dto.DocumentCmd;
import descriptor.dto.DocumentTypeDto;
import descriptor.mapper.DocumentTypeMapper;
import descriptor.messaging.DocumentMessagingDto;
import descriptor.messaging.DocumentMessagingService;
import descriptor.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class DescriptorServiceController {
    private final DocumentTypeService documentTypeService;
    private final DocumentMessagingService documentMessagingService;
    private final DocumentTypeMapper documentTypeMapper;
    private final OAuth2RestTemplate auth2RestTemplate;

    @Autowired
    public DescriptorServiceController(DocumentTypeService documentTypeService,
                                       DocumentMessagingService documentMessagingService,
                                       DocumentTypeMapper documentTypeMapper, OAuth2RestTemplate auth2RestTemplate) {
        this.documentTypeService = documentTypeService;
        this.documentMessagingService = documentMessagingService;
        this.documentTypeMapper = documentTypeMapper;
        this.auth2RestTemplate = auth2RestTemplate;
    }

    @PreAuthorize("hasRole('ROLE_UPLOADER')")
    @PostMapping("/upload")
    public String uploadDocument(DocumentCmd documentCmd, HttpServletRequest request) throws Exception {
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.MULTIPART_FORM_DATA);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(acceptableMediaTypes);
        DocumentType documentType = documentTypeService.findOne(documentCmd.getDocumentType());
        List<Descriptor> descriptors = documentType.getDescriptors();
        System.out.println(request.getParameterMap());
        List<DescriptorDto> descriptorDtos = descriptors.stream().filter(descriptor -> descriptor.getValue() == null).
                map(descriptor -> new DescriptorDto(descriptor.getDescriptorKey(),
                                                    request.getParameter(descriptor.getDescriptorKey()).trim(),
                                                    descriptor.getDescriptorType().getParamClass()))
                                                        .collect(Collectors.toList());
        documentCmd.setDescriptors(descriptorDtos);
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();//
        valueMap.add("file", new ByteArrayResource(documentCmd.getFile().getBytes()));
        documentCmd.setFileName(documentCmd.getFile().getOriginalFilename());
        valueMap.add("documentCmd", documentCmd);
        System.out.println(documentCmd);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(valueMap, headers);
        String documentId = auth2RestTemplate.postForObject("http://document-service/", entity, String.class);
        System.out.println("document id " + documentId);
        documentMessagingService.sendDocumentAdded(
                new DocumentMessagingDto(Long.valueOf(documentId), documentCmd.isInput(), documentCmd.getActivityId()));
        return documentId;
    }

    @GetMapping("/document-type/all")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_USER')")
    public List<DocumentTypeDto> getAllDocumentTypes() {
        return documentTypeMapper.mapToModelList(documentTypeService.findAll());
    }

    @GetMapping("/document-type/{id}")
    //    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_UPLOADER')")
    public DocumentTypeDto getDocumentTypeById(@PathVariable long id) {
        return documentTypeMapper.mapToModel(documentTypeService.findOne(id));
    }

    @PostMapping("/document-type")
    //    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_UPLOADER')")
    public DocumentTypeDto addDocumentType(@RequestBody DocumentTypeDto documentTypeDto) {
        DocumentType documentType = documentTypeMapper.mapToEntity(documentTypeDto);
        return documentTypeMapper.mapToModel(documentTypeService.save(documentType));
    }

}
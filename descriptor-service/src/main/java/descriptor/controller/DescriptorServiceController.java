package descriptor.controller;

import descriptor.domain.Descriptor;
import descriptor.domain.DocumentType;
import descriptor.dto.DescriptorDto;
import descriptor.dto.DocumentCmd;
import descriptor.dto.DocumentTypeDto;
import descriptor.mapper.DescriptorMapper;
import descriptor.mapper.DocumentTypeMapper;
import descriptor.messaging.output.DocumentMessagingService;
import descriptor.messaging.output.dto.DocumentMessagingDto;
import descriptor.service.DescriptorService;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class DescriptorServiceController {
    private final DocumentTypeService documentTypeService;
    private final DescriptorService descriptorService;
    private final DocumentMessagingService documentMessagingService;
    private final DocumentTypeMapper documentTypeMapper;
    private final DescriptorMapper descriptorMapper;
    private final OAuth2RestTemplate auth2RestTemplate;

    @Autowired
    public DescriptorServiceController(DocumentTypeService documentTypeService, DescriptorService descriptorService,
                                       DocumentMessagingService documentMessagingService,
                                       DocumentTypeMapper documentTypeMapper, DescriptorMapper descriptorMapper,
                                       OAuth2RestTemplate auth2RestTemplate) {
        this.documentTypeService = documentTypeService;
        this.descriptorService = descriptorService;
        this.documentMessagingService = documentMessagingService;
        this.documentTypeMapper = documentTypeMapper;
        this.descriptorMapper = descriptorMapper;
        this.auth2RestTemplate = auth2RestTemplate;
    }

    @PreAuthorize("hasRole('ROLE_UPLOADER')")
    @PostMapping("/upload")
    public String uploadDocument(DocumentCmd documentCmd, HttpServletRequest request) throws Exception {
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.MULTIPART_FORM_DATA);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(acceptableMediaTypes);
        DocumentType documentType = documentTypeService.findOne(documentCmd.getDocumentTypeId());
        List<Descriptor> descriptors = documentType.getDescriptors();
        List<DescriptorDto> descriptorDtos = descriptors.stream()
                .filter(descriptor -> descriptor.getDocumentId() == null).
                        map(descriptor -> new DescriptorDto(
                                documentCmd.getDocumentTypeId(),
                                descriptor.getDescriptorKey(),
                                request.getParameter(
                                        descriptor.getDescriptorKey()).trim(),
                                descriptor.getDescriptorType().getParamClass()))
                .collect(Collectors.toList());
        List<Descriptor> newDescriptors = descriptors.stream().filter(descriptor -> descriptor.getDocumentId() == null).
                map(descriptor -> new Descriptor(descriptor.getDescriptorKey(),
                        request.getParameter(descriptor.getDescriptorKey()).trim(),
                        descriptor.getDocumentType(), descriptor.getDescriptorType()))
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
        newDescriptors.forEach(descriptor -> descriptor.setDocumentId(Long.valueOf(documentId)));
        descriptorService.save(newDescriptors);
        documentMessagingService.sendDocumentAdded(
                new DocumentMessagingDto(Long.valueOf(documentId), documentCmd.isInput(), documentCmd.getActivityId()));
        return documentId;
    }

    @GetMapping("/document-type/all")
//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_USER')")
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
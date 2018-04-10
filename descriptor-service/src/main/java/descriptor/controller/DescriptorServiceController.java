package descriptor.controller;

import descriptor.command.DescriptorCmd;
import descriptor.command.DocumentCmd;
import descriptor.command.DocumentTypeCmd;
import descriptor.domain.Descriptor;
import descriptor.domain.DocumentType;
import descriptor.dto.DocumentTypeDto;
import descriptor.mapper.DocumentTypeMapper;
import descriptor.messaging.output.DocumentMessagingService;
import descriptor.messaging.output.dto.DocumentMessagingDto;
import descriptor.service.DescriptorService;
import descriptor.service.DocumentTypeService;
import descriptor.validator.DescriptorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    private final OAuth2RestTemplate auth2RestTemplate;
    private final DescriptorValidator descriptorValidator;

    @Autowired
    public DescriptorServiceController(DocumentTypeService documentTypeService, DescriptorService descriptorService,
                                       DocumentMessagingService documentMessagingService,
                                       DocumentTypeMapper documentTypeMapper,
                                       OAuth2RestTemplate auth2RestTemplate, DescriptorValidator descriptorValidator) {
        this.documentTypeService = documentTypeService;
        this.descriptorService = descriptorService;
        this.documentMessagingService = documentMessagingService;
        this.documentTypeMapper = documentTypeMapper;
        this.auth2RestTemplate = auth2RestTemplate;
        this.descriptorValidator = descriptorValidator;
    }

    @PreAuthorize("hasRole('ROLE_UPLOADER')")
    @PostMapping("/upload")
    public String uploadDocument(@Valid DocumentCmd documentCmd, HttpServletRequest request) throws Exception {
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(acceptableMediaTypes);

        List<Descriptor> defaultDescriptorsForDocumentType = descriptorService.findDefaultDescriptorsForDocumentType(documentCmd.getDocumentTypeId());

        System.out.println("defaultDescriptorsForDocumentType " + defaultDescriptorsForDocumentType);

        List<DescriptorCmd> descriptorsCmd = defaultDescriptorsForDocumentType.stream()
                .map(descriptor -> new DescriptorCmd(
                        documentCmd.getDocumentTypeId(),
                        descriptor.getDescriptorKey(),
                        request.getParameter(
                                descriptor.getDescriptorKey()).trim()))
                .collect(Collectors.toList());

        List<Descriptor> newDescriptors = defaultDescriptorsForDocumentType.stream()
                .map(descriptor -> new Descriptor(descriptor.getDescriptorKey(),
                        request.getParameter(descriptor.getDescriptorKey()).trim(),
                        descriptor.getDocumentType(), descriptor.getDescriptorType()))
                .collect(Collectors.toList());

        documentCmd.getDescriptors().addAll(descriptorsCmd);

        descriptorValidator.validate(newDescriptors);

        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();//
        valueMap.add("file", new ByteArrayResource(documentCmd.getFile().getBytes()));
        documentCmd.setFileName(documentCmd.getFile().getOriginalFilename());
        valueMap.add("documentCmd", documentCmd);
        System.out.println(documentCmd);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(valueMap, headers);
        ResponseEntity<Object> response = auth2RestTemplate.postForEntity("http://document-service/", entity, Object.class);
        System.out.println(" *** response " + response);

        Long documentId = Long.valueOf(response.getBody().toString());

        newDescriptors.forEach(descriptor -> descriptor.setDocumentId(Long.valueOf(documentId)));
        descriptorService.save(newDescriptors);

        documentMessagingService.sendDocumentAdded(
                new DocumentMessagingDto(Long.valueOf(documentId), documentCmd.isInput(), documentCmd.getActivityId()));
        return response.getBody().toString();
    }

    @GetMapping("/document-type/all")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_UPLOADER')")
    public List<DocumentTypeDto> getAllDocumentTypes() {
        return documentTypeMapper.mapToModelList(documentTypeService.findAll());
    }

    @PostMapping("/document-type")
    @PreAuthorize("hasRole('ROLE_USER')")
    public DocumentTypeDto addDocumentType(@RequestBody DocumentTypeCmd documentTypeCmd) {
        DocumentType documentType = documentTypeMapper.mapToEntity(documentTypeCmd);
        return documentTypeMapper.mapToModel(documentTypeService.save(documentType));
    }

}
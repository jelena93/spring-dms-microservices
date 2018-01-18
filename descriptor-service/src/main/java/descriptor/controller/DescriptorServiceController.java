package descriptor.controller;

import descriptor.domain.Descriptor;
import descriptor.domain.DocumentType;
import descriptor.dto.DescriptorDto;
import descriptor.dto.DocumentCmd;
import descriptor.dto.DocumentTypeDto;
import descriptor.mapper.DocumentTypeMapper;
import descriptor.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class DescriptorServiceController {
    private final DocumentTypeService documentTypeService;
    private final DocumentTypeMapper documentTypeMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public DescriptorServiceController(DocumentTypeService documentTypeService, DocumentTypeMapper documentTypeMapper,
                                       RestTemplate restTemplate) {
        this.documentTypeService = documentTypeService;
        this.documentTypeMapper = documentTypeMapper;
        this.restTemplate = restTemplate;
    }

    @PreAuthorize("hasRole('ROLE_UPLOADER')")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadImage(DocumentCmd documentCmd, HttpServletRequest request) throws Exception {
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.MULTIPART_FORM_DATA);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(acceptableMediaTypes);
        DocumentType documentType = documentTypeService.findOne(documentCmd.getDocumentType());
        List<Descriptor> descriptors = documentType.getDescriptors();
        List<DescriptorDto> newDescriptors = new ArrayList<>();
        System.out.println(request.getParameterMap());
        for (Descriptor descriptor : descriptors) {
            if (descriptor.getValue() == null) {
                String key = descriptor.getDescriptorKey();
                System.out.println("key: " + key);
                String value = request.getParameter(key).trim();
                descriptor.setValue(value);
                System.out.println("value: " + value);
                //                Descriptor newDescriptor = new Descriptor(key, descriptor.getValue(), documentType,
                //                                                          descriptor.getDescriptorType());
                DescriptorDto newDescriptor = new DescriptorDto();
                newDescriptor.setDescriptorValue(value);
                newDescriptor.setDescriptorKey(descriptor.getDescriptorKey());
                newDescriptors.add(newDescriptor);
            }
        }
        documentCmd.setDescriptors(newDescriptors);
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();//
        valueMap.add("file", new ByteArrayResource(documentCmd.getFile().getBytes()));
        documentCmd.setFileName(documentCmd.getFile().getOriginalFilename());
        valueMap.add("documentCmd", documentCmd);
        System.out.println(documentCmd);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(valueMap, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .exchange("http://document-service/", HttpMethod.POST, entity, String.class);
        return responseEntity.toString();
    }

    //    @PostMapping("/document")
    //    public String asd(MultipartFile file, HttpServletRequest request) {
    //        DocumentCmd doc = new DocumentCmd();
    //        doc.setFile(file);
    //        doc.setOwnerId(1L);
    //        List<Descriptor> asd = new ArrayList<>();
    //        Descriptor d = new Descriptor();
    //        d.setDescriptorKey("key");
    //        d.setValue(request.getParameter("key"));
    //        request.getParameter("key");
    //        doc.setDescriptors(asd);
    //        DocumentCmd documentCmd = restTemplate.postForObject("http://document-service/", doc, DocumentCmd.class);
    //        return documentCmd.getOwnerId() + "";
    //    }

    @GetMapping("/document-type/all")
    @PreAuthorize("hasRole('ROLE_USER')")
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
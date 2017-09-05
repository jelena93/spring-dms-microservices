/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.service;

import gateway.dto.Descriptor;
import gateway.dto.DocumentType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

@Service
public class DescriptorService {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;
    private final String DESCRIPTOR_SERVICE = "http://descriptor-service/";

    public List<DocumentType> findAll() {
        DocumentType[] documentTypes = oAuth2RestTemplate.
                getForObject(DESCRIPTOR_SERVICE, DocumentType[].class);
        return Arrays.asList(documentTypes);
    }

    public List<DocumentType> findByIds(List<Long> ids) {
        DocumentType[] documentTypes = oAuth2RestTemplate.
                postForObject(DESCRIPTOR_SERVICE + "/ids", ids, DocumentType[].class);
        return Arrays.asList(documentTypes);
    }

    public DocumentType findOne(long documentTypeId) {
        DocumentType documentType = oAuth2RestTemplate.
                getForObject(DESCRIPTOR_SERVICE + "/" + documentTypeId, DocumentType.class);
        return documentType;
    }

    public void updateDocumentType(DocumentType documentType) {
        oAuth2RestTemplate.put(DESCRIPTOR_SERVICE, documentType);
    }

    public List<Long> save(List<Descriptor> descriptors) {
        return Arrays.asList(oAuth2RestTemplate.
                postForObject(DESCRIPTOR_SERVICE, descriptors, Descriptor[].class)).stream()
                .map(Descriptor::getId).collect(Collectors.toList());
    }
}

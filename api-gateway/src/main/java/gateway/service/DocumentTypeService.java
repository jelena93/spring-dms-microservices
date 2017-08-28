/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.service;

import gateway.dto.DocumentType;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author jelena
 */
@Service
public class DocumentTypeService {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;
    private final String DOCUMENT_TYPE_SERVICE = "http://document-type-service/";

    public List<DocumentType> findAll() {
        DocumentType[] documentTypes = oAuth2RestTemplate.
                getForObject(DOCUMENT_TYPE_SERVICE, DocumentType[].class);
        System.out.println("document-types " + Arrays.toString(documentTypes));
        return Arrays.asList(documentTypes);
    }

    public DocumentType findOne(long documentTypeId) {
        DocumentType documentType = oAuth2RestTemplate.
                getForObject(DOCUMENT_TYPE_SERVICE + "/" + documentTypeId, DocumentType.class);
        return documentType;
    }
}

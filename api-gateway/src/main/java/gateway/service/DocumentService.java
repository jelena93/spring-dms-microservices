/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.service;

import gateway.dto.Document;
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
public class DocumentService {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;
    private final String DOCUMENT_SERVICE = "http://document-service/";

    public Document findOne(long documentId) {
        Document document = oAuth2RestTemplate.
                getForObject(DOCUMENT_SERVICE + documentId, Document.class);
        return document;
    }

    public Document findAllByCompanyId(long companyId) {
        Document document = oAuth2RestTemplate.
                getForObject(DOCUMENT_SERVICE + "/search/" + companyId, Document.class);
        return document;
    }

}

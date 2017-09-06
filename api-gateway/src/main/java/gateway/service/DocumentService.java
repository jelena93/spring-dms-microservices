/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.service;

import gateway.dto.Descriptor;
import gateway.dto.DocumentType;
import gateway.model.Document;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

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

    public Document save(Document document) {
        document = oAuth2RestTemplate.postForObject(DOCUMENT_SERVICE + "/add", document, Document.class);
        return document;
    }

    public List<Document> findByIds(List<Long> ids) {
        Document[] documents = oAuth2RestTemplate.
                postForObject(DOCUMENT_SERVICE + "/ids", ids, Document[].class);
        return Arrays.asList(documents);
    }

    public List<DocumentType> findAllDocumentTypes() {
        DocumentType[] documentTypes = oAuth2RestTemplate.
                getForObject(DOCUMENT_SERVICE + "/document-type", DocumentType[].class);
        return Arrays.asList(documentTypes);
    }

    public DocumentType findOneDocumentType(long documentTypeId) {
        DocumentType documentType = oAuth2RestTemplate.
                getForObject(DOCUMENT_SERVICE + "document-type/" + documentTypeId, DocumentType.class);
        return documentType;
    }

    public List<DocumentType> findDocumentTypesByIds(List<Long> ids) {
        DocumentType[] documentTypes = oAuth2RestTemplate.
                postForObject(DOCUMENT_SERVICE + "/document-types", ids, DocumentType[].class);
        return Arrays.asList(documentTypes);
    }

    public List<Descriptor> findDescriptorsByIds(List<Long> ids) {
        Descriptor[] descriptors = oAuth2RestTemplate.
                postForObject(DOCUMENT_SERVICE + "descriptors", ids, Descriptor[].class);
        return Arrays.asList(descriptors);
    }

    public void updateDocumentType(DocumentType documentType) {
        oAuth2RestTemplate.put(DOCUMENT_SERVICE, documentType);
    }

    public List<Long> save(List<Descriptor> descriptors) {
        return Arrays.asList(oAuth2RestTemplate.
                postForObject(DOCUMENT_SERVICE, descriptors, Descriptor[].class)).stream()
                .map(Descriptor::getId).collect(Collectors.toList());
    }
}

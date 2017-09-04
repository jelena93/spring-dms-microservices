/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document.type.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DocumentTypeServiceController {

    @Autowired
    private OAuth2RestTemplate auth2RestTemplate;
    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<DocumentTypeDto> getDocumentTypes() {
        List<DocumentTypeDto> documentTypeDtos = new ArrayList<>();
        List<DocumentType> documentTypes = documentTypeRepository.findAll();
        for (DocumentType documentType : documentTypes) {
            Descriptor[] descriptorDtos = auth2RestTemplate.
                    getForObject("http://descriptor-service/" + documentType.getId(), Descriptor[].class);
            DocumentTypeDto d = new DocumentTypeDto(documentType.getName());
            d.setDescriptors(Arrays.asList(descriptorDtos));
        }
        return documentTypeDtos;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DocumentTypeDto findDocumentType(@PathVariable("id") long id) {
        DocumentType documentType = documentTypeRepository.findOne(id);
        Descriptor[] descriptorDtos = auth2RestTemplate.
                getForObject("http://descriptor-service/" + documentType.getId(), Descriptor[].class);
        DocumentTypeDto d = new DocumentTypeDto(documentType.getName());
        d.setDescriptors(Arrays.asList(descriptorDtos));
        return d;
    }
}

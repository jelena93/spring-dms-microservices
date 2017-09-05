/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.descriptor.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DescriptorServiceController {

    @Autowired
    private DescriptorRepository descriptorRepository;
    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @RequestMapping(value = "/{documentTypeId}", method = RequestMethod.GET)
    public DocumentType findByDocumentType(@PathVariable("documentTypeId") Long documentTypeId) {
        return documentTypeRepository.findOne(documentTypeId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateDocumentType(@RequestBody DocumentType documentType) {
        System.out.println("updating " + documentType);
        documentTypeRepository.save(documentType);
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<Descriptor> addDescriptors(@RequestBody List<Descriptor> descriptors) {
        System.out.println("adding " + descriptors);
        return descriptorRepository.save(descriptors);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<DocumentType> getDocumentTypes() {
        List<DocumentType> documentTypes = documentTypeRepository.findAll();
        return documentTypes;
    }

    @RequestMapping(value = "/ids")
    public List<DocumentType> findDocumentTypesByIds(@RequestBody List<Long> ids) {
        List<DocumentType> documentTypes = documentTypeRepository.findByIdIn(ids);
        for (DocumentType documentType : documentTypes) {
            System.out.println("doc" + documentType);
        }
        return documentTypes;
    }

}

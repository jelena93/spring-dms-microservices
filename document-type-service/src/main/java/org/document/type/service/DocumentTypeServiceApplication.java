/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document.type.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DocumentTypeServiceApplication {

    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<DocumentType> getDocumentTypes() {
        return documentTypeRepository.findAll();
    }

    @RequestMapping(value = "/id}", method = RequestMethod.GET)
    public DocumentType findDocumentType(@PathVariable("id") long id) {
        return documentTypeRepository.findOne(id);
    }
}

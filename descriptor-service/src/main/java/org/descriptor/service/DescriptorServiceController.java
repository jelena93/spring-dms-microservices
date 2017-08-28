/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.descriptor.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DescriptorServiceController {

    @Autowired
    DescriptorRepository descriptorRepository;

    @RequestMapping(value = "/{documentType}", method = RequestMethod.GET)
    public List<Descriptor> findByDocumentType(@PathVariable("documentType") Long documentType) {
        return descriptorRepository.findByDocumentType(documentType);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.controller.rest;

import gateway.Serializator;
import gateway.dto.ActivityDto;
import gateway.dto.DocumentType;
import gateway.model.Activity;
import gateway.service.DescriptorService;
import gateway.service.ProcessService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/processes")
public class ProcessRestController {

    @Autowired
    private ProcessService processService;
    @Autowired
    private DescriptorService descriptorService;

    @RequestMapping(path = "/activity/{id}", method = RequestMethod.GET)
    public ActivityDto getActivity(@PathVariable(name = "id") long id) {
        Activity activity = processService.findOneActivity(id);
        List<DocumentType> inputListDocumentTypes = descriptorService.findByIds(activity.getInputListDocumentTypes());
        for (DocumentType inputListDocumentType : inputListDocumentTypes) {
            System.out.println("doc" + inputListDocumentType);
        }
        List<DocumentType> outputListDocumentTypes = descriptorService.findByIds(activity.getOutputListDocumentTypes());
        ActivityDto dto = Serializator.toDto(activity, inputListDocumentTypes, outputListDocumentTypes);
        return dto;
    }
}

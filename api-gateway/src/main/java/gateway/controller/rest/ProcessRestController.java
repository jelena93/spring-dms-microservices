/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.controller.rest;

import gateway.Serializator;
import gateway.dto.ActivityDto;
import gateway.dto.Descriptor;
import gateway.model.Document;
import gateway.dto.DocumentType;
import gateway.model.Activity;
import gateway.service.DocumentService;
import gateway.service.ProcessService;
import java.util.ArrayList;
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
    private DocumentService documentService;

    @RequestMapping(path = "/activity/{id}", method = RequestMethod.GET)
    public ActivityDto getActivity(@PathVariable(name = "id") long id) {
        Activity activity = processService.findOneActivity(id);
        List<Document> inputList = documentService.findByIds(activity.getInputList());
        List<Document> ouputList = documentService.findByIds(activity.getOutputList());
        List<Descriptor> inpDesc = new ArrayList<>();
        List<Descriptor> outDesc = new ArrayList<>();
        for (Document document : inputList) {
            inpDesc = documentService.findDescriptorsByIds(document.getDescriptors());
        }
        for (Document document : ouputList) {
            outDesc = documentService.findDescriptorsByIds(document.getDescriptors());
        }
        List<DocumentType> inputListDocumentTypes = documentService.findDocumentTypesByIds(activity.getInputListDocumentTypes());
        List<DocumentType> outputListDocumentTypes = documentService.findDocumentTypesByIds(activity.getOutputListDocumentTypes());
        ActivityDto dto = Serializator.toDto(activity, inputList, ouputList, inputListDocumentTypes, outputListDocumentTypes);
        return dto;
    }
}

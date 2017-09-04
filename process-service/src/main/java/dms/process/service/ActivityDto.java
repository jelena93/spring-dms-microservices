/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dms.process.service;

import java.util.ArrayList;
import java.util.List;

public class ActivityDto {

    private Long id;
    private String name;
    private List<DocumentType> inputListDocumentTypes = new ArrayList<>();
    private List<DocumentType> outputListDocumentTypes = new ArrayList<>();

    public ActivityDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DocumentType> getInputListDocumentTypes() {
        return inputListDocumentTypes;
    }

    public void setInputListDocumentTypes(List<DocumentType> inputListDocumentTypes) {
        this.inputListDocumentTypes = inputListDocumentTypes;
    }

    public List<DocumentType> getOutputListDocumentTypes() {
        return outputListDocumentTypes;
    }

    public void setOutputListDocumentTypes(List<DocumentType> outputListDocumentTypes) {
        this.outputListDocumentTypes = outputListDocumentTypes;
    }

}

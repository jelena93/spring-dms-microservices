/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.dto;

import gateway.model.Document;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActivityDto implements Serializable {

    private Long id;
    private String name;
    private List<Document> inputList;
    private List<Document> outputList;
    private List<DocumentType> inputListDocumentTypes = new ArrayList<>();
    private List<DocumentType> outputListDocumentTypes = new ArrayList<>();

    public ActivityDto() {
        this.inputList = new ArrayList<>();
        this.outputList = new ArrayList<>();
    }

    public ActivityDto(String name) {
        this.name = name;
        this.inputList = new ArrayList<>();
        this.outputList = new ArrayList<>();
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

    public List<Document> getInputList() {
        return inputList;
    }

    public void setInputList(List<Document> inputList) {
        this.inputList = inputList;
    }

    public List<Document> getOutputList() {
        return outputList;
    }

    public void setOutputList(List<Document> outputList) {
        this.outputList = outputList;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActivityDto other = (ActivityDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Activity{" + "id=" + id + ", name=" + name + ", inputList=" + inputList + ", outputList=" + outputList + ", inputListDocumentTypes=" + inputListDocumentTypes + ", outputListDocumentTypes=" + outputListDocumentTypes + '}';
    }

}

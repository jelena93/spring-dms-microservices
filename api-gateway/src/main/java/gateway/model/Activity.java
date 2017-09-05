/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity {

    private Long id;
    private String name;
    private List<Long> inputListDocumentTypes = new ArrayList<>();
    private List<Long> outputListDocumentTypes = new ArrayList<>();
    private List<Long> inputList = new ArrayList<>();
    private List<Long> outputList = new ArrayList<>();

    public Activity() {
    }

    public Activity(String name) {
        this.name = name;
    }

    public Activity(Long id, String name) {
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

    public List<Long> getInputListDocumentTypes() {
        return inputListDocumentTypes;
    }

    public void setInputListDocumentTypes(List<Long> inputListDocumentTypes) {
        this.inputListDocumentTypes = inputListDocumentTypes;
    }

    public List<Long> getOutputListDocumentTypes() {
        return outputListDocumentTypes;
    }

    public void setOutputListDocumentTypes(List<Long> outputListDocumentTypes) {
        this.outputListDocumentTypes = outputListDocumentTypes;
    }

    public List<Long> getInputList() {
        return inputList;
    }

    public void setInputList(List<Long> inputList) {
        this.inputList = inputList;
    }

    public List<Long> getOutputList() {
        return outputList;
    }

    public void setOutputList(List<Long> outputList) {
        this.outputList = outputList;
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
        final Activity other = (Activity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Activity{" + "id=" + id + ", name=" + name + ", inputListDocumentTypes=" + inputListDocumentTypes + ", outputListDocumentTypes=" + outputListDocumentTypes + ", inputList=" + inputList + ", outputList=" + outputList + '}';
    }
}

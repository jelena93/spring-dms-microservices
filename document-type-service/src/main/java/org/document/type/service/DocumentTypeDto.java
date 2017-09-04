/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document.type.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DocumentTypeDto implements Serializable {

    private Long id;
    private String name;
    private List<Descriptor> descriptors = new ArrayList<>();

    public DocumentTypeDto() {
    }

    public DocumentTypeDto(String name) {
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

    public List<Descriptor> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(List<Descriptor> descriptors) {
        this.descriptors = descriptors;
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
        final DocumentTypeDto other = (DocumentTypeDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + ": " + descriptors;
    }
}

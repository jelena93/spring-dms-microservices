/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.dto;

import document.model.DescriptorType;
import java.util.Objects;

/**
 *
 * @author jelena
 */
public class DescriptorDto {

    private Long id;
    private Long documentType;
    private String descriptorKey;
    private DescriptorType descriptorType;
    private Object value;
    private final String DATE_FORMAT = "dd.MM.yyyy";

    public DescriptorDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Long documentType) {
        this.documentType = documentType;
    }

    public String getDescriptorKey() {
        return descriptorKey;
    }

    public void setDescriptorKey(String descriptorKey) {
        this.descriptorKey = descriptorKey;
    }

    public DescriptorType getDescriptorType() {
        return descriptorType;
    }

    public void setDescriptorType(DescriptorType descriptorType) {
        this.descriptorType = descriptorType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final DescriptorDto other = (DescriptorDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DescriptorDto{" + "id=" + id + ", documentType=" + documentType + ", descriptorKey=" + descriptorKey + ", descriptorType=" + descriptorType + ", value=" + value + ", DATE_FORMAT=" + DATE_FORMAT + '}';
    }

}

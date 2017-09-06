/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.dto;

import gateway.model.DescriptorType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Descriptor implements Serializable {

    private Long id;
    private Long documentType;
    private String descriptorKey;
    private DescriptorType descriptorType;
    private Long longValue;
    private Double doubleValue;
    private Date dateValue;
    private String stringValue;
    private Object value;
    @JsonInclude
    private final String DATE_FORMAT = "dd.MM.yyyy";

    public Descriptor() {
    }

    public Descriptor(String key, Long documentType, DescriptorType descriptorType) {
        this.descriptorKey = key;
        this.documentType = documentType;
        this.descriptorType = descriptorType;
    }

    public Descriptor(String key, Descriptor descriptor, Object value, Long documentType, DescriptorType descriptorType) {
        this.descriptorKey = key;
        this.documentType = documentType;
        this.descriptorType = descriptorType;
        setValue(descriptor, value);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescriptorKey() {
        return descriptorKey;
    }

    public void setDescriptorKey(String descriptorKey) {
        this.descriptorKey = descriptorKey;
    }

    public Long getDocumentType() {
        return documentType;
    }

    public String getDATE_FORMAT() {
        return DATE_FORMAT;
    }

    public void setDocumentType(Long documentType) {
        this.documentType = documentType;
    }

    public DescriptorType getDescriptorType() {
        return descriptorType;
    }

    public void setDescriptorType(DescriptorType descriptorType) {
        this.descriptorType = descriptorType;
    }

    public String convertValueToString() {
        if (Date.class.equals(descriptorType.getParamClass())) {
            return new SimpleDateFormat(DATE_FORMAT).format(getValue());
        } else {
            return getValue() + "";
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Descriptor other = (Descriptor) obj;
        return !(!Objects.equals(this.descriptorKey, other.descriptorKey) || !Objects.equals(this.getValue(), other.getValue()));
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Descriptor descriptor, Object value) {
        try {
            Class paramClass = descriptor.getDescriptorType().getParamClass();
            if (value == null) {
                longValue = null;
                doubleValue = null;
                stringValue = null;
                dateValue = null;
            } else {
                if (Integer.class.equals(paramClass)) {
                    if (value instanceof String) {
                        Integer valueInt = Integer.parseInt(value.toString());
                        longValue = (valueInt).longValue();
                        this.value = longValue;
                    } else {
                        longValue = ((Integer) value).longValue();
                        this.value = longValue;
                    }
                    doubleValue = longValue.doubleValue();
                } else if (Long.class.equals(paramClass)) {
                    if (value instanceof String) {
                        Long valueLong = Long.parseLong(value.toString());
                        longValue = (valueLong);
                        this.value = longValue;
                    } else {
                        longValue = ((Long) value);
                        this.value = longValue;
                    }
                    doubleValue = longValue.doubleValue();
                } else if (Double.class.equals(paramClass)) {
                    if (value instanceof String) {
                        Double valueDouble = Double.parseDouble(value.toString());
                        doubleValue = (valueDouble);
                        this.value = doubleValue;
                    } else {
                        doubleValue = ((Double) value);
                        this.value = doubleValue;
                    }
                    longValue = doubleValue.longValue();
                } else if (String.class.equals(paramClass)) {
                    stringValue = (String) value;
                    this.value = stringValue;
                } else if (Date.class.equals(paramClass)) {
                    try {
                        dateValue = new SimpleDateFormat(DATE_FORMAT).parse(value.toString());
                        this.value = dateValue;
                    } catch (ParseException ex) {
                        dateValue = (Date) value;
                        this.value = dateValue;
                    }
                }
            }
        } catch (Exception ex) {
            longValue = null;
            doubleValue = null;
            stringValue = null;
            dateValue = null;
        }
    }

    @Override
    public String toString() {
        return "id=" + id + ", documentType=" + documentType + ", descriptorKey="
                + descriptorKey + ", descriptorType=" + descriptorType + ", value="
                + getValue() + ",stringValue=" + stringValue;
    }

}

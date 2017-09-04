/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dms.process.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author jelena
 */
public class Descriptor {

    private Long id;
    private Long documentType;
    private String descriptorKey;
    private DescriptorType descriptorType;
    private Long longValue;
    private Double doubleValue;
    private Date dateValue;
    private String stringValue;
    @JsonInclude
    private final String DATE_FORMAT = "dd.MM.yyyy";

    public Descriptor() {
    }

    public Descriptor(String key, Long documentType, DescriptorType descriptorType) {
        this.descriptorKey = key;
        this.documentType = documentType;
        this.descriptorType = descriptorType;
    }

    public Descriptor(String key, Object value, Long documentType, DescriptorType descriptorType) {
        this.descriptorKey = key;
        this.documentType = documentType;
        this.descriptorType = descriptorType;
        setValue(value);
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

    @Override
    public String toString() {
        return "Descriptor{" + "id=" + id + ", documentType=" + documentType + ", descriptorKey=" + descriptorKey + ", descriptorType=" + descriptorType + ", longValue=" + longValue + ", doubleValue=" + doubleValue + ", dateValue=" + dateValue + ", stringValue=" + stringValue + ", DATE_FORMAT=" + DATE_FORMAT + '}';
    }

    public Object getValue() {
        Class paramClass = descriptorType.getParamClass();
        if (Integer.class.equals(paramClass)) {
            return longValue != null ? longValue.intValue() : null;
        } else if (Long.class.equals(paramClass)) {
            return longValue;
        } else if (Double.class.equals(paramClass)) {
            return doubleValue;
        } else if (String.class.equals(paramClass)) {
            return stringValue;
        } else if (Date.class.equals(paramClass)) {
            return dateValue;
        }
        return null;
    }

    public void setValue(Object value) {
        try {
            Class paramClass = descriptorType.getParamClass();
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
                    } else {
                        longValue = ((Integer) value).longValue();
                    }
                    doubleValue = longValue.doubleValue();
                } else if (Long.class.equals(paramClass)) {
                    if (value instanceof String) {
                        Long valueLong = Long.parseLong(value.toString());
                        longValue = (valueLong);
                    } else {
                        longValue = ((Long) value);
                    }
                    doubleValue = longValue.doubleValue();
                } else if (Double.class.equals(paramClass)) {
                    if (value instanceof String) {
                        Double valueDouble = Double.parseDouble(value.toString());
                        doubleValue = (valueDouble);
                    } else {
                        doubleValue = ((Double) value);
                    }
                    longValue = doubleValue.longValue();
                } else if (String.class.equals(paramClass)) {
                    stringValue = (String) value;
                } else if (Date.class.equals(paramClass)) {
                    try {
                        dateValue = new SimpleDateFormat(DATE_FORMAT).parse(value.toString());
                    } catch (ParseException ex) {
                        dateValue = (Date) value;
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
}

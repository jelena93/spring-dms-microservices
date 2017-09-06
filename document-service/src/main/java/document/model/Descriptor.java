/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "descriptor", indexes = {
    @Index(columnList = "NUMBER_VALUE", name = "idx_param_number_value")
    ,
    @Index(columnList = "DOUBLE_VALUE", name = "idx_param_double_value")
    ,
    @Index(columnList = "DATE_VALUE", name = "idx_param_date_value")
    ,
    @Index(columnList = "STRING_VALUE", name = "idx_param_string_value")
})
public class Descriptor implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "descriptor_id")
    private Long id;
    @Column(name = "document_type")
    @NotNull
    private Long documentType;
    @Column(name = "descriptor_key")
    @NotNull
    private String descriptorKey;
    @JoinColumn(name = "descriptor_type", nullable = false)
    private DescriptorType descriptorType;
    @Column(name = "NUMBER_VALUE")
    private Long longValue;
    @Column(name = "DOUBLE_VALUE")
    private Double doubleValue;
    @Column(name = "DATE_VALUE")
    @Temporal(TemporalType.DATE)
    private Date dateValue;
    @Column(name = "STRING_VALUE")
    private String stringValue;
    private final String DATE_FORMAT = "dd.MM.yyyy";
    @Transient
    private Object value;

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
        if (!Objects.equals(this.descriptorKey, other.descriptorKey) || !Objects.equals(this.getValue(), other.getValue())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + "," + descriptorKey + ": " + getValue();
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
}

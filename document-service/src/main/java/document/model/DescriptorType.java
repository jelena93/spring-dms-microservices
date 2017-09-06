/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "descriptor_type")
public class DescriptorType implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "PARAM_CLASS")
    @Lob
    private Class paramClass;

    public DescriptorType() {
    }

    public DescriptorType(Class paramClass) {
        this.paramClass = paramClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Class getParamClass() {
        return paramClass;
    }

    public void setParamClass(Class paramClass) {
        this.paramClass = paramClass;
    }

    public String getStringMessageByParamClass() {
        if (Integer.class.equals(paramClass)) {
            return "integer";
        } else if (Double.class.equals(paramClass)) {
            return "decimal number";
        } else if (Date.class.equals(paramClass)) {
            return "date";
        }
        return "N/A";
    }
}

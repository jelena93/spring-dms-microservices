package descriptor.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "descriptor_type")
public class DescriptorType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;
    @Column(name = "param_class")
    private Class paramClass;

    public DescriptorType() { }

    public DescriptorType(Class paramClass) {
        this.paramClass = paramClass;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Class getParamClass() { return paramClass; }

    public void setParamClass(Class paramClass) { this.paramClass = paramClass; }

    public String getStringMessageByParamClass() {
        if (Integer.class.equals(paramClass)) { return "integer"; } else if (Double.class.equals(paramClass)) {
            return "decimal number";
        } else if (Date.class.equals(paramClass)) {
            return "date";
        }
        return "N/A";
    }

}
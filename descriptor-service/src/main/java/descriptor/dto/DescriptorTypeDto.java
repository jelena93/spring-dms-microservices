package descriptor.dto;

public class DescriptorTypeDto {

    private Long id;
    private Class paramClass;

    public DescriptorTypeDto() { }

    public DescriptorTypeDto(Class paramClass) {
        this.paramClass = paramClass;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Class getParamClass() { return paramClass; }

    public void setParamClass(Class paramClass) { this.paramClass = paramClass; }

}
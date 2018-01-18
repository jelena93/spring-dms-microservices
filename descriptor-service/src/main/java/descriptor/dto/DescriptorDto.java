package descriptor.dto;

public class DescriptorDto {

    private String descriptorKey;
    private String descriptorValue;
    private Class paramClass;

    public String getDescriptorKey() {
        return descriptorKey;
    }

    public void setDescriptorKey(String descriptorKey) {
        this.descriptorKey = descriptorKey;
    }

    public String getDescriptorValue() {
        return descriptorValue;
    }

    public void setDescriptorValue(String descriptorValue) {
        this.descriptorValue = descriptorValue;
    }

    public Class getParamClass() {
        return paramClass;
    }

    public void setParamClass(Class paramClass) {
        this.paramClass = paramClass;
    }

    @Override
    public String toString() {
        return "DescriptorDto{" + "descriptorKey='" + descriptorKey + '\'' + ", descriptorValue='" + descriptorValue
                + '\'' + ", paramClass=" + paramClass + '}';
    }
}

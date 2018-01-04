package document.dto;

import java.io.Serializable;

public class DescriptorDto implements Serializable {

    private static final long serialVersionUID = 8678545558547116374L;
    private String descriptorKey;
    private String descriptorValue;

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

    @Override
    public String toString() {
        return "DescriptorDto{" + "descriptorKey='" + descriptorKey + '\'' + ", descriptorValue='" + descriptorValue
                + '\'' + '}';
    }
}

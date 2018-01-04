package document.domain;

import java.io.Serializable;

public class Descriptor implements Serializable{

    private static final long serialVersionUID = -7932993172790322047L;

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
}

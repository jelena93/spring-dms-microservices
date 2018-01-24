package document.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Descriptor {
    private Long documentTypeId;
    private String descriptorKey;
    private String descriptorValue;

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

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
        return "Descriptor{" + "documentTypeId=" + documentTypeId + ", descriptorKey='" + descriptorKey + '\''
                + ", descriptorValue='" + descriptorValue + '\'' + '}';
    }
}

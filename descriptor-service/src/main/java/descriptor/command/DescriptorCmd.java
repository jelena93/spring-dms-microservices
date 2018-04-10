package descriptor.command;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class DescriptorCmd {

    @NotNull(message = "{validation.documentTypeId.null}")
    private Long documentTypeId;

    @NotNull(message = "{validation.descriptor.key.null}")
    @NotEmpty(message = "{validation.descriptor.key.empty}")
    private String descriptorKey;

    @NotNull(message = "{validation.descriptor.value.null}")
    @NotEmpty(message = "{validation.descriptor.value.empty}")
    private String descriptorValue;

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public String getDescriptorKey() {
        return descriptorKey;
    }

    public String getDescriptorValue() {
        return descriptorValue;
    }


    public DescriptorCmd(Long documentTypeId, String descriptorKey, String descriptorValue) {
        this.documentTypeId = documentTypeId;
        this.descriptorKey = descriptorKey;
        this.descriptorValue = descriptorValue;
    }

    @Override
    public String toString() {
        return "DescriptorCmd{" +
                "documentTypeId=" + documentTypeId +
                ", descriptorKey='" + descriptorKey + '\'' +
                ", descriptorValue='" + descriptorValue + '\'' +
                '}';
    }
}

package document.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentCmd {

    private Long ownerId;
    private String fileName;
    private Long documentTypeId;
    private List<DescriptorCmd> descriptors = new ArrayList<>();

    public DocumentCmd() {
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<DescriptorCmd> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(List<DescriptorCmd> descriptors) {
        this.descriptors = descriptors;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    @Override
    public String toString() {
        return "DocumentCmd{" +
                "ownerId=" + ownerId +
                ", documentTypeId=" + documentTypeId +
                ", fileName='" + fileName + '\'' +
                ", descriptors=" + descriptors +
                '}';
    }
}

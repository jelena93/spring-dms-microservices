package document.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import document.domain.Descriptor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentValidationCmd {

    private Long documentTypeId;
    private Long ownerId;
    private String fileName;
    private List<Descriptor> descriptors;

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
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

    public List<Descriptor> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(List<Descriptor> descriptors) {
        this.descriptors = descriptors;
    }

    @Override
    public String toString() {
        return "DocumentValidationCmd{" + "documentTypeId=" + documentTypeId + ", ownerId=" + ownerId + ", fileName='"
                + fileName + '\'' + ", descriptors=" + descriptors + '}';
    }
}

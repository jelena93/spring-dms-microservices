package descriptor.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class DocumentCmd {

    private boolean input;
    private Long ownerId;
    private Long activityId;
    private String fileName;
    private Long documentTypeId;
    private List<DescriptorDto> descriptors;
    @JsonIgnore
    private MultipartFile file;

    public DocumentCmd() {
    }

    public boolean isInput() {
        return input;
    }

    public void setInput(boolean input) {
        this.input = input;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public List<DescriptorDto> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(List<DescriptorDto> descriptors) {
        this.descriptors = descriptors;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "DocumentCmd{" + "input=" + input + ", ownerId=" + ownerId + ", fileName='" + fileName + '\''
                + ", documentTypeId=" + documentTypeId + ", descriptors=" + descriptors + ", file=" + file + '}';
    }
}

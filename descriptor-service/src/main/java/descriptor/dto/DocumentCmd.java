package descriptor.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import descriptor.domain.Descriptor;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class DocumentCmd implements Serializable {

    private static final long serialVersionUID = 8406813989467909660L;
    private Long ownerId;
    private String fileName;
    private Long documentType;
    private List<DescriptorDto> descriptors;
    @JsonIgnore
    private MultipartFile file;

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

    public Long getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Long documentType) {
        this.documentType = documentType;
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
        return "DocumentCmd{" + "ownerId=" + ownerId + ", fileName='" + fileName + '\'' + ", documentType="
                + documentType + ", descriptors=" + descriptors + ", file=" + file + '}';
    }
}

package document.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import document.domain.Descriptor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentCmd implements Serializable {

    private static final long serialVersionUID = 7607325603160192150L;

    private Long id;
    private Long ownerId;
    private String fileName;
    private MultipartFile file;
    private List<Descriptor> descriptors;

    public DocumentCmd() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public List<Descriptor> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(List<Descriptor> descriptors) {
        this.descriptors = descriptors;
    }

    @Override
    public String toString() {
        return "DocumentCmd{" + "id=" + id + ", ownerId=" + ownerId + ", fileName='" + fileName + '\'' + ", file="
                + file + ", descriptors=" + descriptors + '}';
    }
}

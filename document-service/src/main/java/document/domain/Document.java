package document.domain;

import java.util.List;
import java.util.Objects;

public class Document {

    private Long id;
    private Long ownerId;
    private String fileName;
    private String content;
    private List<Descriptor> descriptors;

    public Document() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Descriptor> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(List<Descriptor> descriptors) {
        this.descriptors = descriptors;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Document document = (Document) o;

        return id.equals(document.id);
    }

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", ownerId=" + ownerId + ", fileName='" + fileName + '\'' + ", descriptors="
                + descriptors + '}';
    }
}

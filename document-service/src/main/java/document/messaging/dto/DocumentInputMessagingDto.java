package document.messaging.dto;

import java.io.Serializable;
import java.util.List;

public class DocumentInputMessagingDto implements Serializable{
    private List<Long> documentId;

    public DocumentInputMessagingDto() {
    }

    public DocumentInputMessagingDto(List<Long> documentId) {
        this.documentId = documentId;
    }

    public List<Long> getDocumentId() {
        return documentId;
    }

    public void setDocumentId(List<Long> documentId) {
        this.documentId = documentId;
    }
}

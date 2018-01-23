package process.messaging.output.dto;

import java.io.Serializable;
import java.util.List;

public class DocumentMessagingOutputDto implements Serializable {
    private List<Long> documentId;

    public DocumentMessagingOutputDto() {
    }

    public DocumentMessagingOutputDto(List<Long> documentId) {
        this.documentId = documentId;
    }

    public List<Long> getDocumentId() {
        return documentId;
    }

    public void setDocumentId(List<Long> documentId) {
        this.documentId = documentId;
    }
}

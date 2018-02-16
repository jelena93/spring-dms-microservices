package document.messaging;

import document.elasticsearch.DocumentIndexer;
import document.messaging.dto.DocumentInputMessagingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class DocumentMessagingHandler {
    private final DocumentIndexer documentIndexer;

    @Autowired
    public DocumentMessagingHandler(DocumentIndexer documentIndexer) {this.documentIndexer = documentIndexer;}

    @StreamListener(DocumentInputChannel.DOCUMENT_DELETE_INPUT)
    public void handleDocumentDelete(DocumentInputMessagingDto documentInputMessagingDto) {
        System.out.println("handleDocumentDelete " + documentInputMessagingDto);
        documentIndexer.delete(documentInputMessagingDto.getDocumentId());
    }
}

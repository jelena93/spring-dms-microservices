package document.messaging;

import document.elasticsearch.DocumentIndexer;
import document.messaging.dto.DocumentInputMessagingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentInputMessagingService {
    private final DocumentIndexer documentIndexer;

    @Autowired
    public DocumentInputMessagingService(DocumentIndexer documentIndexer) {this.documentIndexer = documentIndexer;}

    @StreamListener(DocumentInputChannel.DOCUMENT_DELETE_INPUT)
    public void handleDocumentDelete(DocumentInputMessagingDto documentInputMessagingDto) {
        System.out.println("handleDocumentDelete " + documentInputMessagingDto);
        documentIndexer.deleteDocuments(documentInputMessagingDto.getDocumentId());
    }
}

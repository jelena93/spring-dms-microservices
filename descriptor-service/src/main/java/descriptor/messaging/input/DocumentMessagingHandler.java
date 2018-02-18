package descriptor.messaging.input;

import descriptor.service.DescriptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class DocumentMessagingHandler {
    private final DescriptorService descriptorService;

    @Autowired
    public DocumentMessagingHandler(DescriptorService descriptorService) {
        this.descriptorService = descriptorService;
    }

    @StreamListener(DocumentInputChannel.DOCUMENT_DELETED_INPUT)
    public void handleDocumentDeleted(DocumentInputMessagingDto documentInputMessagingDto) {
        System.out.println("handleDocumentDeleted " + documentInputMessagingDto);
        descriptorService.removeByDocumentIdIn(documentInputMessagingDto.getDocumentId());
    }
}

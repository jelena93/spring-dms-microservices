package descriptor.messaging.input;

import descriptor.service.DescriptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class DocumentInputMessagingService {
    private final DescriptorService descriptorService;

    @Autowired
    public DocumentInputMessagingService(DescriptorService descriptorService) {
        this.descriptorService = descriptorService;
    }

    @StreamListener(DocumentInputChannel.DOCUMENT_DELETE_INPUT)
    public void handleDocumentDelete(DocumentInputMessagingDto documentInputMessagingDto) {
        System.out.println("handleDocumentDelete " + documentInputMessagingDto);
        descriptorService.removeByDocumentIdIn(documentInputMessagingDto.getDocumentId());
    }
}

package descriptor.messaging.output;

import descriptor.messaging.output.dto.DocumentMessagingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class DocumentOutputMessagingService {
    @Autowired
    @Qualifier(DocumentOutputChannel.DOCUMENT_ADDED_OUTPUT)
    private MessageChannel documentAddedMessageChannel;

    public void sendDocumentAdded(DocumentMessagingDto document) {
        System.out.println("sendDocumentAdded " + document);
        documentAddedMessageChannel.send(MessageBuilder.withPayload(document).build());
    }
}

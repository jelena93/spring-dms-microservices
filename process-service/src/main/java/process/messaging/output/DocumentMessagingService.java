package process.messaging.output;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import process.messaging.output.dto.DocumentMessagingOutputDto;

@Service
public class DocumentMessagingService {
    @Autowired
    @Qualifier(DocumentOutputChannel.DOCUMENT_DELETED_OUTPUT)
    private MessageChannel documentDeletedMessageChannel;

    public void sendDeleteDocuments(DocumentMessagingOutputDto documentMessagingOutputDto) {
        System.out.println("sendDeleteDocument " + documentMessagingOutputDto);
        documentDeletedMessageChannel.send(MessageBuilder.withPayload(documentMessagingOutputDto).build());
    }
}

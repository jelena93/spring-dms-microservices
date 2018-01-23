package process.messaging.output;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import process.messaging.output.dto.DocumentMessagingOutputDto;

import java.util.List;

@Service
public class DocumentOutputMessagingService {
    @Autowired
    @Qualifier(DocumentOutputChannel.DOCUMENT_DELETE_OUTPUT)
    private MessageChannel documentDeleteMessageChannel;

    public void sendDeleteDocuments(DocumentMessagingOutputDto documentMessagingOutputDto) {
        System.out.println("sendDeleteDocument " + documentMessagingOutputDto);
        documentDeleteMessageChannel.send(MessageBuilder.withPayload(documentMessagingOutputDto).build());
    }
}

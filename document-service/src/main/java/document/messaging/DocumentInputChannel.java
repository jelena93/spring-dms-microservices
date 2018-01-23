package document.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface DocumentInputChannel {
    String DOCUMENT_DELETE_INPUT = "documentDeleteInput";

    @Input(DOCUMENT_DELETE_INPUT)
    SubscribableChannel documentDeleteInput();
}

package descriptor.messaging.input;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface DocumentInputChannel {
    String DOCUMENT_DELETED_INPUT = "documentDeletedInput";

    @Input(DOCUMENT_DELETED_INPUT)
    SubscribableChannel documentDeletedInput();
}
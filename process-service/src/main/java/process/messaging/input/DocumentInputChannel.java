package process.messaging.input;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface DocumentInputChannel {

    String DOCUMENT_ADDED_INPUT = "documentAddedInput";

    @Input(DOCUMENT_ADDED_INPUT)
    SubscribableChannel documentAddedInput();

}
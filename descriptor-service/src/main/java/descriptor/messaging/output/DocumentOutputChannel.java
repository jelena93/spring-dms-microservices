package descriptor.messaging.output;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface DocumentOutputChannel {

    String DOCUMENT_ADDED_OUTPUT = "documentAddedOutput";

    @Output(DOCUMENT_ADDED_OUTPUT)
    MessageChannel documentAddedOutput();
}

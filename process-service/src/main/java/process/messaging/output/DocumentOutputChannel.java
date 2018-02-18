package process.messaging.output;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface DocumentOutputChannel {
    String DOCUMENT_DELETED_OUTPUT = "documentDeletedOutput";

    @Output(DOCUMENT_DELETED_OUTPUT)
    MessageChannel documentDeletedOutput();
}

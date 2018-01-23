package process.messaging.output;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface DocumentOutputChannel {
    String DOCUMENT_DELETE_OUTPUT = "documentDeleteOutput";

    @Output(DOCUMENT_DELETE_OUTPUT)
    MessageChannel documentDeleteOutput();
}

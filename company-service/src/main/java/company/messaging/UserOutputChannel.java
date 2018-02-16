package company.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface UserOutputChannel {

    String USER_ADDED_OUTPUT = "userAddedOutput";


    @Output(USER_ADDED_OUTPUT)
    MessageChannel userAddedOutput();

}
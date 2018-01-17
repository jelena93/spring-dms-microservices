package auth.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface UserInputChannel {

    String USER_ADDED_INPUT = "userAddedInput";

    @Input(USER_ADDED_INPUT)
    SubscribableChannel userAddedInput();

}

package company.messaging;

import company.command.UserCmd;
import company.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserMessagingService {
    @Autowired
    @Qualifier(UserOutputChannel.USER_ADDED_OUTPUT)
    private MessageChannel userAddedMessageChannel;

    public void sendUserAdded(UserCmd userCmd) {
        System.out.println("sendUserAdded " + userCmd);
        userAddedMessageChannel.send(MessageBuilder.withPayload(userCmd).build());
    }
}

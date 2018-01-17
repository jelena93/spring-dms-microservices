package company;

import company.rabbit.ProductOutputChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

public class SendChannel {
    @Autowired
    @Qualifier(ProductOutputChannel.PRODUCT_DELETED_OUTPUT)
    private MessageChannel productDeletedMessageChannel;

    @Autowired
    @Qualifier(ProductOutputChannel.PRODUCT_UPDATED_OUTPUT)
    private MessageChannel productUpdatedMessageChannel;

    public void sendMessage() {
        productDeletedMessageChannel.send(MessageBuilder.withPayload("messsageeee").build());
    }
}

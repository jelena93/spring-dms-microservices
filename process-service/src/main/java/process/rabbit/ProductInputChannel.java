package process.rabbit;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ProductInputChannel {

    String PRODUCT_DELETED_INPUT = "productDeletedInput";

    String PRODUCT_UPDATED_INPUT = "productUpdatedInput";

    @Input(PRODUCT_DELETED_INPUT)
    SubscribableChannel productDeletedInput();

    @Input(PRODUCT_UPDATED_INPUT)
    SubscribableChannel productUpdatedInput();
}
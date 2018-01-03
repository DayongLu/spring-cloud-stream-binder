package com.gm.gcc.azure.iothub.springcloud.binder.core.message;

import com.microsoft.azure.eventhubs.EventData;
import org.springframework.messaging.Message;

public interface MessageConverter {

    Message<?> toMessage(Object data);

}

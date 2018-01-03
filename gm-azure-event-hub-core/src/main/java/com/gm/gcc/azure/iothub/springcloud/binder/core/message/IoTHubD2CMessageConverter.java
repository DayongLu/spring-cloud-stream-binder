package com.gm.gcc.azure.iothub.springcloud.binder.core.message;

import com.microsoft.azure.eventhubs.EventData;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;

public class IoTHubD2CMessageConverter implements MessageConverter {

    @Override
    public Message<?> toMessage(Object data) {

        if (data instanceof EventData) {

            Map<String, Object> headerMap = new HashMap<>();
            headerMap.putAll(((EventData) data).getProperties());
            headerMap.putAll(((EventData) data).getSystemProperties());
            MessageHeaders headers = new MessageHeaders(headerMap);
            return MessageBuilder.createMessage(((EventData) data).getBytes(), headers);
        }
        throw new IllegalArgumentException("D2C data received is not from IoT Hub");
    }
}

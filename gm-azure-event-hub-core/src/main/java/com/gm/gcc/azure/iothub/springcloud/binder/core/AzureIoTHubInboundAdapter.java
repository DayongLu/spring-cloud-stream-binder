package com.gm.gcc.azure.iothub.springcloud.binder.core;

import com.gm.gcc.azure.iothub.springcloud.binder.core.container.MessageListenerContainer;
import com.gm.gcc.azure.iothub.springcloud.binder.core.message.MessageConverter;
import com.microsoft.azure.eventhubs.EventData;
import org.springframework.integration.MessageRejectedException;
import org.springframework.integration.context.OrderlyShutdownCapable;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.Message;

public class AzureIoTHubInboundAdapter extends MessageProducerSupport
  implements OrderlyShutdownCapable, D2CDataListener {

    private MessageListenerContainer messageListenerContainer;

    private MessageConverter messageConverter;


    public AzureIoTHubInboundAdapter(MessageListenerContainer messageListenerContainer,
      MessageConverter messageConverter) {

        this.messageListenerContainer = messageListenerContainer;
        this.messageConverter = messageConverter;
    }


    @Override
    protected void doStart() {

        this.messageListenerContainer.setupMessageListener(this);
        this.messageListenerContainer.start();

    }


    @Override
    protected void doStop() {

        this.messageListenerContainer.stop();


    }


    @Override
    public String getComponentType() {

        return "azure-iot-hub:message-driven-channel-adapter";
    }


    @Override
    public int beforeShutdown() {

        this.messageListenerContainer.stop();
        return getPhase();
    }


    @Override
    public int afterShutdown() {

        return getPhase();
    }


    @Override
    public void onMessage(Object message) {

        if (message instanceof EventData) {
            Message msg = this.messageConverter.toMessage(message);
            sendMessage(msg);
            return;
        }
        throw new IllegalArgumentException("Data is not EventData type.");
    }
}

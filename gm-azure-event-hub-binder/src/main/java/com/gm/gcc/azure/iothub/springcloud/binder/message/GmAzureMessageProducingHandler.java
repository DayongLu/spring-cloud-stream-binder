package com.gm.gcc.azure.iothub.springcloud.binder.message;

import org.springframework.context.Lifecycle;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.messaging.Message;

public class GmAzureMessageProducingHandler extends AbstractMessageProducingHandler implements Lifecycle {

    @Override
    public void start() {

    }


    @Override
    public void stop() {

    }


    @Override
    public boolean isRunning() {

        return false;
    }


    @Override
    protected void handleMessageInternal(Message<?> message) throws Exception {

    }
}

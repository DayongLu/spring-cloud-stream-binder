package com.gm.gcc.azure.iothub.springcloud.binder.message;

import com.gm.gcc.azure.iothub.springcloud.binder.util.AzureIoTHeaders;
import com.gm.gcc.azure.iothub.springcloud.binder.util.MessageCoverter;
import com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration.GmAzureIotHubProperties;
import com.gm.gcc.gm.azure.iothub.springboot.starter.exception.GmAzureIotHubSpringbootStarterException;
import com.microsoft.azure.sdk.iot.service.ServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.context.Lifecycle;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.io.IOException;

public class GmAzureMessageHandler extends AbstractMessageHandler implements Lifecycle {

    private ProducerDestination destination;
    private ExtendedProducerProperties<GmAzureIotHubProperties> producerProperties;
    private ServiceClient serviceClient;
    private boolean isRunning = false;

    private static final Logger logger = LoggerFactory.getLogger(GmAzureMessageHandler.class);


    public GmAzureMessageHandler(ProducerDestination destination,
      ExtendedProducerProperties<GmAzureIotHubProperties> producerProperties, ServiceClient serviceClient) {

        super();
        this.destination = destination;
        this.producerProperties = producerProperties;
        this.serviceClient = serviceClient;
    }


    @Override
    protected void handleMessageInternal(Message<?> message) throws Exception {

        this.serviceClient.send((String) message.getHeaders().get(AzureIoTHeaders.DEVICE_ID.getHeaderName()),
          MessageCoverter.springMessageToAzureIoTMessage(message, producerProperties.getExtension()));
    }


    @Override
    public void start() {

        logger.info("starting GmAzureMessageHandler, open amqp connections to Azure IoT Hub ");
        try {
            this.isRunning = true;
            this.serviceClient.open();
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("", e);
            throw new GmAzureIotHubSpringbootStarterException(e);
        }
    }


    @Override
    public void stop() {

        logger.info("stopping GmAzureMessageHandler, close amqp connections to Azure IoT Hub ");
        try {
            this.isRunning = false;
            this.serviceClient.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("", e);
            throw new GmAzureIotHubSpringbootStarterException(e);
        }
    }


    @Override
    public boolean isRunning() {

        logger.info("isRunning called");
        return this.isRunning;
    }
}

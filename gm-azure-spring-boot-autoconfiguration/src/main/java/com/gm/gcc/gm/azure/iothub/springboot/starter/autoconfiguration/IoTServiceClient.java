package com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration;


import com.microsoft.azure.sdk.iot.service.FeedbackReceiver;
import com.microsoft.azure.sdk.iot.service.Message;
import com.microsoft.azure.sdk.iot.service.ServiceClient;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;


public class IoTServiceClient {

    private ServiceClient serviceClient;

    private static final Logger logger = LoggerFactory.getLogger(IoTServiceClient.class);


    IoTServiceClient(ServiceClient serviceClient) {

        this.serviceClient = serviceClient;

    }


    public FeedbackReceiver getFeedBackReceiver() {

        logger.info("Get FeedBack Receiver from {}", this.serviceClient);
        return this.serviceClient.getFeedbackReceiver();
    }


    public void send(String deviceId, Message message) throws IOException, IotHubException {

        this.serviceClient.send(deviceId, message);
    }


    @PostConstruct
    void init() throws IOException {

        logger.info("IoT Hub service Client Open {}", this.serviceClient);
        this.serviceClient.open();
        //        this.serviceClient.getFeedbackReceiver().open();
        //        this.serviceClient.getFeedbackReceiver().open();
    }


    @PreDestroy
    void tearDown() throws IOException {
        //        this.serviceClient.getFeedbackReceiver().close();
        logger.info("IoT Hub service Client Close {}", this.serviceClient);
        this.serviceClient.close();
    }


}

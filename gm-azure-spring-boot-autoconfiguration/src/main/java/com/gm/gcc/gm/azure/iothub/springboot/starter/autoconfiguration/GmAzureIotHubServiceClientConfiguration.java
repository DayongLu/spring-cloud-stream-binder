package com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration;


import com.gm.gcc.gm.azure.iothub.springboot.starter.exception.GmAzureIotHubSpringbootStarterException;
import com.microsoft.azure.sdk.iot.service.IotHubServiceClientProtocol;
import com.microsoft.azure.sdk.iot.service.ServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@EnableConfigurationProperties(GmAzureIotHubProperties.class)
public class GmAzureIotHubServiceClientConfiguration {

    private GmAzureIotHubProperties azureIotHubProperties;

    private Logger logger = LoggerFactory.getLogger(GmAzureIotHubServiceClientConfiguration.class);


    public GmAzureIotHubServiceClientConfiguration(GmAzureIotHubProperties azureIotHubProperties) {

        this.azureIotHubProperties = azureIotHubProperties;
    }


//    @Bean
    //    public FeedbackReceiver getFeedBackReceiver(IoTServiceClient ioTServiceClient) {
    //        logger.info("Creating FeedbackReceiver {}");
    //        return ioTServiceClient.getFeedBackReceiver();
    //    }




    @Bean
    public IoTServiceClient getIotServiceClient(ServiceClient serviceClient) {
        logger.info("Creating IoTServiceClient {}", serviceClient);
        return new IoTServiceClient(serviceClient);
    }


    @Bean
    public ServiceClient getServiceClient() {

        logger.debug("Creating Azure IotHub Service Client using {}", azureIotHubProperties.toString());
        try {
            return ServiceClient
              .createFromConnectionString(azureIotHubProperties.getConnectString(), IotHubServiceClientProtocol.AMQPS);
        } catch (IOException e) {
            logger.error("ServiceClient creation Failed, at {} configuration.", azureIotHubProperties.toString());
            logger.error("", e);
            throw new GmAzureIotHubSpringbootStarterException(e);
        }
    }

}

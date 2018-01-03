package com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration;


import com.gm.gcc.gm.azure.iothub.springboot.starter.exception.GmAzureIotHubSpringbootStarterException;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;
import com.microsoft.azure.eventhubs.PartitionReceiver;
import com.microsoft.azure.sdk.iot.service.IotHubServiceClientProtocol;
import com.microsoft.azure.sdk.iot.service.ServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@EnableConfigurationProperties(GmAzureIotHubProperties.class)
public class GmAzureEventHubClientConfiguration {

    private GmAzureIotHubProperties azureIotHubProperties;

    private Logger logger = LoggerFactory.getLogger(GmAzureEventHubClientConfiguration.class);


    public GmAzureEventHubClientConfiguration(GmAzureIotHubProperties azureIotHubProperties) {

        this.azureIotHubProperties = azureIotHubProperties;
    }


    @Bean
    public List<PartitionReceiver> getPartitionReceiversLatestOffset() {

        try {
            EventHubClient client = EventHubClient
              .createFromConnectionStringSync(this.azureIotHubProperties.getD2cEventHubCompatibleEndpoint());

            List<PartitionReceiver> partitionReceiverList = new ArrayList<>();

            for (int i = 0; i < this.azureIotHubProperties.getD2cEventHubCompatiblePartitionNum(); i++) {
                PartitionReceiver partitionReceiver = client.createReceiverSync(this.azureIotHubProperties
                  .getConsumerGroup(),String.valueOf(i), Instant.now());
                partitionReceiver.setReceiveTimeout(Duration.ofMinutes(1L));
                partitionReceiverList.add(partitionReceiver);

            }
            return partitionReceiverList;

        } catch (EventHubException e) {
            logger.error(e.getMessage());
            logger.error("", e);
            throw new GmAzureIotHubSpringbootStarterException(e);
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("", e);
            throw new GmAzureIotHubSpringbootStarterException(e);
        }
    }


}

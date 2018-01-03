package com.gm.gcc.azure.iothub.springcloud.binder.config;


import com.gm.gcc.azure.iothub.springcloud.binder.AzureIoTHubBinder;
import com.gm.gcc.azure.iothub.springcloud.binder.provision.AzureIoTHubProvisioner;
import com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration.GmAzureEventHubClientConfiguration;
import com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration.GmAzureIotHubProperties;
import com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration.GmAzureIotHubServiceClientConfiguration;
import com.microsoft.azure.eventhubs.PartitionReceiver;
import com.microsoft.azure.sdk.iot.service.ServiceClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Configuration
@ConditionalOnMissingBean(Binder.class)
@EnableConfigurationProperties(GmAzureIotHubProperties.class)
@Import({ GmAzureIotHubServiceClientConfiguration.class, GmAzureEventHubClientConfiguration.class })
public class GmAzureIoTHubBinderConfiguration {

    private GmAzureIotHubProperties gmAzureIotHubProperties;

    private ServiceClient serviceClient;

    private List<PartitionReceiver> receivers;


    public GmAzureIoTHubBinderConfiguration(GmAzureIotHubProperties gmAzureIotHubProperties,
      ServiceClient serviceClient, List<PartitionReceiver> receivers) {

        this.gmAzureIotHubProperties = gmAzureIotHubProperties;
        this.serviceClient = serviceClient;
        this.receivers = receivers;
    }


    @Bean
    public AzureIoTHubBinder getAzureIotHubBinder() {

        return new AzureIoTHubBinder(gmAzureIotHubProperties, new AzureIoTHubProvisioner(), serviceClient, receivers);
    }


}




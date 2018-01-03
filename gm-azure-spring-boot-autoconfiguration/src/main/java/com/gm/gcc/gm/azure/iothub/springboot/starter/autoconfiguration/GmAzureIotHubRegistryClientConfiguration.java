package com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration;


import com.gm.gcc.gm.azure.iothub.springboot.starter.GmIotHubManager;
import com.gm.gcc.gm.azure.iothub.springboot.starter.GmIotHubManagerImpl;
import com.gm.gcc.gm.azure.iothub.springboot.starter.exception.GmAzureIotHubSpringbootStarterException;
import com.microsoft.azure.sdk.iot.service.RegistryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

//@Configuration
//@ConditionalOnClass(RegistryManager.class)
@EnableConfigurationProperties(GmAzureIotHubProperties.class)
public class GmAzureIotHubRegistryClientConfiguration {

    private GmAzureIotHubProperties azureIotHubProperties;

    private Logger logger = LoggerFactory.getLogger(GmAzureIotHubRegistryClientConfiguration.class);


    public GmAzureIotHubRegistryClientConfiguration(GmAzureIotHubProperties azureIotHubProperties) {

        this.azureIotHubProperties = azureIotHubProperties;
    }


    @Bean
    //    @ConditionalOnMissingBean(value = RegistryManager.class)
    public RegistryManager getRegistryManager() {

        logger.debug("Creating Azure IOTHUB properties {}", azureIotHubProperties.toString());
        try {
            return RegistryManager.createFromConnectionString(azureIotHubProperties.getConnectString());
        } catch (IOException e) {
            logger.error("RegistryManager creation Failed, at {} configuration.", azureIotHubProperties.toString());
            logger.error("", e);
            throw new GmAzureIotHubSpringbootStarterException(e);
        }
    }

    @Bean
    public GmIotHubManager getGmAzureIotHubManager(RegistryManager registryManager){
        return new GmIotHubManagerImpl(registryManager);
    }

}

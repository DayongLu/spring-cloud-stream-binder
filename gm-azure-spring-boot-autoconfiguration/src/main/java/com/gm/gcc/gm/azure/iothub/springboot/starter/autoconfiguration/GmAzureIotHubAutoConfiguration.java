package com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration;


import com.gm.gcc.gm.azure.iothub.springboot.starter.exception.GmAzureIotHubSpringbootStarterException;
import com.microsoft.azure.sdk.iot.service.RegistryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@ConditionalOnClass(RegistryManager.class)
//@ConditionalOnProperty(prefix = "gm.azure.iothub", value = "connect-string-primary-key")
@EnableConfigurationProperties(GmAzureIotHubProperties.class)
public class GmAzureIotHubAutoConfiguration {

    private GmAzureIotHubProperties azureIotHubProperties;

    private Logger logger = LoggerFactory.getLogger(GmAzureIotHubAutoConfiguration.class);


    public GmAzureIotHubAutoConfiguration(GmAzureIotHubProperties azureIotHubProperties) {

        this.azureIotHubProperties = azureIotHubProperties;
    }


    @Bean
    @ConditionalOnMissingBean(value = RegistryManager.class)
    public RegistryManager getRegistryManager() {

        logger.debug("Azure IOTHUB properties {}", azureIotHubProperties.toString());
        try {
            return RegistryManager.createFromConnectionString(azureIotHubProperties.getConnectStringPrimaryKey());
        } catch (IOException e) {
            logger.error("RegistryManager creation Failed, at {} configuration.", azureIotHubProperties.toString());
            logger.error("", e);
            throw new GmAzureIotHubSpringbootStarterException(e);
        }
    }
}

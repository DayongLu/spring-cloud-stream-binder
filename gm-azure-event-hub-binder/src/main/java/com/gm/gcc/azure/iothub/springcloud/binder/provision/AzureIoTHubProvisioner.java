package com.gm.gcc.azure.iothub.springcloud.binder.provision;

import com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration.GmAzureIotHubProperties;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningException;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;

public class AzureIoTHubProvisioner implements
  ProvisioningProvider<ExtendedConsumerProperties<GmAzureIotHubProperties>,
    ExtendedProducerProperties<GmAzureIotHubProperties>> {

    @Override
    public ProducerDestination provisionProducerDestination(String name,
      ExtendedProducerProperties<GmAzureIotHubProperties> properties) throws ProvisioningException {

        return null;
    }


    @Override
    public ConsumerDestination provisionConsumerDestination(String name, String group,
      ExtendedConsumerProperties<GmAzureIotHubProperties> properties) throws ProvisioningException {

        return null;
    }
}

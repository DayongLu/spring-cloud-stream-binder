package com.gm.gcc.azure.iothub.springcloud.binder;

import com.gm.gcc.azure.iothub.springcloud.binder.core.AzureIoTHubInboundAdapter;
import com.gm.gcc.azure.iothub.springcloud.binder.core.container.IoTHubD2CMessageListenerContainerImpl;
import com.gm.gcc.azure.iothub.springcloud.binder.core.container.MessageListenerContainer;
import com.gm.gcc.azure.iothub.springcloud.binder.core.message.IoTHubD2CMessageConverter;
import com.gm.gcc.azure.iothub.springcloud.binder.message.GmAzureMessageHandler;
import com.gm.gcc.azure.iothub.springcloud.binder.provision.AzureIoTHubProvisioner;
import com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration.GmAzureIotHubProperties;
import com.microsoft.azure.eventhubs.PartitionReceiver;
import com.microsoft.azure.sdk.iot.service.ServiceClient;
import org.springframework.cloud.stream.binder.AbstractMessageChannelBinder;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.binder.ExtendedPropertiesBinder;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class AzureIoTHubBinder extends
  AbstractMessageChannelBinder<ExtendedConsumerProperties<GmAzureIotHubProperties>,
    ExtendedProducerProperties<GmAzureIotHubProperties>, AzureIoTHubProvisioner>
  implements ExtendedPropertiesBinder<MessageChannel, GmAzureIotHubProperties, GmAzureIotHubProperties> {

    private GmAzureIotHubProperties gmAzureIotHubProperties;


    private ServiceClient serviceClient;

    private List<PartitionReceiver> receivers;


    public AzureIoTHubBinder(GmAzureIotHubProperties gmAzureIotHubProperties, AzureIoTHubProvisioner provisioner,
      ServiceClient serviceClient, List<PartitionReceiver> receivers) {

        super(true, headersToMap(gmAzureIotHubProperties), provisioner);

        this.gmAzureIotHubProperties = gmAzureIotHubProperties;
        this.serviceClient = serviceClient;
        this.receivers = receivers;
    }


    private static String[] headersToMap(GmAzureIotHubProperties gmAzureIotHubProperties) {

        return new String[0];
    }


    public AzureIoTHubBinder(boolean supportsHeadersNatively, String[] headersToEmbed,
      AzureIoTHubProvisioner provisioningProvider) {

        super(supportsHeadersNatively, headersToEmbed, provisioningProvider);
    }


    @Override
    protected MessageHandler createProducerMessageHandler(ProducerDestination destination,
      ExtendedProducerProperties<GmAzureIotHubProperties> producerProperties) throws Exception {

        return new GmAzureMessageHandler(destination, producerProperties, this.serviceClient);
    }


    @Override
    protected MessageProducer createConsumerEndpoint(ConsumerDestination destination, String group,
      ExtendedConsumerProperties<GmAzureIotHubProperties> properties) throws Exception {

        MessageListenerContainer container = new IoTHubD2CMessageListenerContainerImpl(
          new ScheduledThreadPoolExecutor(properties.getExtension().getD2cEventHubCompatiblePartitionNum()),
          this.receivers);
        AzureIoTHubInboundAdapter azureIoTHubInboundAdapter =
          new AzureIoTHubInboundAdapter(container, new IoTHubD2CMessageConverter());
        return azureIoTHubInboundAdapter;
    }


    @Override
    public GmAzureIotHubProperties getExtendedConsumerProperties(String channelName) {

        return this.gmAzureIotHubProperties;
    }


    @Override
    public GmAzureIotHubProperties getExtendedProducerProperties(String channelName) {

        return this.gmAzureIotHubProperties;
    }

}

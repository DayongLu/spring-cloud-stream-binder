package com.gm.gcc.azure.iothub.springcloud.binder.util;

import com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration.GmAzureIotHubProperties;
import com.microsoft.azure.sdk.iot.service.DeliveryAcknowledgement;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.messaging.Message;

import java.util.Date;
import java.util.UUID;

public class MessageCoverter {

    public static com.microsoft.azure.sdk.iot.service.Message springMessageToAzureIoTMessage(Message<?> message,
      GmAzureIotHubProperties gmAzureIotHubProperties) {

        com.microsoft.azure.sdk.iot.service.Message iotMsg =
          new com.microsoft.azure.sdk.iot.service.Message((byte[]) message.getPayload());
        iotMsg.setUserId(gmAzureIotHubProperties.getName());
        iotMsg.setDeliveryAcknowledgement(
          message.getHeaders().get(AzureIoTHeaders.ACKNOWLEDGE_MODE.getHeaderName()).equals("ACK") ?
            DeliveryAcknowledgement.Full :
            DeliveryAcknowledgement.None);
        iotMsg.setCorrelationId((String) message.getHeaders().get(AzureIoTHeaders.CORRELATION_ID.getHeaderName()));
        iotMsg.setExpiryTimeUtc(
          new Date((Long) message.getHeaders().get(AzureIoTHeaders.EXPIRATION_TIMESTAMP.getHeaderName())));
        iotMsg.setMessageId(UUID.randomUUID().toString());
        iotMsg.setTo((String) message.getHeaders().get(AzureIoTHeaders.DEVICE_ID.getHeaderName()));

        return iotMsg;
    }


}

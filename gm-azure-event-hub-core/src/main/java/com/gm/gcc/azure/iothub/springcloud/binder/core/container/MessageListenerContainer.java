package com.gm.gcc.azure.iothub.springcloud.binder.core.container;

import com.gm.gcc.azure.iothub.springcloud.binder.core.D2CDataListener;
import com.microsoft.azure.eventhubs.EventData;
import org.springframework.context.SmartLifecycle;

public interface MessageListenerContainer extends SmartLifecycle {

    void setupMessageListener(D2CDataListener<EventData> messageListener);

}

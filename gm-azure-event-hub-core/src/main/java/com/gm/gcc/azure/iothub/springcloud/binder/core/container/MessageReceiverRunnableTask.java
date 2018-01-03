package com.gm.gcc.azure.iothub.springcloud.binder.core.container;

import com.gm.gcc.azure.iothub.springcloud.binder.core.D2CDataListener;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubException;
import com.microsoft.azure.eventhubs.PartitionReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.SchedulingAwareRunnable;

import java.util.List;

public class MessageReceiverRunnableTask implements SchedulingAwareRunnable {

    private final PartitionReceiver receiver;

    private List<D2CDataListener> listeners;

    private volatile boolean running = false;

    private static final Logger logger = LoggerFactory.getLogger(MessageReceiverRunnableTask.class);


    public MessageReceiverRunnableTask(PartitionReceiver receiver, List<D2CDataListener> listeners) {

        this.receiver = receiver;
        this.listeners = listeners;
    }


    @Override


    public boolean isLongLived() {

        return true;
    }


    @Override
    public void run() {

        while (isRunning()) {
            try {
                Iterable<EventData> eventDataIterable = receiver.receiveSync(10);
                if (eventDataIterable != null) {
                    eventDataIterable.forEach(eventData -> {
                        for (D2CDataListener listener : this.listeners) {
                            listener.onMessage(eventData);
                        }
                    });
                }
            } catch (EventHubException e) {
                logger.error(e.getMessage());
                logger.error("", e);
            }
        }


    }


    public boolean isRunning() {

        return running;
    }


    public void setRunning(boolean running) {

        this.running = running;
    }
}

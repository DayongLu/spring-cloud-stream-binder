package com.gm.gcc.azure.iothub.springcloud.binder.core.container;

import com.gm.gcc.azure.iothub.springcloud.binder.core.D2CDataListener;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.PartitionReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class IoTHubD2CMessageListenerContainerImpl implements MessageListenerContainer {

    private volatile boolean running = false;

    private final List<D2CDataListener> dataListeners = new ArrayList<>();

    private ScheduledThreadPoolExecutor executor;

    private List<PartitionReceiver> receivers;

    private List<MessageReceiverRunnableTask> tasks;


    public IoTHubD2CMessageListenerContainerImpl(ScheduledThreadPoolExecutor executor,
      List<PartitionReceiver> receivers) {

        this.executor = executor;
        this.receivers = receivers;
    }


    @Override
    public void setupMessageListener(D2CDataListener<EventData> messageListener) {

        dataListeners.add(messageListener);
    }


    @Override
    public boolean isAutoStartup() {

        return false;
    }


    @Override
    public void stop(Runnable callback) {
        //        callback.run();
    }


    @Override
    public void start() {

        tasks = new ArrayList<>();
        for (PartitionReceiver receiver : this.receivers) {
            MessageReceiverRunnableTask task = new MessageReceiverRunnableTask(receiver, dataListeners);
            task.setRunning(true);
            tasks.add(task);
            this.executor.submit(task);
        }
        setRunning(true);

    }


    @Override
    public void stop() {

        for (MessageReceiverRunnableTask task : tasks) {
            task.setRunning(false);


        }
        this.executor.shutdown();
        setRunning(false);
    }


    @Override
    public boolean isRunning() {

        return this.running;
    }


    void setRunning(boolean running) {

        this.running = running;
    }


    @Override
    public int getPhase() {

        return 0;
    }
}

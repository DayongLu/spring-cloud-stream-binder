package com.gm.gcc.azure.iothub.springcloud.binder.core;

public interface D2CDataListener<T> {

    void onMessage(T message);

}

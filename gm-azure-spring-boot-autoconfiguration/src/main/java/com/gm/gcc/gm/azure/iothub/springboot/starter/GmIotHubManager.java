package com.gm.gcc.gm.azure.iothub.springboot.starter;

import com.gm.gcc.gm.azure.iothub.springboot.starter.model.CCMHDevice;
import com.gm.gcc.gm.azure.iothub.springboot.starter.model.CCMHRegistryStatistics;

public interface GmIotHubManager {

    CCMHDevice registerDevice(CCMHDevice ccmhDevice);

    CCMHRegistryStatistics getRegistryStatistics();


}

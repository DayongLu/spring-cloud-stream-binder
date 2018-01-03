package com.gm.gcc.gm.azure.iothub.springboot.starter.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CCMHRegistryStatistics {


    private long totalDeviceCount;
    private long enabledDeviceCount;
    private long disabledDeviceCount;


}

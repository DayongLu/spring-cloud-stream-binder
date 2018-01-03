package com.gm.gcc.gm.azure.iothub.springboot.starter.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = true)
public class CCMHDevice {

    private String deviceId;

    private String vin;

    private String registryHubHost;

    private CCMHAuthenticationType ccmhAuthenticationType;

    private String cert;




}

package com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
@ConfigurationProperties(prefix = "gm.azure.iothub")
public class GmAzureIotHubProperties {

    private String name;

    private String primaryKey;

    private String secondaryKey;

    private String accessRole;

    private String connectString;

    private String d2cEventHubCompatibleName;

    private String d2cEventHubCompatibleEndpoint;

    private int d2cEventHubCompatiblePartitionNum;

    private String consumerGroup;

    //    private Map<String, IotHubInfo> hubInfoMap;


}

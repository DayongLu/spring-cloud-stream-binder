package com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
@ConfigurationProperties(prefix = "gm.azure.iothub")
public class GmAzureIotHubProperties {

    private String name;

    private String accessPolicyName;

    private String connectStringPrimaryKey;

    private String connectStringSecondaryKey;


}

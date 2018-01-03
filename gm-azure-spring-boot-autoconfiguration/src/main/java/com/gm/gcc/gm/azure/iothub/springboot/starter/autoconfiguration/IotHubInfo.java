package com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
@AllArgsConstructor
@NoArgsConstructor
public class IotHubInfo {

    private String name;

    private String primaryKey;

    private String secondaryKey;

    private String accessRole;

    private String connectString;


}

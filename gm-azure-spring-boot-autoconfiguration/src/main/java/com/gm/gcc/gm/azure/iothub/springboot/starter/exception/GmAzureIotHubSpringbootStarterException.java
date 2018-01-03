package com.gm.gcc.gm.azure.iothub.springboot.starter.exception;

import java.io.IOException;

public class GmAzureIotHubSpringbootStarterException extends RuntimeException {

    public GmAzureIotHubSpringbootStarterException(IOException e) {
        super(e);
    }
    public GmAzureIotHubSpringbootStarterException(Exception e) {
        super(e);
    }
}

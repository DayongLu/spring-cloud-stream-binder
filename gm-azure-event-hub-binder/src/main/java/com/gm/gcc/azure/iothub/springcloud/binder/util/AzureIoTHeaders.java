package com.gm.gcc.azure.iothub.springcloud.binder.util;



public enum AzureIoTHeaders {
    SOURCE("SOURCE","src"),
    DESTINATION("DESTINATION","dest"),
    PRIORITY("PRIORITY","pri"),
    SENSITIVE("SENSITIVE","sen"),
    ACKNOWLEDGE_MODE("ACKNOWLEDGE_MODE","delAck"),
    EXPIRATION_TIMESTAMP("EXPIRATION_TIMESTAMP","expTs"),
    DEVICE_ID("DEVICE_ID","did"),
    CONTENT_TYPE("CONTENT_TYPE","ct"),
    CORRELATION_ID("CORRELATION_ID","cid"),
    COMPRESSION_TYPE("COMPRESSION_TYPE","comp"),
    MESSAGE_FORMAT_KEY("MESSAGE_FORMAT_KEY","mfk");

    private String headerName;
    private String shortName;

    private AzureIoTHeaders(String headerName, String shortName){
        this.headerName = headerName;
        this.shortName = shortName;
    }


    public String getHeaderName() {

        return headerName;
    }



    public String getShortName() {

        return shortName;
    }
}

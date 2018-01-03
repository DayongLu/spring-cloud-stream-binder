package com.gm.gcc.gm.azure.iothub.springboot.starter.annotation;


import com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration.EnableAzureIotHubRegistryClientImportSelector;
import com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration.EnableAzureIotHubServiceClientImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(EnableAzureIotHubServiceClientImportSelector.class)
public @interface EnableServiceClient {

    boolean autoRegister() default true;

}

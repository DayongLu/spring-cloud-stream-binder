package com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration;

import com.gm.gcc.gm.azure.iothub.springboot.starter.annotation.EnableEventHubClient;
import com.gm.gcc.gm.azure.iothub.springboot.starter.annotation.EnableServiceClient;
import org.springframework.cloud.commons.util.SpringFactoryImportSelector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class EnableAzureEventHubClientImportSelector extends SpringFactoryImportSelector<EnableEventHubClient> {

    @Override
    protected boolean isEnabled() {

        return false;
    }


    @Override
    protected boolean hasDefaultFactory() {

        return true;
    }


    @Override
    public String[] selectImports(AnnotationMetadata metadata) {

        String[] imports = super.selectImports(metadata);

        AnnotationAttributes attributes =
          AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(getAnnotationClass().getName(), true));

        boolean autoRegister = attributes.getBoolean("autoRegister");

        if (autoRegister) {
            List<String> importsList = new ArrayList<>(Arrays.asList(imports));
            importsList.add("com.gm.gcc.gm.azure.iothub.springboot.starter.autoconfiguration"
              + ".GmAzureEventHubClientConfiguration");
            imports = importsList.toArray(new String[0]);
        }

        return imports;
    }
}

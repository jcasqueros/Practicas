package com.viewnext.springbatchf;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Collections;
import java.util.HashMap;

@SpringBootApplication
@EnableCaching
@EnableBatchProcessing
public class Application {

    public static void main(String[] args) {

        HashMap<String, Object> defaultValues = new HashMap<>();

        SpringApplication application = new SpringApplication(Application.class);
        defaultValues.put("execution", args[0]);
        defaultValues.put("filter", "");


        if (args[0].equals("jobExportarCSV")) {
            application.setDefaultProperties(defaultValues);

            SpringApplication.exit(application.run(args));

        } else {
            if (args.length == 2 && args[0].equals("jobImportarDB")) {
                defaultValues.put("filter", args[1]);
                application.setDefaultProperties(defaultValues);
                SpringApplication.exit(application.run(args));

            }

        }

    }

}

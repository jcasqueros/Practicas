package com.viewnext.springbatchf;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.HashMap;

/**
 * The type Application.
 */
@SpringBootApplication
@EnableCaching
@EnableBatchProcessing
public class Application {

    /**
     * The entry point of application.
     *
     * @param args
     *         the input arguments
     */
    public static void main(String[] args) {

        HashMap<String, Object> defaultValues = new HashMap<>();

        SpringApplication application = new SpringApplication(Application.class);
        defaultValues.put("execution", "");
        defaultValues.put("filter", "");
        if (args.length > 0) {
            defaultValues.put("execution", args[0]);

            if (args.length == 2 && args[0].equals("jobImportarDB")) {
                defaultValues.put("filter", args[1]);

            }

        }
        application.setDefaultProperties(defaultValues);
        SpringApplication.exit(application.run(args));

    }

}

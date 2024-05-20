package com.viewnext.springbatchf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessorConfig {

    @Bean
    public TramoCalleItemProcessor processor() {
        return new TramoCalleItemProcessor();
    }
}

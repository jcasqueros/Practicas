package com.santander.kafkaprovider.config;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class to config {@link ModelMapper}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@Configuration
public class ModelMapperConfig {

    /**
     * Model mapper model mapper.
     *
     * @return the model mapper
     */
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

package com.viewnext.films.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for the application.
 *
 * <p>This class provides configuration settings for the application, including the creation of a {@link ModelMapper}
 * bean. The {@link ModelMapper} bean is used for object mapping, facilitating the conversion between different object
 * types.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // In your application code or other configuration classes, you can autowired the ModelMapper bean
 * \@Autowired
 * private ModelMapper modelMapper;
 *
 * // Now you can use the modelMapper for object mapping in your components
 * // For example:
 * SomeDTO someDTO = modelMapper.map(someEntity, SomeDTO.class);
 * }
 * </pre>
 *
 * <p>Instances of this class are typically used as a configuration source for the Spring framework, defining beans and
 * their dependencies.</p>
 *
 * @author Francisco Balonero Olivera
 * @see org.modelmapper.ModelMapper
 */
@Configuration
@Data
public class AppConfiguration {
    /**
     * Creates a ModelMapper bean.
     *
     * @return {@link ModelMapper} The ModelMapper bean
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * OpenApi configuration
     *
     * @return {@link OpenAPI}
     */
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Practice 3").description("Films")
                .contact(new Contact().name("Francisco Balonero Olivera").url("https://github.com/Francisco-B-O"))
                .version("0.0.1"));
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}

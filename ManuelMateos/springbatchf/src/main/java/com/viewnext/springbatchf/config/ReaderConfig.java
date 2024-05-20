package com.viewnext.springbatchf.config;

import com.viewnext.springbatchf.model.TramoCalle;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ReaderConfig {

    @Value("${file.input}")
    private String fileInput;

    @Bean
    public FlatFileItemReader reader() {
        return new FlatFileItemReaderBuilder<TramoCalle>().name("tramos_calle_list")
                .resource(new ClassPathResource(fileInput)).linesToSkip(1).delimited()
                .names(new String[] { "codigo", "via", "nombre", "primerNum", "ultimoNum", "barrio", "codDistrito",
                        "nomDistrito" }).fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(TramoCalle.class);
                }}).build();
    }
}
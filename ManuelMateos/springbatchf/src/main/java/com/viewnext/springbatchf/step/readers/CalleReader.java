package com.viewnext.springbatchf.step.readers;

import com.viewnext.springbatchf.model.Calle;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

/**
 * A class that provides a FlatFileItemReader bean for reading Calle objects from a CSV file.
 *
 * @author Manuel Mateos de Torres
 */
public class CalleReader {

    /**
     * Creates a FlatFileItemReader bean for reading Calle objects from a CSV file.
     *
     * @param fileInput
     *         the path to the CSV file
     * @return a FlatFileItemReader bean
     */
    @Bean
    public FlatFileItemReader<Calle> reader(String fileInput) {
        return new FlatFileItemReaderBuilder<Calle>().name("tramos_calle_list")
                .resource(new ClassPathResource(fileInput)).linesToSkip(1).delimited().delimiter(",")
                .names(new String[] { "codigo", "via", "nombre", "primerNum", "ultimoNum", "barrio", "codDistrito",
                        "nomDistrito" }).fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Calle.class);
                }}).build();
    }
}

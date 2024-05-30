package com.viewnext.springbatchf.step.chunk;

import com.viewnext.springbatchf.model.entity.Street;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.ResourceLoader;


/**
 * The type Street item reader.
 */
public class StreetItemReader {

    public FlatFileItemReader<Street> reader(ResourceLoader resourceLoader) {

        BeanWrapperFieldSetMapper<Street> fieldSerMapper = new BeanWrapperFieldSetMapper<>();
        fieldSerMapper.setTargetType(Street.class);
        return new FlatFileItemReaderBuilder<Street>().name("customerItemReader")
                .resource(resourceLoader.getResource("classpath:config/tramos_calle_BarrioDismuni.csv")).delimited()
                .delimiter(",")
                .names("CODIGO_CALLE", "TIPO_VIA", "NOMBRE_CALLE", "PRIMER_NUM_TRAMO", "ULTIMO_NUM_TRAMO", "BARRIO",
                        "COD_DISTRITO", "NOM_DISTRITO").fieldSetMapper(fieldSerMapper)
                .build();
    }
}

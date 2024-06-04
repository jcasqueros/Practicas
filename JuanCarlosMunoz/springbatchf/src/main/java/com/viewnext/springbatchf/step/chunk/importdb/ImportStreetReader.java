package com.viewnext.springbatchf.step.chunk.importdb;

import com.viewnext.springbatchf.model.entity.Street;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.Resource;

/**
 * The type Street item reader.
 */
public class ImportStreetReader {

    /**
     * Reader flat file item reader.
     *
     * @param resource
     *         the resource
     * @return the flat file item reader
     */
    public FlatFileItemReader<Street> reader(Resource resource) {

        BeanWrapperFieldSetMapper<Street> fieldSerMapper = new BeanWrapperFieldSetMapper<>();
        fieldSerMapper.setTargetType(Street.class);
        return new FlatFileItemReaderBuilder<Street>().name("customerItemReader").resource(resource).delimited()
                .delimiter(",")
                .names("CODIGO_CALLE", "TIPO_VIA", "NOMBRE_CALLE", "PRIMER_NUM_TRAMO", "ULTIMO_NUM_TRAMO", "BARRIO",
                        "COD_DISTRITO", "NOM_DISTRITO").fieldSetMapper(fieldSerMapper).build();
    }

}

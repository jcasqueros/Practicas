package com.viewnext.springbatch.step;

import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.ClassPathResource;

/**
 * Item reader that reads Calle objects from a flat file.
 *
 * @author Francisco Balonero Olivera
 * @see FlatFileItemReader
 */
public class CalleItemReader {

    /**
     * Creates a new instance of FlatFileItemReader that reads Calle objects from a file.
     *
     * @param fileInput
     *         the file path to read from
     * @return a new instance of FlatFileItemReader
     */
    public FlatFileItemReader<Calle> calleReader(String fileInput) {
        BeanWrapperFieldSetMapper<Calle> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(Calle.class);

        return new FlatFileItemReaderBuilder<Calle>().name("CalleItemReader").resource(new ClassPathResource(fileInput))
                .linesToSkip(1).delimited()
                .names("codigoCalle", "tipoVia", "nombreCalle", "primerNumTramo", "ultimoNumTramo", "barrio",
                        "codDistrito", "nomDistrito").fieldSetMapper(beanWrapperFieldSetMapper).build();
    }
}

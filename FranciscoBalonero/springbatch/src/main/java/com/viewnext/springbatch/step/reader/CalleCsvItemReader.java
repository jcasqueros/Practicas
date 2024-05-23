package com.viewnext.springbatch.step.reader;

import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.ClassPathResource;

/**
 * This class is responsible for reading Calle data from a CSV file.
 *
 * <p>It uses Spring Batch's {@link FlatFileItemReader} to read the CSV file and map the data to a {@link Calle}
 * object.</p>
 *
 * @author Francisco Balonero Olivera
 */
public class CalleCsvItemReader {

    /**
     * Creates a {@link FlatFileItemReader} instance to read Calle data from a CSV file.
     *
     * <p>The reader is configured to skip the first line of the file, use a delimited format, and map the columns to
     * the corresponding fields in the {@link Calle} object.</p>
     *
     * @param fileInput
     *         the path to the CSV file
     * @return a new instance of {@link FlatFileItemReader} configured to read Calle data
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

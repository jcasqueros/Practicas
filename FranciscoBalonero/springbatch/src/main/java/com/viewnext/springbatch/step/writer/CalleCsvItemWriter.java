package com.viewnext.springbatch.step.writer;

import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

/**
 * This class is responsible for writing Calle data to a CSV file.
 *
 * <p>It uses Spring Batch's {@link FlatFileItemWriter} to write the data to a CSV file.</p>
 *
 * @author Francisco Balonero Olivera
 */
public class CalleCsvItemWriter {

    /**
     * Creates a {@link FlatFileItemWriter} instance to write Calle data to a CSV file.
     *
     * <p>The writer is configured to write to a file with the specified name, use a delimited format with a comma as
     * the delimiter, and map the Calle fields to the corresponding columns in the CSV file.</p>
     *
     * @param fileName
     *         the name of the CSV file to write to
     * @return a new instance of {@link FlatFileItemWriter} configured to write Calle data
     */
    public FlatFileItemWriter<Calle> csvItemWriter(String fileName) {
        FlatFileItemWriter<Calle> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(fileName + ".csv"));

        DelimitedLineAggregator<Calle> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");

        BeanWrapperFieldExtractor<Calle> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(
                new String[] { "codigoCalle", "tipoVia", "nombreCalle", "primerNumTramo", "ultimoNumTramo", "barrio",
                        "codDistrito", "nomDistrito" });

        lineAggregator.setFieldExtractor(fieldExtractor);
        writer.setLineAggregator(lineAggregator);

        return writer;
    }
}

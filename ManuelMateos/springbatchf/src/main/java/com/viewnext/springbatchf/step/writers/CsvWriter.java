package com.viewnext.springbatchf.step.writers;

import com.viewnext.springbatchf.model.Calle;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

/**
 * A class that provides a FlatFileItemWriter instance for writing Calle objects to a CSV file.
 *
 * @author Manuel Mateos de Torres
 */
public class CsvWriter {

    /**
     * Creates a FlatFileItemWriter instance for writing Calle objects to a CSV file.
     *
     * @param csv
     *         the path to the CSV file
     * @return a FlatFileItemWriter instance
     */
    public FlatFileItemWriter<Calle> csvWriter(String csv) {
        FlatFileItemWriter<Calle> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("csv/" + csv));
        writer.setLineAggregator(new DelimitedLineAggregator<Calle>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<Calle>() {{
                setNames(new String[] { "codigo", "via", "nombre", "primerNum", "ultimoNum", "barrio", "codDistrito",
                        "nomDistrito" });
            }});
        }});
        return writer;
    }
}
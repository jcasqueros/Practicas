package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.Calle;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

public class CsvWriter {

    public ItemWriter<Calle> csvWriter() {
        FlatFileItemWriter<Calle> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("output.csv"));
        writer.setLineAggregator(new DelimitedLineAggregator<Calle>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<Calle>());
        }});
        return writer;
    }
}

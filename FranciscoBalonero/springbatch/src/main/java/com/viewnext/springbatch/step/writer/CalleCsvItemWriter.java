package com.viewnext.springbatch.step.writer;

import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

public class CalleCsvItemWriter {
    public FlatFileItemWriter<Calle> csvItemWriter(String fileName) {
        FlatFileItemWriter<Calle> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(fileName + ".csv"));
        writer.setLineAggregator(new DelimitedLineAggregator<Calle>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<Calle>());
        }});
        return writer;
    }
}

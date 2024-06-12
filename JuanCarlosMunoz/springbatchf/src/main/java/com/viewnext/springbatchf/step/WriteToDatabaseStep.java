package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.step.chunk.writedatabase.WriteDataBaseWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.MultiResourceItemReader;

/**
 * The type Write to database step.
 */
public class WriteToDatabaseStep {

    /**
     * Write db step step.
     *
     * @param reader
     *         the reader
     * @param writer
     *         the writer
     * @param stepBuilderFactory
     *         the step builder factory
     * @return the step
     */
    public Step writeDbStep(MultiResourceItemReader<Object> reader, WriteDataBaseWriter writer,
            StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("writeDbStep").chunk(10).reader(reader).writer(writer).build();
    }
}


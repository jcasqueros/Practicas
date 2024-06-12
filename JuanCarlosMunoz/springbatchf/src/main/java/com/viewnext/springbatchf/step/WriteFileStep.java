package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.step.chunk.writefile.WriteFileProcessor;
import com.viewnext.springbatchf.step.chunk.writefile.WriteFileWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.MultiResourceItemReader;

/**
 * The type Write file step.
 */
public class WriteFileStep {

    /**
     * Write file step.
     *
     * @param reader
     *         the reader
     * @param processor
     *         the processor
     * @param writer
     *         the writer
     * @param stepBuilderFactory
     *         the step builder factory
     * @return the step
     */
    public Step writeFile(MultiResourceItemReader<Object> reader, WriteFileProcessor processor, WriteFileWriter writer,
            StepBuilderFactory stepBuilderFactory

    ) {

        return stepBuilderFactory.get("writeFileSep").chunk(10).reader(reader).processor(processor)
                .writer(writer).build();
    }

}

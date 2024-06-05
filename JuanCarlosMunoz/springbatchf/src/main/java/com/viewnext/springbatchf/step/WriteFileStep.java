package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.step.chunk.writefile.WriteFileProcessor;
import com.viewnext.springbatchf.step.chunk.writefile.WriteFileWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.MultiResourceItemReader;

public class WriteFileStep {

    public Step writeFile(MultiResourceItemReader<Object> reader, WriteFileProcessor processor, WriteFileWriter writer,
            StepBuilderFactory stepBuilderFactory

    ) {

        return stepBuilderFactory.get("writeFileSep").<Object, Object>chunk(10).reader(reader)
                .processor(processor).writer(writer).build();
    }

}

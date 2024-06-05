package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.step.chunk.writedatabase.WriteDataBaseWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.MultiResourceItemReader;

public class WriteToDatabaseStep {

    public Step writeDbStep(
            MultiResourceItemReader<Object> reader,
            WriteDataBaseWriter writer,
            StepBuilderFactory stepBuilderFactory
    ) {
        return stepBuilderFactory.get("writeDbStep").<Object, Object>chunk(10).reader(reader).writer(writer).build();
    }
}


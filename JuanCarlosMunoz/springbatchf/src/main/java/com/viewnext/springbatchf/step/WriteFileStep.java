package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.entity.Street;
import com.viewnext.springbatchf.step.chunk.writefile.WriteFileProcessor;
import com.viewnext.springbatchf.step.chunk.writefile.WriteFileReader;
import com.viewnext.springbatchf.step.chunk.writefile.WriteFileWriter;
import com.viewnext.springbatchf.step.listener.StreetImportListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.core.io.Resource;

public class WriteFileStep {

    public Step writeFile(WriteFileReader reader, WriteFileProcessor processor, WriteFileWriter writer,
            StepBuilderFactory stepBuilderFactory, StreetImportListener streetImportListener,
            Resource resource

    ) {

        return stepBuilderFactory.get("chunkStep").<Street, Street>chunk(10)
                .reader(reader.reader(resource)).processor(processor).writer(writer).faultTolerant()
                .listener(streetImportListener).build();
    }

}

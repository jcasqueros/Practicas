package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.entity.Street;
import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoProcessor;
import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoReader;
import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoWriter;
import com.viewnext.springbatchf.step.listener.TramoExportListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

public class ExportTramoStep {
    public Step exportChunkStep(

            ExportTramoReader reader,
            ExportTramoProcessor processor,
            ExportTramoWriter writer,
            StepBuilderFactory stepBuilderFactory,
            TramoExportListener tramoExportListener


    ) {

        return stepBuilderFactory
                .get("exportChunkStep")
                .<Street, Street>chunk(10)
                .reader(reader).processor(processor)
                .writer(writer).faultTolerant().listener(tramoExportListener).build();
    }
}

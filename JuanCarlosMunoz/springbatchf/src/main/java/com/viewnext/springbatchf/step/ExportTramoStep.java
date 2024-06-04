package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.entity.Street;
import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoProcessor;
import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoReader;
import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoWriter;
import com.viewnext.springbatchf.step.listener.TramoExportListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

/**
 * The type Export tramo step.
 */
public class ExportTramoStep {
    /**
     * Export chunk step step.
     *
     * @param reader
     *         the reader
     * @param processor
     *         the processor
     * @param writer
     *         the writer
     * @param stepBuilderFactory
     *         the step builder factory
     * @param tramoExportListener
     *         the tramo export listener
     * @return the step
     */
    public Step exportChunkStep(

            ExportTramoReader reader, ExportTramoProcessor processor, ExportTramoWriter writer,
            StepBuilderFactory stepBuilderFactory, TramoExportListener tramoExportListener

    ) {

        return stepBuilderFactory.get("exportChunkStep").<Street, Street>chunk(10).reader(reader).processor(processor)
                .writer(writer).faultTolerant().listener(tramoExportListener).build();
    }
}

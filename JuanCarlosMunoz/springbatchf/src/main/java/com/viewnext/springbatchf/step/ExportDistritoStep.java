package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.entity.District;
import com.viewnext.springbatchf.step.chunk.export.district.ExportDistrictProcessor;
import com.viewnext.springbatchf.step.chunk.export.district.ExportDistrictReader;
import com.viewnext.springbatchf.step.chunk.export.district.ExportDistrictWriter;
import com.viewnext.springbatchf.step.listener.DistrictExportListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

public class ExportDistritoStep {
    public Step exportChunkStep(

            ExportDistrictReader reader, ExportDistrictProcessor processor, ExportDistrictWriter writer,
            StepBuilderFactory stepBuilderFactory, DistrictExportListener districtExportListener

    ) {

        return stepBuilderFactory.get("exportChunkStep").<District, District>chunk(10).reader(reader)
                .processor(processor).writer(writer).listener(districtExportListener).build();
    }
}

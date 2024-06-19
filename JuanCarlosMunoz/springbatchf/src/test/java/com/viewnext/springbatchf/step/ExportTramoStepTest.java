//package com.viewnext.springbatchf.step;
//
//import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoProcessor;
//import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoReader;
//import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoWriter;
//import com.viewnext.springbatchf.step.listener.TramoExportListener;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.step.builder.StepBuilder;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ExportTramoStepTest {
//
//    @Mock
//    private ExportTramoReader reader;
//
//    @Mock
//    private ExportTramoProcessor processor;
//
//    @Mock
//    private ExportTramoWriter writer;
//
//    @Mock
//    private StepBuilderFactory stepBuilderFactory;
//
//    @Mock
//    private TramoExportListener tramoExportListener;
//
//    @InjectMocks
//    private ExportTramoStep exportTramoStep;
//
//    @Test
//    void exportChunkStep_CreatesStepWithCorrectConfiguration() {
//        StepBuilderFactory stepBuilderFactory = mock(StepBuilderFactory.class);
//        StepBuilder stepBuilder = mock(StepBuilder.class);
//        Step step = mock(Step.class);
//
//        when(stepBuilderFactory.get("exportChunkStep")).thenReturn(stepBuilder);
//
//        Step result = exportTramoStep.exportChunkStep(reader, processor, writer, stepBuilderFactory, tramoExportListener);
//
//        assertNotNull(result);
//
//    }
//
//}

//package com.viewnext.springbatchf.step;
//
//import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetProcessor;
//import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetReader;
//import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetWriter;
//import com.viewnext.springbatchf.step.listener.StreetImportListener;
//import com.viewnext.springbatchf.step.skippolicy.StreetSkipPolicy;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.step.builder.SimpleStepBuilder;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.core.io.Resource;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ImportStepTest {
//
//    @InjectMocks
//    private ImportStep importStep;
//
//    @Mock
//    private ImportStreetReader reader;
//
//    @Mock
//    private ImportStreetProcessor processor;
//
//    @Mock
//    private ImportStreetWriter writer;
//
//    @Mock
//    private StepBuilderFactory stepBuilderFactory;
//
//    @Mock
//    private StreetImportListener streetImportListener;
//
//    @Mock
//    private StreetSkipPolicy streetSkipPolicy;
//
//    @Mock
//    private Resource resource;
//
//    private StepBuilder stepBuilder;
//    private SimpleStepBuilder simpleStepBuilder;
//
//    @BeforeEach
//    void setup() {
//        stepBuilder = mock(StepBuilder.class);
//        simpleStepBuilder = mock(SimpleStepBuilder.class);
//        when(stepBuilderFactory.get("chunkStep")).thenReturn(stepBuilder);
//        when(stepBuilder.chunk(10)).thenReturn(simpleStepBuilder);
//    }
//
//    @Test
//    void testImportStep() {
//        // Dado
//        Step step = importStep.importStep(reader, processor, writer, stepBuilderFactory, streetImportListener, streetSkipPolicy, resource);
//
//        // Verificar
//        assertNotNull(step);
//        verify(stepBuilderFactory).get("chunkStep");
//        verify(stepBuilder).chunk(10);
//        verify(simpleStepBuilder).reader(reader.reader(resource));
//        verify(processor).process(null);
//        verify(writer).write(null);
//        verify(streetImportListener).beforeStep(null);
//    }
//}

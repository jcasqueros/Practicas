//package com.viewnext.springbatchf.step;
//
//import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetReader;
//import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetWriter;
//import com.viewnext.springbatchf.step.listener.StreetImportListener;
//import com.viewnext.springbatchf.step.skippolicy.StreetSkipPolicy;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.core.io.Resource;
//import org.springframework.core.task.TaskExecutor;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class MultiThreadedStepTest {
//
//    @Mock
//    private ImportStreetReader reader;
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
//    @Mock
//    private TaskExecutor taskExecutor;
//
//    @InjectMocks
//    private MultiThreadedStep multiThreadedStep;
//
//    @Test
//    void multiThreadStep_CreatesStepWithCorrectConfiguration() {
//        // When
//        Step step = multiThreadedStep.multiThreadStep(reader, writer, stepBuilderFactory, streetImportListener, streetSkipPolicy, resource, taskExecutor);
//
//        // Then
//        assertNotNull(step);
//        verify(stepBuilderFactory).get("chunkStep");
//
//    }
//}

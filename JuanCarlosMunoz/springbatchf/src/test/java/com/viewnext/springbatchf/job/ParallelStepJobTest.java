//package com.viewnext.springbatchf.job;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.job.flow.Flow;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ParallelStepJobTest {
//
//    @Mock
//    private JobBuilderFactory jobBuilderFactory;
//
//    @Mock
//    private Step writeFileStep;
//
//    @Mock
//    private Step writeDbStep;
//
//    @InjectMocks
//    private ParallelStepJob parallelStepJob;
//
//    @Test
//    void job_CreatesJobWithCorrectConfiguration() {
//        // When
//        Job job = parallelStepJob.job(jobBuilderFactory, writeFileStep, writeDbStep);
//
//        // Then
//        assertNotNull(job);
//        verify(jobBuilderFactory).get("parallelJob");
//        JobBuilder jobBuilder = jobBuilderFactory.get("parallelJob");
//        verify(jobBuilder).incrementer(new RunIdIncrementer());
//        verify(jobBuilder).start(any(Flow.class));
//
//    }
//}

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
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class TwoStepJobTest {
//
//    @Mock
//    private JobBuilderFactory jobBuilderFactory;
//
//    @Mock
//    private Step step1;
//
//    @Mock
//    private Step step2;
//
//    @InjectMocks
//    private TwoStepJob twoStepJob;
//
//    @Test
//    void job_CreatesJobWithCorrectConfiguration() {
//        // Given
//        String nameJob = "myJob";
//
//        // When
//        Job job = twoStepJob.job(jobBuilderFactory, step1, step2, nameJob);
//
//        // Then
//        assertNotNull(job);
//        verify(jobBuilderFactory).get(nameJob);
//        JobBuilder jobBuilder = jobBuilderFactory.get(nameJob);
//        verify(jobBuilder).incrementer(new RunIdIncrementer());
//
//    }
//}

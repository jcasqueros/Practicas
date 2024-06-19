//package com.viewnext.springbatchf.job;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.test.context.SpringBatchTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBatchTest
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {OneStepJob.class})
// class OneStepJobTest {
//
//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    private OneStepJob oneStepJob;
//
//    @Qualifier("multiThreadStep")
//    Step multiThreadStep;
//
//    @Test
//    void testJob() {
//
//
//
//        Job job = oneStepJob.job(jobBuilderFactory, multiThreadStep, "multiThreadsJob");
//
//        assertNotNull(job);
//        Assertions.assertEquals("multiThreadsJob", job.getName());
//    }
//}
//

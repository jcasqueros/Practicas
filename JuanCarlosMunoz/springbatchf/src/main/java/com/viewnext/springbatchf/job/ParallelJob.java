package com.viewnext.springbatchf.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

/**
 * The type Parallel job.
 */
public class ParallelJob {

    /**
     * Job job.
     *
     * @param jobBuilderFactory
     *         the job builder factory
     * @param writeFileStep
     *         the write file step
     * @return the job
     */
    //    public Job job(JobBuilderFactory jobBuilderFactory, Step WriteFileStep, Step writeDBStep) {
    public Job job(JobBuilderFactory jobBuilderFactory, Step writeFileStep) {

        return jobBuilderFactory.get("exportJob")
                //For each time we launch a job, we increment the ID
                .incrementer(new RunIdIncrementer())
                //First step that will be executed
                .flow(writeFileStep)
                //                .next(writeDBStep)
                .end().build();
    }

}

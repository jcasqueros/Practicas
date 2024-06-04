package com.viewnext.springbatchf.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

/**
 * The type Multi threaded job.
 */
public class MultiThreadedJob {

    /**
     * Job import data.
     *
     * @param jobBuilderFactory
     *         the job builder factory
     * @param multiThreadStep
     *         the multi thread step
     * @return the job
     */
    public Job job(JobBuilderFactory jobBuilderFactory, Step multiThreadStep) {

        return jobBuilderFactory.get("multiThreadJob")
                //For each time we launch a job, we increment the ID
                .incrementer(new RunIdIncrementer())
                //First step that will be executed
                .flow(multiThreadStep).end().build();
    }

}

package com.viewnext.springbatchf.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

/**
 * The type Multi threaded job.
 */
public class OneStepJob {

    /**
     * Job import data.
     *
     * @param jobBuilderFactory
     *         the job builder factory
     * @param multiThreadStep
     *         the multi thread step
     * @param nameJob
     *         the name job
     * @return the job
     */
    public Job job(JobBuilderFactory jobBuilderFactory, Step multiThreadStep, String nameJob) {

        return jobBuilderFactory.get(nameJob)
                //For each time we launch a job, we increment the ID
                .incrementer(new RunIdIncrementer())
                //First step that will be executed
                .flow(multiThreadStep).end().build();
    }

}

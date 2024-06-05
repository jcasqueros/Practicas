package com.viewnext.springbatchf.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

/**
 * The type Street export job.
 */
public class TwoStepJob {

    /**
     * Job export data to csv.
     *
     * @param jobBuilderFactory
     *         the job builder factory
     * @param step1
     *         the step 1
     * @param step2
     *         the step 2
     * @param nameJob
     *         the name job
     * @return the job
     */
    public Job job(JobBuilderFactory jobBuilderFactory, Step step1, Step step2, String nameJob) {

        return jobBuilderFactory.get(nameJob)
                //For each time we launch a job, we increment the ID
                .incrementer(new RunIdIncrementer())
                //First step that will be executed
                .flow(step1).next(step2).end().build();
    }

}

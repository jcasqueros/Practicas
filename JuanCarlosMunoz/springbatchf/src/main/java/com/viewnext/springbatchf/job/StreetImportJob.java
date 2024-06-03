package com.viewnext.springbatchf.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

/**
 * The type Street import job.
 */
public class StreetImportJob {

    /**
     * Job import data.
     *
     * @param jobBuilderFactory
     *         the job builder factory
     * @param streetChunkStep
     *         the street chunk step
     * @return the job
     */
    public Job job(JobBuilderFactory jobBuilderFactory, Step streetChunkStep) {

        return jobBuilderFactory.get("importJob")
                //For each time we launch a job, we increment the ID
                .incrementer(new RunIdIncrementer())
                //First step that will be executed
                .flow(streetChunkStep).end().build();
    }

}

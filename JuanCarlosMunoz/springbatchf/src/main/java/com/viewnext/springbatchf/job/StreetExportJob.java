package com.viewnext.springbatchf.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

/**
 * The type Street export job.
 */
public class StreetExportJob {

    /**
     * Job export data to csv.
     *
     * @param jobBuilderFactory
     *         the job builder factory
     * @param streetTramosStep
     *         the street tramos step
     * @param streetDistritoStep
     *         the street distrito step
     * @return the job
     */
    public Job job(JobBuilderFactory jobBuilderFactory, Step streetTramosStep, Step streetDistritoStep) {

        return jobBuilderFactory.get("exportJob")
                //For each time we launch a job, we increment the ID
                .incrementer(new RunIdIncrementer())
                //First step that will be executed
                .flow(streetTramosStep).next(streetDistritoStep).end().build();
    }

}

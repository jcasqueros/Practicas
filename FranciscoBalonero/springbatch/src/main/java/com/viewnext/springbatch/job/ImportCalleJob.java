package com.viewnext.springbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;

/**
 * Job configuration for the import Calle job.
 *
 * @author Francisco Balonero Olivera
 */
public class ImportCalleJob {

    /**
     * Creates a new job instance with the given configuration.
     *
     * @param jobRepository
     *         the job repository instance
     * @param listener
     *         the job listener instance
     * @param step1
     *         the first step of the job
     * @return a new job instance
     */
    public Job importCalle(JobRepository jobRepository, JobListener listener, Step step1) {
        return new JobBuilder("importCalleJob").repository(jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(step1).end().build();
    }
}

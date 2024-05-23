package com.viewnext.springbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;

/**
 * This class is responsible for building a job for importing {@link com.viewnext.springbatch.model.Calle} data.
 *
 * <p>It uses Spring Batch's {@link JobBuilder} to create a job that consists of two steps and a job listener.</p>
 *
 * @author Francisco Balonero Olivera
 */
public class ImportCalleJob {

    /**
     * Builds a job for importing {@link com.viewnext.springbatch.model.Calle} data.
     *
     * <p>The job consists of two steps and a job listener. It uses a {@link RunIdIncrementer} to increment the job's
     * run ID.</p>
     *
     * @param jobRepository
     *         the job repository to use
     * @param listener
     *         the job listener to use
     * @param step1
     *         the first step in the job
     * @param step2
     *         the second step in the job
     * @return the built job
     */
    public Job importCalle(JobRepository jobRepository, ImportJobListener listener, Step step1, Step step2) {
        return new JobBuilder("importCalleJob").repository(jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(step1).next(step2).end().build();
    }
}

package com.viewnext.springbatchf.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;

/**
 * A class that builds and configures a job for exporting data.
 *
 * @author Manuel Mateos de Torres
 */
public class ExportJob {

    /**
     * Builds and returns a job instance with the specified steps.
     *
     * @param jobRepository
     *         the job repository
     * @param exportStep1
     *         the first step in the job flow
     * @param exportStep2
     *         the second step in the job flow
     * @return a configured job instance
     */
    public Job exportJob(JobRepository jobRepository, Step exportStep1, Step exportStep2) {
        return new JobBuilder("exportJob").repository(jobRepository).incrementer(new RunIdIncrementer())
                .flow(exportStep1).next(exportStep2).end().build();
    }
}
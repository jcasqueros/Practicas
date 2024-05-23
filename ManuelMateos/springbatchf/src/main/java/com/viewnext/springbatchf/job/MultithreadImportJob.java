package com.viewnext.springbatchf.job;

import com.viewnext.springbatchf.job.listeners.JobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;

/**
 * A class that builds and configures a job for importing data.
 *
 * @author Manuel Mateos de Torres
 */
public class MultithreadImportJob {

    /**
     * Builds and returns a job instance with the specified steps.
     *
     * @param jobRepository
     *         the job repository
     * @param listener
     *         the job listener
     * @param step1
     *         the first step in the job flow
     * @return a configured job instance
     */
    public Job importJob(JobRepository jobRepository, JobListener listener, Step step1) {
        return new JobBuilder("importUserJob").repository(jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(step1).end().build();
    }
}
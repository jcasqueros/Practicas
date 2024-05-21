package com.viewnext.springbatch.job;

import com.viewnext.springbatch.repository.CalleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class JobListener extends JobExecutionListenerSupport {

    private CalleRepository calleRepository;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("FINALIZO EL JOB!");
        }
    }
}
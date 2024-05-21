package com.viewnext.batch01.job.listener;

import com.viewnext.batch01.repository.TramoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class TramoJobListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(TramoJobListener.class.getCanonicalName());

    private TramoRepository repository;

    public TramoJobListener(TramoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job finalizado con éxito");
        }

        if (jobExecution.getStatus().isUnsuccessful()) {
            log.warn("No se pudo completar el job");
        }
    }

}

package com.viewnext.springbatchf.job;

import com.viewnext.springbatchf.model.TramoCalle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private final EntityManager entityManager;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            TypedQuery<TramoCalle> query = entityManager.createQuery("SELECT t FROM TramoCalle t", TramoCalle.class);
            List<TramoCalle> tramosCalle = query.getResultList();
        }
    }
}
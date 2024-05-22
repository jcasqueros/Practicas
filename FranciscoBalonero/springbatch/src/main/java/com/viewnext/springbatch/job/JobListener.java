package com.viewnext.springbatch.job;

import com.mongodb.BasicDBObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Job execution listener that performs actions before and after the job execution.
 *
 * @author Francisco Balonero Olivera
 * @see JobExecutionListenerSupport
 */
@Slf4j
@AllArgsConstructor
public class JobListener extends JobExecutionListenerSupport {

    /**
     * MongoDB template used to interact with the database.
     */
    private MongoTemplate mongoTemplate;

    /**
     * Called before the job execution starts.
     *
     * @param jobExecution
     *         the job execution instance
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Delete all documents from the "calles" collection
        mongoTemplate.getCollection("calles").deleteMany(new BasicDBObject());
    }

    /**
     * Called after the job execution finishes.
     *
     * @param jobExecution
     *         the job execution instance
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("FINALIZO EL JOB!");
        }
    }
}

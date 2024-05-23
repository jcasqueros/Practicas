package com.viewnext.springbatch.job;

import com.mongodb.BasicDBObject;
import com.viewnext.springbatch.model.DistritoViviendas;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Map;

/**
 * This class is responsible for listening to job execution events.
 *
 * <p>It extends Spring Batch's {@link JobExecutionListenerSupport} class to provide before and after job execution
 * methods.</p>
 *
 * @author Francisco Balonero Olivera
 */
@Slf4j
@AllArgsConstructor
public class ImportJobListener extends JobExecutionListenerSupport {

    /**
     * The distrito to process.
     */
    private String distrito;

    /**
     * The MongoTemplate instance to use for database operations.
     */
    private MongoTemplate mongoTemplate;

    /**
     * A map to store the count of viviendas by distrito.
     */
    private Map<String, Long> distritoCounts;

    /**
     * The collection name for distritos restantes.
     */
    private String distritosRestantes;

    /**
     * Called before the job execution starts.
     *
     * <p>Deletes all documents from the specified collections.</p>
     *
     * @param jobExecution
     *         the job execution instance
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        mongoTemplate.getCollection(distrito).deleteMany(new BasicDBObject());
        mongoTemplate.getCollection(distritosRestantes).deleteMany(new BasicDBObject());
        mongoTemplate.getCollection("distritos_viviendas").deleteMany(new BasicDBObject());
    }

    /**
     * Called after the job execution finishes.
     *
     * <p>Saves the distrito counts to the database and logs a message if the job completed successfully.</p>
     *
     * @param jobExecution
     *         the job execution instance
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        distritoCounts.forEach((nomDistrito, count) -> {
            DistritoViviendas dv = new DistritoViviendas();
            dv.setDistrito(nomDistrito);
            dv.setNumViviendas(count);
            mongoTemplate.save(dv);
        });
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("FINALIZO EL JOB!");
        }
    }
}


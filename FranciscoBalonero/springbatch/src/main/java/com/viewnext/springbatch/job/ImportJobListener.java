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

@Slf4j
@AllArgsConstructor
public class ImportJobListener extends JobExecutionListenerSupport {

    private String distrito;
    private MongoTemplate mongoTemplate;
    private Map<String, Long> distritoCounts;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        mongoTemplate.getCollection(distrito).deleteMany(new BasicDBObject());
        mongoTemplate.getCollection("others").deleteMany(new BasicDBObject());
        mongoTemplate.getCollection("distritos_viviendas").deleteMany(new BasicDBObject());
    }

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

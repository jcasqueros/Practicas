package com.viewnext.springbatchf.job;

import com.mongodb.BasicDBObject;
import com.viewnext.springbatchf.model.Calle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class JobListener implements JobExecutionListener {

    private final MongoTemplate mongoTemplate;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            Query query = new Query();
            List<Calle> tramosCalle = mongoTemplate.find(query, Calle.class);
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        mongoTemplate.getCollection("calles").deleteMany(new BasicDBObject());
        mongoTemplate.getCollection("distritoEspecifico").deleteMany(new BasicDBObject());
        mongoTemplate.getCollection("distritos_count").deleteMany(new BasicDBObject());
    }
}
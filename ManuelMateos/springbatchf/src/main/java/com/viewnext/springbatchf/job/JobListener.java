package com.viewnext.springbatchf.job;

import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.data.mongodb.core.MongoTemplate;

@RequiredArgsConstructor
@Slf4j
public class JobListener implements JobExecutionListener {

    private final MongoTemplate mongoTemplate;

    @Override
    public void afterJob(JobExecution jobExecution) {

    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        mongoTemplate.getCollection("distritoEspecifico").deleteMany(new BasicDBObject());
        mongoTemplate.getCollection("otrosDistritos").deleteMany(new BasicDBObject());
        mongoTemplate.getCollection("distrito_count").deleteMany(new BasicDBObject());
    }
}
package com.viewnext.springbatchf.job;

import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class JobListener implements JobExecutionListener {

    private final MongoTemplate mongoTemplate;
    private final Map<String, Long> distritoCounts;

    @Override
    public void afterJob(JobExecution jobExecution) {
        for (Map.Entry<String, Long> entry : distritoCounts.entrySet()) {
            Document document = new Document();
            document.put("nom_distrito", entry.getKey());
            document.put("count", entry.getValue());
            mongoTemplate.getDb().getCollection("distrito_count").insertOne(document);
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        mongoTemplate.getCollection("distritoEspecifico").deleteMany(new BasicDBObject());
        mongoTemplate.getCollection("otrosDistritos").deleteMany(new BasicDBObject());
        mongoTemplate.getCollection("distrito_count").deleteMany(new BasicDBObject());
    }
}
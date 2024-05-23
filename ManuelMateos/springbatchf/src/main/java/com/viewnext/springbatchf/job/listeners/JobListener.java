package com.viewnext.springbatchf.job.listeners;

import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Map;

/**
 * A job execution listener that interacts with a MongoDB database.
 *
 * @author Manuel Mateos de Torres
 */
@RequiredArgsConstructor
@Slf4j
public class JobListener implements JobExecutionListener {

    /**
     * MongoDB template used to interact with the database.
     */
    private final MongoTemplate mongoTemplate;

    /**
     * A map of district names to their corresponding counts.
     */
    private final Map<String, Long> distritoCounts;

    /**
     * Called after the job execution. Inserts the district counts into the "distrito_count" collection.
     *
     * @param jobExecution
     *         the job execution
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        for (Map.Entry<String, Long> entry : distritoCounts.entrySet()) {
            Document document = new Document();
            document.put("nom_distrito", entry.getKey());
            document.put("count", entry.getValue());
            mongoTemplate.getDb().getCollection("distrito_count").insertOne(document);
        }
    }

    /**
     * Called before the job execution. Deletes all documents from the "distritoEspecifico", "otrosDistritos", and
     * "distrito_count" collections.
     *
     * @param jobExecution
     *         the job execution
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        mongoTemplate.getCollection("distritoEspecifico").deleteMany(new BasicDBObject());
        mongoTemplate.getCollection("otrosDistritos").deleteMany(new BasicDBObject());
        mongoTemplate.getCollection("distrito_count").deleteMany(new BasicDBObject());
    }
}
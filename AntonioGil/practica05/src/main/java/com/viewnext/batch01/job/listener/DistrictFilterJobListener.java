package com.viewnext.batch01.job.listener;

import com.viewnext.batch01.job.history.DistrictFilterHistoryEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.item.data.MongoItemWriter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Job listener for district filter jobs.
 *
 * @author Antonio Gil
 */
public class DistrictFilterJobListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(DistrictFilterJobListener.class.getCanonicalName());

    private final MongoItemWriter<DistrictFilterHistoryEntry> mongoItemWriter;

    private final String districtName;

    public DistrictFilterJobListener(MongoItemWriter<DistrictFilterHistoryEntry> mongoItemWriter,
                                     String districtName) {
        this.mongoItemWriter = mongoItemWriter;
        this.districtName = districtName;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Nothing to do here
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        final LocalDateTime dateTime = LocalDateTime.now();
        final ExitStatus exitStatus = jobExecution.getExitStatus();
        final AtomicLong persistedEntryCount = new AtomicLong();

        jobExecution.getStepExecutions().forEach(step -> persistedEntryCount.addAndGet(step.getWriteCount()));

        final var historyEntry = new DistrictFilterHistoryEntry(dateTime, districtName, exitStatus.getExitCode(),
                persistedEntryCount.get());

        try {
            mongoItemWriter.write(List.of(historyEntry));
        } catch (Exception ex) {
            log.error("Error trying to write to history: {}", ex.getMessage());
        }
    }

}

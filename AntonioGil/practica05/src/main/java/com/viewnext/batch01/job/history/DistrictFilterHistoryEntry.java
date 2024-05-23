package com.viewnext.batch01.job.history;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * The {@code DistrictFilterHistoryEntry} encapsulates an entry in the log history for district filtering operations.
 *
 * @author Antonio Gil
 */
@Entity
@IdClass(DistrictFilterHistoryEntryId.class)
@Table(name = "DISTRICT_FILTER_HISTORY",
        uniqueConstraints = @UniqueConstraint(columnNames = {"TIMESTAMP", "DISTRICT_NAME"}))
public class DistrictFilterHistoryEntry {

    @Id
    @Column(name = "TIMESTAMP")
    @Field(name = "TIMESTAMP")
    LocalDateTime timestamp;

    @Id
    @Column(name = "DISTRICT_NAME")
    @Field(name = "DISTRICT_NAME")
    String districtName;

    @Column(name = "JOB_EXIT_STATUS")
    @Field(name = "JOB_EXIT_STATUS")
    String jobExitStatus;

    @Column(name = "PERSISTED_ENTRY_COUNT")
    @Field(name = "PERSISTED_ENTRY_COUNT")
    long persistedEntryCount;

    public DistrictFilterHistoryEntry() {
    }

    public DistrictFilterHistoryEntry(LocalDateTime timestamp, String districtName, String jobExitStatus,
                                      long persistedEntryCount) {
        this.timestamp = timestamp;
        this.districtName = districtName;
        this.jobExitStatus = jobExitStatus;
        this.persistedEntryCount = persistedEntryCount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getJobExitStatus() {
        return jobExitStatus;
    }

    public void setJobExitStatus(String jobExitStatus) {
        this.jobExitStatus = jobExitStatus;
    }

    public long getPersistedEntryCount() {
        return persistedEntryCount;
    }

    public void setPersistedEntryCount(long persistedEntryCount) {
        this.persistedEntryCount = persistedEntryCount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DistrictFilterHistoryEntry.class.getSimpleName() + "[", "]")
                .add("timestamp=" + timestamp)
                .add("districtName='" + districtName + "'")
                .add("jobExitStatus='" + jobExitStatus + "'")
                .add("persistedEntryCount=" + persistedEntryCount)
                .toString();
    }

}

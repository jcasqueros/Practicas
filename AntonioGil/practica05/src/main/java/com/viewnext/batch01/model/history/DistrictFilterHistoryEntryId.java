package com.viewnext.batch01.model.history;

import java.io.Serializable;

/**
 * The {@code DistrictFilterHistoryEntry} is a composite primary key for {@code DistrictFilterHistoryEntry} entities.
 *
 * @author Antonio Gil
 */
public class DistrictFilterHistoryEntryId implements Serializable {

    long timestamp;

    String districtName;

    public DistrictFilterHistoryEntryId() {
        this.timestamp = Long.MIN_VALUE;
        this.districtName = "";
    }

    public DistrictFilterHistoryEntryId(long timestamp, String districtName) {
        this.timestamp = timestamp;
        this.districtName = districtName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public String toString() {
        return "DistrictFilterHistoryEntryId{" +
                "timestamp=" + timestamp +
                ", districtName='" + districtName + '\'' +
                '}';
    }

}

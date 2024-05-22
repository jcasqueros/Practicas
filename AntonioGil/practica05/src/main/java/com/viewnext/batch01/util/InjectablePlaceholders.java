package com.viewnext.batch01.util;

import com.viewnext.batch01.job.history.DistrictFilterHistoryEntry;
import org.springframework.batch.item.data.MongoItemWriter;

public class InjectablePlaceholders {

    private InjectablePlaceholders() {
    }

    public static final String STRING_PLACEHOLDER = null;

    public static final MongoItemWriter<DistrictFilterHistoryEntry> FILTER_HISTORY_WRITER_PLACEHOLDER = null;

}

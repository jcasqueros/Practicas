package com.viewnext.batch01.util;

import com.viewnext.batch01.job.history.DistrictFilterHistoryEntry;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * A utility class with constants that serve as placeholders for parameters that are meant to be injected.
 *
 * @author Antonio Gil
 */
public class InjectablePlaceholders {

    public static final String STRING_PLACEHOLDER = null;
    public static final MongoTemplate MONGO_TEMPLATE_PLACEHOLDER = null;
    public static final MongoItemWriter<DistrictFilterHistoryEntry> FILTER_HISTORY_WRITER_PLACEHOLDER = null;

    private InjectablePlaceholders() {
    }

}

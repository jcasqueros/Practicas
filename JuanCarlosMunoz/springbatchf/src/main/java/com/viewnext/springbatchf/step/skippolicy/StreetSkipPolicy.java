package com.viewnext.springbatchf.step.skippolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * The type Street skip policy.
 */
@Component
public class StreetSkipPolicy implements SkipPolicy {

    private static final Logger logger = LoggerFactory.getLogger(StreetSkipPolicy.class);

    @Qualifier("logWriter")
    private final FileWriter logWriter;

    /**
     * Instantiates a new Street skip policy.
     *
     * @param logWriter
     *         the log writer
     */
    @Autowired
    public StreetSkipPolicy(FileWriter logWriter) {
        this.logWriter = logWriter;
    }

    @Override
    public boolean shouldSkip(Throwable throwable, int skipCount) throws SkipLimitExceededException {

        boolean skip = false;
        try {
            if (throwable instanceof FlatFileParseException) {

                FlatFileParseException ffpe = (FlatFileParseException) throwable;
                String errorMessage = getErrorMessage(ffpe, skipCount);
                logger.error(errorMessage);

                logWriter.write(errorMessage + "\n");

                skip = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return skip;
    }

    private String getErrorMessage(FlatFileParseException ffpe, int skipCount) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return String.format("[%d][%s] Failed to read CSV file at line %d due to content '%s'", skipCount, date,
                ffpe.getLineNumber(), ffpe.getInput());
    }

}

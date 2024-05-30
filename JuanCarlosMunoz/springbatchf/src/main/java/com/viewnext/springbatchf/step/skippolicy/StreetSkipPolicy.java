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

import com.viewnext.springbatchf.step.chunk.StreetItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StreetSkipPolicy implements SkipPolicy {

    private static final Logger logger = LoggerFactory.getLogger(StreetSkipPolicy.class);
    private static final String ERROR_FILE = "error_log.txt";
    private static final String RESOURCE_FOLDER = "src/main/resources/log/";

    @Autowired
    private StreetItemProcessor streetItemProcessor;

    @Override
    public boolean shouldSkip(Throwable throwable, int skipCount) throws SkipLimitExceededException {

        boolean skip = false;

        if (throwable instanceof FlatFileParseException) {

            FlatFileParseException ffpe = (FlatFileParseException) throwable;
            String errorMessage = getErrorMessage(ffpe, skipCount);
            logger.error(errorMessage);
            writeErrorToLogFile(errorMessage);

            skip = true;
        }
        return skip;
    }

    private String getErrorMessage(FlatFileParseException ffpe, int skipCount) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return String.format("[%d][%s] Failed to read CSV file at line %d due to content '%s'", skipCount, date,
                ffpe.getLineNumber(), ffpe.getInput());
    }

    private void writeErrorToLogFile(String errorMessage) {
        try (FileWriter writer = new FileWriter(RESOURCE_FOLDER + ERROR_FILE, true)) {

            writer.write(errorMessage + "\n");

        } catch (IOException e) {
            logger.error("Error al escribir en el fichero de errores", e);
        }
    }

}

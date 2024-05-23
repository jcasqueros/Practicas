package com.viewnext.springbatch.step.chunk;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.step.skip.*;

import com.viewnext.springbatch.model.Direccion;

import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FlatFileParseExceptionSkipListener implements SkipListener<Direccion, Direccion> {

	private static final String LOG_FILE = "src/main/resources/error.log";

    @Override
    public void onSkipInRead(Throwable throwable) {
        logError(throwable);
    }

    @Override
    public void onSkipInWrite(Direccion item, Throwable throwable) {
        logError(throwable);
    }

    @Override
    public void onSkipInProcess(Direccion item, Throwable throwable) {
        logError(throwable);
    }

    private void logError(Throwable throwable) {
        try (Writer writer = new FileWriter(LOG_FILE, true)) {
            writer.write("Error processing record: " + throwable.getMessage() + "\n");
        } catch (IOException e) {
            System.out.println("Some of these files couldn't be processed correctly.");
        }
    }
}

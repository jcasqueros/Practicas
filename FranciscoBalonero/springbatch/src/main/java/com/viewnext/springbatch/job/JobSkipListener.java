package com.viewnext.springbatch.job;

import com.viewnext.springbatch.model.Calle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class JobSkipListener implements SkipListener<Calle, Calle> {

    @Override
    public void onSkipInRead(Throwable throwable) {
        log.error("Error al leer la linea: {}", throwable.getMessage());
        logSkip(throwable.getMessage());
    }

    @Override
    public void onSkipInWrite(Calle calle, Throwable throwable) {
        // No-op
    }

    @Override
    public void onSkipInProcess(Calle calle, Throwable throwable) {
        // No-op
    }

    private void logSkip(String message) {
        try (FileWriter writer = new FileWriter("skipped_records.log", true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            log.error("Error al escribir en el fichero de log", e);
        }
    }
}

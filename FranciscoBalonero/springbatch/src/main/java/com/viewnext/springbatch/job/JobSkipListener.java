package com.viewnext.springbatch.job;

import com.viewnext.springbatch.model.Calle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Skip listener that logs skipped records and writes them to a file.
 *
 * @author Francisco Balonero Olivera
 * @see SkipListener
 */
@Slf4j
public class JobSkipListener implements SkipListener<Calle, Calle> {

    /**
     * Called when a record is skipped during the read phase.
     *
     * @param throwable
     *         the exception that caused the skip
     */
    @Override
    public void onSkipInRead(Throwable throwable) {
        log.error("Error al leer la linea: {}", throwable.getMessage());
        logSkip(throwable.getMessage());
    }

    /**
     * Called when a record is skipped during the write phase.
     *
     * @param calle
     *         the record that was skipped
     * @param throwable
     *         the exception that caused the skip
     */
    @Override
    public void onSkipInWrite(Calle calle, Throwable throwable) {
        // No-op
    }

    /**
     * Called when a record is skipped during the process phase.
     *
     * @param calle
     *         the record that was skipped
     * @param throwable
     *         the exception that caused the skip
     */
    @Override
    public void onSkipInProcess(Calle calle, Throwable throwable) {
        // No-op
    }

    /**
     * Writes a skipped record to a log file.
     *
     * @param message
     *         the message to write to the log file
     */
    private void logSkip(String message) {
        try (FileWriter writer = new FileWriter("skipped_records.log", true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            log.error("Error al escribir en el fichero de log", e);
        }
    }
}

package com.viewnext.springbatch.job;

import com.viewnext.springbatch.model.Calle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is responsible for listening to skip events during the job execution.
 *
 * <p>It implements Spring Batch's {@link SkipListener} interface to handle skip events during the read, process, and
 * write phases of the job.</p>
 *
 * @author Francisco Balonero Olivera
 */
@Slf4j
public class JobSkipListener implements SkipListener<Calle, Calle> {

    /**
     * Called when a skip event occurs during the read phase.
     *
     * <p>Logs an error message with the throwable's message and writes the message to a log file.</p>
     *
     * @param throwable
     *         the throwable that caused the skip
     */
    @Override
    public void onSkipInRead(Throwable throwable) {
        log.error("Error al leer la linea: {}", throwable.getMessage());
        logSkip(throwable.getMessage());
    }

    /**
     * Called when a skip event occurs during the write phase.
     *
     * <p>No-op implementation.</p>
     *
     * @param calle
     *         the Calle object being written
     * @param throwable
     *         the throwable that caused the skip
     */
    @Override
    public void onSkipInWrite(Calle calle, Throwable throwable) {
        // No-op
    }

    /**
     * Called when a skip event occurs during the process phase.
     *
     * <p>No-op implementation.</p>
     *
     * @param calle
     *         the Calle object being processed
     * @param throwable
     *         the throwable that caused the skip
     */
    @Override
    public void onSkipInProcess(Calle calle, Throwable throwable) {
        // No-op
    }

    /**
     * Writes a skip message to a log file.
     *
     * <p>Appends the message to the "skipped_records.log" file.</p>
     *
     * @param message
     *         the message to write
     */
    private void logSkip(String message) {
        try (FileWriter writer = new FileWriter("skipped_records.log", true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            log.error("Error al escribir en el fichero de log", e);
        }
    }
}


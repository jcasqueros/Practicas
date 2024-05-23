package com.viewnext.springbatchf.job.listeners;

import com.viewnext.springbatchf.model.Calle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

/**
 * A skip listener that logs errors when skipping items during a job execution.
 *
 * @author Manuel Mateos de Torres
 */
@Slf4j
public class JobSkipListener implements SkipListener<Calle, Calle> {

    /**
     * Called when an item is skipped during the read phase.
     *
     * @param throwable
     *         the exception that caused the skip
     */
    @Override
    public void onSkipInRead(Throwable throwable) {
        log.error("Error al leer línea: {}", throwable.getMessage());
    }

    /**
     * Called when an item is skipped during the write phase.
     *
     * @param calle
     *         the item being processed
     * @param throwable
     *         the exception that caused the skip
     */
    @Override
    public void onSkipInWrite(Calle calle, Throwable throwable) {

    }

    /**
     * Called when an item is skipped during the process phase.
     *
     * @param calle
     *         the item being processed
     * @param throwable
     *         the exception that caused the skip
     */
    @Override
    public void onSkipInProcess(Calle calle, Throwable throwable) {

    }
}

package com.viewnext.batch01.step.listener;

import com.viewnext.batch01.model.Tramo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.file.FlatFileParseException;

/**
 * An item read listener that logs read errors.
 *
 * @author Antonio Gil
 */
public class TramoReadErrorLogger implements ItemReadListener<Tramo> {

    private static final Logger log = LoggerFactory.getLogger(TramoReadErrorLogger.class.getCanonicalName());
    private static final Logger parseErrorLog =
            LoggerFactory.getLogger(TramoReadErrorLogger.class.getCanonicalName() + "#parseErrorLog");

    @Override
    public void beforeRead() {
        log.trace("Reading line...");
    }

    @Override
    public void afterRead(Tramo tramo) {
        log.trace("Finished reading line");
    }

    @Override
    public void onReadError(Exception ex) {
        log.error("{} :  {}", ex.getClass().getCanonicalName(), ex.getMessage());

        if (ex instanceof FlatFileParseException flatFileParseException) {
            parseErrorLog.error(flatFileParseException.getInput());
        }
    }

}

package com.viewnext.springbatch.step.processor;

import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.item.ItemProcessor;

/**
 * This class is responsible for processing {@link Calle} data before it is written to the output.
 *
 * <p>It implements Spring Batch's {@link ItemProcessor} interface to process {@link Calle} objects.</p>
 *
 * @author Francisco Balonero Olivera
 */
public class CalleExportProcessor implements ItemProcessor<Calle, Calle> {

    /**
     * Processes a {@link Calle} object.
     *
     * <p>In this implementation, the {@link Calle} object is simply returned as is, without any modifications.</p>
     *
     * @param calle
     *         the {@link Calle} object to process
     * @return the processed {@link Calle} object
     * @throws Exception
     *         if any error occurs during processing
     */
    @Override
    public Calle process(Calle calle) throws Exception {
        return calle;
    }
}

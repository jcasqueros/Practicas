package com.viewnext.springbatch.step;

import com.viewnext.springbatch.model.Calle;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;

/**
 * Item processor that filters out Calle objects based on the distrito value.
 *
 * @author Francisco Balonero Olivera
 * @see ItemProcessor
 */
@Slf4j
@AllArgsConstructor
public class CalleItemProcessor implements ItemProcessor<Calle, Calle> {

    /**
     * The distrito value to filter by.
     */
    private String distrito;

    /**
     * Processes a Calle object and returns it if it matches the distrito value, or null otherwise.
     *
     * @param calle
     *         the Calle object to process
     * @return the processed Calle object, or null if it doesn't match the distrito value
     * @throws Exception
     *         if an error occurs during processing
     */
    @Override
    public Calle process(Calle calle) throws Exception {
        if (Objects.equals(calle.getNomDistrito(), distrito)) {
            return calle;
        } else {
            return null;
        }
    }
}

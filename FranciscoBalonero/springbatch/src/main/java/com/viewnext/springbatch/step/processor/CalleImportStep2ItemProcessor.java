package com.viewnext.springbatch.step.processor;

import com.viewnext.springbatch.model.Calle;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;

/**
 * This class is responsible for processing {@link Calle} data during the import step 2.
 *
 * <p>It implements Spring Batch's {@link ItemProcessor} interface to process {@link Calle} objects.</p>
 *
 * @author Francisco Balonero Olivera
 */
@AllArgsConstructor
public class CalleImportStep2ItemProcessor implements ItemProcessor<Calle, Calle> {

    /**
     * The distrito to filter by.
     */
    private String distrito;

    /**
     * Processes a {@link Calle} object.
     *
     * <p>If the {@link Calle} object's nomDistrito does not match the specified distrito, it is returned as is.
     * Otherwise, null is returned.</p>
     *
     * @param calle
     *         the {@link Calle} object to process
     * @return the processed {@link Calle} object, or null if it matches the specified distrito
     * @throws Exception
     *         if any error occurs during processing
     */
    @Override
    public Calle process(Calle calle) throws Exception {
        if (!Objects.equals(calle.getNomDistrito(), distrito)) {
            return calle;
        } else {
            return null;
        }
    }
}

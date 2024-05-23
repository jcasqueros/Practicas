package com.viewnext.springbatch.step.processor;

import com.viewnext.springbatch.model.Calle;
import lombok.Getter;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class is responsible for processing {@link Calle} data during the import step 1.
 *
 * <p>It implements Spring Batch's {@link ItemProcessor} interface to process {@link Calle} objects.</p>
 *
 * @author Francisco Balonero Olivera
 */
@Getter
public class CalleImportStep1ItemProcessor implements ItemProcessor<Calle, Calle> {

    /**
     * The distrito to filter by.
     */
    private final String distrito;

    /**
     * A map to store the count of {@link Calle} objects by distrito.
     */
    private final Map<String, Long> distritoCounts = new HashMap<>();

    /**
     * Creates a new instance of CalleImportStep1ItemProcessor.
     *
     * @param distrito
     *         the distrito to filter by
     */
    public CalleImportStep1ItemProcessor(String distrito) {
        this.distrito = distrito;
    }

    /**
     * Processes a {@link Calle} object.
     *
     * <p>If the Calle object's nomDistrito matches the specified distrito, it is returned as is. Otherwise, null is
     * returned.</p>
     *
     * @param calle
     *         the Calle object to process
     * @return the processed {@link Calle} object, or null if it does not match the specified distrito
     * @throws Exception
     *         if any error occurs during processing
     */
    @Override
    public Calle process(Calle calle) throws Exception {
        String nomDistrito = calle.getNomDistrito();
        distritoCounts.put(nomDistrito, distritoCounts.getOrDefault(nomDistrito, 0L) + 1);
        if (Objects.equals(calle.getNomDistrito(), distrito)) {
            return calle;
        } else {
            return null;
        }
    }
}

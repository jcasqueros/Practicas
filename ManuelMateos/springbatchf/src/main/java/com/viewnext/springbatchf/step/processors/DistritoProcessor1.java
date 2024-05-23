package com.viewnext.springbatchf.step.processors;

import com.viewnext.springbatchf.model.Calle;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * An item processor that processes Calle objects and updates a map of district counts.
 *
 * @author Manuel Mateos de Torres
 */
@Slf4j
@Getter
public class DistritoProcessor1 implements ItemProcessor<Calle, Calle> {

    @Value("${distrito}")
    private String distrito;

    private Map<String, Long> distritoCounts = new HashMap<>();

    /**
     * Processes a Calle object and updates the district counts map.
     *
     * @param calle
     *         the Calle object to process
     * @return the processed Calle object if it's valid, null otherwise
     * @throws Exception
     *         if an error occurs during processing
     */
    @Override
    public Calle process(Calle calle) throws Exception {

        try {
            String nomDistrito = calle.getNomDistrito();
            distritoCounts.put(nomDistrito, distritoCounts.getOrDefault(nomDistrito, 0L) + 1);
            return isValid(calle, distrito);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Checks if a Calle object is valid based on the district name.
     *
     * @param calle
     *         the Calle object to check
     * @param distrito
     *         the district name to filter by
     * @return the processed Calle object if it's valid, null otherwise
     */
    private Calle isValid(Calle calle, String distrito) {
        if (calle.getNomDistrito().equalsIgnoreCase(distrito.toUpperCase())) {
            return processItem(calle);
        }
        return null;
    }

    /**
     * Processes a valid Calle object by converting its fields to uppercase.
     *
     * @param calle
     *         the Calle object to process
     * @return the processed Calle object
     */
    private Calle processItem(Calle calle) {
        calle.setBarrio(calle.getBarrio().toUpperCase());
        calle.setNombre(calle.getNombre().toUpperCase());
        calle.setVia(calle.getVia().toUpperCase());
        calle.setNomDistrito(calle.getNomDistrito().toUpperCase());
        return calle;
    }
}

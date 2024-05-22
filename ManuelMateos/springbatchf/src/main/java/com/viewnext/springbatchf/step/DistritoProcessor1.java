package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.Calle;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class DistritoProcessor1 implements ItemProcessor<Calle, Calle> {

    @Value("${distrito}")
    private String distrito;

    private Map<String, Long> distritoCounts = new HashMap<>();

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

    private Calle isValid(Calle calle, String distrito) {
        if (calle.getNomDistrito().equalsIgnoreCase(distrito.toUpperCase())) {
            return processItem(calle);
        }
        return null;
    }

    private Calle processItem(Calle calle) {
        calle.setBarrio(calle.getBarrio().toUpperCase());
        calle.setNombre(calle.getNombre().toUpperCase());
        calle.setVia(calle.getVia().toUpperCase());
        calle.setNomDistrito(calle.getNomDistrito().toUpperCase());
        return calle;
    }
}

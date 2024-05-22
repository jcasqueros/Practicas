package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.Calle;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Getter
public class DistritoProcessor2 implements ItemProcessor<Calle, Calle> {

    @Value("${distrito}")
    private String distrito;

    @Override
    public Calle process(Calle calle) throws Exception {

        try {
            return isValid(calle, distrito);
        } catch (Exception e) {
            return null;
        }
    }

    private Calle isValid(Calle calle, String distrito) {
        if (calle.getNomDistrito().equalsIgnoreCase(distrito.toUpperCase())) {
            return null;
        }
        return processItem(calle);
    }

    private Calle processItem(Calle calle) {
        calle.setBarrio(calle.getBarrio().toUpperCase());
        calle.setNombre(calle.getNombre().toUpperCase());
        calle.setVia(calle.getVia().toUpperCase());
        calle.setNomDistrito(calle.getNomDistrito().toUpperCase());
        return calle;
    }
}

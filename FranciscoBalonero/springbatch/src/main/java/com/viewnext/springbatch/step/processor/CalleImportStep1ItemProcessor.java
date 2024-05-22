package com.viewnext.springbatch.step.processor;

import com.viewnext.springbatch.model.Calle;
import lombok.Getter;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class CalleImportStep1ItemProcessor implements ItemProcessor<Calle, Calle> {

    private String distrito;

    private Map<String, Long> distritoCounts = new HashMap<>();

    public CalleImportStep1ItemProcessor(String distrito) {
        this.distrito = distrito;
    }

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

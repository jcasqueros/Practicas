package com.viewnext.springbatch.step.processor;

import com.viewnext.springbatch.model.Calle;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;

@AllArgsConstructor
public class CalleImportStep2ItemProcessor implements ItemProcessor<Calle, Calle> {
    private String distrito;

    @Override
    public Calle process(Calle calle) throws Exception {
        if (!Objects.equals(calle.getNomDistrito(), distrito)) {
            return calle;
        } else {
            return null;
        }
    }
}

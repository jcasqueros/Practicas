package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.Calle;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Data
public class DistritoProcessor implements ItemProcessor<Calle, Calle> {

    private String distrito;
    FileWriter writer;

    {
        try {
            writer = new FileWriter("distritosError.log");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Calle process(Calle calle) throws Exception {

        try {
            if (!isValid(calle, distrito)) {
                writer.write("Error al procesar el item {}: {}" + calle + "No cumple con la validación\n");
                return null;
            }
            return processItem(calle);
        } catch (Exception e) {
            writer.write("Error al procesar el item {}: {}" + calle + "No cumple con la validación\n");
            return null;
        }
    }

    private boolean isValid(Calle calle, String distrito) {
        if (calle.getNomDistrito().toUpperCase().equals(distrito.toUpperCase())) {
            return true;
        }
        return false;
    }

    private Calle processItem(Calle calle) {
        calle.setBarrio(calle.getBarrio().toUpperCase());
        calle.setNombre(calle.getNombre().toUpperCase());
        calle.setVia(calle.getVia().toUpperCase());
        calle.setNomDistrito(calle.getNomDistrito().toUpperCase());
        return calle;
    }
}

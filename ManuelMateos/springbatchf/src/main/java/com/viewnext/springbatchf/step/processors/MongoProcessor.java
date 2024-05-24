package com.viewnext.springbatchf.step.processors;

import com.viewnext.springbatchf.model.Calle;
import org.springframework.batch.item.ItemProcessor;

/**
 * An item processor that proccesses Calle Objects.
 *
 * @author Manuel Mateos de Torres
 */
public class MongoProcessor implements ItemProcessor<Calle, Calle> {

    @Override
    public Calle process(Calle calle) throws Exception {
        return calle;
    }
}

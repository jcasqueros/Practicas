package com.viewnext.springbatchf.job;

import com.viewnext.springbatchf.model.Calle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

@Slf4j
public class JobSkipListener implements SkipListener<Calle, Calle> {
    @Override
    public void onSkipInRead(Throwable throwable) {
        log.error("Error al leer línea: {}", throwable.getMessage());
    }

    @Override
    public void onSkipInWrite(Calle calle, Throwable throwable) {

    }

    @Override
    public void onSkipInProcess(Calle calle, Throwable throwable) {

    }
}

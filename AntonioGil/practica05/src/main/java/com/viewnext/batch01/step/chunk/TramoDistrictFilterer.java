package com.viewnext.batch01.step.chunk;

import com.viewnext.batch01.model.Tramo;
import org.springframework.batch.item.ItemProcessor;

public class TramoDistrictFilterer implements ItemProcessor<Tramo, Tramo> {

    private final String districtName;

    public TramoDistrictFilterer() {
        this.districtName = "";
    }

    public TramoDistrictFilterer(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public Tramo process(Tramo tramo) throws Exception {
        return (districtName.equals(tramo.getNombreDistrito())) ? tramo : null;
    }

}

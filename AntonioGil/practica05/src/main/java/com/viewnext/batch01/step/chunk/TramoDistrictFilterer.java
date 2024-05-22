package com.viewnext.batch01.step.chunk;

import com.viewnext.batch01.model.Tramo;
import org.springframework.batch.item.ItemProcessor;

/**
 * An item processor that returns the input {@code Tramo} object if and only if its district is equal to the given
 * district.
 *
 * @author Antonio Gil
 */
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

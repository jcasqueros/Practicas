package com.viewnex.bsan.practica04.sampledata;

import com.viewnex.bsan.practica04.entity.ProductionCompany;

import java.util.List;

/**
 * Sample data for production companies. Useful for unit tests, especially if they involve mocking.
 *
 * @author Antonio Gil
 */
public class ProductionCompanySampleData {

    public static final List<ProductionCompany> SAMPLE_COMPANIES = List.of(
            ProductionCompany.builder().id(1L).name("PRODUCTION_COMPANY1").yearFounded(1995).build(),
            ProductionCompany.builder().id(2L).name("PRODUCTION_COMPANY2").yearFounded(1975).build(),
            ProductionCompany.builder().id(3L).name("PRODUCTION_COMPANY3").yearFounded(1980).build(),
            ProductionCompany.builder().id(4L).name("PRODUCTION_COMPANY4").yearFounded(2000).build(),
            ProductionCompany.builder().id(5L).name("PRODUCTION_COMPANY5").yearFounded(1990).build()
    );

}

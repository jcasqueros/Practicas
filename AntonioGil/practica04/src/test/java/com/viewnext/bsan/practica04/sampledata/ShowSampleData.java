package com.viewnext.bsan.practica04.sampledata;

import com.viewnext.bsan.practica04.entity.Show;

import java.util.List;
import java.util.Set;

/**
 * Sample data for shows. Useful for unit tests, especially if they involve mocking.
 *
 * @author Antonio Gil
 */
public class ShowSampleData {

    public static final List<Show> SAMPLE_SHOWS = List.of(
            Show.builder().id(1L).title("SHOW1").year(2015).director(DirectorSampleData.SAMPLE_DIRECTORS.get(0))
                    .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(0))
                    .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(3), ActorSampleData.SAMPLE_ACTORS.get(4)))
                    .build(),
            Show.builder().id(2L).title("SHOW2").year(2020).director(DirectorSampleData.SAMPLE_DIRECTORS.get(1))
                    .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(1))
                    .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(4), ActorSampleData.SAMPLE_ACTORS.get(0)))
                    .build(),
            Show.builder().id(3L).title("SHOW3").year(2005).director(DirectorSampleData.SAMPLE_DIRECTORS.get(2))
                    .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(2))
                    .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(0), ActorSampleData.SAMPLE_ACTORS.get(1)))
                    .build(),
            Show.builder().id(4L).title("SHOW4").year(2010).director(DirectorSampleData.SAMPLE_DIRECTORS.get(3))
                    .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(3))
                    .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(1), ActorSampleData.SAMPLE_ACTORS.get(2)))
                    .build(),
            Show.builder().id(5L).title("SHOW5").year(1995).director(DirectorSampleData.SAMPLE_DIRECTORS.get(4))
                    .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(4))
                    .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(2), ActorSampleData.SAMPLE_ACTORS.get(3)))
                    .build()
    );

}

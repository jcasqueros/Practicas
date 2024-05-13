package com.viewnex.bsan.practica04.sampledata;

import com.viewnex.bsan.practica04.entity.Film;

import java.util.List;
import java.util.Set;

/**
 * Sample data for films. Useful for unit tests, especially if they involve mocking.
 *
 * @author Antonio Gil
 */
public class FilmSampleData {

    public static final List<Film> SAMPLE_FILMS = List.of(
            Film.builder().id(1L).title("FILM1").year(2005).director(DirectorSampleData.SAMPLE_DIRECTORS.get(4))
                    .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(3))
                    .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(1), ActorSampleData.SAMPLE_ACTORS.get(2)))
                    .build(),
            Film.builder().id(2L).title("FILM2").year(2010).director(DirectorSampleData.SAMPLE_DIRECTORS.get(3))
                    .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(2))
                    .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(2), ActorSampleData.SAMPLE_ACTORS.get(3)))
                    .build(),
            Film.builder().id(3L).title("FILM3").year(2000).director(DirectorSampleData.SAMPLE_DIRECTORS.get(2))
                    .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(1))
                    .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(3), ActorSampleData.SAMPLE_ACTORS.get(4)))
                    .build(),
            Film.builder().id(4L).title("FILM4").year(2015).director(DirectorSampleData.SAMPLE_DIRECTORS.get(1))
                    .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(0))
                    .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(4), ActorSampleData.SAMPLE_ACTORS.get(0)))
                    .build(),
            Film.builder().id(5L).title("FILM5").year(1995).director(DirectorSampleData.SAMPLE_DIRECTORS.get(0))
                    .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(4))
                    .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(0), ActorSampleData.SAMPLE_ACTORS.get(1)))
                    .build()
    );

}

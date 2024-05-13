package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.models.Producer;
import com.pracs.films.persistence.models.Serie;
import com.pracs.films.persistence.repositories.criteria.impl.ActorRepositoryImpl;
import com.pracs.films.persistence.repositories.criteria.impl.DirectorRepositoryImpl;
import com.pracs.films.persistence.repositories.criteria.impl.ProducerRepositoryImpl;
import com.pracs.films.persistence.repositories.criteria.impl.SerieRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ComponentScan("com.pracs.films.persistence.repositories.criteria")
class SerieRepositoryImplTest {

    @Autowired
    private SerieRepositoryImpl serieRepository;

    @Autowired
    private DirectorRepositoryImpl directorRepository;

    @Autowired
    private ActorRepositoryImpl actorRepository;

    @Autowired
    private ProducerRepositoryImpl producerRepository;
    private Actor actor;

    private Director director;

    private Producer producer;

    private Serie serie;

    @BeforeEach
    void setup() {

        actor = new Actor();
        actor.setName("prueba");
        actor.setAge(25);
        actor.setNationality("Spain");
        List<Actor> actors = new ArrayList<>();
        actors.add(actor);

        actorRepository.saveActor(actor);

        director = new Director();
        director.setName("prueba");
        director.setAge(25);
        director.setNationality("Spain");

        directorRepository.saveDirector(director);

        producer = new Producer();
        producer.setName("prueba");
        producer.setDebut(2020);

        producerRepository.saveProducer(producer);

        serie = new Serie();
        serie.setTitle("prueba");
        serie.setDebut(2020);
        serie.setActors(actors);
        serie.setProducer(producer);
        serie.setDirector(director);
    }

    @DisplayName("JUnit test for save a serie")
    @Test
    void givenSerieObject_whenSaveSerie_thenReturnSerieObject() {

        Serie savedSerie = serieRepository.saveSerie(serie);

        assertEquals(serie, savedSerie);
    }

    @DisplayName("JUnit test for update a serie")
    @Test
    void givenSerieObject_whenUpdateSerie_thenReturnSerieObject() {

        serieRepository.saveSerie(serie);

        serie.setTitle("update");
        serie.setDebut(2000);

        serieRepository.updateSerie(serie);

        Optional<Serie> savedSerie = serieRepository.findSerieById(serie.getId());

        assertEquals("update", savedSerie.get().getTitle());
        assertEquals(2000, savedSerie.get().getDebut());
    }

    @DisplayName("JUnit test for get a serie by his id")
    @Test
    void givenSerieId_whenFindSerieById_thenReturnSerieObject() {

        Optional<Serie> savedSerie = serieRepository.findSerieById(1L);

        assertEquals(1L, savedSerie.get().getId());
    }

    @DisplayName("JUnit test for get all series")
    @Test
    void givenNothing_whenFindAllSerie_thenReturnSerieList() {

        Page<Serie> savedSeries = serieRepository.findAllSerie(PageRequest.of(0, 5, Sort.by("title").ascending()));

        assertEquals(5, savedSeries.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all series filtered")
    @Test
    void givenPageableAndAttributesList_whenFindAllSerieFilter_thenReturnSerieList() {

        Page<Serie> savedSeries = serieRepository.findAllFilter(PageRequest.of(0, 5, Sort.by("title").ascending()),
                List.of(), List.of(2020), List.of(), List.of(), List.of());

        assertEquals(5, savedSeries.getNumberOfElements());
    }

    @DisplayName("JUnit test for delete a serie")
    @Test
    void givenSerieId_whenDeleteSerieById_thenDelete() {

        serieRepository.deleteSerieById(serie);

        Optional<Serie> foundSerie = serieRepository.findSerieById(serie.getId());

        assertThat(foundSerie).isEmpty();
    }
}
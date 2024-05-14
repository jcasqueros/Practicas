package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.entity.Producer;
import com.viewnext.films.persistencelayer.entity.Serie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@ComponentScan(basePackages = "com.viewnext.films.persistencelayer.repository.criteria")
@Sql(scripts = "/no-data.sql")
class SerieCriteriaRepositoryTest {
    @Autowired
    SerieCriteriaRepository serieCriteriaRepository;

    @Autowired
    ProducerCriteriaRepository producerCriteriaRepository;

    @Autowired
    DirectorCriteriaRepository directorCriteriaRepository;

    @Autowired
    ActorCriteriaRepository actorCriteriaRepository;
    private Serie serie;

    @BeforeEach
    void setup() {

        Actor actor = new Actor();
        actor.setAge(18);
        actor.setName("Jhon");
        actor.setNationality("spain");

        Director director = new Director();
        director.setAge(18);
        director.setName("James");
        director.setNationality("france");

        Producer producer = new Producer();
        producer.setName("Paramount");
        producer.setFoundationYear(2003);

        serie = new Serie();
        serie.setTitle("Friends");
        serie.setReleaseYear(2004);
        serie.setDirector(directorCriteriaRepository.createDirector(director));
        serie.setProducer(producerCriteriaRepository.createProducer(producer));
        List<Actor> actors = new ArrayList<>();
        actors.add(actorCriteriaRepository.createActor(actor));
        serie.setActors(actors);
    }

    @Test
    @DisplayName("Create serie operation")
    void givenSerieObject_whenCreateSerie_thenReturnCreatedSerie() {

        Serie createdSerie = serieCriteriaRepository.createSerie(serie);

        assertThat(createdSerie).isNotNull();
        assertThat(createdSerie.getTitle()).isEqualTo(serie.getTitle());
        assertThat(createdSerie.getReleaseYear()).isEqualTo(serie.getReleaseYear());
        assertThat(createdSerie.getDirector()).isEqualTo(serie.getDirector());
        assertThat(createdSerie.getProducer()).isEqualTo(serie.getProducer());
        assertThat(createdSerie.getActors()).isEqualTo(serie.getActors());
        assertThat(createdSerie.getId()).isNotNull();

    }

    @Test
    @DisplayName("Get all series operation")
    void givenNothing_whenGetAllSeries_thenReturnPageWithAllSeries() {

        Serie createdSerie = serieCriteriaRepository.createSerie(serie);

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 series
        List<Serie> foundSeries = serieCriteriaRepository.getAllSeries(pageable);

        assertThat(foundSeries).isNotNull();
        assertThat(foundSeries.get(0)).isEqualTo(createdSerie);

    }

    @Test
    @DisplayName("Get serie by id operation")
    void givenId_whenGetSerieById_thenReturnFoundSerie() {

        Serie createdSerie = serieCriteriaRepository.createSerie(serie);

        Optional<Serie> foundSerie = serieCriteriaRepository.getSerieById(createdSerie.getId());

        assertThat(foundSerie).isPresent();
        assertThat(foundSerie).contains(createdSerie);
    }

    @Test
    @DisplayName("Update serie operation")
    void givenSerie_whenUpdateSerie_thenReturnUpdatedSerie() {

        Serie createdSerie = serieCriteriaRepository.createSerie(serie);
        createdSerie.setTitle("Serie de prueba actualizada");
        createdSerie.setReleaseYear(2023);

        Serie updatedSerie = serieCriteriaRepository.updateSerie(createdSerie);

        assertThat(updatedSerie).isNotNull();
        assertThat(updatedSerie.getId()).isEqualTo(createdSerie.getId());
        assertThat(updatedSerie.getTitle()).isEqualTo("Serie de prueba actualizada");
        assertThat(updatedSerie.getReleaseYear()).isEqualTo(2023);
    }

    @Test
    @DisplayName("Delete serie operation")
    void givenId_whenDeleteSerie_thenDeleteSerie() {

        Serie createdSerie = serieCriteriaRepository.createSerie(serie);

        serieCriteriaRepository.deleteSerie(createdSerie.getId());
        Optional<Serie> foundSerie = serieCriteriaRepository.getSerieById(createdSerie.getId());

        assertThat(foundSerie).isEmpty();

    }

    @Test
    @DisplayName("Filter series by title operation")
    void givenTitle_whenFilterSeriesByTitle_thenReturnFilteredSeries() {

        Serie createdSerie = serieCriteriaRepository.createSerie(serie);

        List<String> titles = new ArrayList<>();
        titles.add("Friends");

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 series
        List<Serie> filteredSeries = serieCriteriaRepository.filterSeries(titles, null, null, null, null, pageable);

        assertThat(filteredSeries).isNotNull();
        assertThat(filteredSeries.size()).isEqualTo(1);
        assertThat(filteredSeries.get(0)).isEqualTo(createdSerie);
    }

    @Test
    @DisplayName("Filter series by release year operation")
    void givenReleaseYear_whenFilterSeriesByReleaseYear_thenReturnFilteredSeries() {

        Serie createdSerie = serieCriteriaRepository.createSerie(serie);

        List<Integer> releaseYears = new ArrayList<>();
        releaseYears.add(2004);

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 series
        List<Serie> filteredSeries = serieCriteriaRepository.filterSeries(null, releaseYears, null, null, null,
                pageable);

        assertThat(filteredSeries).isNotNull();
        assertThat(filteredSeries.size()).isEqualTo(1);
        assertThat(filteredSeries.get(0)).isEqualTo(createdSerie);
    }

    @Test
    @DisplayName("Filter series by director operation")
    void givenDirector_whenFilterSeriesByDirector_thenReturnFilteredSeries() {

        Serie createdSerie = serieCriteriaRepository.createSerie(serie);

        Director director = createdSerie.getDirector();
        List<Director> directors = new ArrayList<>();
        directors.add(director);

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 series
        List<Serie> filteredSeries = serieCriteriaRepository.filterSeries(null, null, directors, null, null, pageable);

        assertThat(filteredSeries).isNotNull();
        assertThat(filteredSeries.size()).isEqualTo(1);
        assertThat(filteredSeries.get(0)).isEqualTo(createdSerie);
    }

    @Test
    @DisplayName("Filter series by producer operation")
    void givenProducer_whenFilterSeriesByProducer_thenReturnFilteredSeries() {

        Serie createdSerie = serieCriteriaRepository.createSerie(serie);

        Producer producer = createdSerie.getProducer();
        List<Producer> producers = new ArrayList<>();
        producers.add(producer);

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 series
        List<Serie> filteredSeries = serieCriteriaRepository.filterSeries(null, null, null, producers, null, pageable);

        assertThat(filteredSeries).isNotNull();
        assertThat(filteredSeries.size()).isEqualTo(1);
        assertThat(filteredSeries.get(0)).isEqualTo(createdSerie);
    }

    @Test
    @DisplayName("Filter series by actors operation")
    void givenActor_whenFilterSeriesByActor_thenReturnFilteredSeries() {

        Serie createdSerie = serieCriteriaRepository.createSerie(serie);

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 series
        List<Serie> filteredSeries = serieCriteriaRepository.filterSeries(null, null, null, null,
                createdSerie.getActors(), pageable);

        assertThat(filteredSeries).isNotNull();
        assertThat(filteredSeries.size()).isEqualTo(1);
        assertThat(filteredSeries.get(0)).isEqualTo(createdSerie);
    }

    @Test
    @DisplayName("Filter series by multiple criteria operation")
    void givenMultipleCriteria_whenFilterSeriesByMultipleCriteria_thenReturnFilteredSeries() {

        Serie createdSerie = serieCriteriaRepository.createSerie(serie);

        List<String> titles = new ArrayList<>();
        titles.add("Friends");

        List<Integer> releaseYears = new ArrayList<>();
        releaseYears.add(2004);

        Director director = createdSerie.getDirector();
        List<Director> directors = new ArrayList<>();
        directors.add(director);

        Producer producer = createdSerie.getProducer();
        List<Producer> producers = new ArrayList<>();
        producers.add(producer);

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 series
        List<Serie> filteredSeries = serieCriteriaRepository.filterSeries(titles, releaseYears, directors, producers,
                createdSerie.getActors(), pageable);

        assertThat(filteredSeries).isNotNull();
        assertThat(filteredSeries.size()).isEqualTo(1);
        assertThat(filteredSeries.get(0)).isEqualTo(createdSerie);
    }
}


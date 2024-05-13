package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.models.Film;
import com.pracs.films.persistence.models.Producer;
import com.pracs.films.persistence.repositories.criteria.impl.ActorRepositoryImpl;
import com.pracs.films.persistence.repositories.criteria.impl.DirectorRepositoryImpl;
import com.pracs.films.persistence.repositories.criteria.impl.FilmRepositoryImpl;
import com.pracs.films.persistence.repositories.criteria.impl.ProducerRepositoryImpl;
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
class filmRepositoryImplTest {

    @Autowired
    private FilmRepositoryImpl filmRepository;

    @Autowired
    private DirectorRepositoryImpl directorRepository;

    @Autowired
    private ActorRepositoryImpl actorRepository;

    @Autowired
    private ProducerRepositoryImpl producerRepository;
    private Actor actor;

    private Director director;

    private Producer producer;

    private Film film;

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

        film = new Film();
        film.setTitle("prueba");
        film.setDebut(2020);
        film.setActors(actors);
        film.setProducer(producer);
        film.setDirector(director);
    }

    @DisplayName("JUnit test for save a film")
    @Test
    void givenFilmObject_whenSaveFilm_thenReturnFilmObject() {

        Film savedFilm = filmRepository.saveFilm(film);

        assertEquals(film, savedFilm);
    }

    @DisplayName("JUnit test for update a film")
    @Test
    void givenFilmObject_whenUpdateFilm_thenReturnFilmObject() {

        filmRepository.saveFilm(film);

        film.setTitle("update");
        film.setDebut(2000);

        filmRepository.updateFilm(film);

        Optional<Film> savedFilm = filmRepository.findFilmById(film.getId());

        assertEquals("update", savedFilm.get().getTitle());
        assertEquals(2000, savedFilm.get().getDebut());
    }

    @DisplayName("JUnit test for get a film by his id")
    @Test
    void givenFilmId_whenFindFilmById_thenReturnFilmObject() {

        Optional<Film> savedFilm = filmRepository.findFilmById(1L);

        assertEquals(1L, savedFilm.get().getId());
    }

    @DisplayName("JUnit test for get all films")
    @Test
    void givenNothing_whenFindAllFilm_thenReturnFilmList() {

        Page<Film> savedFilms = filmRepository.findAllFilm(PageRequest.of(0, 5, Sort.by("title").ascending()));

        assertEquals(5, savedFilms.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all films filtered")
    @Test
    void givenPageableAndAttributesList_whenFindAllSerieFilter_thenReturnSerieList() {

        Page<Film> savedFilms = filmRepository.findAllFilter(PageRequest.of(0, 5, Sort.by("title").ascending()),
                List.of(), List.of(2020), List.of(), List.of(), List.of());

        assertEquals(5, savedFilms.getNumberOfElements());
    }

    @DisplayName("JUnit test for delete a film")
    @Test
    void givenFilmId_whenDeleteFilmById_thenDelete() {

        filmRepository.deleteFilmById(film);

        Optional<Film> foundFilm = filmRepository.findFilmById(film.getId());

        assertThat(foundFilm).isEmpty();
    }
}
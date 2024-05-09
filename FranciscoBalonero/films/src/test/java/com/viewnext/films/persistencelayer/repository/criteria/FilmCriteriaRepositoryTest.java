package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.entity.Film;
import com.viewnext.films.persistencelayer.entity.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@ComponentScan(basePackages = "com.viewnext.films.persistencelayer.repository.criteria")
@Sql(scripts = "/no-data.sql")
class FilmCriteriaRepositoryTest {
    @Autowired
    FilmCriteriaRepository filmCriteriaRepository;

    @Autowired
    ProducerCriteriaRepository producerCriteriaRepository;

    @Autowired
    DirectorCriteriaRepository directorCriteriaRepository;

    @Autowired
    ActorCriteriaRepository actorCriteriaRepository;
    private Film film;

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

        film = new Film();
        film.setTitle("Friends");
        film.setReleaseYear(2004);
        film.setDirector(directorCriteriaRepository.createDirector(director));
        film.setProducer(producerCriteriaRepository.createProducer(producer));
        List<Actor> actors = new ArrayList<>();
        actors.add(actorCriteriaRepository.createActor(actor));
        film.setActors(actors);
    }

    @Test
    @DisplayName("Create film operation")
    void givenFilmObject_whenCreateFilm_thenReturnCreatedFilm() {

        Film createdFilm = filmCriteriaRepository.createFilm(film);

        assertThat(createdFilm).isNotNull();
        assertThat(createdFilm.getTitle()).isEqualTo(film.getTitle());
        assertThat(createdFilm.getReleaseYear()).isEqualTo(film.getReleaseYear());
        assertThat(createdFilm.getDirector()).isEqualTo(film.getDirector());
        assertThat(createdFilm.getProducer()).isEqualTo(film.getProducer());
        assertThat(createdFilm.getActors()).isEqualTo(film.getActors());
        assertThat(createdFilm.getId()).isNotNull();

    }

    @Test
    @DisplayName("Get all films operation")
    void givenNothing_whenGetAllFilms_thenReturnListWithAllFilms() {

        Film createdFilm = filmCriteriaRepository.createFilm(film);

        List<Film> foundFilms = filmCriteriaRepository.getAllFilms();

        assertThat(foundFilms).isNotNull();
        assertThat(foundFilms.get(0)).isEqualTo(createdFilm);

    }

    @Test
    @DisplayName("Get film by id operation")
    void givenId_whenGetFilmById_thenReturnFoundFilm() {

        Film createdFilm = filmCriteriaRepository.createFilm(film);

        Optional<Film> foundFilm = filmCriteriaRepository.getFilmById(createdFilm.getId());

        assertThat(foundFilm).isPresent();
        assertThat(foundFilm).contains(createdFilm);
    }

    @Test
    @DisplayName("Update film operation")
    void givenFilm_whenUpdateFilm_thenReturnUpdatedFilm() {

        Film createdFilm = filmCriteriaRepository.createFilm(film);
        createdFilm.setTitle("Film de prueba actualizada");
        createdFilm.setReleaseYear(2023);

        Film updatedFilm = filmCriteriaRepository.updateFilm(createdFilm);

        assertThat(updatedFilm).isNotNull();
        assertThat(updatedFilm.getId()).isEqualTo(createdFilm.getId());
        assertThat(updatedFilm.getTitle()).isEqualTo("Film de prueba actualizada");
        assertThat(updatedFilm.getReleaseYear()).isEqualTo(2023);
    }

    @Test
    @DisplayName("Delete film operation")
    void givenId_whenDeleteFilm_thenDeleteFilm() {

        Film createdFilm = filmCriteriaRepository.createFilm(film);

        filmCriteriaRepository.deleteFilm(createdFilm.getId());
        Optional<Film> foundFilm = filmCriteriaRepository.getFilmById(createdFilm.getId());

        assertThat(foundFilm).isEmpty();

    }
}

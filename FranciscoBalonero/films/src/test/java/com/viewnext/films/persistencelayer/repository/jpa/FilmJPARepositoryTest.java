package com.viewnext.films.persistencelayer.repository.jpa;

import com.viewnext.films.persistencelayer.entity.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@ComponentScan(basePackages = "com.viewnext.films.persistencelayer.repository.jpa")
@Sql(scripts = "/no-data.sql")
class FilmJPARepositoryTest {
    @Autowired
    FilmJPARepository filmJPARepository;
    private Film film;

    @BeforeEach
    void setup() {
        film = new Film();
        film.setTitle("Film 1");
        film.setReleaseYear(2020);
    }

    @Test
    @DisplayName("Save film operation")
    void givenFilmObject_whenSaveFilm_thenReturnSavedFilm() {

        Film savedFilm = filmJPARepository.save(film);

        assertThat(savedFilm).isNotNull();
        assertThat(savedFilm.getTitle()).isEqualTo(film.getTitle());
        assertThat(savedFilm.getReleaseYear()).isEqualTo(film.getReleaseYear());
        assertThat(savedFilm.getId()).isNotNull();

    }

    @Test
    @DisplayName("Find film by id operation")
    void givenId_whenFindFilmById_thenReturnFoundFilm() {

        Film savedFilm = filmJPARepository.save(film);

        Optional<Film> foundFilm = filmJPARepository.findById(savedFilm.getId());

        assertThat(foundFilm).isPresent();
        assertThat(foundFilm).contains(savedFilm);
    }

    @Test
    @DisplayName("Find all films operation")
    void givenNothing_whenFindAllFilms_thenReturnListWithAllFilms() {

        Film savedFilm = filmJPARepository.save(film);

        List<Film> foundFilms = filmJPARepository.findAll();

        assertThat(foundFilms).isNotNull();
        assertThat(foundFilms.get(0)).isEqualTo(savedFilm);

    }

    @Test
    @DisplayName("Delete film by id operation")
    void givenId_whenDeleteFilmById_thenDeleteFilm() {

        Film savedFilm = filmJPARepository.save(film);

        filmJPARepository.deleteById(savedFilm.getId());
        Optional<Film> foundFilm = filmJPARepository.findById(savedFilm.getId());

        assertThat(foundFilm).isEmpty();

    }
}

package com.viewnext.films.persistencelayer.repository.jpa;

import com.viewnext.films.persistencelayer.entity.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

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
        assertThat(savedFilm.getId()).isNotZero();

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
    void givenNothing_whenFindAllFilms_thenReturnPageWithAllFilms() {

        Film savedFilm = filmJPARepository.save(film);

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 films
        Page<Film> foundFilmsPage = filmJPARepository.findAll(pageable);

        assertThat(foundFilmsPage).isNotNull();
        assertThat(foundFilmsPage.getContent().get(0)).isEqualTo(savedFilm);

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

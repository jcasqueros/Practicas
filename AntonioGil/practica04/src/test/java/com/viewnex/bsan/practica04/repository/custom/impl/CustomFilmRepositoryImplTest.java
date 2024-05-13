package com.viewnex.bsan.practica04.repository.custom.impl;

import com.viewnex.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnex.bsan.practica04.entity.Actor;
import com.viewnex.bsan.practica04.entity.Director;
import com.viewnex.bsan.practica04.entity.Film;
import com.viewnex.bsan.practica04.entity.ProductionCompany;
import com.viewnex.bsan.practica04.sampledata.ActorSampleData;
import com.viewnex.bsan.practica04.sampledata.DirectorSampleData;
import com.viewnex.bsan.practica04.sampledata.FilmSampleData;
import com.viewnex.bsan.practica04.sampledata.ProductionCompanySampleData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code CustomActorRepositoryImpl}.
 *
 * @author Antonio Gil
 */
@DataJpaTest
@Import(CrudPeliculasAppTestConfig.class)
class CustomFilmRepositoryImplTest {

    private static final List<Film> SAMPLE_FILMS = FilmSampleData.SAMPLE_FILMS;

    private final CustomFilmRepositoryImpl repository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public CustomFilmRepositoryImplTest(CustomFilmRepositoryImpl repository, TestEntityManager testEntityManager) {
        this.repository = repository;
        this.testEntityManager = testEntityManager;
    }

    private void persistFilmWithNestedEntities(Film film) {
        testEntityManager.persist(film.getDirector());
        testEntityManager.persist(film.getProductionCompany());
        film.getActors().forEach(testEntityManager::persist);
        testEntityManager.persist(film);
    }

    @DisplayName("[CustomFilmRepositoryImpl] findAll (should find empty list)")
    @Test
    void givenNoFilms_whenFindAll_thenReturnEmptyList() {
        final List<Film> foundFilms = repository.getAll();

        assertTrue(foundFilms.isEmpty());
    }

    @DisplayName("[CustomFilmRepositoryImpl] findAll (should find films)")
    @Test
    void givenFilms_whenFindAll_thenReturnFilmList() {
        SAMPLE_FILMS.forEach(this::persistFilmWithNestedEntities);

        final List<Film> foundFilms = repository.getAll();

        assertFalse(foundFilms.isEmpty());
        assertEquals(SAMPLE_FILMS.size(), foundFilms.size());
        assertTrue(foundFilms.containsAll(SAMPLE_FILMS));
    }

    @DisplayName("[CustomFilmRepositoryImpl] existsById (nonexistent film)")
    @Test
    void givenNonExistentFilmId_whenExistsById_thenReturnFalse() {
        final long id = -1;

        assertFalse(repository.existsById(id));
    }

    @DisplayName("[CustomFilmRepositoryImpl] existsById (existing film)")
    @Test
    void givenFilmId_whenExistsById_thenReturnTrue() {
        SAMPLE_FILMS.forEach(this::persistFilmWithNestedEntities);

        final Long id = SAMPLE_FILMS.get(2).getId();

        assertTrue(repository.existsById(id));
    }

    @DisplayName("[CustomFilmRepositoryImpl] findById (nonexistent film)")
    @Test
    void givenNonExistentFilmId_whenFindById_thenReturnEmpty() {
        final long id = -1;

        final Optional<Film> foundFilm = repository.getById(id);

        assertTrue(foundFilm.isEmpty());
    }

    @DisplayName("[CustomFilmRepositoryImpl] findById (existing film)")
    @Test
    void givenFilmId_whenFindById_thenReturnFilmObject() {
        SAMPLE_FILMS.forEach(this::persistFilmWithNestedEntities);

        final Film film = SAMPLE_FILMS.get(4);
        final Long id = film.getId();

        final Film foundFilm = repository.getById(id).orElseThrow();

        assertEquals(id, foundFilm.getId());
        assertEquals(film.getTitle(), foundFilm.getTitle());
        assertEquals(film.getYear(), foundFilm.getYear());
        assertEquals(film.getDirector(), foundFilm.getDirector());
        assertEquals(film.getProductionCompany(), foundFilm.getProductionCompany());
    }

    @DisplayName("[CustomFilmRepositoryImpl] save (invalid data)")
    @Test
    void givenInvalidFilmData_whenSave_thenThrow() {
        final Film invalidFilm = Film.builder().title("FILM6").year(2020).build();

        assertThrows(RuntimeException.class, () -> repository.save(invalidFilm));
    }

    @DisplayName("[CustomFilmRepositoryImpl] save (valid data)")
    @Test
    void givenFilmData_whenSave_thenFilmIsSaved() {
        final Director director = DirectorSampleData.SAMPLE_DIRECTORS.get(2);
        final ProductionCompany company = ProductionCompanySampleData.SAMPLE_COMPANIES.get(0);
        final Set<Actor> actors = new HashSet<>(ActorSampleData.SAMPLE_ACTORS);

        testEntityManager.persist(director);
        testEntityManager.persist(company);
        actors.forEach(testEntityManager::persist);

        final Film film = Film.builder().id(6L).title("FILM6").year(2020).director(director).productionCompany(company)
                .actors(actors).build();

        repository.save(film);

        final Film foundFilm = testEntityManager.find(Film.class, film.getId());

        assertEquals(film.getId(), foundFilm.getId());
        assertEquals(film.getTitle(), foundFilm.getTitle());
        assertEquals(film.getYear(), foundFilm.getYear());
        assertEquals(film.getDirector(), foundFilm.getDirector());
        assertEquals(film.getProductionCompany(), foundFilm.getProductionCompany());
    }

    @DisplayName("[CustomFilmRepositoryImpl] deleteById (nonexistent ID)")
    @Test
    void givenNonExistentFilmId_whenDeleteById_thenNothingHappens() {
        SAMPLE_FILMS.forEach(this::persistFilmWithNestedEntities);
        Predicate<Film> filmIsPresent = film -> testEntityManager.find(Film.class, film.getId()) != null;

        assertTrue(SAMPLE_FILMS.stream().allMatch(filmIsPresent));

        final long id = -1;

        assertFalse(repository.deleteById(id));
        testEntityManager.clear();

        assertTrue(SAMPLE_FILMS.stream().allMatch(filmIsPresent));
    }

    @DisplayName("[CustomFilmRepositoryImpl] deleteById (existing ID)")
    @Test
    void givenFilmId_whenDeleteById_thenFilmIsRemoved() {
        SAMPLE_FILMS.forEach(this::persistFilmWithNestedEntities);
        Predicate<Film> filmIsPresent = film -> testEntityManager.find(Film.class, film.getId()) != null;

        assertTrue(SAMPLE_FILMS.stream().allMatch(filmIsPresent));

        final Film film = SAMPLE_FILMS.get(3);
        final Long id = film.getId();

        assertTrue(repository.deleteById(id));
        testEntityManager.clear();

        assertFalse(SAMPLE_FILMS.stream().allMatch(filmIsPresent));
        assertFalse(filmIsPresent.test(film));
    }

}

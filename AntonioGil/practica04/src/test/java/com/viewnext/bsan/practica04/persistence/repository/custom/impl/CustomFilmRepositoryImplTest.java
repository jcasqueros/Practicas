package com.viewnext.bsan.practica04.persistence.repository.custom.impl;

import com.viewnext.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnext.bsan.practica04.persistence.entity.Actor;
import com.viewnext.bsan.practica04.persistence.entity.Director;
import com.viewnext.bsan.practica04.persistence.entity.Film;
import com.viewnext.bsan.practica04.persistence.entity.ProductionCompany;
import com.viewnext.bsan.practica04.persistence.specification.FilmSpecifications;
import com.viewnext.bsan.practica04.sampledata.FilmSampleData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static com.viewnext.bsan.practica04.sampledata.ActorSampleData.SAMPLE_ACTORS;
import static com.viewnext.bsan.practica04.sampledata.ProductionCompanySampleData.SAMPLE_COMPANIES;
import static com.viewnext.bsan.practica04.sampledata.DirectorSampleData.SAMPLE_DIRECTORS;
import static com.viewnext.bsan.practica04.sampledata.FilmSampleData.SAMPLE_FILMS;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code CustomFilmRepositoryImpl}.
 *
 * @author Antonio Gil
 */
@DataJpaTest
@Import(CrudPeliculasAppTestConfig.class)
class CustomFilmRepositoryImplTest {

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

    @DisplayName("[CustomFilmRepositoryImpl] findAll (no filters)")
    @Test
    void givenFilmsAndNoFilters_whenFindAll_thenReturnPage() {
        // Arrange
        SAMPLE_FILMS.forEach(this::persistFilmWithNestedEntities);

        final int pageSize = 3;
        final PageRequest pageRequest = PageRequest.of(0, pageSize);
        final List<Film> expectedFilms = List.of(
                Film.builder().id(1L).title("FILM1").year(2005).director(SAMPLE_DIRECTORS.get(4))
                        .productionCompany(SAMPLE_COMPANIES.get(3))
                        .actors(Set.of(SAMPLE_ACTORS.get(1), SAMPLE_ACTORS.get(2)))
                        .build(),
                Film.builder().id(2L).title("FILM2").year(2010).director(SAMPLE_DIRECTORS.get(3))
                        .productionCompany(SAMPLE_COMPANIES.get(2))
                        .actors(Set.of(SAMPLE_ACTORS.get(2), SAMPLE_ACTORS.get(3)))
                        .build(),
                Film.builder().id(3L).title("FILM3").year(2000).director(SAMPLE_DIRECTORS.get(2))
                        .productionCompany(SAMPLE_COMPANIES.get(1))
                        .actors(Set.of(SAMPLE_ACTORS.get(3), SAMPLE_ACTORS.get(4)))
                        .build()
        );

        // Act
        final List<Film> foundFilms = repository.findAll(pageRequest);

        // Assert
        assertFalse(foundFilms.isEmpty());
        assertEquals(expectedFilms.size(), foundFilms.size());
        assertTrue(foundFilms.containsAll(expectedFilms));
    }

    @DisplayName("[CustomFilmRepositoryImpl] findAll (no filters)")
    @Test
    void givenCompaniesAndNoFilters_whenFindAll_thenReturnPage() {
        // Arrange
        SAMPLE_FILMS.forEach(this::persistFilmWithNestedEntities);

        final int pageSize = 3;
        final PageRequest pageRequest = PageRequest.of(0, pageSize);
        final List<Film> expectedFilms = List.of(
                Film.builder().id(2L).title("FILM2").year(2010).director(SAMPLE_DIRECTORS.get(3))
                        .productionCompany(SAMPLE_COMPANIES.get(2))
                        .actors(Set.of(SAMPLE_ACTORS.get(2), SAMPLE_ACTORS.get(3)))
                        .build()
        );
        final Specification<Film> yearMatches = FilmSpecifications.yearIsEqualTo(2010);

        // Act
        final List<Film> foundFilms = repository.findAll(yearMatches, pageRequest);

        // Assert
        assertFalse(foundFilms.isEmpty());
        assertEquals(expectedFilms.size(), foundFilms.size());
        assertTrue(foundFilms.containsAll(expectedFilms));
    }

    @DisplayName("[CustomFilmRepositoryImpl] existsById (nonexistent film)")
    @Test
    void givenNonExistentFilmId_whenExistsById_thenReturnFalse() {
        // Arrange
        final long id = -1L;

        // Act + Assert
        assertFalse(repository.existsById(id));
    }

    @DisplayName("[CustomFilmRepositoryImpl] existsById (existing film)")
    @Test
    void givenFilmId_whenExistsById_thenReturnTrue() {
        // Arrange
        SAMPLE_FILMS.forEach(this::persistFilmWithNestedEntities);

        final Long id = SAMPLE_FILMS.get(2).getId();

        // Act + Assert
        assertTrue(repository.existsById(id));
    }

    @DisplayName("[CustomFilmRepositoryImpl] findById (nonexistent film)")
    @Test
    void givenNonExistentFilmId_whenFindById_thenReturnEmpty() {
        // Arrange
        final long id = -1;

        // Act
        final Optional<Film> foundFilm = repository.findById(id);

        // Assert
        assertTrue(foundFilm.isEmpty());
    }

    @DisplayName("[CustomFilmRepositoryImpl] findById (existing film)")
    @Test
    void givenFilmId_whenFindById_thenReturnFilmObject() {
        // Arrange
        SAMPLE_FILMS.forEach(this::persistFilmWithNestedEntities);
        final Film film = SAMPLE_FILMS.get(4);
        final Long id = film.getId();

        // Act
        final Film foundFilm = repository.findById(id).orElseThrow();

        // Assert
        assertEquals(id, foundFilm.getId());
        assertEquals(film.getTitle(), foundFilm.getTitle());
        assertEquals(film.getYear(), foundFilm.getYear());
        assertEquals(film.getDirector(), foundFilm.getDirector());
        assertEquals(film.getProductionCompany(), foundFilm.getProductionCompany());
    }

    @DisplayName("[CustomFilmRepositoryImpl] save (invalid data)")
    @Test
    void givenInvalidFilmData_whenSave_thenThrow() {
        // Arrange
        final Film invalidFilm = Film.builder().title("FILM6").year(2020).build();

        // Act + Assert
        assertThrows(RuntimeException.class, () -> repository.save(invalidFilm));
    }

    @DisplayName("[CustomFilmRepositoryImpl] save (valid data)")
    @Test
    void givenFilmData_whenSave_thenFilmIsSaved() {
        // Arrange
        final Director director = SAMPLE_DIRECTORS.get(2);
        final ProductionCompany company = SAMPLE_COMPANIES.get(0);
        final Set<Actor> actors = new HashSet<>(SAMPLE_ACTORS);

        testEntityManager.persist(director);
        testEntityManager.persist(company);
        actors.forEach(testEntityManager::persist);

        final Film film = Film.builder().id(6L).title("FILM6").year(2020).director(director).productionCompany(company)
                .actors(actors).build();

        // Act
        repository.save(film);

        // Assert
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
        // Arrange
        SAMPLE_FILMS.forEach(this::persistFilmWithNestedEntities);
        Predicate<Film> filmIsPresent = film -> testEntityManager.find(Film.class, film.getId()) != null;

        assertTrue(SAMPLE_FILMS.stream().allMatch(filmIsPresent));

        final long id = -1L;

        // Act + Assert
        assertFalse(repository.deleteById(id));
        testEntityManager.clear();

        assertTrue(SAMPLE_FILMS.stream().allMatch(filmIsPresent));
    }

    @DisplayName("[CustomFilmRepositoryImpl] deleteById (existing ID)")
    @Test
    void givenFilmId_whenDeleteById_thenFilmIsRemoved() {
        // Arrange
        SAMPLE_FILMS.forEach(this::persistFilmWithNestedEntities);
        Predicate<Film> filmIsPresent = film -> testEntityManager.find(Film.class, film.getId()) != null;

        assertTrue(SAMPLE_FILMS.stream().allMatch(filmIsPresent));

        final Film film = SAMPLE_FILMS.get(3);
        final Long id = film.getId();

        // Act + assert
        repository.deleteById(id);
        testEntityManager.clear();

        assertFalse(SAMPLE_FILMS.stream().allMatch(filmIsPresent));
        assertFalse(filmIsPresent.test(film));
    }

}

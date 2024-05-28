package com.viewnext.bsan.practica04.persistence.repository.custom.impl;

import com.viewnext.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnext.bsan.practica04.persistence.entity.Actor;
import com.viewnext.bsan.practica04.persistence.entity.Director;
import com.viewnext.bsan.practica04.persistence.entity.ProductionCompany;
import com.viewnext.bsan.practica04.persistence.entity.Show;
import com.viewnext.bsan.practica04.persistence.specification.ShowSpecifications;
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
import static com.viewnext.bsan.practica04.sampledata.DirectorSampleData.SAMPLE_DIRECTORS;
import static com.viewnext.bsan.practica04.sampledata.ProductionCompanySampleData.SAMPLE_COMPANIES;
import static com.viewnext.bsan.practica04.sampledata.ShowSampleData.SAMPLE_SHOWS;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code CustomShowRepositoryImpl}.
 *
 * @author Antonio Gil
 */
@DataJpaTest
@Import(CrudPeliculasAppTestConfig.class)
class CustomShowRepositoryImplTest {

    private final CustomShowRepositoryImpl repository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public CustomShowRepositoryImplTest(CustomShowRepositoryImpl repository, TestEntityManager testEntityManager) {
        this.repository = repository;
        this.testEntityManager = testEntityManager;
    }

    private void persistShowWithNestedEntities(Show show) {
        testEntityManager.persist(show.getDirector());
        testEntityManager.persist(show.getProductionCompany());
        show.getActors().forEach(testEntityManager::persist);
        testEntityManager.persist(show);
    }

    @DisplayName("[CustomShowRepositoryImpl] findAll (no filters)")
    @Test
    void givenShowsAndNoFilters_whenFindAll_thenReturnPage() {
        // Arrange
        SAMPLE_SHOWS.forEach(this::persistShowWithNestedEntities);

        final int pageSize = 3;
        final PageRequest pageRequest = PageRequest.of(0, pageSize);
        final List<Show> expectedShows = List.of(
                Show.builder().id(1L).title("SHOW1").year(2015).director(SAMPLE_DIRECTORS.get(0))
                        .productionCompany(SAMPLE_COMPANIES.get(0))
                        .actors(Set.of(SAMPLE_ACTORS.get(3), SAMPLE_ACTORS.get(4)))
                        .build(),
                Show.builder().id(2L).title("SHOW2").year(2020).director(SAMPLE_DIRECTORS.get(1))
                        .productionCompany(SAMPLE_COMPANIES.get(1))
                        .actors(Set.of(SAMPLE_ACTORS.get(4), SAMPLE_ACTORS.get(0)))
                        .build(),
                Show.builder().id(3L).title("SHOW3").year(2005).director(SAMPLE_DIRECTORS.get(2))
                        .productionCompany(SAMPLE_COMPANIES.get(2))
                        .actors(Set.of(SAMPLE_ACTORS.get(0), SAMPLE_ACTORS.get(1)))
                        .build()
        );

        // Act
        final List<Show> foundShows = repository.findAll(pageRequest);

        // Assert
        assertFalse(foundShows.isEmpty());
        assertEquals(expectedShows.size(), foundShows.size());
        assertTrue(foundShows.containsAll(expectedShows));
    }

    @DisplayName("[CustomShowRepositoryImpl] findAll (no filters)")
    @Test
    void givenShowsAndFilters_whenFindAll_thenReturnPage() {
        // Arrange
        SAMPLE_SHOWS.forEach(this::persistShowWithNestedEntities);

        final int pageSize = 3;
        final PageRequest pageRequest = PageRequest.of(0, pageSize);
        final List<Show> expectedShows = List.of(
                Show.builder().id(3L).title("SHOW3").year(2005).director(SAMPLE_DIRECTORS.get(2))
                        .productionCompany(SAMPLE_COMPANIES.get(2))
                        .actors(Set.of(SAMPLE_ACTORS.get(0), SAMPLE_ACTORS.get(1)))
                        .build()
        );
        final Specification<Show> yearMatches = ShowSpecifications.yearIsEqualTo(2005);

        // Act
        final List<Show> foundShows = repository.findAll(yearMatches, pageRequest);

        // Assert
        assertFalse(foundShows.isEmpty());
        assertEquals(expectedShows.size(), foundShows.size());
        assertTrue(foundShows.containsAll(expectedShows));
    }

    @DisplayName("[CustomShowRepositoryImpl] existsById (nonexistent film)")
    @Test
    void givenNonExistentShowId_whenExistsById_thenReturnFalse() {
        // Arrange
        final long id = -1L;

        // Act + Assert
        assertFalse(repository.existsById(id));
    }

    @DisplayName("[CustomShowRepositoryImpl] existsById (existing film)")
    @Test
    void givenFilmId_whenExistsById_thenReturnTrue() {
        // Arrange
        SAMPLE_SHOWS.forEach(this::persistShowWithNestedEntities);

        final Long id = SAMPLE_SHOWS.get(2).getId();

        // Act + Assert
        assertTrue(repository.existsById(id));
    }

    @DisplayName("[CustomShowRepositoryImpl] findById (nonexistent film)")
    @Test
    void givenNonExistentFilmId_whenFindById_thenReturnEmpty() {
        // Arrange
        final long id = -1;

        // Act
        final Optional<Show> foundShow = repository.findById(id);

        // Assert
        assertTrue(foundShow.isEmpty());
    }

    @DisplayName("[CustomShowRepositoryImpl] findById (existing film)")
    @Test
    void givenFilmId_whenFindById_thenReturnFilmObject() {
        // Arrange
        SAMPLE_SHOWS.forEach(this::persistShowWithNestedEntities);
        final Show show = SAMPLE_SHOWS.get(4);
        final Long id = show.getId();

        // Act
        final Show foundShow = repository.findById(id).orElseThrow();

        // Assert
        assertEquals(id, foundShow.getId());
        assertEquals(show.getTitle(), foundShow.getTitle());
        assertEquals(show.getYear(), foundShow.getYear());
        assertEquals(show.getDirector(), foundShow.getDirector());
        assertEquals(show.getProductionCompany(), foundShow.getProductionCompany());
    }

    @DisplayName("[CustomShowRepositoryImpl] save (invalid data)")
    @Test
    void givenInvalidShowData_whenSave_thenThrow() {
        // Arrange
        final Show invalidShow = Show.builder().title("SHOW6").year(2020).build();

        // Act + Assert
        assertThrows(RuntimeException.class, () -> repository.save(invalidShow));
    }

    @DisplayName("[CustomShowRepositoryImpl] save (valid data)")
    @Test
    void givenFilmData_whenSave_thenFilmIsSaved() {
        // Arrange
        final Director director = SAMPLE_DIRECTORS.get(2);
        final ProductionCompany company = SAMPLE_COMPANIES.get(0);
        final Set<Actor> actors = new HashSet<>(SAMPLE_ACTORS);

        testEntityManager.persist(director);
        testEntityManager.persist(company);
        actors.forEach(testEntityManager::persist);

        final Show show = Show.builder().id(6L).title("FILM6").year(2020).director(director).productionCompany(company)
                .actors(actors).build();

        // Act
        repository.save(show);

        // Assert
        final Show foundShow = testEntityManager.find(Show.class, show.getId());
        assertEquals(show.getId(), foundShow.getId());
        assertEquals(show.getTitle(), foundShow.getTitle());
        assertEquals(show.getYear(), foundShow.getYear());
        assertEquals(show.getDirector(), foundShow.getDirector());
        assertEquals(show.getProductionCompany(), foundShow.getProductionCompany());
    }

    @DisplayName("[CustomShowRepositoryImpl] deleteById (nonexistent ID)")
    @Test
    void givenNonExistentFilmId_whenDeleteById_thenNothingHappens() {
        // Arrange
        SAMPLE_SHOWS.forEach(this::persistShowWithNestedEntities);
        Predicate<Show> showIsPresent = show -> testEntityManager.find(Show.class, show.getId()) != null;

        assertTrue(SAMPLE_SHOWS.stream().allMatch(showIsPresent));

        final long id = -1L;

        // Act + Assert
        assertFalse(repository.deleteById(id));
        testEntityManager.clear();

        assertTrue(SAMPLE_SHOWS.stream().allMatch(showIsPresent));
    }

    @DisplayName("[CustomShowRepositoryImpl] deleteById (existing ID)")
    @Test
    void givenFilmId_whenDeleteById_thenFilmIsRemoved() {
        // Arrange
        SAMPLE_SHOWS.forEach(this::persistShowWithNestedEntities);
        Predicate<Show> showIsPresent = show -> testEntityManager.find(Show.class, show.getId()) != null;

        assertTrue(SAMPLE_SHOWS.stream().allMatch(showIsPresent));

        final Show show = SAMPLE_SHOWS.get(3);
        final Long id = show.getId();

        // Act + assert
        repository.deleteById(id);
        testEntityManager.clear();

        assertFalse(SAMPLE_SHOWS.stream().allMatch(showIsPresent));
        assertFalse(showIsPresent.test(show));
    }

}

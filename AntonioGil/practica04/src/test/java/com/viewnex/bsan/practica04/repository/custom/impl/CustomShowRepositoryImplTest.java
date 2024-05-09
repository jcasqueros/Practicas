package com.viewnex.bsan.practica04.repository.custom.impl;

import com.viewnex.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnex.bsan.practica04.entity.Actor;
import com.viewnex.bsan.practica04.entity.Director;
import com.viewnex.bsan.practica04.entity.ProductionCompany;
import com.viewnex.bsan.practica04.entity.Show;
import com.viewnex.bsan.practica04.sampledata.ActorSampleData;
import com.viewnex.bsan.practica04.sampledata.DirectorSampleData;
import com.viewnex.bsan.practica04.sampledata.ProductionCompanySampleData;
import com.viewnex.bsan.practica04.sampledata.ShowSampleData;
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

@DataJpaTest
@Import(CrudPeliculasAppTestConfig.class)
class CustomShowRepositoryImplTest {

    private static final List<Show> SAMPLE_SHOWS = ShowSampleData.SAMPLE_SHOWS;

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

    @DisplayName("[ShowRepository] findAll (should find empty list)")
    @Test
    void givenNoShows_whenFindAll_thenReturnEmptyList() {
        final List<Show> foundShows = repository.getAll();

        assertTrue(foundShows.isEmpty());
    }

    @DisplayName("[ShowRepository] findAll (should find shows)")
    @Test
    void givenShows_whenFindAll_thenReturnShowList() {
        SAMPLE_SHOWS.forEach(this::persistShowWithNestedEntities);

        final List<Show> foundShows = repository.getAll();

        assertFalse(foundShows.isEmpty());
        assertEquals(SAMPLE_SHOWS.size(), foundShows.size());
        assertTrue(foundShows.containsAll(SAMPLE_SHOWS));
    }

    @DisplayName("[ShowRepository] existsById (nonexistent show)")
    @Test
    void givenNonExistentShowId_whenExistsById_thenReturnFalse() {
        final long id = -1;

        assertFalse(repository.existsById(id));
    }

    @DisplayName("[ShowRepository] existsById (existing show)")
    @Test
    void givenShowId_whenExistsById_thenReturnTrue() {
        SAMPLE_SHOWS.forEach(this::persistShowWithNestedEntities);

        final Long id = SAMPLE_SHOWS.get(3).getId();

        assertTrue(repository.existsById(id));
    }

    @DisplayName("[ShowRepository] findById (nonexistent show)")
    @Test
    void givenNonExistentShowId_whenFindById_thenReturnEmpty() {
        final long id = -1;

        final Optional<Show> foundShow = repository.getById(id);

        assertTrue(foundShow.isEmpty());
    }

    @DisplayName("[ShowRepository] findById (existing show)")
    @Test
    void givenShowId_whenFindByid_thenReturnShowObject() {
        SAMPLE_SHOWS.forEach(this::persistShowWithNestedEntities);

        final Show show = SAMPLE_SHOWS.get(1);
        final Long id = show.getId();

        final Show foundShow = repository.getById(id).orElseThrow();

        assertEquals(id, foundShow.getId());
        assertEquals(show.getTitle(), foundShow.getTitle());
        assertEquals(show.getYear(), foundShow.getYear());
        assertEquals(show.getDirector(), foundShow.getDirector());
        assertEquals(show.getProductionCompany(), foundShow.getProductionCompany());
    }

    @DisplayName("[ShowRepository] save (invalid data)")
    @Test
    void givenInvalidShowData_whenSave_thenThrow() {
        final Show invalidShow = Show.builder().title("SHOW6").year(2000).build();

        assertThrows(RuntimeException.class, () -> repository.save(invalidShow));
    }

    @DisplayName("[ShowRepository] save (valid data)")
    @Test
    void givenShowData_whenSave_thenShowIsSaved() {
        final Director director = DirectorSampleData.SAMPLE_DIRECTORS.get(0);
        final ProductionCompany company = ProductionCompanySampleData.SAMPLE_COMPANIES.get(4);
        final Set<Actor> actors = new HashSet<>(ActorSampleData.SAMPLE_ACTORS);

        testEntityManager.persist(director);
        testEntityManager.persist(company);
        actors.forEach(testEntityManager::persist);

        final Show show = Show.builder().id(6L).title("SHOW6").year(2000).director(director).productionCompany(company)
                .actors(actors).build();

        repository.save(show);

        final Show foundShow = testEntityManager.find(Show.class, show.getId());

        assertEquals(show.getId(), foundShow.getId());
        assertEquals(show.getTitle(), foundShow.getTitle());
        assertEquals(show.getYear(), foundShow.getYear());
        assertEquals(show.getDirector(), foundShow.getDirector());
        assertEquals(show.getProductionCompany(), foundShow.getProductionCompany());
    }

    @DisplayName("[ShowRepository] deleteById (nonexistent ID)")
    @Test
    void givenNonExistentShowId_whenDeleteById_thenNothingHappens() {
        SAMPLE_SHOWS.forEach(this::persistShowWithNestedEntities);
        Predicate<Show> showIsPresent = show -> testEntityManager.find(Show.class, show.getId()) != null;

        assertTrue(SAMPLE_SHOWS.stream().allMatch(showIsPresent));

        final long id = -1;

        assertFalse(repository.deleteById(id));
        testEntityManager.clear();

        assertTrue(SAMPLE_SHOWS.stream().allMatch(showIsPresent));
    }

    @DisplayName("[ShowRepository] deleteById (existing ID)")
    @Test
    void givenShowId_whenDeleteById_thenShowIsRemoved() {
        SAMPLE_SHOWS.forEach(this::persistShowWithNestedEntities);
        Predicate<Show> showIsPresent = show -> testEntityManager.find(Show.class, show.getId()) != null;

        assertTrue(SAMPLE_SHOWS.stream().allMatch(showIsPresent));

        final Show show = SAMPLE_SHOWS.get(2);
        final Long id = show.getId();

        assertTrue(repository.deleteById(id));
        testEntityManager.clear();

        assertFalse(SAMPLE_SHOWS.stream().allMatch(showIsPresent));
        assertFalse(showIsPresent.test(show));
    }

}

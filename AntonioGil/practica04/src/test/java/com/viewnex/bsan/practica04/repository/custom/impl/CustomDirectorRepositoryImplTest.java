package com.viewnex.bsan.practica04.repository.custom.impl;

import com.viewnex.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnex.bsan.practica04.entity.Director;
import com.viewnex.bsan.practica04.sampledata.DirectorSampleData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code CustomActorRepositoryImpl}.
 *
 * @author Antonio Gil
 */
@DataJpaTest
@Import(CrudPeliculasAppTestConfig.class)
class CustomDirectorRepositoryImplTest {

    private static final List<Director> SAMPLE_DIRECTORS = DirectorSampleData.SAMPLE_DIRECTORS;

    private final CustomDirectorRepositoryImpl repository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public CustomDirectorRepositoryImplTest(CustomDirectorRepositoryImpl repository,
                                            TestEntityManager testEntityManager) {
        this.repository = repository;
        this.testEntityManager = testEntityManager;
    }

    @DisplayName("[CustomDirectorRepositoryImpl] findAll (should find empty list)")
    @Test
    void givenNoDirectors_whenFindAll_thenReturnEmptyList() {
        final List<Director> foundDirectors = repository.getAll();

        assertTrue(foundDirectors.isEmpty());
    }

    @DisplayName("[CustomDirectorRepositoryImpl] findAll (should find directors)")
    @Test
    void givenDirectors_whenFindAll_thenReturnDirectorList() {
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);

        final List<Director> foundDirectors = repository.getAll();

        assertFalse(foundDirectors.isEmpty());
        assertEquals(SAMPLE_DIRECTORS.size(), foundDirectors.size());
        assertTrue(foundDirectors.containsAll(SAMPLE_DIRECTORS));
    }

    @DisplayName("[CustomDirectorRepositoryImpl] existsById (nonexistent director)")
    @Test
    void givenNonExistentDirectorId_whenExistsById_thenReturnFalse() {
        final long id = -1;

        assertFalse(repository.existsById(id));
    }

    @DisplayName("[CustomDirectorRepositoryImpl] existsById (existing director)")
    @Test
    void givenDirectorId_whenExistsById_thenReturnTrue() {
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);

        final Long id = SAMPLE_DIRECTORS.get(4).getId();

        assertTrue(repository.existsById(id));
    }

    @DisplayName("[CustomDirectorRepositoryImpl] findById (nonexistent director)")
    @Test
    void givenNonExistentDirectorId_whenFindById_thenReturnEmpty() {
        final long id = -1;

        final Optional<Director> foundDirector = repository.getById(id);

        assertTrue(foundDirector.isEmpty());
    }

    @DisplayName("[CustomDirectorRepositoryImpl] findById (existing director)")
    @Test
    void givenDirectorId_whenFindById_thenReturnDirectorObject() {
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);

        final Director director = SAMPLE_DIRECTORS.get(3);
        final Long id = director.getId();

        final Director foundDirector = repository.getById(id).orElseThrow();

        assertEquals(id, foundDirector.getId());
        assertEquals(director.getName(), foundDirector.getName());
        assertEquals(director.getAge(), foundDirector.getAge());
        assertEquals(director.getNationality(), foundDirector.getNationality());
    }

    @DisplayName("[CustomDirectorRepositoryImpl] save (invalid data)")
    @Test
    void givenInvalidDirectorData_whenSave_thenThrow() {
        final Director invalidDirector = Director.builder().name("DIRECTOR6").age(70).nationality("USA").build();

        assertThrows(RuntimeException.class, () -> repository.save(invalidDirector));
    }

    @DisplayName("[CustomDirectorRepositoryImpl] save (valid data)")
    @Test
    void givenDirectorData_whenSave_thenDirectorIsSaved() {
        final Director director = Director.builder().id(6L).name("DIRECTOR6").age(70).nationality("USA").build();

        repository.save(director);

        final Director foundDirector = testEntityManager.find(Director.class, director.getId());

        assertEquals(director.getId(), foundDirector.getId());
        assertEquals(director.getName(), foundDirector.getName());
        assertEquals(director.getAge(), foundDirector.getAge());
        assertEquals(director.getNationality(), foundDirector.getNationality());
    }

    @DisplayName("[CustomDirectorRepositoryImpl] deleteById (nonexistent ID)")
    @Test
    void givenNonExistentDirectorId_whenDeleteById_thenNothingHappens() {
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);
        Predicate<Director> directorIsPresent = director ->
                testEntityManager.find(Director.class, director.getId()) != null;

        assertTrue(SAMPLE_DIRECTORS.stream().allMatch(directorIsPresent));

        final long id = -1;

        assertFalse(repository.deleteById(id));
        testEntityManager.clear();

        assertTrue(SAMPLE_DIRECTORS.stream().allMatch(directorIsPresent));
    }

    @DisplayName("[CustomDirectorRepositoryImpl] deleteById (existing ID)")
    @Test
    void givenDirectorId_whenDeleteById_thenDirectorIsRemoved() {
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);
        Predicate<Director> directorIsPresent = director ->
                testEntityManager.find(Director.class, director.getId()) != null;

        assertTrue(SAMPLE_DIRECTORS.stream().allMatch(directorIsPresent));

        final Director director = SAMPLE_DIRECTORS.get(2);
        final Long id = director.getId();

        assertTrue(repository.deleteById(id));
        testEntityManager.clear();

        assertFalse(SAMPLE_DIRECTORS.stream().allMatch(directorIsPresent));
        assertFalse(directorIsPresent.test(director));
    }

}

package com.viewnext.bsan.practica04.repository;

import com.viewnext.bsan.practica04.entity.Director;
import com.viewnext.bsan.practica04.sampledata.DirectorSampleData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.core.NestedRuntimeException;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code DirectorRepository} (implementation provided by Spring Data JPA).
 *
 * @author Antonio Gil
 */
@DataJpaTest
class DirectorRepositoryTest {

    private static final List<Director> SAMPLE_DIRECTORS = DirectorSampleData.SAMPLE_DIRECTORS;

    private final DirectorRepository repository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public DirectorRepositoryTest(DirectorRepository repository, TestEntityManager testEntityManager) {
        this.repository = repository;
        this.testEntityManager = testEntityManager;
    }

    @DisplayName("[DirectorRepository] findAll (should find empty list)")
    @Test
    void givenNoDirectors_whenFindAll_thenReturnEmptyList() {
        final List<Director> foundDirectors = repository.findAll();

        assertTrue(foundDirectors.isEmpty());
    }

    @DisplayName("[DirectorRepository] findAll (should find directors)")
    @Test
    void givenDirectors_whenFindAll_thenReturnDirectorList() {
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);

        final List<Director> foundDirectors = repository.findAll();

        assertFalse(foundDirectors.isEmpty());
        assertEquals(SAMPLE_DIRECTORS.size(), foundDirectors.size());
        assertTrue(foundDirectors.containsAll(SAMPLE_DIRECTORS));
    }

    @DisplayName("[DirectorRepository] existsById (nonexistent director)")
    @Test
    void givenNonExistentDirectorId_whenExistsById_thenReturnFalse() {
        final Long id = -1L;

        assertFalse(repository.existsById(id));
    }

    @DisplayName("[DirectorRepository] existsById (existing director)")
    @Test
    void givenDirectorId_whenExistsById_thenReturnTrue() {
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);

        final Long id = SAMPLE_DIRECTORS.get(4).getId();

        assertTrue(repository.existsById(id));
    }

    @DisplayName("[DirectorRepository] findById (nonexistent director)")
    @Test
    void givenNonExistentDirectorId_whenFindById_thenReturnEmpty() {
        final Long id = -1L;

        final Optional<Director> foundDirector = repository.findById(id);

        assertTrue(foundDirector.isEmpty());
    }

    @DisplayName("[DirectorRepository] findById (existing director)")
    @Test
    void givenDirectorId_whenFindById_thenReturnDirectorObject() {
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);

        final Director director = SAMPLE_DIRECTORS.get(3);
        final Long id = director.getId();

        final Director foundDirector = repository.findById(id).orElseThrow();

        assertEquals(id, foundDirector.getId());
        assertEquals(director.getName(), foundDirector.getName());
        assertEquals(director.getAge(), foundDirector.getAge());
        assertEquals(director.getNationality(), foundDirector.getNationality());
    }

    @DisplayName("[DirectorRepository] save (invalid data)")
    @Test
    void givenInvalidDirectorData_whenSave_thenThrow() {
        final Director invalidDirector = Director.builder().name("DIRECTOR6").age(70).nationality("USA").build();

        assertThrows(NestedRuntimeException.class, () -> repository.save(invalidDirector));
    }

    @DisplayName("[DirectorRepository] save (valid data)")
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

    @DisplayName("[DirectorRepository] deleteById (nonexistent ID)")
    @Test
    void givenNonExistentDirectorId_whenDeleteById_thenNothingHappens() {
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);
        Predicate<Director> directorIsPresent = director ->
                testEntityManager.find(Director.class, director.getId()) != null;

        assertTrue(SAMPLE_DIRECTORS.stream().allMatch(directorIsPresent));

        final Long id = -1L;

        repository.deleteById(id);

        assertTrue(SAMPLE_DIRECTORS.stream().allMatch(directorIsPresent));
    }

    @DisplayName("[DirectorRepository] deleteById (existing ID)")
    @Test
    void givenDirectorId_whenDeleteById_thenDirectorIsRemoved() {
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);
        Predicate<Director> directorIsPresent = director ->
                testEntityManager.find(Director.class, director.getId()) != null;

        assertTrue(SAMPLE_DIRECTORS.stream().allMatch(directorIsPresent));

        final Director director = SAMPLE_DIRECTORS.get(2);
        final Long id = director.getId();

        repository.deleteById(id);

        assertFalse(SAMPLE_DIRECTORS.stream().allMatch(directorIsPresent));
        assertFalse(directorIsPresent.test(director));
    }

}

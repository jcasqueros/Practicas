package com.viewnext.bsan.practica04.persistence.repository.custom.impl;

import com.viewnext.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnext.bsan.practica04.persistence.entity.Director;
import com.viewnext.bsan.practica04.persistence.specification.DirectorSpecifications;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.viewnext.bsan.practica04.sampledata.DirectorSampleData.SAMPLE_DIRECTORS;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code CustomDirectorRepositoryImpl}.
 *
 * @author Antonio Gil
 */
@DataJpaTest
@Import(CrudPeliculasAppTestConfig.class)
class CustomDirectorRepositoryImplTest {

    private final CustomDirectorRepositoryImpl repository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public CustomDirectorRepositoryImplTest(CustomDirectorRepositoryImpl repository,
                                            TestEntityManager testEntityManager) {
        this.repository = repository;
        this.testEntityManager = testEntityManager;
    }

    @DisplayName("[CustomDirectorRepositoryImpl] findAll (no filtering)")
    @Test
    void givenDirectorsAndNoFilters_whenFindAll_thenReturnPage() {
        // Arrange
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);

        final int pageSize = 4;
        final PageRequest pageRequest = PageRequest.of(0, pageSize);
        final List<Director> expectedDirectors = List.of(
                Director.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build(),
                Director.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build(),
                Director.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build(),
                Director.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build()
        );

        // Act
        final List<Director> foundDirectors = repository.findAll(pageRequest);

        // Assert
        assertFalse(foundDirectors.isEmpty());
        assertEquals(expectedDirectors.size(), foundDirectors.size());
        assertTrue(foundDirectors.containsAll(expectedDirectors));
    }

    @DisplayName("[CustomDirectorRepositoryImpl] findAll (filtering by nationality)")
    @Test
    void givenDirectorsAndFilters_whenFindAll_thenReturnPage() {
        // Arrange
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);

        final int pageSize = 4;
        final PageRequest pageRequest = PageRequest.of(0, pageSize);
        final List<Director> expectedDirectors = List.of(
                Director.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build()
        );
        final Specification<Director> nameContainsSubstring = DirectorSpecifications.nameContainsIgnoreCase("DIRECTOR2");

        // Act
        final List<Director> foundDirectors = repository.findAll(nameContainsSubstring, pageRequest);

        // Assert
        assertFalse(foundDirectors.isEmpty());
        assertEquals(expectedDirectors.size(), foundDirectors.size());
        assertTrue(foundDirectors.containsAll(expectedDirectors));
    }

    @DisplayName("[CustomDirectorRepositoryImpl] existsById (nonexistent director)")
    @Test
    void givenNonExistentDirectorId_whenExistsById_thenReturnFalse() {
        // Arrange
        final long id = -1;

        // Act + Assert
        assertFalse(repository.existsById(id));
    }

    @DisplayName("[CustomDirectorRepositoryImpl] existsById (existing director)")
    @Test
    void givenDirectorId_whenExistsById_thenReturnTrue() {
        // Arrange
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);

        final Long id = SAMPLE_DIRECTORS.get(4).getId();

        // Act + Assert
        assertTrue(repository.existsById(id));
    }

    @DisplayName("[CustomDirectorRepositoryImpl] findById (nonexistent director)")
    @Test
    void givenNonExistentDirectorId_whenFindById_thenReturnEmpty() {
        // Arrange
        final long id = -1;

        // Act
        final Optional<Director> foundDirector = repository.findById(id);

        // Assert
        assertTrue(foundDirector.isEmpty());
    }

    @DisplayName("[CustomDirectorRepositoryImpl] findById (existing director)")
    @Test
    void givenDirectorId_whenFindById_thenReturnDirectorObject() {
        // Arrange
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);
        final Director director = SAMPLE_DIRECTORS.get(3);
        final Long id = director.getId();

        // Act
        final Director foundDirector = repository.findById(id).orElseThrow();

        // Assert
        assertEquals(id, foundDirector.getId());
        assertEquals(director.getName(), foundDirector.getName());
        assertEquals(director.getAge(), foundDirector.getAge());
        assertEquals(director.getNationality(), foundDirector.getNationality());
    }

    @DisplayName("[CustomDirectorRepositoryImpl] save (invalid data)")
    @Test
    void givenInvalidDirectorData_whenSave_thenThrow() {
        // Arrange
        final Director invalidDirector = Director.builder().name("DIRECTOR6").age(70).nationality("USA").build();

        // Act + Assert
        assertThrows(RuntimeException.class, () -> repository.save(invalidDirector));
    }

    @DisplayName("[CustomDirectorRepositoryImpl] save (valid data)")
    @Test
    void givenDirectorData_whenSave_thenDirectorIsSaved() {
        // Arrange
        final Director director = Director.builder().id(6L).name("DIRECTOR6").age(70).nationality("USA").build();

        // Act
        repository.save(director);

        // Assert
        final Director foundDirector = testEntityManager.find(Director.class, director.getId());
        assertEquals(director.getId(), foundDirector.getId());
        assertEquals(director.getName(), foundDirector.getName());
        assertEquals(director.getAge(), foundDirector.getAge());
        assertEquals(director.getNationality(), foundDirector.getNationality());
    }

    @DisplayName("[CustomDirectorRepositoryImpl] deleteById (nonexistent ID)")
    @Test
    void givenNonExistentDirectorId_whenDeleteById_thenNothingHappens() {
        // Arrange
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);
        Predicate<Director> directorIsPresent = director ->
                testEntityManager.find(Director.class, director.getId()) != null;

        assertTrue(SAMPLE_DIRECTORS.stream().allMatch(directorIsPresent));

        final long id = -1;

        // Act + Assert
        assertFalse(repository.deleteById(id));
        testEntityManager.clear();

        assertTrue(SAMPLE_DIRECTORS.stream().allMatch(directorIsPresent));
    }

    @DisplayName("[CustomDirectorRepositoryImpl] deleteById (existing ID)")
    @Test
    void givenDirectorId_whenDeleteById_thenDirectorIsRemoved() {
        // Arrange
        SAMPLE_DIRECTORS.forEach(testEntityManager::persist);
        Predicate<Director> directorIsPresent = director ->
                testEntityManager.find(Director.class, director.getId()) != null;

        assertTrue(SAMPLE_DIRECTORS.stream().allMatch(directorIsPresent));

        final Director director = SAMPLE_DIRECTORS.get(2);
        final Long id = director.getId();

        // Act + Assert
        assertTrue(repository.deleteById(id));
        testEntityManager.clear();

        assertFalse(SAMPLE_DIRECTORS.stream().allMatch(directorIsPresent));
        assertFalse(directorIsPresent.test(director));
    }

}

package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Director;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(showSql = false)
@ComponentScan(basePackages = "com.viewnext.films.persistencelayer.repository.criteria")
@Sql(scripts = "/no-data.sql")
class DirectorCriteriaRepositoryTest {
    @Autowired
    DirectorCriteriaRepository directorCriteriaRepository;
    private Director director;

    @BeforeEach
    void setup() {
        director = new Director();
        director.setAge(18);
        director.setName("Jhon");
        director.setNationality("spain");
    }

    @Test
    @DisplayName("Create director operation")
    void givenDirectorObject_whenCreateDirector_thenReturnCreatedDirector() {

        Director createdDirector = directorCriteriaRepository.createDirector(director);

        assertThat(createdDirector).isNotNull();
        assertThat(createdDirector.getName()).isEqualTo(director.getName());
        assertThat(createdDirector.getAge()).isEqualTo(director.getAge());
        assertThat(createdDirector.getNationality()).isEqualTo(director.getNationality());
        assertThat(createdDirector.getId()).isNotNull();

    }

    @Test
    @DisplayName("Get all directors operation")
    void givenNothing_whenGetAllDirectors_thenReturnPageWithAllDirectors() {

        Director createdDirector = directorCriteriaRepository.createDirector(director);

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 directors
        List<Director> foundDirectors = directorCriteriaRepository.getAllDirectors(pageable);

        assertThat(foundDirectors).isNotNull();
        assertThat(foundDirectors.get(0)).isEqualTo(createdDirector);

    }

    @Test
    @DisplayName("Get director by id operation")
    void givenId_whenGetDirectorById_thenReturnFoundDirector() {

        Director createdDirector = directorCriteriaRepository.createDirector(director);

        Optional<Director> foundDirector = directorCriteriaRepository.getDirectorById(director.getId());

        assertThat(foundDirector).isPresent();
        assertThat(foundDirector).contains(createdDirector);
    }

    @Test
    @DisplayName("Update director operation")
    void givenDirector_whenUpdateDirector_thenReturnUpdatedDirector() {

        Director createdDirector = directorCriteriaRepository.createDirector(director);
        createdDirector.setName("James");
        createdDirector.setNationality("france");
        createdDirector.setAge(20);

        Director updatedDirector = directorCriteriaRepository.updateDirector(createdDirector);

        assertThat(updatedDirector).isNotNull();
        assertThat(updatedDirector.getId()).isEqualTo(createdDirector.getId());
        assertThat(updatedDirector.getName()).isEqualTo("James");
        assertThat(updatedDirector.getNationality()).isEqualTo("france");
        assertThat(updatedDirector.getAge()).isEqualTo(20);
    }

    @Test
    @DisplayName("Delete director operation")
    void givenId_whenDeleteDirector_thenDeleteDirector() {

        Director createdDirector = directorCriteriaRepository.createDirector(director);

        directorCriteriaRepository.deleteDirector(createdDirector.getId());
        Optional<Director> foundDirector = directorCriteriaRepository.getDirectorById(createdDirector.getId());

        assertThat(foundDirector).isEmpty();

    }

    @Test
    @DisplayName("Filter directors by name")
    void givenName_whenFilterDirectors_thenReturnFilteredDirectors() {
        // Arrange
        directorCriteriaRepository.createDirector(director);
        List<String> names = List.of("Jhon");
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Director> filteredDirectors = directorCriteriaRepository.filterDirectors(names, null, null, pageable);

        // Assert
        assertThat(filteredDirectors).isNotNull();
        assertEquals(1, filteredDirectors.size());
        assertThat(filteredDirectors.get(0).getName()).isEqualTo("Jhon");
    }

    @Test
    @DisplayName("Filter directors by age")
    void givenAge_whenFilterDirectors_thenReturnFilteredDirectors() {
        // Arrange
        directorCriteriaRepository.createDirector(director);
        List<Integer> ages = List.of(18);
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Director> filteredDirectors = directorCriteriaRepository.filterDirectors(null, ages, null, pageable);

        // Assert
        assertThat(filteredDirectors).isNotNull();
        assertEquals(1, filteredDirectors.size());
        assertThat(filteredDirectors.get(0).getAge()).isEqualTo(18);
    }

    @Test
    @DisplayName("Filter directors by nationality")
    void givenNationality_whenFilterDirectors_thenReturnFilteredDirectors() {
        // Arrange
        directorCriteriaRepository.createDirector(director);
        List<String> nationalities = List.of("spain");
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Director> filteredDirectors = directorCriteriaRepository.filterDirectors(null, null, nationalities,
                pageable);

        // Assert
        assertThat(filteredDirectors).isNotNull();
        assertEquals(1, filteredDirectors.size());
        assertThat(filteredDirectors.get(0).getNationality()).isEqualTo("spain");
    }

    @Test
    @DisplayName("Filter directors by multiple criteria")
    void givenMultipleCriteria_whenFilterDirectors_thenReturnFilteredDirectors() {
        // Arrange
        directorCriteriaRepository.createDirector(director);
        List<String> names = List.of("Jhon");
        List<Integer> ages = List.of(18);
        List<String> nationalities = List.of("spain");
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Director> filteredDirectors = directorCriteriaRepository.filterDirectors(names, ages, nationalities,
                pageable);

        // Assert
        assertThat(filteredDirectors).isNotNull();
        assertEquals(1, filteredDirectors.size());
        assertThat(filteredDirectors.get(0).getName()).isEqualTo("Jhon");
        assertThat(filteredDirectors.get(0).getAge()).isEqualTo(18);
        assertThat(filteredDirectors.get(0).getNationality()).isEqualTo("spain");
    }

    @Test
    @DisplayName("Filter directors with no criteria")
    void givenNoCriteria_whenFilterDirectors_thenReturnAllDirectors() {
        // Arrange
        directorCriteriaRepository.createDirector(director);
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Director> filteredDirectors = directorCriteriaRepository.filterDirectors(null, null, null, pageable);

        // Assert
        assertThat(filteredDirectors).isNotNull();
        assertEquals(1, filteredDirectors.size());
    }

    @Test
    @DisplayName("Get directors by name and age")
    void givenNameAndAge_whenGetDirectorsByNameAndAge_thenReturnFilteredDirectors() {
        // Arrange
        directorCriteriaRepository.createDirector(director);
        // Act
        List<Director> filteredDirectors = directorCriteriaRepository.getDirectorsByNameAndAge("Jhon", 18);

        // Assert
        assertThat(filteredDirectors).isNotNull();
        assertEquals(1, filteredDirectors.size());
        assertThat(filteredDirectors.get(0).getName()).isEqualTo("Jhon");
        assertThat(filteredDirectors.get(0).getAge()).isEqualTo(18);
    }
}

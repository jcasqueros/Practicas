package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Director;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@ComponentScan(basePackages = "com.viewnext.films.persistencelayer.repository.criteria")
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
        assertThat(createdDirector.getId()).isPositive();

    }

    @Test
    @DisplayName("Get all directors operation")
    void givenNothing_whenGetAllDirectors_thenReturnListWithAllDirectors() {

        Director createdDirector = directorCriteriaRepository.createDirector(director);

        List<Director> foundDirectors = directorCriteriaRepository.getAllDirectors();

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
}

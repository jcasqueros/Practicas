package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Actor;
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
class ActorCriteriaRepositoryTest {
    @Autowired
    ActorCriteriaRepository actorCriteriaRepository;
    private Actor actor;

    @BeforeEach
    void setup() {
        actor = new Actor();
        actor.setAge(18);
        actor.setName("Jhon");
        actor.setNationality("spain");
    }

    @Test
    @DisplayName("Create actor operation")
    void givenActorObject_whenCreateActor_thenReturnCreatedActor() {

        Actor createdActor = actorCriteriaRepository.createActor(actor);

        assertThat(createdActor).isNotNull();
        assertThat(createdActor.getName()).isEqualTo(actor.getName());
        assertThat(createdActor.getAge()).isEqualTo(actor.getAge());
        assertThat(createdActor.getNationality()).isEqualTo(actor.getNationality());
        assertThat(createdActor.getId()).isNotNull();

    }

    @Test
    @DisplayName("Get all actors operation")
    void givenNothing_whenGetAllActors_thenReturnPageWithAllActors() {

        Actor createdActor = actorCriteriaRepository.createActor(actor);

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 actors
        List<Actor> foundActors = actorCriteriaRepository.getAllActors(pageable);

        assertThat(foundActors).isNotNull();
        assertThat(foundActors.get(0)).isEqualTo(createdActor);

    }

    @Test
    @DisplayName("Get actor by id operation")
    void givenId_whenGetActorById_thenReturnFoundActor() {

        Actor createdActor = actorCriteriaRepository.createActor(actor);

        Optional<Actor> foundActor = actorCriteriaRepository.getActorById(actor.getId());

        assertThat(foundActor).isPresent();
        assertThat(foundActor).contains(createdActor);
    }

    @Test
    @DisplayName("Update actor operation")
    void givenActor_whenUpdateActor_thenReturnUpdatedActor() {

        Actor createdActor = actorCriteriaRepository.createActor(actor);
        createdActor.setName("James");
        createdActor.setNationality("france");
        createdActor.setAge(20);

        Actor updatedActor = actorCriteriaRepository.updateActor(createdActor);

        assertThat(updatedActor).isNotNull();
        assertThat(updatedActor.getId()).isEqualTo(createdActor.getId());
        assertThat(updatedActor.getName()).isEqualTo("James");
        assertThat(updatedActor.getNationality()).isEqualTo("france");
        assertThat(updatedActor.getAge()).isEqualTo(20);
    }

    @Test
    @DisplayName("Delete actor operation")
    void givenId_whenDeleteActor_thenDeleteActor() {

        Actor createdActor = actorCriteriaRepository.createActor(actor);

        actorCriteriaRepository.deleteActor(createdActor.getId());
        Optional<Actor> foundActor = actorCriteriaRepository.getActorById(createdActor.getId());

        assertThat(foundActor).isEmpty();

    }

    @Test
    @DisplayName("Filter actors by name")
    void givenName_whenFilterActors_thenReturnFilteredActors() {
        // Arrange
        actorCriteriaRepository.createActor(actor);
        List<String> names = List.of("Jhon");
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Actor> filteredActors = actorCriteriaRepository.filterActors(names, null, null, pageable);

        // Assert
        assertThat(filteredActors).isNotNull();
        assertEquals(1, filteredActors.size());
        assertThat(filteredActors.get(0).getName()).isEqualTo("Jhon");
    }

    @Test
    @DisplayName("Filter actors by age")
    void givenAge_whenFilterActors_thenReturnFilteredActors() {
        // Arrange
        actorCriteriaRepository.createActor(actor);
        List<Integer> ages = List.of(18);
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Actor> filteredActors = actorCriteriaRepository.filterActors(null, ages, null, pageable);

        // Assert
        assertThat(filteredActors).isNotNull();
        assertEquals(1, filteredActors.size());
        assertThat(filteredActors.get(0).getAge()).isEqualTo(18);
    }

    @Test
    @DisplayName("Filter actors by nationality")
    void givenNationality_whenFilterActors_thenReturnFilteredActors() {
        // Arrange
        actorCriteriaRepository.createActor(actor);
        List<String> nationalities = List.of("spain");
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Actor> filteredActors = actorCriteriaRepository.filterActors(null, null, nationalities, pageable);

        // Assert
        assertThat(filteredActors).isNotNull();
        assertEquals(1, filteredActors.size());
        assertThat(filteredActors.get(0).getNationality()).isEqualTo("spain");
    }

    @Test
    @DisplayName("Filter actors by multiple criteria")
    void givenMultipleCriteria_whenFilterActors_thenReturnFilteredActors() {
        // Arrange
        actorCriteriaRepository.createActor(actor);
        List<String> names = List.of("Jhon");
        List<Integer> ages = List.of(18);
        List<String> nationalities = List.of("spain");
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Actor> filteredActors = actorCriteriaRepository.filterActors(names, ages, nationalities, pageable);

        // Assert
        assertThat(filteredActors).isNotNull();
        assertEquals(1, filteredActors.size());
        assertThat(filteredActors.get(0).getName()).isEqualTo("Jhon");
        assertThat(filteredActors.get(0).getAge()).isEqualTo(18);
        assertThat(filteredActors.get(0).getNationality()).isEqualTo("spain");
    }

    @Test
    @DisplayName("Filter actors with no criteria")
    void givenNoCriteria_whenFilterActors_thenReturnAllActors() {
        // Arrange
        actorCriteriaRepository.createActor(actor);
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Actor> filteredActors = actorCriteriaRepository.filterActors(null, null, null, pageable);

        // Assert
        assertThat(filteredActors).isNotNull();
        assertEquals(1, filteredActors.size());
    }

    @Test
    @DisplayName("Get actors by name and age")
    void givenNameAndAge_whenGetActorsByNameAndAge_thenReturnFilteredActors() {
        // Arrange
        actorCriteriaRepository.createActor(actor);
        // Act
        List<Actor> filteredActors = actorCriteriaRepository.getActorsByNameAndAge("Jhon", 18);

        // Assert
        assertThat(filteredActors).isNotNull();
        assertEquals(1, filteredActors.size());
        assertThat(filteredActors.get(0).getName()).isEqualTo("Jhon");
        assertThat(filteredActors.get(0).getAge()).isEqualTo(18);
    }
}

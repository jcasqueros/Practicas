package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Actor;
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
        assertThat(createdActor.getId()).isPositive();

    }

    @Test
    @DisplayName("Get all actors operation")
    void givenNothing_whenGetAllActors_thenReturnListWithAllActors() {

        Actor createdActor = actorCriteriaRepository.createActor(actor);

        List<Actor> foundActors = actorCriteriaRepository.getAllActors();

        assertThat(foundActors).isNotNull();
        assertThat(foundActors.get(0)).isEqualTo(createdActor);

    }

    @Test
    @DisplayName("Get actor by id operation")
    void givenId_whenGetActorById_thenReturnFoundActor() {

        Actor createdActor = actorCriteriaRepository.createActor(actor);

        Optional<Actor> foundActor = actorCriteriaRepository.getActorById(createdActor.getId());

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
}

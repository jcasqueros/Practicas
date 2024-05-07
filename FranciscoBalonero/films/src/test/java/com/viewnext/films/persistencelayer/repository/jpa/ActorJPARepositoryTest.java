package com.viewnext.films.persistencelayer.repository.jpa;

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
@ComponentScan(basePackages = "com.viewnext.films.persistencelayer.repository.jpa")
class ActorJPARepositoryTest {
    @Autowired
    ActorJPARepository actorJPARepository;
    private Actor actor;

    @BeforeEach
    void setup() {
        actor = new Actor();
        actor.setAge(18);
        actor.setName("Jhon");
        actor.setNationality("spain");
    }

    @Test
    @DisplayName("Save actor operation")
    void givenActorObject_whenSaveActor_thenReturnSavedActor() {

        Actor savedActor = actorJPARepository.save(actor);

        assertThat(savedActor).isNotNull();
        assertThat(savedActor.getName()).isEqualTo(actor.getName());
        assertThat(savedActor.getAge()).isEqualTo(actor.getAge());
        assertThat(savedActor.getNationality()).isEqualTo(actor.getNationality());
        assertThat(savedActor.getId()).isPositive();

    }

    @Test
    @DisplayName("Find actor by id operation")
    void givenId_whenFindActorById_thenReturnFoundActor() {

        Actor savedActor = actorJPARepository.save(actor);

        Optional<Actor> foundActor = actorJPARepository.findById(savedActor.getId());

        assertThat(foundActor).isPresent();
        assertThat(foundActor).contains(savedActor);
    }

    @Test
    @DisplayName("Find all actors operation")
    void givenNothing_whenFindAllActors_thenReturnListWithAllActors() {

        Actor savedActor = actorJPARepository.save(actor);

        List<Actor> foundActors = actorJPARepository.findAll();

        assertThat(foundActors).isNotNull();
        assertThat(foundActors.get(0)).isEqualTo(savedActor);

    }

    @Test
    @DisplayName("Delete actor by id operation")
    void givenId_whenDeleteActorById_thenDeleteActor() {

        Actor savedActor = actorJPARepository.save(actor);

        actorJPARepository.deleteById(savedActor.getId());
        Optional<Actor> foundActor = actorJPARepository.findById(savedActor.getId());

        assertThat(foundActor).isEmpty();

    }
}

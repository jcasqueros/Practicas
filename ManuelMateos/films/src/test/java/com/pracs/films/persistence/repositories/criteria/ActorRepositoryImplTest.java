package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.repositories.criteria.impl.ActorRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ComponentScan("com.pracs.films.persistence.repositories.criteria")
class ActorRepositoryImplTest {

    @Autowired
    private ActorRepositoryImpl actorRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Actor actor;

    @BeforeEach
    void setup() {

        actor = new Actor();
        actor.setName("prueba");
        actor.setAge(25);
        actor.setNationality("Spain");

    }

    @DisplayName("JUnit test for save an actor")
    @Test
    void givenActorObject_whenSaveActor_thenReturnActorObject() {

        Actor savedActor = actorRepository.saveActor(actor);

        assertEquals(actor, savedActor);
    }

    @DisplayName("JUnit test for update an actor")
    @Test
    void givenActorObject_whenUpdateActor_thenReturnActorObject() {

        actorRepository.saveActor(actor);

        actor.setName("update");
        actor.setAge(90);
        actor.setNationality("update");

        actorRepository.updateActor(actor);

        Optional<Actor> savedActor = actorRepository.findActorById(actor.getId());

        assertEquals("update", savedActor.get().getName());
        assertEquals(90, savedActor.get().getAge());
        assertEquals("update", savedActor.get().getNationality());
    }

    @DisplayName("JUnit test for get an actor by his id")
    @Test
    void givenActorId_whenFindActorById_thenReturnActorObject() {

        Optional<Actor> savedActor = actorRepository.findActorById(500L);

        assertEquals(500L, savedActor.get().getId());
    }

    @DisplayName("JUnit test for get all actors")
    @Test
    void givenNothing_whenFindAllActor_thenReturnActorList() {

        List<Actor> savedActors = actorRepository.findAllActor();

        assertEquals(501, savedActors.size());
    }

    @DisplayName("JUnit test for delete an actor")
    @Test
    void givenActorId_whenDeleteActorByid_thenDelete() {

        actorRepository.deleteActorById(actor);

        Optional<Actor> foundActor = actorRepository.findActorById(actor.getId());

        assertThat(foundActor).isEmpty();
    }
}
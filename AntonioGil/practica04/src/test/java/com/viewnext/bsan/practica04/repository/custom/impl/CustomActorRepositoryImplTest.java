package com.viewnext.bsan.practica04.repository.custom.impl;

import com.viewnext.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnext.bsan.practica04.entity.Actor;
import com.viewnext.bsan.practica04.sampledata.ActorSampleData;
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
class CustomActorRepositoryImplTest {

    private static final List<Actor> SAMPLE_ACTORS = ActorSampleData.SAMPLE_ACTORS;

    private final CustomActorRepositoryImpl repository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public CustomActorRepositoryImplTest(CustomActorRepositoryImpl repository, TestEntityManager testEntityManager) {
        this.repository = repository;
        this.testEntityManager = testEntityManager;
    }

    @DisplayName("[CustomActorRepositoryImpl] findAll (should find empty list)")
    @Test
    void givenNoActors_whenFindAll_thenReturnEmptyList() {
        final List<Actor> foundActors = repository.findAll();

        assertTrue(foundActors.isEmpty());
    }

    @DisplayName("[CustomActorRepositoryImpl] findAll (should find actors)")
    @Test
    void givenActors_whenFindAll_thenReturnActorList() {
        SAMPLE_ACTORS.forEach(testEntityManager::persist);

        final List<Actor> foundActors = repository.findAll();

        assertFalse(foundActors.isEmpty());
        assertEquals(SAMPLE_ACTORS.size(), foundActors.size());
        assertTrue(foundActors.containsAll(SAMPLE_ACTORS));
    }

    @DisplayName("[CustomActorRepositoryImpl] existsById (nonexistent actor)")
    @Test
    void givenNonExistentActorId_whenExistsById_thenReturnFalse() {
        final long id = -1;

        assertFalse(repository.existsById(id));
    }

    @DisplayName("[CustomActorRepositoryImpl] existsById (existing actor)")
    @Test
    void givenActorId_whenExistsById_thenReturnTrue() {
        SAMPLE_ACTORS.forEach(testEntityManager::persist);

        final long id = SAMPLE_ACTORS.get(4).getId();

        assertTrue(repository.existsById(id));
    }

    @DisplayName("[CustomActorRepositoryImpl] findById (nonexistent actor)")
    @Test
    void givenNonExistentActorId_whenFindById_thenReturnEmpty() {
        final long id = -1;

        final Optional<Actor> foundActor = repository.findById(id);

        assertTrue(foundActor.isEmpty());
    }

    @DisplayName("[CustomActorRepositoryImpl] findById (existing actor)")
    @Test
    void givenActorId_whenFindById_thenReturnActorObject() {
        SAMPLE_ACTORS.forEach(testEntityManager::persist);
        final Actor actor = SAMPLE_ACTORS.get(3);
        final Long id = actor.getId();

        final Actor foundActor = repository.findById(id).orElseThrow();

        assertEquals(id, foundActor.getId());
        assertEquals(actor.getName(), foundActor.getName());
        assertEquals(actor.getAge(), foundActor.getAge());
        assertEquals(actor.getNationality(), foundActor.getNationality());
    }

    @DisplayName("[CustomActorRepositoryImpl] save (invalid data)")
    @Test
    void givenInvalidActorData_whenSave_thenThrow() {
        final Actor invalidActor = Actor.builder().name("ACTOR6").age(60).nationality("GER").build();

        assertThrows(RuntimeException.class, () -> repository.save(invalidActor));
    }

    @DisplayName("[CustomActorRepositoryImpl] save (valid data)")
    @Test
    void givenActorData_whenSave_thenActorIsSaved() {
        final Actor actor = Actor.builder().id(6L).name("ACTOR6").age(60).nationality("GER").build();

        repository.save(actor);

        final Actor foundActor = testEntityManager.find(Actor.class, actor.getId());

        assertEquals(actor.getId(), foundActor.getId());
        assertEquals(actor.getName(), foundActor.getName());
        assertEquals(actor.getAge(), foundActor.getAge());
        assertEquals(actor.getNationality(), foundActor.getNationality());
    }

    @DisplayName("[CustomActorRepositoryImpl] deleteById (nonexistent ID)")
    @Test
    void givenNonExistentActorId_whenDeleteById_thenNothingHappens() {
        SAMPLE_ACTORS.forEach(testEntityManager::persist);
        Predicate<Actor> actorIsPresent = (actor -> testEntityManager.find(Actor.class, actor.getId()) != null);

        assertTrue(SAMPLE_ACTORS.stream().allMatch(actorIsPresent));

        final long id = -1;

        assertFalse(repository.deleteById(id));
        testEntityManager.clear();

        assertTrue(SAMPLE_ACTORS.stream().allMatch(actorIsPresent));
    }

    @DisplayName("[CustomActorRepositoryImpl] deleteById (existing ID)")
    @Test
    void givenActorId_whenDeleteById_thenActorIsRemoved() {
        SAMPLE_ACTORS.forEach(testEntityManager::persist);
        Predicate<Actor> actorIsPresent = (actor -> testEntityManager.find(Actor.class, actor.getId()) != null);

        assertTrue(SAMPLE_ACTORS.stream().allMatch(actorIsPresent));

        final Actor actor = SAMPLE_ACTORS.get(3);
        final Long id = actor.getId();

        assertTrue(repository.deleteById(id));
        testEntityManager.clear();

        assertFalse(SAMPLE_ACTORS.stream().allMatch(actorIsPresent));
        assertFalse(actorIsPresent.test(actor));
    }

}

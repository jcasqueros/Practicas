package com.viewnext.bsan.practica04.repository.custom.impl;

import com.viewnext.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnext.bsan.practica04.entity.Actor;
import com.viewnext.bsan.practica04.entity.specification.ActorSpecifications;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.viewnext.bsan.practica04.sampledata.ActorSampleData.SAMPLE_ACTORS;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code CustomActorRepositoryImpl}.
 *
 * @author Antonio Gil
 */
@DataJpaTest
@Import(CrudPeliculasAppTestConfig.class)
class CustomActorRepositoryImplTest {

    private final CustomActorRepositoryImpl repository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public CustomActorRepositoryImplTest(CustomActorRepositoryImpl repository, TestEntityManager testEntityManager) {
        this.repository = repository;
        this.testEntityManager = testEntityManager;
    }

    @DisplayName("[CustomActorRepositoryImpl] findAll (no filtering)")
    @Test
    void givenActorsAndNoFilters_whenFindAll_thenReturnPage() {
        // Arrange
        SAMPLE_ACTORS.forEach(testEntityManager::persist);

        final int pageSize = 4;
        final PageRequest pageRequest = PageRequest.of(0, pageSize);
        final List<Actor> expectedActors = List.of(
                Actor.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                Actor.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
                Actor.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
                Actor.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build()
        );

        // Act
        final List<Actor> foundActors = repository.findAll(pageRequest);

        // Assert
        assertFalse(foundActors.isEmpty());
        assertEquals(pageSize, foundActors.size());
        assertTrue(foundActors.containsAll(expectedActors));
    }

    @DisplayName("[CustomActorRepositoryImpl] findAll (filtering by nationality)")
    @Test
    void givenActorsAndFilters_whenFindAll_thenReturnPage() {
        // Arrange
        SAMPLE_ACTORS.forEach(testEntityManager::persist);

        final int pageSize = 4;
        final PageRequest pageRequest = PageRequest.of(0, pageSize);
        final List<Actor> expectedActors = List.of(
                Actor.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                Actor.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build()
        );

        // Act
        final List<Actor> foundActors = repository.findAll(ActorSpecifications.nationalityIsEqualToIgnoreCase("USA"),
                pageRequest);

        // Assert
        assertFalse(foundActors.isEmpty());
        assertEquals(expectedActors.size(), foundActors.size());
        assertTrue(foundActors.containsAll(expectedActors));
    }

    @DisplayName("[CustomActorRepositoryImpl] existsById (nonexistent actor)")
    @Test
    void givenNonExistentActorId_whenExistsById_thenReturnFalse() {
        // Arrange
        final long id = -1;

        // Act + Assert
        assertFalse(repository.existsById(id));
    }

    @DisplayName("[CustomActorRepositoryImpl] existsById (existing actor)")
    @Test
    void givenActorId_whenExistsById_thenReturnTrue() {
        // Arrange
        SAMPLE_ACTORS.forEach(testEntityManager::persist);

        final long id = SAMPLE_ACTORS.get(4).getId();

        // Act + Assert
        assertTrue(repository.existsById(id));
    }

    @DisplayName("[CustomActorRepositoryImpl] findById (nonexistent actor)")
    @Test
    void givenNonExistentActorId_whenFindById_thenReturnEmpty() {
        // Arrange
        final long id = -1;

        // Act
        final Optional<Actor> foundActor = repository.findById(id);

        // Assert
        assertTrue(foundActor.isEmpty());
    }

    @DisplayName("[CustomActorRepositoryImpl] findById (existing actor)")
    @Test
    void givenActorId_whenFindById_thenReturnActorObject() {
        // Arrange
        SAMPLE_ACTORS.forEach(testEntityManager::persist);
        final Actor actor = SAMPLE_ACTORS.get(3);
        final Long id = actor.getId();

        // Act
        final Actor foundActor = repository.findById(id).orElseThrow();

        // Assert
        assertEquals(id, foundActor.getId());
        assertEquals(actor.getName(), foundActor.getName());
        assertEquals(actor.getAge(), foundActor.getAge());
        assertEquals(actor.getNationality(), foundActor.getNationality());
    }

    @DisplayName("[CustomActorRepositoryImpl] save (invalid data)")
    @Test
    void givenInvalidActorData_whenSave_thenThrow() {
        // Arrange
        final Actor invalidActor = Actor.builder().name("ACTOR6").age(60).nationality("GER").build();

        // Act + Assert
        assertThrows(RuntimeException.class, () -> repository.save(invalidActor));
    }

    @DisplayName("[CustomActorRepositoryImpl] save (valid data)")
    @Test
    void givenActorData_whenSave_thenActorIsSaved() {
        // Arrange
        final Actor actor = Actor.builder().id(6L).name("ACTOR6").age(60).nationality("GER").build();

        // Act
        repository.save(actor);

        // Assert
        final Actor foundActor = testEntityManager.find(Actor.class, actor.getId());
        assertEquals(actor.getId(), foundActor.getId());
        assertEquals(actor.getName(), foundActor.getName());
        assertEquals(actor.getAge(), foundActor.getAge());
        assertEquals(actor.getNationality(), foundActor.getNationality());
    }

    @DisplayName("[CustomActorRepositoryImpl] deleteById (nonexistent ID)")
    @Test
    void givenNonExistentActorId_whenDeleteById_thenNothingHappens() {
        // Arrange
        SAMPLE_ACTORS.forEach(testEntityManager::persist);
        Predicate<Actor> actorIsPresent = (actor -> testEntityManager.find(Actor.class, actor.getId()) != null);

        assertTrue(SAMPLE_ACTORS.stream().allMatch(actorIsPresent));

        final long id = -1;

        // Act + Assert
        assertFalse(repository.deleteById(id));
        testEntityManager.clear();

        assertTrue(SAMPLE_ACTORS.stream().allMatch(actorIsPresent));
    }

    @DisplayName("[CustomActorRepositoryImpl] deleteById (existing ID)")
    @Test
    void givenActorId_whenDeleteById_thenActorIsRemoved() {
        // Arrange
        SAMPLE_ACTORS.forEach(testEntityManager::persist);
        Predicate<Actor> actorIsPresent = (actor -> testEntityManager.find(Actor.class, actor.getId()) != null);

        assertTrue(SAMPLE_ACTORS.stream().allMatch(actorIsPresent));

        final Actor actor = SAMPLE_ACTORS.get(3);
        final Long id = actor.getId();

        // Act + Assert
        assertTrue(repository.deleteById(id));
        testEntityManager.clear();

        assertFalse(SAMPLE_ACTORS.stream().allMatch(actorIsPresent));
        assertFalse(actorIsPresent.test(actor));
    }

}

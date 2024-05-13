package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.entity.Actor;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnex.bsan.practica04.repository.ActorRepository;
import com.viewnex.bsan.practica04.repository.custom.CustomActorRepository;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelActorMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.viewnex.bsan.practica04.sampledata.ActorSampleData.SAMPLE_ACTORS;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code ActorServiceImpl}.
 *
 * @author Antonio Gil
 */
@ExtendWith(MockitoExtension.class)
class ActorServiceImplTest {

    @Mock
    ActorRepository repository;

    @Mock
    CustomActorRepository customRepository;

    @Mock
    ServiceLevelActorMapper mapper;

    @InjectMocks
    ActorServiceImpl service;

    @DisplayName("[ActorServiceImpl] getAll (success)")
    @Test
    void givenActors_whenGetAll_thenReturnActorList() {
        Mockito.when(repository.findAll()).thenReturn(SAMPLE_ACTORS);
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(0))).thenReturn(
                ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(1))).thenReturn(
                ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(2))).thenReturn(
                ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(3))).thenReturn(
                ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(4))).thenReturn(
                ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build()
        );

        List<ActorBo> expectedActors = List.of(
                ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
                ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
                ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build(),
                ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build()
        );
        List<ActorBo> foundActors = service.getAll();

        assertFalse(foundActors.isEmpty());
        assertEquals(SAMPLE_ACTORS.size(), foundActors.size());
        assertTrue(foundActors.containsAll(expectedActors));
    }

    @DisplayName("[ActorServiceImpl] customGetAll (success)")
    @Test
    void givenActors_whenCustomGetAll_thenReturnActorList() {
        Mockito.when(customRepository.findAll()).thenReturn(SAMPLE_ACTORS);
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(0))).thenReturn(
                ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(1))).thenReturn(
                ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(2))).thenReturn(
                ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(3))).thenReturn(
                ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(4))).thenReturn(
                ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build()
        );

        List<ActorBo> expectedActors = List.of(
                ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
                ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
                ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build(),
                ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build()
        );
        List<ActorBo> foundActors = service.customGetAll();

        assertFalse(foundActors.isEmpty());
        assertEquals(SAMPLE_ACTORS.size(), foundActors.size());
        assertTrue(foundActors.containsAll(expectedActors));
    }

    @DisplayName("[ActorServiceImpl] getById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenGetById_thenThrow() {
        final long id = -1;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @DisplayName("[ActorServiceImpl] getById (should return actor)")
    @Test
    void givenActorId_whenGetById_thenReturnActorObject() {
        final long id = 2;
        final ActorBo expectedActor = ActorBo.builder().id(id).name("ACTOR2").age(50).nationality("ESP").build();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(SAMPLE_ACTORS.get(1)));
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(1))).thenReturn(expectedActor);

        final ActorBo foundActor = service.getById(id);

        assertNotNull(foundActor);
        assertEquals(expectedActor.getId(), foundActor.getId());
        assertEquals(expectedActor.getName(), foundActor.getName());
        assertEquals(expectedActor.getAge(), foundActor.getAge());
        assertEquals(expectedActor.getNationality(), foundActor.getNationality());
    }

    @DisplayName("[ActorServiceImpl] customGetById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenCustomGetById_thenThrow() {
        final long id = -1;

        Mockito.when(customRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.customGetById(id));
    }

    @DisplayName("[ActorServiceImpl] customGetById (should return actor)")
    @Test
    void givenNonExistentActorId_whenCustomGetById_thenReturnActorObject() {
        final long id = 4;
        final ActorBo expectedActor = ActorBo.builder().id(id).name("ACTOR5").age(35).nationality("ITA").build();

        Mockito.when(customRepository.findById(id)).thenReturn(Optional.of(SAMPLE_ACTORS.get(3)));
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(3))).thenReturn(expectedActor);

        final ActorBo foundActor = service.customGetById(id);

        assertNotNull(foundActor);
        assertEquals(expectedActor.getId(), foundActor.getId());
        assertEquals(expectedActor.getName(), foundActor.getName());
        assertEquals(expectedActor.getAge(), foundActor.getAge());
        assertEquals(expectedActor.getNationality(), foundActor.getNationality());
    }

    @DisplayName("[ActorServiceImpl] validateName (should throw MissingRequiredFieldException)")
    @Test
    void givenInvalidName_whenValidateName_thenThrow() {
        final String name = "   \t \n";

        assertThrows(MissingRequiredFieldException.class, () -> service.validateName(name));
    }

    @DisplayName("[ActorServiceImpl] validateName (should not throw)")
    @Test
    void givenName_whenValidateName_thenDoesNotThrow() {
        final String name = "BRAD PITT";

        assertDoesNotThrow(() -> service.validateName(name));
    }

    @DisplayName("[ActorServiceImpl] validateAge (should throw BadInputDataException)")
    @Test
    void givenInvalidAge_whenValidateAge_thenThrow() {
        final int age = -33;

        assertThrows(BadInputDataException.class, () -> service.validateAge(age));
    }

    @DisplayName("[ActorServiceImpl] validateAge (should not throw)")
    @Test
    void givenAge_whenValidateAge_thenDoesNotThrow() {
        final int age = 33;

        assertDoesNotThrow(() -> service.validateAge(age));
    }

    @DisplayName("[ActorServiceImpl] validateNationality (should throw MissingRequiredFieldException)")
    @Test
    void givenInvalidNationality_whenValidateNationality_thenThrow() {
        final String nationality = "     \t";

        assertThrows(MissingRequiredFieldException.class, () -> service.validateNationality(nationality));
    }

    @DisplayName("[ActorServiceImpl] validateNationality (should not throw)")
    @Test
    void givenNationality_whenValidateNationality_thenDoesNotThrow() {
        final String nationality = "TUR";

        assertDoesNotThrow(() -> service.validateNationality(nationality));
    }

    @DisplayName("[ActorServiceImpl] validateActor (should throw BadInputDataException)")
    @Test
    void givenNullActor_whenValidateActor_thenThrow() {
        assertThrows(BadInputDataException.class, () -> service.validateActor(null));
    }

    @DisplayName("[ActorServiceImpl] validateActor (should not throw)")
    @Test
    void givenActor_whenValidateActor_thenDoesNotThrow() {
        final ActorBo actor = ActorBo.builder().id(6L).name("ACTOR6").age(35).nationality("GRC").build();

        assertDoesNotThrow(() -> service.validateActor(actor));
    }

    @DisplayName("[ActorServiceImpl] create (throws DuplicateUniqueFieldException)")
    @Test
    void givenActorWithDuplicateData_whenCreate_thenThrow() {
        final long id = 1;
        final ActorBo actor = ActorBo.builder().id(id).name("ACTOR1").age(30).nationality("USA").build();

        Mockito.when(repository.existsById(id)).thenReturn(true);

        assertThrows(DuplicateUniqueFieldException.class, () -> service.create(actor));
    }

    @DisplayName("[ActorServiceImpl] create (success)")
    @Test
    void givenActor_whenCreate_thenActorIsSaved() {
        final long id = 1;
        final ActorBo actor = ActorBo.builder().id(id).name("ACTOR1").age(30).nationality("USA").build();

        Mockito.when(repository.existsById(id)).thenReturn(false);
        Mockito.when(mapper.boToEntity(actor)).thenReturn(SAMPLE_ACTORS.get(0));
        Mockito.when(repository.save(SAMPLE_ACTORS.get(0))).thenReturn(SAMPLE_ACTORS.get(0));
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(0))).thenReturn(actor);

        final ActorBo savedActor = service.create(actor);

        assertEquals(actor.getId(), savedActor.getId());
        assertEquals(actor.getName(), savedActor.getName());
        assertEquals(actor.getAge(), savedActor.getAge());
        assertEquals(actor.getNationality(), savedActor.getNationality());
    }

    @DisplayName("[ActorServiceImpl] customCreate (throws DuplicateUniqueFieldException)")
    @Test
    void givenActorWithDuplicateData_whenCustomCreate_thenThrow() {
        final long id = 1;
        final ActorBo actor = ActorBo.builder().id(id).name("ACTOR1").age(30).nationality("USA").build();

        Mockito.when(customRepository.existsById(id)).thenReturn(true);

        assertThrows(DuplicateUniqueFieldException.class, () -> service.customCreate(actor));
    }

    @DisplayName("[ActorServiceImpl] customCreate (success)")
    @Test
    void givenActor_whenCustomCreate_thenActorIsSaved() {
        final long id = 1;
        final ActorBo actor = ActorBo.builder().id(id).name("ACTOR1").age(30).nationality("USA").build();

        Mockito.when(customRepository.existsById(id)).thenReturn(false);
        Mockito.when(mapper.boToEntity(actor)).thenReturn(SAMPLE_ACTORS.get(0));
        Mockito.when(customRepository.save(SAMPLE_ACTORS.get(0))).thenReturn(SAMPLE_ACTORS.get(0));
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(0))).thenReturn(actor);

        final ActorBo savedActor = service.customCreate(actor);

        assertEquals(actor.getId(), savedActor.getId());
        assertEquals(actor.getName(), savedActor.getName());
        assertEquals(actor.getAge(), savedActor.getAge());
        assertEquals(actor.getNationality(), savedActor.getNationality());
    }

    @DisplayName("[ActorServiceImpl] update (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenUpdate_thenThrow() {
        final long id = -1;
        final ActorBo actor = ActorBo.builder().id(id).name("NEW_ACTOR1").age(30).nationality("USA").build();

        Mockito.when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.update(id, actor));
    }

    @DisplayName("[ActorServiceImpl] update (success)")
    @Test
    void givenActorId_whenUpdate_thenActorIsUpdated() {
        final long id = 1;
        final ActorBo actor = ActorBo.builder().id(id).name("NEW_ACTOR1").age(30).nationality("USA").build();

        Mockito.when(repository.existsById(id)).thenReturn(true);
        Mockito.when(mapper.boToEntity(actor)).thenReturn(
                Actor.builder().id(id).name("NEW_ACTOR1").age(30).nationality("USA").build()
        );
        Mockito.when(repository.save(Actor.builder().id(id).name("NEW_ACTOR1").age(30).nationality("USA").build()))
                .thenReturn(Actor.builder().id(id).name("NEW_ACTOR1").age(30).nationality("USA").build());
        Mockito.when(mapper.entityToBo(Actor.builder().id(id).name("NEW_ACTOR1").age(30).nationality("USA").build()))
                .thenReturn(actor);

        final ActorBo updatedActor = service.update(id, actor);

        assertEquals(actor.getId(), updatedActor.getId());
        assertEquals(actor.getName(), updatedActor.getName());
        assertEquals(actor.getAge(), updatedActor.getAge());
        assertEquals(actor.getNationality(), updatedActor.getNationality());
    }

    @DisplayName("[ActorServiceImpl] customUpdate (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenCustomUpdate_thenThrow() {
        final long id = -1;
        final ActorBo updatedActor = ActorBo.builder().id(id).name("NEW_ACTOR1").age(30).nationality("USA").build();

        Mockito.when(customRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.customUpdate(id, updatedActor));
    }

    @DisplayName("[ActorServiceImpl] customUpdate (success)")
    @Test
    void givenActorId_whenCustomUpdate_thenActorIsUpdated() {
        final long id = 1;
        final ActorBo actor = ActorBo.builder().id(id).name("NEW_ACTOR1").age(30).nationality("USA").build();

        Mockito.when(customRepository.existsById(id)).thenReturn(true);
        Mockito.when(mapper.boToEntity(actor)).thenReturn(
                Actor.builder().id(id).name("NEW_ACTOR1").age(30).nationality("USA").build()
        );
        Mockito.when(customRepository.save(
                        Actor.builder().id(id).name("NEW_ACTOR1").age(30).nationality("USA").build()))
                .thenReturn(Actor.builder().id(id).name("NEW_ACTOR1").age(30).nationality("USA").build());
        Mockito.when(mapper.entityToBo(Actor.builder().id(id).name("NEW_ACTOR1").age(30).nationality("USA").build()))
                .thenReturn(actor);

        final ActorBo updatedActor = service.customUpdate(id, actor);

        assertEquals(actor.getId(), updatedActor.getId());
        assertEquals(actor.getName(), updatedActor.getName());
        assertEquals(actor.getAge(), updatedActor.getAge());
        assertEquals(actor.getNationality(), updatedActor.getNationality());
    }

    @DisplayName("[ActorServiceImpl] deleteById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenDeleteById_thenThrow() {
        final long id = -1;

        Mockito.when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(id));
    }

    @DisplayName("[ActorServiceImpl] deleteById (success)")
    @Test
    void givenActorId_whenDeleteById_thenActorIsRemoved() {
        final long id = 1;

        Mockito.when(repository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteById(id));
    }

    @DisplayName("[ActorServiceImpl] customDeleteById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenCustomDeleteById_thenThrow() {
        final long id = -1;

        Mockito.when(customRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.customDeleteById(id));
    }

    @DisplayName("[ActorServiceImpl] customDeleteById (success)")
    @Test
    void givenActorId_whenCustomDeleteById_thenActorIsRemoved() {
        final long id = 1;

        Mockito.when(customRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> service.customDeleteById(id));
    }

}

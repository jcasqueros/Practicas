package com.viewnext.bsan.practica04.business.service.impl;

import com.viewnext.bsan.practica04.business.bo.ActorBo;
import com.viewnext.bsan.practica04.business.exception.BadInputDataException;
import com.viewnext.bsan.practica04.business.exception.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.business.exception.ResourceNotFoundException;
import com.viewnext.bsan.practica04.persistence.entity.Actor;
import com.viewnext.bsan.practica04.persistence.repository.ActorRepository;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomActorRepository;
import com.viewnext.bsan.practica04.presentation.request.PersonFilter;
import com.viewnext.bsan.practica04.presentation.request.QueryOptions;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelActorMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static com.viewnext.bsan.practica04.sampledata.ActorSampleData.SAMPLE_ACTORS;
import static com.viewnext.bsan.practica04.sampledata.ActorSampleData.SAMPLE_ACTORS_AS_BO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;

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

    @DisplayName("[ActorServiceImpl] getAll (custom)")
    @Test
    void givenActors_whenCustomGetAll_thenReturnActorList() {
        Mockito.when(customRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(List.of(SAMPLE_ACTORS.get(0), SAMPLE_ACTORS.get(3)));
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(0))).thenReturn(SAMPLE_ACTORS_AS_BO.get(0));
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(3))).thenReturn(SAMPLE_ACTORS_AS_BO.get(3));

        List<ActorBo> expectedActors = List.of(SAMPLE_ACTORS_AS_BO.get(0), SAMPLE_ACTORS_AS_BO.get(3));

        PersonFilter filter = new PersonFilter(Optional.empty(), Optional.empty(), Optional.of("USA"));
        QueryOptions queryOptions = new QueryOptions(Optional.of(true), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());
        List<ActorBo> foundActors = service.getAll(filter, queryOptions);

        assertFalse(foundActors.isEmpty());
        assertEquals(expectedActors.size(), foundActors.size());
        assertTrue(foundActors.containsAll(expectedActors));
    }

    @DisplayName("[ActorServiceImpl] getAll (regular)")
    @Test
    void givenActors_whenRegularGetAll_thenReturnActorList() {
        Mockito.when(repository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(SAMPLE_ACTORS.get(2))));
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(2))).thenReturn(SAMPLE_ACTORS_AS_BO.get(2));

        List<ActorBo> expectedActors = List.of(SAMPLE_ACTORS_AS_BO.get(2));

        PersonFilter filter = new PersonFilter(Optional.empty(), Optional.empty(), Optional.of("FRA"));
        QueryOptions queryOptions = new QueryOptions(Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());
        List<ActorBo> foundActors = service.getAll(filter, queryOptions);

        assertFalse(foundActors.isEmpty());
        assertEquals(expectedActors.size(), foundActors.size());
        assertTrue(foundActors.containsAll(expectedActors));
    }

    @DisplayName("[ActorServiceImpl] getById (custom, should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenCustomGetById_thenThrow() {
        final long id = -1;

        Mockito.when(customRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Boolean> useCustomRepository = Optional.of(true);
        assertThrows(ResourceNotFoundException.class, () -> service.getById(id, useCustomRepository));
    }

    @DisplayName("[ActorServiceImpl] getById (regular, should throw ResourceNotFoundException)")
    @Test
    void givenActorId_whenCustomGetById_thenReturnActorObject() {
        final ActorBo expectedActor = SAMPLE_ACTORS_AS_BO.get(3);
        final long id = expectedActor.getId();

        Mockito.when(customRepository.findById(id)).thenReturn(Optional.of(SAMPLE_ACTORS.get(3)));
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(3))).thenReturn(expectedActor);

        ActorBo foundActor = service.getById(id, Optional.of(true));

        assertNotNull(foundActor);
        assertEquals(expectedActor.getId(), foundActor.getId());
        assertEquals(expectedActor.getName(), foundActor.getName());
        assertEquals(expectedActor.getAge(), foundActor.getAge());
        assertEquals(expectedActor.getNationality(), foundActor.getNationality());
    }

    @DisplayName("[ActorServiceImpl] getById (regular, should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenRegularGetById_thenThrow() {
        final long id = -1;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Boolean> useCustomRepository = Optional.empty();
        assertThrows(ResourceNotFoundException.class, () -> service.getById(id, useCustomRepository));
    }

    @DisplayName("[ActorServiceImpl] getById (regular, should throw ResourceNotFoundException)")
    @Test
    void givenActorId_whenRegularGetById_thenReturnActorObject() {
        final ActorBo expectedActor = SAMPLE_ACTORS_AS_BO.get(3);
        final long id = expectedActor.getId();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(SAMPLE_ACTORS.get(3)));
        Mockito.when(mapper.entityToBo(SAMPLE_ACTORS.get(3))).thenReturn(expectedActor);

        ActorBo foundActor = service.getById(id, Optional.empty());

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

    @DisplayName("[ActorServiceImpl] getAll (success)")
    @Test
    void xxxxxy() {
        // TODO: Re-do this test
        fail("Not yet implemented");
    }

}

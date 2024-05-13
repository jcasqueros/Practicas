package com.pracs.films.bussiness.services;

import com.pracs.films.bussiness.bo.ActorBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.impl.ActorServiceImpl;
import com.pracs.films.exceptions.DuplicatedIdException;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.repositories.criteria.impl.ActorRepositoryImpl;
import com.pracs.films.persistence.repositories.jpa.ActorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

/**
 * Test for {@link com.pracs.films.bussiness.services.impl.ActorServiceImpl}
 *
 * @author Manuel Mateos de Torres
 */
@ExtendWith(MockitoExtension.class)
class ActorServicesImplTest {

    @Mock
    private ModelToBoConverter modelToBoConverter;

    @Mock
    private BoToModelConverter boToModelConverter;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private ActorRepositoryImpl actorRepositoryCriteria;

    @InjectMocks
    private ActorServiceImpl actorService;

    private Pageable pageable;

    private Actor actor;

    private ActorBO actorBO;

    @BeforeEach
    void setup() {

        actor = new Actor();
        actor.setId(1L);
        actor.setName("prueba");
        actor.setAge(20);
        actor.setNationality("Spain");

        actorBO = new ActorBO();
        actorBO.setId(1L);
        actorBO.setName("prueba");
        actorBO.setAge(20);
        actorBO.setNationality("Spain");

        pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
    }

    @DisplayName("JUnit test for save an actor - positive")
    @Test
    void givenActorBoObject_whenSave_thenReturnActorBoObject() throws ServiceException {
        given(modelToBoConverter.actorModelToBo(actor)).willReturn(actorBO);
        given(boToModelConverter.actorBoToModel(actorBO)).willReturn(actor);
        given(actorRepository.existsById(1L)).willReturn(false);
        given(actorRepository.save(boToModelConverter.actorBoToModel(actorBO))).willReturn(actor);

        ActorBO savedActorBO = actorService.save(actorBO);

        assertEquals(actorBO, savedActorBO);
    }

    @DisplayName("JUnit test for save an actor - negative")
    @Test
    void givenActorBoObject_whenSave_thenThrowDuplicatedException() throws ServiceException {
        given(actorRepository.existsById(1L)).willReturn(true);

        assertThrows(DuplicatedIdException.class, () -> actorService.save(actorBO));
    }

    @DisplayName("JUnit test for save an actor - negative")
    @Test()
    void givenActorBoObject_whenSave_thenThrowNestedRuntimeException() throws ServiceException {
        given(actorRepository.existsById(1L)).willReturn(false);
        when(actorRepository.save(boToModelConverter.actorBoToModel(actorBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> actorService.save(actorBO));
    }

    @DisplayName("JUnit test for update an actor - positive")
    @Test
    void givenActorBoObject_whenUpdate_thenReturnActorBoObject() throws ServiceException {
        given(modelToBoConverter.actorModelToBo(actor)).willReturn(actorBO);
        given(boToModelConverter.actorBoToModel(actorBO)).willReturn(actor);
        given(actorRepository.findById(1L)).willReturn(Optional.of(actor));
        given(actorRepository.save(boToModelConverter.actorBoToModel(actorBO))).willReturn(actor);

        ActorBO savedActorBO = actorBO;
        savedActorBO.setName("update");
        savedActorBO.setAge(25);
        savedActorBO.setNationality("update");
        ActorBO updateActorBO = actorService.update(savedActorBO);

        assertEquals(updateActorBO, savedActorBO);
    }

    @DisplayName("JUnit test for update an actor - negative")
    @Test
    void givenActorBoObject_whenupdate_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(actorRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> actorService.update(actorBO));
    }

    @DisplayName("JUnit test for update an actor - negative")
    @Test()
    void givenActorBoObject_whenUpdate_thenThrowNestedRuntimeException() throws ServiceException {
        given(modelToBoConverter.actorModelToBo(actor)).willReturn(actorBO);
        given(boToModelConverter.actorBoToModel(actorBO)).willReturn(actor);
        given(actorRepository.findById(1L)).willReturn(Optional.of(actor));
        when(actorRepository.save(boToModelConverter.actorBoToModel(actorBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> actorService.update(actorBO));
    }

    @DisplayName("JUnit test for find an actor by his id - positive")
    @Test
    void givenActorId_whenFindById_thenReturnActorBoObject() throws ServiceException {
        given(modelToBoConverter.actorModelToBo(actor)).willReturn(actorBO);
        given(actorRepository.findById(1L)).willReturn(Optional.of(actor));

        ActorBO savedActorBO = actorService.findById(actorBO.getId());

        assertEquals(actorBO, savedActorBO);
    }

    @DisplayName("JUnit test for find an actor by his id - negative")
    @Test
    void givenActorId_whenFindById_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(actorRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> actorService.findById(actorBO.getId()));
    }

    @DisplayName("JUnit test for find an actor by his id - negative")
    @Test
    void givenActorId_whenFindById_thenThrowNestedRuntimeException() throws ServiceException {
        when(actorRepository.findById(actorBO.getId())).thenThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> actorService.findById(actorBO.getId()));
    }

    @DisplayName("JUnit test for get all actors - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnActorBoObject() throws ServiceException {
        given(modelToBoConverter.actorModelToBo(actor)).willReturn(actorBO);
        Page<Actor> page = new PageImpl<>(List.of(actor), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(actorRepository.findAll(pageable)).willReturn(page);

        Page<ActorBO> savedActorsBO = actorService.findAll(PageRequest.of(0, 5, Sort.by("name").ascending()));

        assertEquals(1, savedActorsBO.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all actors - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowEmptyException() throws ServiceException {
        Page<Actor> page = new PageImpl<>(List.of(), PageRequest.of(0, 5, Sort.by("name").ascending()), 0);
        given(actorRepository.findAll(pageable)).willReturn(page);

        assertThrows(EmptyException.class, () -> actorService.findAll(pageable));
    }

    @DisplayName("JUnit test for get all actors - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowNestedRuntimeException() throws ServiceException {
        given(actorRepository.findAll(pageable)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> actorService.findAll(pageable));
    }

    @DisplayName("JUnit test for delete an actor by his id - positive")
    @Test
    void givenActorId_whenDeleteById_thenReturnActorBoObject() throws ServiceException {
        given(actorRepository.existsById(1L)).willReturn(true);
        willDoNothing().given(actorRepository).deleteById(actorBO.getId());

        actorService.deleteById(actorBO.getId());

        verify(actorRepository, times(1)).deleteById(actorBO.getId());
    }

    @DisplayName("JUnit test for delete an actor by his id - negative")
    @Test
    void givenActorId_whenDeleteById_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(actorRepository.existsById(1L)).willReturn(false);

        assertThrows(EntityNotFoundException.class, () -> actorService.deleteById(actorBO.getId()));
    }

    @DisplayName("JUnit test for delete an actor by his id - negative")
    @Test
    void givenActorId_whenDeleteById_thenThrowNestedRuntimeException() throws ServiceException {
        given(actorRepository.existsById(1L)).willReturn(true);
        willThrow(InvalidDataAccessApiUsageException.class).given(actorRepository).deleteById(actorBO.getId());

        assertThrows(ServiceException.class, () -> actorService.deleteById(actorBO.getId()));
    }

    @DisplayName("JUnit test for save an actor - positive")
    @Test
    void givenActorBoObject_whenSaveCriteria_thenReturnActorBoObject() throws ServiceException {
        given(modelToBoConverter.actorModelToBo(actor)).willReturn(actorBO);
        given(boToModelConverter.actorBoToModel(actorBO)).willReturn(actor);
        given(actorRepositoryCriteria.findActorById(1L)).willReturn(Optional.empty());
        given(actorRepositoryCriteria.saveActor(boToModelConverter.actorBoToModel(actorBO))).willReturn(actor);

        ActorBO savedActorBO = actorService.saveCriteria(actorBO);

        assertEquals(actorBO, savedActorBO);
    }

    @DisplayName("JUnit test for save an actor - negative")
    @Test
    void givenActorBoObject_whenSaveCriteria_thenThrowDuplicatedException() throws ServiceException {
        given(actorRepositoryCriteria.findActorById(1L)).willReturn(Optional.of(actor));

        assertThrows(DuplicatedIdException.class, () -> actorService.saveCriteria(actorBO));
    }

    @DisplayName("JUnit test for save an actor - negative")
    @Test()
    void givenActorBoObject_whenSaveCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(actorRepositoryCriteria.findActorById(1L)).willReturn(Optional.empty());
        when(actorRepositoryCriteria.saveActor(boToModelConverter.actorBoToModel(actorBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> actorService.saveCriteria(actorBO));
    }

    @DisplayName("JUnit test for update an actor - positive")
    @Test
    void givenActorBoObject_whenUpdateCriteria_thenReturnActorBoObject() throws ServiceException {
        given(modelToBoConverter.actorModelToBo(actor)).willReturn(actorBO);
        given(boToModelConverter.actorBoToModel(actorBO)).willReturn(actor);
        given(actorRepositoryCriteria.findActorById(1L)).willReturn(Optional.of(actor));
        given(actorRepositoryCriteria.updateActor(boToModelConverter.actorBoToModel(actorBO))).willReturn(actor);

        ActorBO savedActorBO = actorBO;
        savedActorBO.setName("update");
        savedActorBO.setAge(25);
        savedActorBO.setNationality("update");
        ActorBO updateActorBO = actorService.updateCriteria(savedActorBO);

        assertEquals(updateActorBO, savedActorBO);
    }

    @DisplayName("JUnit test for update an actor - negative")
    @Test
    void givenActorBoObject_whenupdateCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(actorRepositoryCriteria.findActorById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> actorService.updateCriteria(actorBO));
    }

    @DisplayName("JUnit test for update an actor - negative")
    @Test()
    void givenActorBoObject_whenUpdateCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(modelToBoConverter.actorModelToBo(actor)).willReturn(actorBO);
        given(boToModelConverter.actorBoToModel(actorBO)).willReturn(actor);
        given(actorRepositoryCriteria.findActorById(1L)).willReturn(Optional.of(actor));
        when(actorRepositoryCriteria.updateActor(boToModelConverter.actorBoToModel(actorBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> actorService.updateCriteria(actorBO));
    }

    @DisplayName("JUnit test for find an actor by his id - positive")
    @Test
    void givenActorId_whenFindByIdCriteria_thenReturnActorBoObject() throws ServiceException {
        given(modelToBoConverter.actorModelToBo(actor)).willReturn(actorBO);
        given(actorRepositoryCriteria.findActorById(1L)).willReturn(Optional.of(actor));

        ActorBO savedActorBO = actorService.findByIdCriteria(actorBO.getId());

        assertEquals(actorBO, savedActorBO);
    }

    @DisplayName("JUnit test for find an actor by his id - negative")
    @Test
    void givenActorId_whenFindByIdCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(actorRepositoryCriteria.findActorById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> actorService.findByIdCriteria(actorBO.getId()));
    }

    @DisplayName("JUnit test for find an actor by his id - negative")
    @Test
    void givenActorId_whenFindByIdCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        when(actorRepositoryCriteria.findActorById(actorBO.getId())).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> actorService.findByIdCriteria(actorBO.getId()));
    }

    @DisplayName("JUnit test for get all actors - positive")
    @Test
    void givenNothing_whenFindAllCriteria_thenReturnActorBoOList() throws ServiceException {
        given(modelToBoConverter.actorModelToBo(actor)).willReturn(actorBO);
        Page<Actor> page = new PageImpl<>(List.of(actor), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(actorRepositoryCriteria.findAllActors(pageable)).willReturn(page);

        Page<ActorBO> savedActorsBO = actorService.findAllCriteria(pageable);

        assertEquals(1, savedActorsBO.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all actors - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowEmptyException() throws ServiceException {
        Page<Actor> page = new PageImpl<>(List.of(), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(actorRepositoryCriteria.findAllActors(pageable)).willReturn(page);

        assertThrows(EmptyException.class, () -> actorService.findAllCriteria(pageable));
    }

    @DisplayName("JUnit test for get all actors - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(actorRepositoryCriteria.findAllActors(pageable)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> actorService.findAllCriteria(pageable));
    }

    @DisplayName("JUnit test for get all actors filtered - positive")
    @Test
    void givenPageableAndAttributesList_whenFindAllCriteriaFilter_thenReturnActorBoList() throws ServiceException {
        given(modelToBoConverter.actorModelToBo(actor)).willReturn(actorBO);
        Page<Actor> page = new PageImpl<>(List.of(actor), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(actorRepositoryCriteria.findAllFilter(pageable, List.of(), List.of(20), List.of())).willReturn(page);

        Page<ActorBO> savedActorsBO = actorService.findAllCriteriaFilter(pageable, List.of(), List.of(20), List.of());

        assertEquals(1, savedActorsBO.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all actors filtered - negative")
    @Test
    void givenNothing_whenFindAllCriteriaFilter_thenThrowEmptyException() throws ServiceException {
        Page<Actor> page = new PageImpl<>(List.of(), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(actorRepositoryCriteria.findAllFilter(pageable, List.of(), List.of(20), List.of())).willReturn(page);

        assertThrows(EmptyException.class,
                () -> actorService.findAllCriteriaFilter(pageable, List.of(), List.of(20), List.of()));
    }

    @DisplayName("JUnit test for get all actors filtered - negative")
    @Test
    void givenNothing_whenFindAllCriteriaFilter_thenThrowNestedRuntimeException() throws ServiceException {
        given(actorRepositoryCriteria.findAllFilter(pageable, List.of(), List.of(20), List.of())).willThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class,
                () -> actorService.findAllCriteriaFilter(pageable, List.of(), List.of(20), List.of()));
    }

    @DisplayName("JUnit test for delete an actor by his id - positive")
    @Test
    void givenActorId_whenDeleteByIdCriteria_thenReturnActorBoObject() throws ServiceException {
        given(actorRepositoryCriteria.findActorById(1L)).willReturn(Optional.of(actor));
        willDoNothing().given(actorRepositoryCriteria).deleteActorById(actor);

        actorService.deleteByIdCriteria(actorBO.getId());

        verify(actorRepositoryCriteria, times(1)).deleteActorById(actor);
    }

    @DisplayName("JUnit test for delete an actor by his id - negative")
    @Test
    void givenActorId_whenDeleteByIdCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(actorRepositoryCriteria.findActorById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> actorService.deleteByIdCriteria(actorBO.getId()));
    }

    @DisplayName("JUnit test for delete an actor by his id - negative")
    @Test
    void givenActorId_whenDeleteByIdCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(actorRepositoryCriteria.findActorById(1L)).willReturn(Optional.of(actor));
        willThrow(InvalidDataAccessApiUsageException.class).given(actorRepositoryCriteria).deleteActorById(actor);

        assertThrows(ServiceException.class, () -> actorService.deleteByIdCriteria(actorBO.getId()));
    }
}

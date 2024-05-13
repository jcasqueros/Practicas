package com.pracs.films.bussiness.services;

import com.pracs.films.bussiness.bo.ProducerBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.impl.ProducerServiceImpl;
import com.pracs.films.exceptions.DuplicatedIdException;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Producer;
import com.pracs.films.persistence.repositories.criteria.impl.ProducerRepositoryImpl;
import com.pracs.films.persistence.repositories.jpa.ProducerRepository;
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
 * Test for {@link ProducerServiceImpl}
 *
 * @author Manuel Mateos de Torres
 */
@ExtendWith(MockitoExtension.class)
class ProducerServicesImplTest {

    @Mock
    private ModelToBoConverter modelToBoConverter;

    @Mock
    private BoToModelConverter boToModelConverter;

    @Mock
    private ProducerRepository producerRepository;

    @Mock
    private ProducerRepositoryImpl producerRepositoryCriteria;

    @InjectMocks
    private ProducerServiceImpl producerService;

    private Pageable pageable;

    private Producer producer;

    private ProducerBO producerBO;

    @BeforeEach
    void setup() {

        producer = new Producer();
        producer.setId(1L);
        producer.setName("prueba");
        producer.setDebut(2020);

        producerBO = new ProducerBO();
        producerBO.setId(1L);
        producerBO.setName("prueba");
        producerBO.setDebut(2020);

        pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
    }

    @DisplayName("JUnit test for save an producer - positive")
    @Test
    void givenProducerBoObject_whenSave_thenReturnProducerBoObject() throws ServiceException {
        given(modelToBoConverter.producerModelToBo(producer)).willReturn(producerBO);
        given(boToModelConverter.producerBoToModel(producerBO)).willReturn(producer);
        given(producerRepository.existsById(1L)).willReturn(false);
        given(producerRepository.save(boToModelConverter.producerBoToModel(producerBO))).willReturn(producer);

        ProducerBO savedproducerBO = producerService.save(producerBO);

        assertEquals(producerBO, savedproducerBO);
    }

    @DisplayName("JUnit test for save an producer - negative")
    @Test
    void givenProducerBoObject_whenSave_thenThrowDuplicatedException() throws ServiceException {
        given(producerRepository.existsById(1L)).willReturn(true);

        assertThrows(DuplicatedIdException.class, () -> producerService.save(producerBO));
    }

    @DisplayName("JUnit test for save an producer - negative")
    @Test()
    void givenProducerBoObject_whenSave_thenThrowNestedRuntimeException() throws ServiceException {
        given(producerRepository.existsById(1L)).willReturn(false);
        when(producerRepository.save(boToModelConverter.producerBoToModel(producerBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> producerService.save(producerBO));
    }

    @DisplayName("JUnit test for update an producer - positive")
    @Test
    void givenProducerBoObject_whenUpdate_thenReturnProducerBoObject() throws ServiceException {
        given(modelToBoConverter.producerModelToBo(producer)).willReturn(producerBO);
        given(boToModelConverter.producerBoToModel(producerBO)).willReturn(producer);
        given(producerRepository.findById(1L)).willReturn(Optional.of(producer));
        given(producerRepository.save(boToModelConverter.producerBoToModel(producerBO))).willReturn(producer);

        ProducerBO savedproducerBO = producerBO;
        savedproducerBO.setName("update");
        savedproducerBO.setDebut(2000);
        ProducerBO updateproducerBO = producerService.update(savedproducerBO);

        assertEquals(updateproducerBO, savedproducerBO);
    }

    @DisplayName("JUnit test for update an producer - negative")
    @Test
    void givenProducerBoObject_whenupdate_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(producerRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> producerService.update(producerBO));
    }

    @DisplayName("JUnit test for update an producer - negative")
    @Test()
    void givenProducerBoObject_whenUpdate_thenThrowNestedRuntimeException() throws ServiceException {
        given(modelToBoConverter.producerModelToBo(producer)).willReturn(producerBO);
        given(boToModelConverter.producerBoToModel(producerBO)).willReturn(producer);
        given(producerRepository.findById(1L)).willReturn(Optional.of(producer));
        when(producerRepository.save(boToModelConverter.producerBoToModel(producerBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> producerService.update(producerBO));
    }

    @DisplayName("JUnit test for find an producer by his id - positive")
    @Test
    void givenproducerId_whenFindById_thenReturnProducerBoObject() throws ServiceException {
        given(modelToBoConverter.producerModelToBo(producer)).willReturn(producerBO);
        given(producerRepository.findById(1L)).willReturn(Optional.of(producer));

        ProducerBO savedproducerBO = producerService.findById(producerBO.getId());

        assertEquals(producerBO, savedproducerBO);
    }

    @DisplayName("JUnit test for find an producer by his id - negative")
    @Test
    void givenproducerId_whenFindById_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(producerRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> producerService.findById(producerBO.getId()));
    }

    @DisplayName("JUnit test for find an producer by his id - negative")
    @Test
    void givenproducerId_whenFindById_thenThrowNestedRuntimeException() throws ServiceException {
        when(producerRepository.findById(producerBO.getId())).thenThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> producerService.findById(producerBO.getId()));
    }

    @DisplayName("JUnit test for get all producers - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnProducerBoObject() throws ServiceException {
        given(modelToBoConverter.producerModelToBo(producer)).willReturn(producerBO);
        Page<Producer> page = new PageImpl<>(List.of(producer), pageable, 10);
        given(producerRepository.findAll(pageable)).willReturn(page);

        Page<ProducerBO> savedProducersBO = producerService.findAll(pageable);

        assertEquals(1, savedProducersBO.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all producers - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowEmptyException() throws ServiceException {
        Page<Producer> page = new PageImpl<>(List.of(), pageable, 10);
        given(producerRepository.findAll(pageable)).willReturn(page);

        assertThrows(EmptyException.class, () -> producerService.findAll(pageable));
    }

    @DisplayName("JUnit test for get all producers - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowNestedRuntimeException() throws ServiceException {
        given(producerRepository.findAll(pageable)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> producerService.findAll(pageable));
    }

    @DisplayName("JUnit test for delete an producer by his id - positive")
    @Test
    void givenproducerId_whenDeleteById_thenReturnProducerBoObject() throws ServiceException {
        given(producerRepository.existsById(1L)).willReturn(true);
        willDoNothing().given(producerRepository).deleteById(producerBO.getId());

        producerService.deleteById(producerBO.getId());

        verify(producerRepository, times(1)).deleteById(producerBO.getId());
    }

    @DisplayName("JUnit test for delete an producer by his id - negative")
    @Test
    void givenproducerId_whenDeleteById_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(producerRepository.existsById(1L)).willReturn(false);

        assertThrows(EntityNotFoundException.class, () -> producerService.deleteById(producerBO.getId()));
    }

    @DisplayName("JUnit test for delete an producer by his id - negative")
    @Test
    void givenproducerId_whenDeleteById_thenThrowNestedRuntimeException() throws ServiceException {
        given(producerRepository.existsById(1L)).willReturn(true);
        willThrow(InvalidDataAccessApiUsageException.class).given(producerRepository).deleteById(producerBO.getId());

        assertThrows(ServiceException.class, () -> producerService.deleteById(producerBO.getId()));
    }

    @DisplayName("JUnit test for save an producer - positive")
    @Test
    void givenProducerBoObject_whenSaveCriteria_thenReturnProducerBoObject() throws ServiceException {
        given(modelToBoConverter.producerModelToBo(producer)).willReturn(producerBO);
        given(boToModelConverter.producerBoToModel(producerBO)).willReturn(producer);
        given(producerRepositoryCriteria.findProducerById(1L)).willReturn(Optional.empty());
        given(producerRepositoryCriteria.saveProducer(boToModelConverter.producerBoToModel(producerBO))).willReturn(
                producer);

        ProducerBO savedproducerBO = producerService.saveCriteria(producerBO);

        assertEquals(producerBO, savedproducerBO);
    }

    @DisplayName("JUnit test for save an producer - negative")
    @Test
    void givenProducerBoObject_whenSaveCriteria_thenThrowDuplicatedException() throws ServiceException {
        given(producerRepositoryCriteria.findProducerById(1L)).willReturn(Optional.of(producer));

        assertThrows(DuplicatedIdException.class, () -> producerService.saveCriteria(producerBO));
    }

    @DisplayName("JUnit test for save an producer - negative")
    @Test()
    void givenProducerBoObject_whenSaveCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(producerRepositoryCriteria.findProducerById(1L)).willReturn(Optional.empty());
        when(producerRepositoryCriteria.saveProducer(boToModelConverter.producerBoToModel(producerBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> producerService.saveCriteria(producerBO));
    }

    @DisplayName("JUnit test for update an producer - positive")
    @Test
    void givenProducerBoObject_whenUpdateCriteria_thenReturnProducerBoObject() throws ServiceException {
        given(modelToBoConverter.producerModelToBo(producer)).willReturn(producerBO);
        given(boToModelConverter.producerBoToModel(producerBO)).willReturn(producer);
        given(producerRepositoryCriteria.findProducerById(1L)).willReturn(Optional.of(producer));
        given(producerRepositoryCriteria.updateProducer(boToModelConverter.producerBoToModel(producerBO))).willReturn(
                producer);

        ProducerBO savedproducerBO = producerBO;
        savedproducerBO.setName("update");
        savedproducerBO.setDebut(2000);
        ProducerBO updateproducerBO = producerService.updateCriteria(savedproducerBO);

        assertEquals(updateproducerBO, savedproducerBO);
    }

    @DisplayName("JUnit test for update an producer - negative")
    @Test
    void givenProducerBoObject_whenupdateCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(producerRepositoryCriteria.findProducerById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> producerService.updateCriteria(producerBO));
    }

    @DisplayName("JUnit test for update an producer - negative")
    @Test()
    void givenProducerBoObject_whenUpdateCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(modelToBoConverter.producerModelToBo(producer)).willReturn(producerBO);
        given(boToModelConverter.producerBoToModel(producerBO)).willReturn(producer);
        given(producerRepositoryCriteria.findProducerById(1L)).willReturn(Optional.of(producer));
        when(producerRepositoryCriteria.updateProducer(boToModelConverter.producerBoToModel(producerBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> producerService.updateCriteria(producerBO));
    }

    @DisplayName("JUnit test for find an producer by his id - positive")
    @Test
    void givenproducerId_whenFindByIdCriteria_thenReturnProducerBoObject() throws ServiceException {
        given(modelToBoConverter.producerModelToBo(producer)).willReturn(producerBO);
        given(producerRepositoryCriteria.findProducerById(1L)).willReturn(Optional.of(producer));

        ProducerBO savedproducerBO = producerService.findByIdCriteria(producerBO.getId());

        assertEquals(producerBO, savedproducerBO);
    }

    @DisplayName("JUnit test for find an producer by his id - negative")
    @Test
    void givenproducerId_whenFindByIdCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(producerRepositoryCriteria.findProducerById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> producerService.findByIdCriteria(producerBO.getId()));
    }

    @DisplayName("JUnit test for find an producer by his id - negative")
    @Test
    void givenproducerId_whenFindByIdCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        when(producerRepositoryCriteria.findProducerById(producerBO.getId())).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> producerService.findByIdCriteria(producerBO.getId()));
    }

    @DisplayName("JUnit test for get all producers - positive")
    @Test
    void givenNothing_whenFindAllCriteria_thenReturnProducerBoObject() throws ServiceException {
        given(modelToBoConverter.producerModelToBo(producer)).willReturn(producerBO);
        Page<Producer> page = new PageImpl<>(List.of(producer), pageable, 10);
        given(producerRepositoryCriteria.findAllProducer(pageable)).willReturn(page);

        Page<ProducerBO> savedproducersBO = producerService.findAllCriteria(pageable);

        assertEquals(1, savedproducersBO.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all producers - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowEmptyException() throws ServiceException {
        Page<Producer> page = new PageImpl<>(List.of(), pageable, 10);
        given(producerRepositoryCriteria.findAllProducer(pageable)).willReturn(page);

        assertThrows(EmptyException.class, () -> producerService.findAllCriteria(pageable));
    }

    @DisplayName("JUnit test for get all producers - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(producerRepositoryCriteria.findAllProducer(pageable)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> producerService.findAllCriteria(pageable));
    }

    @DisplayName("JUnit test for delete an producer by his id - positive")
    @Test
    void givenproducerId_whenDeleteByIdCriteria_thenReturnProducerBoObject() throws ServiceException {
        given(producerRepositoryCriteria.findProducerById(1L)).willReturn(Optional.of(producer));
        willDoNothing().given(producerRepositoryCriteria).deleteProducerById(producer);

        producerService.deleteByIdCriteria(producerBO.getId());

        verify(producerRepositoryCriteria, times(1)).deleteProducerById(producer);
    }

    @DisplayName("JUnit test for delete an producer by his id - negative")
    @Test
    void givenproducerId_whenDeleteByIdCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(producerRepositoryCriteria.findProducerById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> producerService.deleteByIdCriteria(producerBO.getId()));
    }

    @DisplayName("JUnit test for delete an producer by his id - negative")
    @Test
    void givenproducerId_whenDeleteByIdCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(producerRepositoryCriteria.findProducerById(1L)).willReturn(Optional.of(producer));
        willThrow(InvalidDataAccessApiUsageException.class).given(producerRepositoryCriteria)
                .deleteProducerById(producer);

        assertThrows(ServiceException.class, () -> producerService.deleteByIdCriteria(producerBO.getId()));
    }
}

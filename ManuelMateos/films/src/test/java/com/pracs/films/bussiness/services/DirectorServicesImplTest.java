package com.pracs.films.bussiness.services;

import com.pracs.films.bussiness.bo.DirectorBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.impl.DirectorServiceImpl;
import com.pracs.films.exceptions.DuplicatedIdException;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.repositories.criteria.impl.DirectorRepositoryImpl;
import com.pracs.films.persistence.repositories.jpa.DirectorRepository;
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
 * Test for {@link DirectorServiceImpl}
 *
 * @author Manuel Mateos de Torres
 */
@ExtendWith(MockitoExtension.class)
class DirectorServicesImplTest {

    @Mock
    private ModelToBoConverter modelToBoConverter;

    @Mock
    private BoToModelConverter boToModelConverter;

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private DirectorRepositoryImpl directorRepositoryCriteria;

    @InjectMocks
    private DirectorServiceImpl directorService;

    private Pageable pageable;

    private Director director;

    private DirectorBO directorBO;

    @BeforeEach
    void setup() {

        director = new Director();
        director.setId(1L);
        director.setName("prueba");
        director.setAge(20);
        director.setNationality("Spain");

        directorBO = new DirectorBO();
        directorBO.setId(1L);
        directorBO.setName("prueba");
        directorBO.setAge(20);
        directorBO.setNationality("Spain");

        pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
    }

    @DisplayName("JUnit test for save a director - positive")
    @Test
    void givenDirectorBObject_whenSave_thenReturnDirectorBObject() throws ServiceException {
        given(modelToBoConverter.directorModelToBo(director)).willReturn(directorBO);
        given(boToModelConverter.directorBoToModel(directorBO)).willReturn(director);
        given(directorRepository.existsById(1L)).willReturn(false);
        given(directorRepository.save(boToModelConverter.directorBoToModel(directorBO))).willReturn(director);

        DirectorBO savedDirectorBO = directorService.save(directorBO);

        assertEquals(directorBO, savedDirectorBO);
    }

    @DisplayName("JUnit test for save a director - negative")
    @Test
    void givenDirectorBObject_whenSave_thenThrowDuplicatedException() throws ServiceException {
        given(directorRepository.existsById(1L)).willReturn(true);

        assertThrows(DuplicatedIdException.class, () -> directorService.save(directorBO));
    }

    @DisplayName("JUnit test for save a director - negative")
    @Test()
    void givenDirectorBObject_whenSave_thenThrowNestedRuntimeException() throws ServiceException {
        given(directorRepository.existsById(1L)).willReturn(false);
        when(directorRepository.save(boToModelConverter.directorBoToModel(directorBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> directorService.save(directorBO));
    }

    @DisplayName("JUnit test for update a director - positive")
    @Test
    void givenDirectorBObject_whenUpdate_thenReturnDirectorBObject() throws ServiceException {
        given(modelToBoConverter.directorModelToBo(director)).willReturn(directorBO);
        given(boToModelConverter.directorBoToModel(directorBO)).willReturn(director);
        given(directorRepository.findById(1L)).willReturn(Optional.of(director));
        given(directorRepository.save(boToModelConverter.directorBoToModel(directorBO))).willReturn(director);

        DirectorBO savedDirectorBO = directorBO;
        savedDirectorBO.setName("update");
        savedDirectorBO.setAge(25);
        savedDirectorBO.setNationality("update");
        DirectorBO updatedirectorBO = directorService.update(savedDirectorBO);

        assertEquals(updatedirectorBO, savedDirectorBO);
    }

    @DisplayName("JUnit test for update a director - negative")
    @Test
    void givenDirectorBObject_whenupdate_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(directorRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> directorService.update(directorBO));
    }

    @DisplayName("JUnit test for update a director - negative")
    @Test()
    void givenDirectorBObject_whenUpdate_thenThrowNestedRuntimeException() throws ServiceException {
        given(modelToBoConverter.directorModelToBo(director)).willReturn(directorBO);
        given(boToModelConverter.directorBoToModel(directorBO)).willReturn(director);
        given(directorRepository.findById(1L)).willReturn(Optional.of(director));
        when(directorRepository.save(boToModelConverter.directorBoToModel(directorBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> directorService.update(directorBO));
    }

    @DisplayName("JUnit test for find a director by his id - positive")
    @Test
    void givendirectorId_whenFindById_thenReturnDirectorBObject() throws ServiceException {
        given(modelToBoConverter.directorModelToBo(director)).willReturn(directorBO);
        given(directorRepository.findById(1L)).willReturn(Optional.of(director));

        DirectorBO savedDirectorBO = directorService.findById(directorBO.getId());

        assertEquals(directorBO, savedDirectorBO);
    }

    @DisplayName("JUnit test for find a director by his id - negative")
    @Test
    void givendirectorId_whenFindById_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(directorRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> directorService.findById(directorBO.getId()));
    }

    @DisplayName("JUnit test for find a director by his id - negative")
    @Test
    void givendirectorId_whenFindById_thenThrowNestedRuntimeException() throws ServiceException {
        when(directorRepository.findById(directorBO.getId())).thenThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> directorService.findById(directorBO.getId()));
    }

    @DisplayName("JUnit test for find a director by his name and age - positive")
    @Test
    void givendirectorNameAndAge_whenFindByNameAndAge_thenReturnListDirectorBObject() throws ServiceException {
        given(modelToBoConverter.directorModelToBo(director)).willReturn(directorBO);
        given(directorRepository.findByNameAndAge(director.getName(), director.getAge())).willReturn(List.of(director));

        List<DirectorBO> directorBOList = directorService.findByNameAndAge(director.getName(), director.getAge());

        assertEquals(1, directorBOList.size());
    }

    @DisplayName("JUnit test for find a director by his name and age - negative")
    @Test
    void givendirectorNameAndAge_whenFindByNameAndAge_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(directorRepository.findByNameAndAge(director.getName(), director.getAge())).willReturn(List.of());

        assertThrows(EmptyException.class,
                () -> directorService.findByNameAndAge(director.getName(), director.getAge()));
    }

    @DisplayName("JUnit test for find a director by his name and age - negative")
    @Test
    void givendirectorNameAndAge_whenFindByNameAndAge_thenThrowNestedRuntimeException() throws ServiceException {
        when(directorRepository.findByNameAndAge(director.getName(), director.getAge())).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class,
                () -> directorService.findByNameAndAge(director.getName(), director.getAge()));
    }

    @DisplayName("JUnit test for get all directors - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnDirectorBObject() throws ServiceException {
        given(modelToBoConverter.directorModelToBo(director)).willReturn(directorBO);
        Page<Director> page = new PageImpl<>(List.of(director), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(directorRepository.findAll(pageable)).willReturn(page);

        Page<DirectorBO> saveddirectorsBO = directorService.findAll(pageable);

        assertEquals(1, saveddirectorsBO.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all directors - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowEmptyException() throws ServiceException {
        Page<Director> page = new PageImpl<>(List.of(), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(directorRepository.findAll(pageable)).willReturn(page);

        assertThrows(EmptyException.class, () -> directorService.findAll(pageable));
    }

    @DisplayName("JUnit test for get all directors - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowNestedRuntimeException() throws ServiceException {
        given(directorRepository.findAll(pageable)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> directorService.findAll(pageable));
    }

    @DisplayName("JUnit test for delete a director by his id - positive")
    @Test
    void givendirectorId_whenDeleteById_thenReturnDirectorBObject() throws ServiceException {
        given(directorRepository.existsById(1L)).willReturn(true);
        willDoNothing().given(directorRepository).deleteById(directorBO.getId());

        directorService.deleteById(directorBO.getId());

        verify(directorRepository, times(1)).deleteById(directorBO.getId());
    }

    @DisplayName("JUnit test for delete a director by his id - negative")
    @Test
    void givendirectorId_whenDeleteById_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(directorRepository.existsById(1L)).willReturn(false);

        assertThrows(EntityNotFoundException.class, () -> directorService.deleteById(directorBO.getId()));
    }

    @DisplayName("JUnit test for delete a director by his id - negative")
    @Test
    void givendirectorId_whenDeleteById_thenThrowNestedRuntimeException() throws ServiceException {
        given(directorRepository.existsById(1L)).willReturn(true);
        willThrow(InvalidDataAccessApiUsageException.class).given(directorRepository).deleteById(directorBO.getId());

        assertThrows(ServiceException.class, () -> directorService.deleteById(directorBO.getId()));
    }

    @DisplayName("JUnit test for save a director - positive")
    @Test
    void givenDirectorBObject_whenSaveCriteria_thenReturnDirectorBObject() throws ServiceException {
        given(modelToBoConverter.directorModelToBo(director)).willReturn(directorBO);
        given(boToModelConverter.directorBoToModel(directorBO)).willReturn(director);
        given(directorRepositoryCriteria.findDirectorById(1L)).willReturn(Optional.empty());
        given(directorRepositoryCriteria.saveDirector(boToModelConverter.directorBoToModel(directorBO))).willReturn(
                director);

        DirectorBO savedDirectorBO = directorService.saveCriteria(directorBO);

        assertEquals(directorBO, savedDirectorBO);
    }

    @DisplayName("JUnit test for save a director - negative")
    @Test
    void givenDirectorBObject_whenSaveCriteria_thenThrowDuplicatedException() throws ServiceException {
        given(directorRepositoryCriteria.findDirectorById(1L)).willReturn(Optional.of(director));

        assertThrows(DuplicatedIdException.class, () -> directorService.saveCriteria(directorBO));
    }

    @DisplayName("JUnit test for save a director - negative")
    @Test()
    void givenDirectorBObject_whenSaveCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(directorRepositoryCriteria.findDirectorById(1L)).willReturn(Optional.empty());
        when(directorRepositoryCriteria.saveDirector(boToModelConverter.directorBoToModel(directorBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> directorService.saveCriteria(directorBO));
    }

    @DisplayName("JUnit test for update a director - positive")
    @Test
    void givenDirectorBObject_whenUpdateCriteria_thenReturnDirectorBObject() throws ServiceException {
        given(modelToBoConverter.directorModelToBo(director)).willReturn(directorBO);
        given(boToModelConverter.directorBoToModel(directorBO)).willReturn(director);
        given(directorRepositoryCriteria.findDirectorById(1L)).willReturn(Optional.of(director));
        given(directorRepositoryCriteria.updateDirector(boToModelConverter.directorBoToModel(directorBO))).willReturn(
                director);

        DirectorBO savedDirectorBO = directorBO;
        savedDirectorBO.setName("update");
        savedDirectorBO.setAge(25);
        savedDirectorBO.setNationality("update");
        DirectorBO updatedirectorBO = directorService.updateCriteria(savedDirectorBO);

        assertEquals(updatedirectorBO, savedDirectorBO);
    }

    @DisplayName("JUnit test for update a director - negative")
    @Test
    void givenDirectorBObject_whenupdateCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(directorRepositoryCriteria.findDirectorById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> directorService.updateCriteria(directorBO));
    }

    @DisplayName("JUnit test for update a director - negative")
    @Test()
    void givenDirectorBObject_whenUpdateCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(modelToBoConverter.directorModelToBo(director)).willReturn(directorBO);
        given(boToModelConverter.directorBoToModel(directorBO)).willReturn(director);
        given(directorRepositoryCriteria.findDirectorById(1L)).willReturn(Optional.of(director));
        when(directorRepositoryCriteria.updateDirector(boToModelConverter.directorBoToModel(directorBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> directorService.updateCriteria(directorBO));
    }

    @DisplayName("JUnit test for find a director by his id - positive")
    @Test
    void givendirectorId_whenFindByIdCriteria_thenReturnDirectorBObject() throws ServiceException {
        given(modelToBoConverter.directorModelToBo(director)).willReturn(directorBO);
        given(directorRepositoryCriteria.findDirectorById(1L)).willReturn(Optional.of(director));

        DirectorBO savedDirectorBO = directorService.findByIdCriteria(directorBO.getId());

        assertEquals(directorBO, savedDirectorBO);
    }

    @DisplayName("JUnit test for find a director by his id - negative")
    @Test
    void givendirectorId_whenFindByIdCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(directorRepositoryCriteria.findDirectorById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> directorService.findByIdCriteria(directorBO.getId()));
    }

    @DisplayName("JUnit test for find a director by his id - negative")
    @Test
    void givendirectorId_whenFindByIdCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        when(directorRepositoryCriteria.findDirectorById(directorBO.getId())).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> directorService.findByIdCriteria(directorBO.getId()));
    }

    @DisplayName("JUnit test for find a director by his name and age - positive")
    @Test
    void givendirectorNameAndAge_whenFindByNameAndAgeCriteria_thenReturnListDirectorBObject() throws ServiceException {
        given(modelToBoConverter.directorModelToBo(director)).willReturn(directorBO);
        given(directorRepositoryCriteria.findByNameAndAge(director.getName(), director.getAge())).willReturn(
                List.of(director));

        List<DirectorBO> directorBOList = directorService.findByNameAndAgeCriteria(director.getName(),
                director.getAge());

        assertEquals(1, directorBOList.size());
    }

    @DisplayName("JUnit test for find a director by his name and age - negative")
    @Test
    void givendirectorNameAndAge_whenFindByNameAndAgeCriteria_thenThrowEntityNotFoundExcepition()
            throws ServiceException {
        given(directorRepositoryCriteria.findByNameAndAge(director.getName(), director.getAge())).willReturn(List.of());

        assertThrows(EmptyException.class,
                () -> directorService.findByNameAndAgeCriteria(director.getName(), director.getAge()));
    }

    @DisplayName("JUnit test for find a director by his name and age - negative")
    @Test
    void givendirectorNameAndAge_whenFindByNameAndAgeCriteria_thenThrowNestedRuntimeException()
            throws ServiceException {
        when(directorRepositoryCriteria.findByNameAndAge(director.getName(), director.getAge())).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class,
                () -> directorService.findByNameAndAgeCriteria(director.getName(), director.getAge()));
    }

    @DisplayName("JUnit test for get all directors - positive")
    @Test
    void givenNothing_whenFindAllCriteria_thenReturnDirectorBObject() throws ServiceException {
        given(modelToBoConverter.directorModelToBo(director)).willReturn(directorBO);
        Page<Director> page = new PageImpl<>(List.of(director), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(directorRepositoryCriteria.findAllDirector(pageable)).willReturn(page);

        Page<DirectorBO> savedDirectorsBO = directorService.findAllCriteria(pageable);

        assertEquals(1, savedDirectorsBO.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all directors - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowEmptyException() throws ServiceException {
        Page<Director> page = new PageImpl<>(List.of(), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(directorRepositoryCriteria.findAllDirector(pageable)).willReturn(page);

        assertThrows(EmptyException.class, () -> directorService.findAllCriteria(pageable));
    }

    @DisplayName("JUnit test for get all directors - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(directorRepositoryCriteria.findAllDirector(pageable)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> directorService.findAllCriteria(pageable));
    }

    @DisplayName("JUnit test for get all directors filtered - positive")
    @Test
    void givenPageableAndAttributesList_whenFindAllCriteriaFilter_thenReturnDirectorBoList() throws ServiceException {
        given(modelToBoConverter.directorModelToBo(director)).willReturn(directorBO);
        Page<Director> page = new PageImpl<>(List.of(director), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(directorRepositoryCriteria.findAllFilter(pageable, List.of(), List.of(20), List.of())).willReturn(page);

        Page<DirectorBO> savedDirectorBO = directorService.findAllCriteriaFilter(pageable, List.of(), List.of(20),
                List.of());

        assertEquals(1, savedDirectorBO.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all directors filtered - negative")
    @Test
    void givenNothing_whenFindAllCriteriaFilter_thenThrowEmptyException() throws ServiceException {
        Page<Director> page = new PageImpl<>(List.of(), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(directorRepositoryCriteria.findAllFilter(pageable, List.of(), List.of(20), List.of())).willReturn(page);

        assertThrows(EmptyException.class,
                () -> directorService.findAllCriteriaFilter(pageable, List.of(), List.of(20), List.of()));
    }

    @DisplayName("JUnit test for get all directors filtered - negative")
    @Test
    void givenNothing_whenFindAllCriteriaFilter_thenThrowNestedRuntimeException() throws ServiceException {
        given(directorRepositoryCriteria.findAllFilter(pageable, List.of(), List.of(20), List.of())).willThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class,
                () -> directorService.findAllCriteriaFilter(pageable, List.of(), List.of(20), List.of()));
    }

    @DisplayName("JUnit test for delete a director by his id - positive")
    @Test
    void givendirectorId_whenDeleteByIdCriteria_thenReturnDirectorBObject() throws ServiceException {
        given(directorRepositoryCriteria.findDirectorById(1L)).willReturn(Optional.of(director));
        willDoNothing().given(directorRepositoryCriteria).deleteDirectorById(director);

        directorService.deleteByIdCriteria(directorBO.getId());

        verify(directorRepositoryCriteria, times(1)).deleteDirectorById(director);
    }

    @DisplayName("JUnit test for delete a director by his id - negative")
    @Test
    void givendirectorId_whenDeleteByIdCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(directorRepositoryCriteria.findDirectorById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> directorService.deleteByIdCriteria(directorBO.getId()));
    }

    @DisplayName("JUnit test for delete a director by his id - negative")
    @Test
    void givendirectorId_whenDeleteByIdCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(directorRepositoryCriteria.findDirectorById(1L)).willReturn(Optional.of(director));
        willThrow(InvalidDataAccessApiUsageException.class).given(directorRepositoryCriteria)
                .deleteDirectorById(director);

        assertThrows(ServiceException.class, () -> directorService.deleteByIdCriteria(directorBO.getId()));
    }
}

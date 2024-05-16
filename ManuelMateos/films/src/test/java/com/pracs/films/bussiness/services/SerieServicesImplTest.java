package com.pracs.films.bussiness.services;

import com.pracs.films.bussiness.bo.ActorBO;
import com.pracs.films.bussiness.bo.DirectorBO;
import com.pracs.films.bussiness.bo.ProducerBO;
import com.pracs.films.bussiness.bo.SerieBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.impl.SerieServiceImpl;
import com.pracs.films.configuration.ConstantMessages;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.models.Producer;
import com.pracs.films.persistence.models.Serie;
import com.pracs.films.persistence.repositories.criteria.impl.SerieRepositoryImpl;
import com.pracs.films.persistence.repositories.jpa.SerieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

/**
 * Test for {@link SerieServiceImpl}
 *
 * @author Manuel Mateos de Torres
 */
@ExtendWith(MockitoExtension.class)
class SerieServicesImplTest {

    @Mock
    private ModelToBoConverter modelToBoConverter;

    @Mock
    private BoToModelConverter boToModelConverter;

    @Mock
    private SerieRepository serieRepository;

    @Mock
    private SerieRepositoryImpl serieRepositoryCriteria;

    @Mock
    private ConstantMessages constantMessages;

    @MockBean
    private WebClient webClient;

    @InjectMocks
    private SerieServiceImpl serieService;

    private Pageable pageable;

    private Actor actor;

    private ActorBO actorBO;

    private Director director;

    private DirectorBO directorBO;

    private Producer producer;

    private ProducerBO producerBO;

    private Serie serie;

    private SerieBO serieBO;

    @BeforeEach
    void setup() {

        actor = new Actor();
        actor.setId(1L);
        actor.setName("prueba");
        actor.setAge(25);
        actor.setNationality("Spain");
        List<Actor> actors = new ArrayList<>();
        actors.add(actor);

        director = new Director();
        director.setId(2L);
        director.setName("prueba");
        director.setAge(25);
        director.setNationality("Spain");

        producer = new Producer();
        producer.setId(1L);
        producer.setName("prueba");
        producer.setDebut(2020);

        serie = new Serie();
        serie.setId(1L);
        serie.setTitle("prueba");
        serie.setDebut(2020);
        serie.setActors(actors);
        serie.setProducer(producer);
        serie.setDirector(director);

        actorBO = new ActorBO();
        actorBO.setId(1L);
        actorBO.setName("prueba");
        actorBO.setAge(25);
        actorBO.setNationality("Spain");
        List<ActorBO> actorsBO = new ArrayList<>();
        actorsBO.add(actorBO);

        directorBO = new DirectorBO();
        directorBO.setId(2L);
        directorBO.setName("prueba");
        directorBO.setAge(25);
        directorBO.setNationality("Spain");

        producerBO = new ProducerBO();
        producerBO.setId(1L);
        producerBO.setName("prueba");
        producerBO.setDebut(2020);

        serieBO = new SerieBO();
        serieBO.setId(1L);
        serieBO.setTitle("prueba");
        serieBO.setDebut(2020);
        serieBO.setActors(actorsBO);
        serieBO.setProducer(producerBO);
        serieBO.setDirector(directorBO);

        pageable = PageRequest.of(0, 5, Sort.by("title").ascending());
    }

    @DisplayName("JUnit test for save an serie - positive")
    @Test
    void givenSerieBoObject_whenSave_thenReturnSerieBoObject() throws ServiceException {
        given(modelToBoConverter.serieModelToBo(serie)).willReturn(serieBO);
        given(boToModelConverter.serieBoToModel(serieBO)).willReturn(serie);
        given(serieService.existsActorJPA(1L, "8080")).willReturn(false);
        given(serieService.existsDirectorJPA(1L, "8080")).willReturn(false);
        given(serieService.existsProducerJPA(1L, "8080")).willReturn(false);
        given(serieRepository.save(boToModelConverter.serieBoToModel(serieBO))).willReturn(serie);

        SerieBO savedserieBO = serieService.save(serieBO, "8080");

        assertEquals(serieBO, savedserieBO);
    }

    @DisplayName("JUnit test for save an serie - negative")
    @Test
    void givenSerieBoObject_whenSave_thenThrowDuplicatedException() throws ServiceException {

        assertThrows(EntityNotFoundException.class, () -> serieService.save(serieBO, "8080"));
    }

    @DisplayName("JUnit test for save an serie - negative")
    @Test()
    void givenSerieBoObject_whenSave_thenThrowNestedRuntimeException() throws ServiceException {
        given(serieService.existsActorJPA(1L, "8080")).willReturn(false);
        given(serieService.existsDirectorJPA(1L, "8080")).willReturn(false);
        given(serieService.existsProducerJPA(1L, "8080")).willReturn(false);
        when(serieRepository.save(boToModelConverter.serieBoToModel(serieBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> serieService.save(serieBO, "8080"));
    }

    @DisplayName("JUnit test for update an serie - positive")
    @Test
    void givenSerieBoObject_whenUpdate_thenReturnSerieBoObject() throws ServiceException {
        given(modelToBoConverter.serieModelToBo(serie)).willReturn(serieBO);
        given(boToModelConverter.serieBoToModel(serieBO)).willReturn(serie);
        given(serieRepository.findById(1L)).willReturn(Optional.of(serie));
        given(serieRepository.save(boToModelConverter.serieBoToModel(serieBO))).willReturn(serie);

        SerieBO savedserieBO = serieBO;
        savedserieBO.setTitle("update");
        savedserieBO.setDebut(2000);
        SerieBO updateserieBO = serieService.update(savedserieBO);

        assertEquals(updateserieBO, savedserieBO);
    }

    @DisplayName("JUnit test for update an serie - negative")
    @Test
    void givenSerieBoObject_whenupdate_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(serieRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serieService.update(serieBO));
    }

    @DisplayName("JUnit test for update an serie - negative")
    @Test()
    void givenSerieBoObject_whenUpdate_thenThrowNestedRuntimeException() throws ServiceException {
        given(modelToBoConverter.serieModelToBo(serie)).willReturn(serieBO);
        given(boToModelConverter.serieBoToModel(serieBO)).willReturn(serie);
        given(serieRepository.findById(1L)).willReturn(Optional.of(serie));
        when(serieRepository.save(boToModelConverter.serieBoToModel(serieBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> serieService.update(serieBO));
    }

    @DisplayName("JUnit test for find an serie by his id - positive")
    @Test
    void givenserieId_whenFindById_thenReturnSerieBoObject() throws ServiceException {
        given(modelToBoConverter.serieModelToBo(serie)).willReturn(serieBO);
        given(serieRepository.findById(1L)).willReturn(Optional.of(serie));

        SerieBO savedserieBO = serieService.findById(serieBO.getId());

        assertEquals(serieBO, savedserieBO);
    }

    @DisplayName("JUnit test for find an serie by his id - negative")
    @Test
    void givenserieId_whenFindById_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(serieRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serieService.findById(serieBO.getId()));
    }

    @DisplayName("JUnit test for find an serie by his id - negative")
    @Test
    void givenserieId_whenFindById_thenThrowNestedRuntimeException() throws ServiceException {
        when(serieRepository.findById(serieBO.getId())).thenThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> serieService.findById(serieBO.getId()));
    }

    @DisplayName("JUnit test for get all series - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnSerieBoObject() throws ServiceException {
        given(modelToBoConverter.serieModelToBo(serie)).willReturn(serieBO);
        Page<Serie> page = new PageImpl<>(List.of(serie), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(serieRepository.findAll(pageable)).willReturn(page);

        Page<SerieBO> savedSeriesBO = serieService.findAll(pageable);

        assertEquals(1, savedSeriesBO.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all series - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowEmptyException() throws ServiceException {
        Page<Serie> page = new PageImpl<>(List.of(), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(serieRepository.findAll(pageable)).willReturn(page);

        assertThrows(EmptyException.class, () -> serieService.findAll(pageable));
    }

    @DisplayName("JUnit test for get all series - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowNestedRuntimeException() throws ServiceException {
        given(serieRepository.findAll(pageable)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> serieService.findAll(pageable));
    }

    @DisplayName("JUnit test for delete an serie by his id - positive")
    @Test
    void givenserieId_whenDeleteById_thenReturnSerieBoObject() throws ServiceException {
        given(serieRepository.existsById(1L)).willReturn(true);
        willDoNothing().given(serieRepository).deleteById(serieBO.getId());

        serieService.deleteById(serieBO.getId());

        verify(serieRepository, times(1)).deleteById(serieBO.getId());
    }

    @DisplayName("JUnit test for delete an serie by his id - negative")
    @Test
    void givenserieId_whenDeleteById_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(serieRepository.existsById(1L)).willReturn(false);

        assertThrows(EntityNotFoundException.class, () -> serieService.deleteById(serieBO.getId()));
    }

    @DisplayName("JUnit test for delete an serie by his id - negative")
    @Test
    void givenserieId_whenDeleteById_thenThrowNestedRuntimeException() throws ServiceException {
        given(serieRepository.existsById(1L)).willReturn(true);
        willThrow(InvalidDataAccessApiUsageException.class).given(serieRepository).deleteById(serieBO.getId());

        assertThrows(ServiceException.class, () -> serieService.deleteById(serieBO.getId()));
    }

    @DisplayName("JUnit test for save an serie - positive")
    @Test
    void givenSerieBoObject_whenSaveCriteria_thenReturnSerieBoObject() throws ServiceException {
        given(modelToBoConverter.serieModelToBo(serie)).willReturn(serieBO);
        given(boToModelConverter.serieBoToModel(serieBO)).willReturn(serie);

        given(serieService.existsActorJPA(1L, "8080")).willReturn(false);
        given(serieService.existsDirectorJPA(1L, "8080")).willReturn(false);
        given(serieService.existsProducerJPA(1L, "8080")).willReturn(false);
        given(serieRepositoryCriteria.saveSerie(boToModelConverter.serieBoToModel(serieBO))).willReturn(serie);

        SerieBO savedserieBO = serieService.saveCriteria(serieBO, "8080");

        assertEquals(serieBO, savedserieBO);
    }

    @DisplayName("JUnit test for save an serie - negative")
    @Test
    void givenSerieBoObject_whenSaveCriteria_thenThrowDuplicatedException() throws ServiceException {
        given(serieService.existsActorJPA(1L, "8080")).willReturn(true);
        given(serieService.existsDirectorJPA(1L, "8080")).willReturn(false);
        given(serieService.existsProducerJPA(1L, "8080")).willReturn(false);
        assertThrows(EntityNotFoundException.class, () -> serieService.saveCriteria(serieBO, "8080"));
    }

    @DisplayName("JUnit test for save an serie - negative")
    @Test()
    void givenSerieBoObject_whenSaveCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(serieService.existsActorJPA(1L, "8080")).willReturn(false);
        given(serieService.existsDirectorJPA(1L, "8080")).willReturn(false);
        given(serieService.existsProducerJPA(1L, "8080")).willReturn(false);

        when(serieRepositoryCriteria.saveSerie(boToModelConverter.serieBoToModel(serieBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> serieService.saveCriteria(serieBO, "8080"));
    }

    @DisplayName("JUnit test for update an serie - positive")
    @Test
    void givenSerieBoObject_whenUpdateCriteria_thenReturnSerieBoObject() throws ServiceException {
        given(modelToBoConverter.serieModelToBo(serie)).willReturn(serieBO);
        given(boToModelConverter.serieBoToModel(serieBO)).willReturn(serie);
        given(serieRepositoryCriteria.findSerieById(serie.getId())).willReturn(Optional.of(serie));
        given(serieRepositoryCriteria.updateSerie(boToModelConverter.serieBoToModel(serieBO))).willReturn(serie);

        SerieBO savedserieBO = serieBO;
        savedserieBO.setTitle("update");
        savedserieBO.setDebut(2000);
        SerieBO updateserieBO = serieService.updateCriteria(savedserieBO);

        assertEquals(updateserieBO, savedserieBO);
    }

    @DisplayName("JUnit test for update an serie - negative")
    @Test
    void givenSerieBoObject_whenupdateCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(serieRepositoryCriteria.findSerieById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serieService.updateCriteria(serieBO));
    }

    @DisplayName("JUnit test for update an serie - negative")
    @Test()
    void givenSerieBoObject_whenUpdateCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(modelToBoConverter.serieModelToBo(serie)).willReturn(serieBO);
        given(boToModelConverter.serieBoToModel(serieBO)).willReturn(serie);
        given(serieRepositoryCriteria.findSerieById(1L)).willReturn(Optional.of(serie));
        when(serieRepositoryCriteria.updateSerie(boToModelConverter.serieBoToModel(serieBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> serieService.updateCriteria(serieBO));
    }

    @DisplayName("JUnit test for find an serie by his id - positive")
    @Test
    void givenserieId_whenFindByIdCriteria_thenReturnSerieBoObject() throws ServiceException {
        given(modelToBoConverter.serieModelToBo(serie)).willReturn(serieBO);
        given(serieRepositoryCriteria.findSerieById(1L)).willReturn(Optional.of(serie));

        SerieBO savedserieBO = serieService.findByIdCriteria(serieBO.getId());

        assertEquals(serieBO, savedserieBO);
    }

    @DisplayName("JUnit test for find an serie by his id - negative")
    @Test
    void givenserieId_whenFindByIdCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(serieRepositoryCriteria.findSerieById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serieService.findByIdCriteria(serieBO.getId()));
    }

    @DisplayName("JUnit test for find an serie by his id - negative")
    @Test
    void givenserieId_whenFindByIdCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        when(serieRepositoryCriteria.findSerieById(serieBO.getId())).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> serieService.findByIdCriteria(serieBO.getId()));
    }

    @DisplayName("JUnit test for get all series - positive")
    @Test
    void givenNothing_whenFindAllCriteria_thenReturnSerieBoObject() throws ServiceException {
        given(modelToBoConverter.serieModelToBo(serie)).willReturn(serieBO);
        Page<Serie> page = new PageImpl<>(List.of(serie), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(serieRepositoryCriteria.findAllSerie(pageable)).willReturn(page);

        Page<SerieBO> savedSeriesBO = serieService.findAllCriteria(pageable);

        assertEquals(1, savedSeriesBO.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all series - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowEmptyException() throws ServiceException {
        Page<Serie> page = new PageImpl<>(List.of(), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(serieRepositoryCriteria.findAllSerie(pageable)).willReturn(page);

        assertThrows(EmptyException.class, () -> serieService.findAllCriteria(pageable));
    }

    @DisplayName("JUnit test for get all series - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(serieRepositoryCriteria.findAllSerie(pageable)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> serieService.findAllCriteria(pageable));
    }

    @DisplayName("JUnit test for get all series filtered - positive")
    @Test
    void givenPageableAndAttributesList_whenFindAllCriteriaFilter_thenReturnSeriesBoList() throws ServiceException {
        given(modelToBoConverter.serieModelToBo(serie)).willReturn(serieBO);
        Page<Serie> page = new PageImpl<>(List.of(serie), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(serieRepositoryCriteria.findAllFilter(pageable, List.of(), List.of(2020), List.of(), List.of(),
                List.of())).willReturn(page);

        Page<SerieBO> savedSerieBO = serieService.findAllCriteriaFilter(pageable, List.of(), List.of(2020), List.of(),
                List.of(), List.of());

        assertEquals(1, savedSerieBO.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all series filtered - negative")
    @Test
    void givenNothing_whenFindAllCriteriaFilter_thenThrowEmptyException() throws ServiceException {
        Page<Serie> page = new PageImpl<>(List.of(), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(serieRepositoryCriteria.findAllFilter(pageable, List.of(), List.of(2020), List.of(), List.of(),
                List.of())).willReturn(page);

        assertThrows(EmptyException.class,
                () -> serieService.findAllCriteriaFilter(pageable, List.of(), List.of(2020), List.of(), List.of(),
                        List.of()));
    }

    @DisplayName("JUnit test for get all series filtered - negative")
    @Test
    void givenNothing_whenFindAllCriteriaFilter_thenThrowNestedRuntimeException() throws ServiceException {
        given(serieRepositoryCriteria.findAllFilter(pageable, List.of(), List.of(2020), List.of(), List.of(),
                List.of())).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class,
                () -> serieService.findAllCriteriaFilter(pageable, List.of(), List.of(2020), List.of(), List.of(),
                        List.of()));
    }

    @DisplayName("JUnit test for delete an serie by his id - positive")
    @Test
    void givenserieId_whenDeleteByIdCriteria_thenReturnSerieBoObject() throws ServiceException {
        given(serieRepositoryCriteria.findSerieById(1L)).willReturn(Optional.of(serie));
        willDoNothing().given(serieRepositoryCriteria).deleteSerieById(serie);

        serieService.deleteByIdCriteria(serieBO.getId());

        verify(serieRepositoryCriteria, times(1)).deleteSerieById(serie);
    }

    @DisplayName("JUnit test for delete an serie by his id - negative")
    @Test
    void givenserieId_whenDeleteByIdCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(serieRepositoryCriteria.findSerieById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serieService.deleteByIdCriteria(serieBO.getId()));
    }

    @DisplayName("JUnit test for delete an serie by his id - negative")
    @Test
    void givenserieId_whenDeleteByIdCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(serieRepositoryCriteria.findSerieById(1L)).willReturn(Optional.of(serie));
        willThrow(InvalidDataAccessApiUsageException.class).given(serieRepositoryCriteria).deleteSerieById(serie);

        assertThrows(ServiceException.class, () -> serieService.deleteByIdCriteria(serieBO.getId()));
    }
}

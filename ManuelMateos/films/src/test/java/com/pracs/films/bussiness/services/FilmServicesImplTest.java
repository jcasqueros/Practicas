package com.pracs.films.bussiness.services;

import com.pracs.films.bussiness.bo.ActorBO;
import com.pracs.films.bussiness.bo.DirectorBO;
import com.pracs.films.bussiness.bo.FilmBO;
import com.pracs.films.bussiness.bo.ProducerBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.impl.FilmServiceImpl;
import com.pracs.films.exceptions.DuplicatedIdException;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.models.Film;
import com.pracs.films.persistence.models.Producer;
import com.pracs.films.persistence.repositories.criteria.impl.FilmRepositoryImpl;
import com.pracs.films.persistence.repositories.jpa.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

/**
 * Test for {@link FilmServiceImpl}
 *
 * @author Manuel Mateos de Torres
 */
@ExtendWith(MockitoExtension.class)
class FilmServicesImplTest {

    @Mock
    private ModelToBoConverter modelToBoConverter;

    @Mock
    private BoToModelConverter boToModelConverter;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private FilmRepositoryImpl filmRepositoryCriteria;

    @InjectMocks
    private FilmServiceImpl filmService;

    private Pageable pageable;

    private Actor actor;

    private ActorBO actorBO;

    private Director director;

    private DirectorBO directorBO;

    private Producer producer;

    private ProducerBO producerBO;

    private Film film;

    private FilmBO filmBO;

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

        film = new Film();
        film.setId(1L);
        film.setTitle("prueba");
        film.setDebut(2020);
        film.setActors(actors);
        film.setProducer(producer);
        film.setDirector(director);

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

        filmBO = new FilmBO();
        filmBO.setId(1L);
        filmBO.setTitle("prueba");
        filmBO.setDebut(2020);
        filmBO.setActors(actorsBO);
        filmBO.setProducer(producerBO);
        filmBO.setDirector(directorBO);

        pageable = PageRequest.of(0, 5, Sort.by("title").ascending());
    }

    @DisplayName("JUnit test for save a film - positive")
    @Test
    void givenFilmBoObject_whenSave_thenReturnFilmBoObject() throws ServiceException {
        given(modelToBoConverter.filmModelToBo(film)).willReturn(filmBO);
        given(boToModelConverter.filmBoToModel(filmBO)).willReturn(film);
        given(filmRepository.existsById(1L)).willReturn(false);
        given(filmRepository.save(boToModelConverter.filmBoToModel(filmBO))).willReturn(film);

        FilmBO savedfilmBO = filmService.save(filmBO);

        assertEquals(filmBO, savedfilmBO);
    }

    @DisplayName("JUnit test for save a film - negative")
    @Test
    void givenFilmBoObject_whenSave_thenThrowDuplicatedException() throws ServiceException {
        given(filmRepository.existsById(1L)).willReturn(true);

        assertThrows(DuplicatedIdException.class, () -> filmService.save(filmBO));
    }

    @DisplayName("JUnit test for save a film - negative")
    @Test()
    void givenFilmBoObject_whenSave_thenThrowNestedRuntimeException() throws ServiceException {
        given(filmRepository.existsById(1L)).willReturn(false);
        when(filmRepository.save(boToModelConverter.filmBoToModel(filmBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.save(filmBO));
    }

    @DisplayName("JUnit test for update a film - positive")
    @Test
    void givenFilmBoObject_whenUpdate_thenReturnFilmBoObject() throws ServiceException {
        given(modelToBoConverter.filmModelToBo(film)).willReturn(filmBO);
        given(boToModelConverter.filmBoToModel(filmBO)).willReturn(film);
        given(filmRepository.findById(1L)).willReturn(Optional.of(film));
        given(filmRepository.save(boToModelConverter.filmBoToModel(filmBO))).willReturn(film);

        FilmBO savedfilmBO = filmBO;
        savedfilmBO.setTitle("update");
        savedfilmBO.setDebut(2000);
        FilmBO updatefilmBO = filmService.update(savedfilmBO);

        assertEquals(updatefilmBO, savedfilmBO);
    }

    @DisplayName("JUnit test for update a film - negative")
    @Test
    void givenFilmBoObject_whenupdate_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(filmRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> filmService.update(filmBO));
    }

    @DisplayName("JUnit test for update a film - negative")
    @Test()
    void givenFilmBoObject_whenUpdate_thenThrowNestedRuntimeException() throws ServiceException {
        given(modelToBoConverter.filmModelToBo(film)).willReturn(filmBO);
        given(boToModelConverter.filmBoToModel(filmBO)).willReturn(film);
        given(filmRepository.findById(1L)).willReturn(Optional.of(film));
        when(filmRepository.save(boToModelConverter.filmBoToModel(filmBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.update(filmBO));
    }

    @DisplayName("JUnit test for find a film by his id - positive")
    @Test
    void givenfilmId_whenFindById_thenReturnFilmBoObject() throws ServiceException {
        given(modelToBoConverter.filmModelToBo(film)).willReturn(filmBO);
        given(filmRepository.findById(1L)).willReturn(Optional.of(film));

        FilmBO savedfilmBO = filmService.findById(filmBO.getId());

        assertEquals(filmBO, savedfilmBO);
    }

    @DisplayName("JUnit test for find a film by his id - negative")
    @Test
    void givenfilmId_whenFindById_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(filmRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> filmService.findById(filmBO.getId()));
    }

    @DisplayName("JUnit test for find a film by his id - negative")
    @Test
    void givenfilmId_whenFindById_thenThrowNestedRuntimeException() throws ServiceException {
        when(filmRepository.findById(filmBO.getId())).thenThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.findById(filmBO.getId()));
    }

    @DisplayName("JUnit test for get all films - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnFilmBoObject() throws ServiceException {
        given(modelToBoConverter.filmModelToBo(film)).willReturn(filmBO);
        Page<Film> page = new PageImpl<>(List.of(film), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(filmRepository.findAll(pageable)).willReturn(page);

        Page<FilmBO> savedfilmsBO = filmService.findAll(pageable);

        assertEquals(1, savedfilmsBO.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all films - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowEmptyException() throws ServiceException {
        Page<Film> page = new PageImpl<>(List.of(), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(filmRepository.findAll(pageable)).willReturn(page);

        assertThrows(EmptyException.class, () -> filmService.findAll(pageable));
    }

    @DisplayName("JUnit test for get all films - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowNestedRuntimeException() throws ServiceException {
        given(filmRepository.findAll(pageable)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.findAll(pageable));
    }

    @DisplayName("JUnit test for delete a film by his id - positive")
    @Test
    void givenfilmId_whenDeleteById_thenReturnFilmBoObject() throws ServiceException {
        given(filmRepository.existsById(1L)).willReturn(true);
        willDoNothing().given(filmRepository).deleteById(filmBO.getId());

        filmService.deleteById(filmBO.getId());

        verify(filmRepository, times(1)).deleteById(filmBO.getId());
    }

    @DisplayName("JUnit test for delete a film by his id - negative")
    @Test
    void givenfilmId_whenDeleteById_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(filmRepository.existsById(1L)).willReturn(false);

        assertThrows(EntityNotFoundException.class, () -> filmService.deleteById(filmBO.getId()));
    }

    @DisplayName("JUnit test for delete a film by his id - negative")
    @Test
    void givenfilmId_whenDeleteById_thenThrowNestedRuntimeException() throws ServiceException {
        given(filmRepository.existsById(1L)).willReturn(true);
        willThrow(InvalidDataAccessApiUsageException.class).given(filmRepository).deleteById(filmBO.getId());

        assertThrows(ServiceException.class, () -> filmService.deleteById(filmBO.getId()));
    }

    @DisplayName("JUnit test for save a film - positive")
    @Test
    void givenFilmBoObject_whenSaveCriteria_thenReturnFilmBoObject() throws ServiceException {
        given(modelToBoConverter.filmModelToBo(film)).willReturn(filmBO);
        given(boToModelConverter.filmBoToModel(filmBO)).willReturn(film);
        given(filmRepositoryCriteria.findFilmById(1L)).willReturn(Optional.empty());
        given(filmRepositoryCriteria.saveFilm(boToModelConverter.filmBoToModel(filmBO))).willReturn(film);

        FilmBO savedfilmBO = filmService.saveCriteria(filmBO);

        assertEquals(filmBO, savedfilmBO);
    }

    @DisplayName("JUnit test for save a film - negative")
    @Test
    void givenFilmBoObject_whenSaveCriteria_thenThrowDuplicatedException() throws ServiceException {
        given(filmRepositoryCriteria.findFilmById(1L)).willReturn(Optional.of(film));

        assertThrows(DuplicatedIdException.class, () -> filmService.saveCriteria(filmBO));
    }

    @DisplayName("JUnit test for save a film - negative")
    @Test()
    void givenFilmBoObject_whenSaveCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(filmRepositoryCriteria.findFilmById(1L)).willReturn(Optional.empty());
        when(filmRepositoryCriteria.saveFilm(boToModelConverter.filmBoToModel(filmBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.saveCriteria(filmBO));
    }

    @DisplayName("JUnit test for update a film - positive")
    @Test
    void givenFilmBoObject_whenUpdateCriteria_thenReturnFilmBoObject() throws ServiceException {
        given(modelToBoConverter.filmModelToBo(film)).willReturn(filmBO);
        given(boToModelConverter.filmBoToModel(filmBO)).willReturn(film);
        given(filmRepositoryCriteria.findFilmById(film.getId())).willReturn(Optional.of(film));
        given(filmRepositoryCriteria.updateFilm(boToModelConverter.filmBoToModel(filmBO))).willReturn(film);

        FilmBO savedfilmBO = filmBO;
        savedfilmBO.setTitle("update");
        savedfilmBO.setDebut(2000);
        FilmBO updatefilmBO = filmService.updateCriteria(savedfilmBO);

        assertEquals(updatefilmBO, savedfilmBO);
    }

    @DisplayName("JUnit test for update a film - negative")
    @Test
    void givenFilmBoObject_whenupdateCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(filmRepositoryCriteria.findFilmById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> filmService.updateCriteria(filmBO));
    }

    @DisplayName("JUnit test for update a film - negative")
    @Test()
    void givenFilmBoObject_whenUpdateCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(modelToBoConverter.filmModelToBo(film)).willReturn(filmBO);
        given(boToModelConverter.filmBoToModel(filmBO)).willReturn(film);
        given(filmRepositoryCriteria.findFilmById(1L)).willReturn(Optional.of(film));
        when(filmRepositoryCriteria.updateFilm(boToModelConverter.filmBoToModel(filmBO))).thenThrow(
                InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.updateCriteria(filmBO));
    }

    @DisplayName("JUnit test for find a film by his id - positive")
    @Test
    void givenfilmId_whenFindByIdCriteria_thenReturnFilmBoObject() throws ServiceException {
        given(modelToBoConverter.filmModelToBo(film)).willReturn(filmBO);
        given(filmRepositoryCriteria.findFilmById(1L)).willReturn(Optional.of(film));

        FilmBO savedfilmBO = filmService.findByIdCriteria(filmBO.getId());

        assertEquals(filmBO, savedfilmBO);
    }

    @DisplayName("JUnit test for find a film by his id - negative")
    @Test
    void givenfilmId_whenFindByIdCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(filmRepositoryCriteria.findFilmById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> filmService.findByIdCriteria(filmBO.getId()));
    }

    @DisplayName("JUnit test for find a film by his id - negative")
    @Test
    void givenfilmId_whenFindByIdCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        when(filmRepositoryCriteria.findFilmById(filmBO.getId())).thenThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.findByIdCriteria(filmBO.getId()));
    }

    @DisplayName("JUnit test for get all films - positive")
    @Test
    void givenNothing_whenFindAllCriteria_thenReturnFilmBoObject() throws ServiceException {
        given(modelToBoConverter.filmModelToBo(film)).willReturn(filmBO);
        Page<Film> page = new PageImpl<>(List.of(film), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(filmRepositoryCriteria.findAllFilm(pageable)).willReturn(page);

        Page<FilmBO> savedFilmsBO = filmService.findAllCriteria(pageable);

        assertEquals(1, savedFilmsBO.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all films - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowEmptyException() throws ServiceException {
        Page<Film> page = new PageImpl<>(List.of(), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(filmRepositoryCriteria.findAllFilm(pageable)).willReturn(page);

        assertThrows(EmptyException.class, () -> filmService.findAllCriteria(pageable));
    }

    @DisplayName("JUnit test for get all films - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(filmRepositoryCriteria.findAllFilm(pageable)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.findAllCriteria(pageable));
    }

    @DisplayName("JUnit test for delete a film by his id - positive")
    @Test
    void givenfilmId_whenDeleteByIdCriteria_thenReturnFilmBoObject() throws ServiceException {
        given(filmRepositoryCriteria.findFilmById(1L)).willReturn(Optional.of(film));
        willDoNothing().given(filmRepositoryCriteria).deleteFilmById(film);

        filmService.deleteByIdCriteria(filmBO.getId());

        verify(filmRepositoryCriteria, times(1)).deleteFilmById(film);
    }

    @DisplayName("JUnit test for delete a film by his id - negative")
    @Test
    void givenfilmId_whenDeleteByIdCriteria_thenThrowEntityNotFoundExcepition() throws ServiceException {
        given(filmRepositoryCriteria.findFilmById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> filmService.deleteByIdCriteria(filmBO.getId()));
    }

    @DisplayName("JUnit test for delete a film by his id - negative")
    @Test
    void givenfilmId_whenDeleteByIdCriteria_thenThrowNestedRuntimeException() throws ServiceException {
        given(filmRepositoryCriteria.findFilmById(1L)).willReturn(Optional.of(film));
        willThrow(InvalidDataAccessApiUsageException.class).given(filmRepositoryCriteria).deleteFilmById(film);

        assertThrows(ServiceException.class, () -> filmService.deleteByIdCriteria(filmBO.getId()));
    }
}

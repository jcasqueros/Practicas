package com.viewnext.films.businesslayer.service;

import com.viewnext.films.businesslayer.bo.ActorBO;
import com.viewnext.films.businesslayer.bo.DirectorBO;
import com.viewnext.films.businesslayer.bo.FilmBO;
import com.viewnext.films.businesslayer.bo.ProducerBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.impl.FilmServiceImpl;
import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.entity.Film;
import com.viewnext.films.persistencelayer.entity.Producer;
import com.viewnext.films.persistencelayer.repository.criteria.FilmCriteriaRepository;
import com.viewnext.films.persistencelayer.repository.jpa.FilmJPARepository;
import com.viewnext.films.util.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FilmServiceImplTest {

    @Mock
    private FilmCriteriaRepository filmCriteriaRepository;

    @Mock
    private FilmJPARepository filmJPARepository;

    @Mock
    private Converter converter;

    @InjectMocks
    private FilmServiceImpl filmService;

    private Film film;
    private FilmBO filmBO;

    @BeforeEach
    void setup() {
        Actor actor = new Actor();
        actor.setId(1L);
        actor.setAge(18);
        actor.setName("Jhon");
        actor.setNationality("spain");

        Director director = new Director();
        director.setId(2L);
        director.setAge(18);
        director.setName("James");
        director.setNationality("france");

        Producer producer = new Producer();
        producer.setId(1L);
        producer.setName("Paramount");
        producer.setFoundationYear(2003);

        film = new Film();
        film.setId(1L);
        film.setTitle("Friends");
        film.setReleaseYear(2004);
        film.setDirector(director);
        film.setProducer(producer);
        List<Actor> actors = new ArrayList<>();
        actors.add(actor);
        film.setActors(actors);

        ActorBO actorBO = new ActorBO();
        actorBO.setId(1L);
        actorBO.setAge(18);
        actorBO.setName("Jhon");
        actorBO.setNationality("spain");

        DirectorBO directorBO = new DirectorBO();
        directorBO.setId(2L);
        directorBO.setAge(18);
        directorBO.setName("James");
        directorBO.setNationality("france");

        ProducerBO producerBO = new ProducerBO();
        producerBO.setId(1L);
        producerBO.setName("Paramount");
        producerBO.setFoundationYear(2003);

        filmBO = new FilmBO();
        filmBO.setId(1L);
        filmBO.setTitle("Friends");
        filmBO.setReleaseYear(2004);
        filmBO.setDirector(directorBO);
        filmBO.setProducer(producerBO);
        List<ActorBO> actorsBO = new ArrayList<>();
        actorsBO.add(actorBO);
        filmBO.setActors(actorsBO);
    }

    @Test
    @DisplayName("Criteria get by id: correct case")
    void givenId_whenCriteriaGetById_thenReturnFilmBO() throws ServiceException {
        BDDMockito.given(filmCriteriaRepository.getFilmById(1L)).willReturn(Optional.of(film));
        BDDMockito.given(converter.filmEntityToBO(film)).willReturn(filmBO);

        FilmBO result = filmService.criteriaGetById(1L);

        assertThat(result).isNotNull().isEqualTo(filmBO);
    }

    @Test
    @DisplayName("Criteria get by id: not found")
    void givenId_whenCriteriaGetById_thenThrowNotFoundException() throws ServiceException {
        BDDMockito.given(filmCriteriaRepository.getFilmById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> filmService.criteriaGetById(1L));
    }

    @Test
    @DisplayName("Criteria get by id: nested runtime exception")
    void givenId_whenCriteriaGetById_thenThrowNestedRuntimeException() throws ServiceException {
        BDDMockito.given(filmCriteriaRepository.getFilmById(1L)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.criteriaGetById(1L));
    }

    @Test
    @DisplayName("Criteria get all: correct case")
    void givenNothing_whenCriteriaGetAll_thenReturnListWithAllFilmsBO() throws ServiceException {
        List<Film> films = List.of(film);
        BDDMockito.given(filmCriteriaRepository.getAllFilms()).willReturn(films);
        BDDMockito.given(converter.filmEntityToBO(film)).willReturn(filmBO);

        List<FilmBO> result = filmService.criteriaGetAll();

        assertThat(result).isNotNull().hasSize(1).contains(filmBO);
    }

    @Test
    @DisplayName("Criteria get all: no films found")
    void givenNothing_whenCriteriaGetAll_thenThrowNotFoundException() throws ServiceException {
        BDDMockito.given(filmCriteriaRepository.getAllFilms()).willReturn(List.of());

        assertThrows(NotFoundException.class, () -> filmService.criteriaGetAll());
    }

    @Test
    @DisplayName("Criteria get all: nested runtime exception")
    void givenNothing_whenCriteriaGetAll_thenThrowNestedRuntimeException() throws ServiceException {
        BDDMockito.given(filmCriteriaRepository.getAllFilms()).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.criteriaGetAll());
    }

    @Test
    @DisplayName("Criteria delete by id: correct case")
    void givenId_whenCriteriaDeleteById_thenDeleteFilm() throws ServiceException {
        BDDMockito.willDoNothing().given(filmCriteriaRepository).deleteFilm(1L);

        filmService.criteriaDeleteById(1L);

        verify(filmCriteriaRepository, times(1)).deleteFilm(1L);
    }

    @Test
    @DisplayName("Criteria delete by id: nested runtime exception")
    void givenId_whenCriteriaDeleteById_thenThrowNestedRuntimeException() throws ServiceException {
        willThrow(InvalidDataAccessApiUsageException.class).given(filmCriteriaRepository).deleteFilm(1L);
        assertThrows(ServiceException.class, () -> filmService.criteriaDeleteById(1L));
    }

    @Test
    @DisplayName("Criteria update: correct case")
    void givenFilmBO_whenCriteriaUpdate_thenReturnUpdatedFilmBO() throws ServiceException {
        BDDMockito.given(filmCriteriaRepository.getFilmById(1L)).willReturn(Optional.of(film));
        BDDMockito.given(filmCriteriaRepository.updateFilm(film)).willReturn(film);
        BDDMockito.given(converter.filmEntityToBO(film)).willReturn(filmBO);
        BDDMockito.given(converter.filmBOToEntity(filmBO)).willReturn(film);

        FilmBO result = filmService.criteriaUpdate(filmBO);

        assertThat(result).isNotNull().isEqualTo(filmBO);
    }

    @Test
    @DisplayName("Criteria update: nested runtime exception")
    void givenFilmBO_whenCriteriaUpdate_thenThrowNestedRuntimeException() throws ServiceException {
        BDDMockito.given(filmCriteriaRepository.getFilmById(1L)).willReturn(Optional.of(film));
        BDDMockito.given(converter.filmEntityToBO(film)).willReturn(filmBO);
        BDDMockito.given(filmCriteriaRepository.updateFilm(film)).willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.filmBOToEntity(filmBO)).willReturn(film);

        assertThrows(ServiceException.class, () -> filmService.criteriaUpdate(filmBO));
    }

    @Test
    @DisplayName("Criteria create: correct case")
    void givenFilmBO_whenCriteriaCreate_thenReturnCreatedFilmBO() throws ServiceException {
        BDDMockito.given(filmCriteriaRepository.createFilm(film)).willReturn(film);
        BDDMockito.given(converter.filmEntityToBO(film)).willReturn(filmBO);
        BDDMockito.given(converter.filmBOToEntity(filmBO)).willReturn(film);

        FilmBO result = filmService.criteriaCreate(filmBO);

        assertThat(result).isNotNull().isEqualTo(filmBO);
    }

    @Test
    @DisplayName("Criteria create: nested runtime exception")
    void givenFilmBO_whenCriteriaCreate_thenThrowNestedRuntimeException() throws ServiceException {
        BDDMockito.given(filmCriteriaRepository.createFilm(film)).willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.filmBOToEntity(filmBO)).willReturn(film);

        assertThrows(ServiceException.class, () -> filmService.criteriaCreate(filmBO));
    }

    @Test
    @DisplayName("JPA get by id: correct case")
    void givenId_whenJpaGetById_thenReturnFilmBO() throws ServiceException {
        BDDMockito.given(filmJPARepository.findById(1L)).willReturn(Optional.of(film));
        BDDMockito.given(converter.filmEntityToBO(film)).willReturn(filmBO);

        FilmBO result = filmService.jpaGetById(1L);

        assertThat(result).isNotNull().isEqualTo(filmBO);
    }

    @Test
    @DisplayName("JPA get by id: not found")
    void givenId_whenJpaGetById_thenThrowNotFoundException() {
        BDDMockito.given(filmJPARepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> filmService.jpaGetById(1L));
    }

    @Test
    @DisplayName("JPA get all: correct case")
    void givenNothing_whenJpaGetAll_thenReturnListWithAllFilmsBO() throws ServiceException {
        List<Film> films = List.of(film);
        BDDMockito.given(filmJPARepository.findAll()).willReturn(films);
        BDDMockito.given(converter.filmEntityToBO(film)).willReturn(filmBO);

        List<FilmBO> result = filmService.jpaGetAll();

        assertThat(result).isNotNull().hasSize(1).contains(filmBO);
    }

    @Test
    @DisplayName("JPA get all: no films found")
    void givenNothing_whenJpaGetAll_thenThrowNotFoundException() {
        BDDMockito.given(filmJPARepository.findAll()).willReturn(List.of());

        assertThrows(NotFoundException.class, () -> filmService.jpaGetAll());
    }

    @Test
    @DisplayName("JPA delete by id: correct case")
    void givenId_whenJpaDeleteById_thenDeleteFilm() throws ServiceException {
        BDDMockito.willDoNothing().given(filmJPARepository).deleteById(1L);

        filmService.jpaDeleteById(1L);

        verify(filmJPARepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("JPA update: correct case")
    void givenFilmBO_whenJpaUpdate_thenReturnUpdatedFilmBO() throws ServiceException {
        BDDMockito.given(filmJPARepository.existsById(filmBO.getId())).willReturn(true);
        BDDMockito.given(filmJPARepository.save(film)).willReturn(film);
        BDDMockito.given(converter.filmEntityToBO(film)).willReturn(filmBO);
        BDDMockito.given(converter.filmBOToEntity(filmBO)).willReturn(film);

        FilmBO result = filmService.jpaUpdate(filmBO);

        assertThat(result).isNotNull().isEqualTo(filmBO);
    }

    @Test
    @DisplayName("JPA update: not found")
    void givenFilmBO_whenJpaUpdate_thenThrowNotFoundException() throws ServiceException {
        BDDMockito.given(filmJPARepository.existsById(filmBO.getId())).willReturn(false);

        assertThrows(NotFoundException.class, () -> filmService.jpaUpdate(filmBO));
    }

    @Test
    @DisplayName("JPA create: correct case")
    void givenFilmBO_whenJpaCreate_thenReturnCreatedFilmBO() throws ServiceException {
        BDDMockito.given(filmJPARepository.save(film)).willReturn(film);
        BDDMockito.given(converter.filmEntityToBO(film)).willReturn(filmBO);
        BDDMockito.given(converter.filmBOToEntity(filmBO)).willReturn(film);

        FilmBO result = filmService.jpaCreate(filmBO);

        assertThat(result).isNotNull().isEqualTo(filmBO);
    }

    @Test
    @DisplayName("JPA get by id: nested runtime exception")
    void givenId_whenJpaGetById_thenThrowNestedRuntimeException() {
        BDDMockito.given(filmJPARepository.findById(1L)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.jpaGetById(1L));
    }

    @Test
    @DisplayName("JPA get all: nested runtime exception")
    void givenNothing_whenJpaGetAll_thenThrowNestedRuntimeException() {
        BDDMockito.given(filmJPARepository.findAll()).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.jpaGetAll());
    }

    @Test
    @DisplayName("JPA delete by id: nested runtime exception")
    void givenId_whenJpaDeleteById_thenThrowNestedRuntimeException() {
        willThrow(InvalidDataAccessApiUsageException.class).given(filmJPARepository).deleteById(1L);

        assertThrows(ServiceException.class, () -> filmService.jpaDeleteById(1L));
    }

    @Test
    @DisplayName("JPA update: nested runtime exception")
    void givenFilmBO_whenJpaUpdate_thenThrowNestedRuntimeException() {
        BDDMockito.given(filmJPARepository.existsById(filmBO.getId())).willReturn(true);
        BDDMockito.given(filmJPARepository.save(film)).willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.filmBOToEntity(filmBO)).willReturn(film);

        assertThrows(ServiceException.class, () -> filmService.jpaUpdate(filmBO));
    }

    @Test
    @DisplayName("JPA create: nested runtime exception")
    void givenFilmBO_whenJpaCreate_thenThrowNestedRuntimeException() {
        BDDMockito.given(filmJPARepository.save(film)).willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.filmBOToEntity(filmBO)).willReturn(film);
        
        assertThrows(ServiceException.class, () -> filmService.jpaCreate(filmBO));
    }

}


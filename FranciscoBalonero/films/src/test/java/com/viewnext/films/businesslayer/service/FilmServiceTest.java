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
import com.viewnext.films.persistencelayer.repository.jpa.ActorJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.DirectorJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.FilmJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.ProducerJPARepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
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
    @Mock
    private ActorJPARepository actorJPARepository;
    @Mock
    private DirectorJPARepository directorJPARepository;
    @Mock
    private ProducerJPARepository producerJPARepository;
    @InjectMocks
    private FilmServiceImpl filmService;
    private Film film;
    private FilmBO filmBO;
    private DirectorBO directorBO;
    private ActorBO actorBO;
    private ProducerBO producerBO;
    private Producer producer;
    private Actor actor;
    private Director director;

    @BeforeEach
    void setup() {
        actor = new Actor();
        actor.setId(1L);
        actor.setAge(18);
        actor.setName("Jhon");
        actor.setNationality("spain");

        director = new Director();
        director.setId(2L);
        director.setAge(18);
        director.setName("James");
        director.setNationality("france");

        producer = new Producer();
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
        BDDMockito.given(filmCriteriaRepository.getAllFilms(PageRequest.of(0, 10, Sort.by("title").ascending())))
                .willReturn(films);
        BDDMockito.given(converter.filmEntityToBO(film)).willReturn(filmBO);

        List<FilmBO> result = filmService.criteriaGetAll(0, 10, "title", false);

        assertThat(result).isNotNull().hasSize(1).contains(filmBO);
    }

    @Test
    @DisplayName("Criteria get all: no films found")
    void givenNothing_whenCriteriaGetAll_thenThrowNotFoundException() throws ServiceException {
        BDDMockito.given(filmCriteriaRepository.getAllFilms(PageRequest.of(0, 10, Sort.by("title").ascending())))
                .willReturn(List.of());

        assertThrows(NotFoundException.class, () -> filmService.criteriaGetAll(0, 10, "title", false));
    }

    @Test
    @DisplayName("Criteria get all: nested runtime exception")
    void givenNothing_whenCriteriaGetAll_thenThrowNestedRuntimeException() throws ServiceException {
        BDDMockito.given(filmCriteriaRepository.getAllFilms(PageRequest.of(0, 10, Sort.by("title").ascending())))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.criteriaGetAll(0, 10, "title", false));
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
        BDDMockito.given(filmJPARepository.findAll(PageRequest.of(0, 10, Sort.by("title").ascending())))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> filmService.jpaGetAll(0, 10, "title", false));
    }

    @Test
    @DisplayName("JPA get all: correct case")
    void givenNothing_whenJpaGetAll_thenReturnListWithAllFilmsBO() throws ServiceException {
        List<Film> films = List.of(film);
        BDDMockito.given(filmJPARepository.findAll(PageRequest.of(0, 10, Sort.by("title").ascending())))
                .willReturn(new PageImpl<>(films));
        BDDMockito.given(converter.filmEntityToBO(film)).willReturn(filmBO);

        List<FilmBO> result = filmService.jpaGetAll(0, 10, "title", false);

        assertThat(result).isNotNull().hasSize(1).contains(filmBO);
    }

    @Test
    @DisplayName("JPA get all: no films found")
    void givenNothing_whenJpaGetAll_thenThrowNotFoundException() {
        BDDMockito.given(filmJPARepository.findAll(PageRequest.of(0, 10, Sort.by("title").ascending())))
                .willReturn(new PageImpl<>(List.of()));

        assertThrows(NotFoundException.class, () -> filmService.jpaGetAll(0, 10, "title", false));
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

    @Test
    @DisplayName("Filter films: correct case")
    void givenFilters_whenFilterFilms_thenReturnListWithFilteredFilmsBO() throws ServiceException {

        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "title";
        boolean sortOrder = true;

        List<Film> films = List.of(film);
        BDDMockito.given(actorJPARepository.findByName(anyString())).willReturn(List.of(actor));
        BDDMockito.given(directorJPARepository.findByName(anyString())).willReturn(List.of(director));
        BDDMockito.given(producerJPARepository.findByName(anyString())).willReturn(List.of(producer));

        BDDMockito.given(
                        filmCriteriaRepository.filterFilms(List.of(filmBO.getTitle()), List.of(filmBO.getReleaseYear()),
                                List.of(director), List.of(producer), List.of(actor),
                                PageRequest.of(pageNumber, pageSize).withSort((Sort.by(sortBy).descending()))))
                .willReturn(films);
        BDDMockito.given(converter.filmEntityToBO(film)).willReturn(filmBO);

        List<FilmBO> result = filmService.filterFilms(List.of(filmBO.getTitle()), List.of(filmBO.getReleaseYear()),
                List.of(director.getName()), List.of(producer.getName()), List.of(actor.getName()), pageNumber,
                pageSize, sortBy, sortOrder);

        assertThat(result).isNotNull().hasSize(1).contains(filmBO);
    }

    @Test
    @DisplayName("Filter films: no films found")
    void givenFilters_whenFilterFilms_thenThrowNotFoundException() throws ServiceException {

        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "title";
        boolean sortOrder = true;

        BDDMockito.given(actorJPARepository.findByName(anyString())).willReturn(List.of(actor));
        BDDMockito.given(directorJPARepository.findByName(anyString())).willReturn(List.of(director));
        BDDMockito.given(producerJPARepository.findByName(anyString())).willReturn(List.of(producer));

        BDDMockito.given(
                        filmCriteriaRepository.filterFilms(List.of(filmBO.getTitle()), List.of(filmBO.getReleaseYear()),
                                List.of(director), List.of(producer), List.of(actor),
                                PageRequest.of(pageNumber, pageSize).withSort((Sort.by(sortBy).descending()))))
                .willReturn(List.of());

        assertThrows(NotFoundException.class,
                () -> filmService.filterFilms(List.of(filmBO.getTitle()), List.of(filmBO.getReleaseYear()),
                        List.of(director.getName()), List.of(producer.getName()), List.of(actor.getName()), pageNumber,
                        pageSize, sortBy, sortOrder));
    }

    @Test
    @DisplayName("Filter films: nested runtime exception")
    void givenFilters_whenFilterFilms_thenThrowNestedRuntimeException() throws ServiceException {

        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "title";
        boolean sortOrder = true;

        BDDMockito.given(actorJPARepository.findByName(anyString())).willReturn(List.of(actor));
        BDDMockito.given(directorJPARepository.findByName(anyString())).willReturn(List.of(director));
        BDDMockito.given(producerJPARepository.findByName(anyString())).willReturn(List.of(producer));

        BDDMockito.given(
                        filmCriteriaRepository.filterFilms(List.of(filmBO.getTitle()), List.of(filmBO.getReleaseYear()),
                                List.of(director), List.of(producer), List.of(actor),
                                PageRequest.of(pageNumber, pageSize).withSort((Sort.by(sortBy).descending()))))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class,
                () -> filmService.filterFilms(List.of(filmBO.getTitle()), List.of(filmBO.getReleaseYear()),
                        List.of(director.getName()), List.of(producer.getName()), List.of(actor.getName()), pageNumber,
                        pageSize, sortBy, sortOrder));
    }

}


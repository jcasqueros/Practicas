package com.viewnext.films.businesslayer.service;

import com.viewnext.films.businesslayer.bo.ActorBO;
import com.viewnext.films.businesslayer.bo.DirectorBO;
import com.viewnext.films.businesslayer.bo.ProducerBO;
import com.viewnext.films.businesslayer.bo.SerieBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.impl.SerieServiceImpl;
import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.entity.Producer;
import com.viewnext.films.persistencelayer.entity.Serie;
import com.viewnext.films.persistencelayer.repository.criteria.SerieCriteriaRepository;
import com.viewnext.films.persistencelayer.repository.jpa.ActorJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.DirectorJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.ProducerJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.SerieJPARepository;
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
class SerieServiceImplTest {

    @Mock
    private SerieCriteriaRepository serieCriteriaRepository;
    @Mock
    private SerieJPARepository serieJPARepository;
    @Mock
    private WebClientService webClientService;
    @Mock
    private Converter converter;
    @Mock
    private ActorJPARepository actorJPARepository;
    @Mock
    private DirectorJPARepository directorJPARepository;
    @Mock
    private ProducerJPARepository producerJPARepository;
    @InjectMocks
    private SerieServiceImpl serieService;

    private Serie serie;
    private SerieBO serieBO;
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
        director.setId(1L);
        director.setAge(18);
        director.setName("James");
        director.setNationality("france");

        producer = new Producer();
        producer.setId(1L);
        producer.setName("Paramount");
        producer.setFoundationYear(2003);

        serie = new Serie();
        serie.setId(1L);
        serie.setTitle("Friends");
        serie.setReleaseYear(2004);
        serie.setDirector(director);
        serie.setProducer(producer);
        List<Actor> actors = new ArrayList<>();
        actors.add(actor);
        serie.setActors(actors);

        ActorBO actorBO = new ActorBO();
        actorBO.setId(1L);
        actorBO.setAge(18);
        actorBO.setName("Jhon");
        actorBO.setNationality("spain");

        DirectorBO directorBO = new DirectorBO();
        directorBO.setId(1L);
        directorBO.setAge(18);
        directorBO.setName("James");
        directorBO.setNationality("france");

        ProducerBO producerBO = new ProducerBO();
        producerBO.setId(1L);
        producerBO.setName("Paramount");
        producerBO.setFoundationYear(2003);

        serieBO = new SerieBO();
        serieBO.setId(1L);
        serieBO.setTitle("Friends");
        serieBO.setReleaseYear(2004);
        serieBO.setDirector(directorBO);
        serieBO.setProducer(producerBO);
        List<ActorBO> actorsBO = new ArrayList<>();
        actorsBO.add(actorBO);
        serieBO.setActors(actorsBO);
    }

    @Test
    @DisplayName("Criteria get by id: correct case")
    void givenId_whenCriteriaGetById_thenReturnSerieBO() throws ServiceException {
        BDDMockito.given(serieCriteriaRepository.getSerieById(1L)).willReturn(Optional.of(serie));
        BDDMockito.given(converter.serieEntityToBO(serie)).willReturn(serieBO);

        SerieBO result = serieService.criteriaGetById(1L);

        assertThat(result).isNotNull().isEqualTo(serieBO);
    }

    @Test
    @DisplayName("Criteria get by id: not found")
    void givenId_whenCriteriaGetById_thenThrowNotFoundException() {
        BDDMockito.given(serieCriteriaRepository.getSerieById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> serieService.criteriaGetById(1L));
    }

    @Test
    @DisplayName("Criteria get by id: nested runtime exception")
    void givenId_whenCriteriaGetById_thenThrowNestedRuntimeException() {
        BDDMockito.given(serieCriteriaRepository.getSerieById(1L)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> serieService.criteriaGetById(1L));
    }

    @Test
    @DisplayName("Criteria get all: correct case")
    void givenNothing_whenCriteriaGetAll_thenReturnListWithAllSeriesBO() throws ServiceException {
        List<Serie> series = List.of(serie);
        BDDMockito.given(serieCriteriaRepository.getAllSeries(PageRequest.of(0, 10, Sort.by("title").ascending())))
                .willReturn(series);
        BDDMockito.given(converter.serieEntityToBO(serie)).willReturn(serieBO);

        List<SerieBO> result = serieService.criteriaGetAll(0, 10, "title", false);

        assertThat(result).isNotNull().hasSize(1).contains(serieBO);
    }

    @Test
    @DisplayName("Criteria get all: no series found")
    void givenNothing_whenCriteriaGetAll_thenThrowNotFoundException() {
        BDDMockito.given(serieCriteriaRepository.getAllSeries(PageRequest.of(0, 10, Sort.by("title").ascending())))
                .willReturn(List.of());

        assertThrows(NotFoundException.class, () -> serieService.criteriaGetAll(0, 10, "title", false));
    }

    @Test
    @DisplayName("Criteria get all: nested runtime exception")
    void givenNothing_whenCriteriaGetAll_thenThrowNestedRuntimeException() {
        BDDMockito.given(serieCriteriaRepository.getAllSeries(PageRequest.of(0, 10, Sort.by("title").ascending())))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> serieService.criteriaGetAll(0, 10, "title", false));
    }

    @Test
    @DisplayName("Criteria delete by id: correct case")
    void givenId_whenCriteriaDeleteById_thenDeleteSerie() throws ServiceException {
        BDDMockito.willDoNothing().given(serieCriteriaRepository).deleteSerie(1L);

        serieService.criteriaDeleteById(1L);

        verify(serieCriteriaRepository, times(1)).deleteSerie(1L);
    }

    @Test
    @DisplayName("Criteria delete by id: nested runtime exception")
    void givenId_whenCriteriaDeleteById_thenThrowNestedRuntimeException() {
        willThrow(InvalidDataAccessApiUsageException.class).given(serieCriteriaRepository).deleteSerie(1L);
        assertThrows(ServiceException.class, () -> serieService.criteriaDeleteById(1L));
    }

    @Test
    @DisplayName("Criteria update: correct case")
    void givenSerieBO_whenCriteriaUpdate_thenReturnUpdatedSerieBO() throws ServiceException {
        BDDMockito.given(serieCriteriaRepository.getSerieById(1L)).willReturn(Optional.of(serie));
        BDDMockito.given(serieCriteriaRepository.updateSerie(serie)).willReturn(serie);
        BDDMockito.given(converter.serieEntityToBO(serie)).willReturn(serieBO);
        BDDMockito.given(converter.serieBOToEntity(serieBO)).willReturn(serie);

        SerieBO result = serieService.criteriaUpdate(serieBO);

        assertThat(result).isNotNull().isEqualTo(serieBO);
    }

    @Test
    @DisplayName("Criteria update: nested runtime exception")
    void givenSerieBO_whenCriteriaUpdate_thenThrowNestedRuntimeException() {
        BDDMockito.given(serieCriteriaRepository.getSerieById(1L)).willReturn(Optional.of(serie));
        BDDMockito.given(converter.serieEntityToBO(serie)).willReturn(serieBO);
        BDDMockito.given(serieCriteriaRepository.updateSerie(serie))
                .willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.serieBOToEntity(serieBO)).willReturn(serie);

        assertThrows(ServiceException.class, () -> serieService.criteriaUpdate(serieBO));
    }

    @Test
    @DisplayName("Criteria create: correct case")
    void givenSerieBO_whenCriteriaCreate_thenReturnCreatedSerieBO() throws ServiceException {
        BDDMockito.willDoNothing().given(webClientService).existsActor(1L);
        BDDMockito.willDoNothing().given(webClientService).existsDirector(1L);
        BDDMockito.willDoNothing().given(webClientService).existsProducer(1L);
        BDDMockito.given(serieCriteriaRepository.createSerie(serie)).willReturn(serie);
        BDDMockito.given(converter.serieEntityToBO(serie)).willReturn(serieBO);
        BDDMockito.given(converter.serieBOToEntity(serieBO)).willReturn(serie);

        SerieBO result = serieService.criteriaCreate(serieBO);

        assertThat(result).isNotNull().isEqualTo(serieBO);
    }

    @Test
    @DisplayName("Criteria create: nested runtime exception")
    void givenSerieBO_whenCriteriaCreate_thenThrowNestedRuntimeException() throws ServiceException {
        BDDMockito.willDoNothing().given(webClientService).existsActor(1L);
        BDDMockito.willDoNothing().given(webClientService).existsDirector(1L);
        BDDMockito.willDoNothing().given(webClientService).existsProducer(1L);
        BDDMockito.given(serieCriteriaRepository.createSerie(serie))
                .willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.serieBOToEntity(serieBO)).willReturn(serie);

        assertThrows(ServiceException.class, () -> serieService.criteriaCreate(serieBO));
    }

    @Test
    @DisplayName("JPA get by id: correct case")
    void givenId_whenJpaGetById_thenReturnSerieBO() throws ServiceException {
        BDDMockito.given(serieJPARepository.findById(1L)).willReturn(Optional.of(serie));
        BDDMockito.given(converter.serieEntityToBO(serie)).willReturn(serieBO);

        SerieBO result = serieService.jpaGetById(1L);

        assertThat(result).isNotNull().isEqualTo(serieBO);
    }

    @Test
    @DisplayName("JPA get by id: not found")
    void givenId_whenJpaGetById_thenThrowNotFoundException() {
        BDDMockito.given(serieJPARepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> serieService.jpaGetById(1L));
    }

    @Test
    @DisplayName("JPA delete by id: correct case")
    void givenId_whenJpaDeleteById_thenDeleteSerie() throws ServiceException {
        BDDMockito.willDoNothing().given(serieJPARepository).deleteById(1L);

        serieService.jpaDeleteById(1L);

        verify(serieJPARepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("JPA update: correct case")
    void givenSerieBO_whenJpaUpdate_thenReturnUpdatedSerieBO() throws ServiceException {
        BDDMockito.given(serieJPARepository.existsById(serieBO.getId())).willReturn(true);
        BDDMockito.given(serieJPARepository.save(serie)).willReturn(serie);
        BDDMockito.given(converter.serieEntityToBO(serie)).willReturn(serieBO);
        BDDMockito.given(converter.serieBOToEntity(serieBO)).willReturn(serie);

        SerieBO result = serieService.jpaUpdate(serieBO);

        assertThat(result).isNotNull().isEqualTo(serieBO);
    }

    @Test
    @DisplayName("JPA update: not found")
    void givenSerieBO_whenJpaUpdate_thenThrowNotFoundException() {
        BDDMockito.given(serieJPARepository.existsById(serieBO.getId())).willReturn(false);

        assertThrows(NotFoundException.class, () -> serieService.jpaUpdate(serieBO));
    }

    @Test
    @DisplayName("JPA create: correct case")
    void givenSerieBO_whenJpaCreate_thenReturnCreatedSerieBO() throws ServiceException {
        BDDMockito.willDoNothing().given(webClientService).existsActor(1L);
        BDDMockito.willDoNothing().given(webClientService).existsDirector(1L);
        BDDMockito.willDoNothing().given(webClientService).existsProducer(1L);
        BDDMockito.given(serieJPARepository.save(serie)).willReturn(serie);
        BDDMockito.given(converter.serieEntityToBO(serie)).willReturn(serieBO);
        BDDMockito.given(converter.serieBOToEntity(serieBO)).willReturn(serie);

        SerieBO result = serieService.jpaCreate(serieBO);

        assertThat(result).isNotNull().isEqualTo(serieBO);
    }

    @Test
    @DisplayName("JPA get by id: nested runtime exception")
    void givenId_whenJpaGetById_thenThrowNestedRuntimeException() {
        BDDMockito.given(serieJPARepository.findById(1L)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> serieService.jpaGetById(1L));
    }

    @Test
    @DisplayName("JPA get all: nested runtime exception")
    void givenNothing_whenJpaGetAll_thenThrowNestedRuntimeException() {
        BDDMockito.given(serieJPARepository.findAll(PageRequest.of(0, 10, Sort.by("title").ascending())))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> serieService.jpaGetAll(0, 10, "title", false));
    }

    @Test
    @DisplayName("JPA get all: correct case")
    void givenNothing_whenJpaGetAll_thenReturnListWithAllSeriesBO() throws ServiceException {
        List<Serie> series = List.of(serie);
        BDDMockito.given(serieJPARepository.findAll(PageRequest.of(0, 10, Sort.by("title").ascending())))
                .willReturn(new PageImpl<>(series));
        BDDMockito.given(converter.serieEntityToBO(serie)).willReturn(serieBO);

        List<SerieBO> result = serieService.jpaGetAll(0, 10, "title", false);

        assertThat(result).isNotNull().hasSize(1).contains(serieBO);
    }

    @Test
    @DisplayName("JPA get all: no series found")
    void givenNothing_whenJpaGetAll_thenThrowNotFoundException() {
        BDDMockito.given(serieJPARepository.findAll(PageRequest.of(0, 10, Sort.by("title").ascending())))
                .willReturn(new PageImpl<>(List.of()));

        assertThrows(NotFoundException.class, () -> serieService.jpaGetAll(0, 10, "title", false));
    }

    @Test
    @DisplayName("JPA delete by id: nested runtime exception")
    void givenId_whenJpaDeleteById_thenThrowNestedRuntimeException() {
        willThrow(InvalidDataAccessApiUsageException.class).given(serieJPARepository).deleteById(1L);

        assertThrows(ServiceException.class, () -> serieService.jpaDeleteById(1L));
    }

    @Test
    @DisplayName("JPA update: nested runtime exception")
    void givenSerieBO_whenJpaUpdate_thenThrowNestedRuntimeException() {
        BDDMockito.given(serieJPARepository.existsById(serieBO.getId())).willReturn(true);
        BDDMockito.given(serieJPARepository.save(serie)).willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.serieBOToEntity(serieBO)).willReturn(serie);

        assertThrows(ServiceException.class, () -> serieService.jpaUpdate(serieBO));
    }

    @Test
    @DisplayName("JPA create: nested runtime exception")
    void givenSerieBO_whenJpaCreate_thenThrowNestedRuntimeException() throws NotFoundException {
        BDDMockito.willDoNothing().given(webClientService).existsActor(1L);
        BDDMockito.willDoNothing().given(webClientService).existsDirector(1L);
        BDDMockito.willDoNothing().given(webClientService).existsProducer(1L);
        BDDMockito.given(serieJPARepository.save(serie)).willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.serieBOToEntity(serieBO)).willReturn(serie);

        assertThrows(ServiceException.class, () -> serieService.jpaCreate(serieBO));
    }

    @Test
    @DisplayName("Filter series: correct case")
    void givenFilters_whenFilterSeries_thenReturnListWithFilteredSeriesBO() throws ServiceException {

        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "title";
        boolean sortOrder = true;

        List<Serie> series = List.of(serie);
        BDDMockito.given(actorJPARepository.findByName(anyString())).willReturn(List.of(actor));
        BDDMockito.given(directorJPARepository.findByName(anyString())).willReturn(List.of(director));
        BDDMockito.given(producerJPARepository.findByName(anyString())).willReturn(List.of(producer));

        BDDMockito.given(
                        serieCriteriaRepository.filterSeries(List.of(serieBO.getTitle()), List.of(serieBO.getReleaseYear()),
                                List.of(director), List.of(producer), List.of(actor),
                                PageRequest.of(pageNumber, pageSize).withSort((Sort.by(sortBy).descending()))))
                .willReturn(series);
        BDDMockito.given(converter.serieEntityToBO(serie)).willReturn(serieBO);

        List<SerieBO> result = serieService.filterSeries(List.of(serieBO.getTitle()), List.of(serieBO.getReleaseYear()),
                List.of(director.getName()), List.of(producer.getName()), List.of(actor.getName()), pageNumber,
                pageSize, sortBy, sortOrder);

        assertThat(result).isNotNull().hasSize(1).contains(serieBO);
    }

    @Test
    @DisplayName("Filter series: no series found")
    void givenFilters_whenFilterSeries_thenThrowNotFoundException() {

        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "title";
        boolean sortOrder = true;

        BDDMockito.given(actorJPARepository.findByName(anyString())).willReturn(List.of(actor));
        BDDMockito.given(directorJPARepository.findByName(anyString())).willReturn(List.of(director));
        BDDMockito.given(producerJPARepository.findByName(anyString())).willReturn(List.of(producer));

        BDDMockito.given(
                        serieCriteriaRepository.filterSeries(List.of(serieBO.getTitle()), List.of(serieBO.getReleaseYear()),
                                List.of(director), List.of(producer), List.of(actor),
                                PageRequest.of(pageNumber, pageSize).withSort((Sort.by(sortBy).descending()))))
                .willReturn(List.of());

        assertThrows(NotFoundException.class,
                () -> serieService.filterSeries(List.of(serieBO.getTitle()), List.of(serieBO.getReleaseYear()),
                        List.of(director.getName()), List.of(producer.getName()), List.of(actor.getName()), pageNumber,
                        pageSize, sortBy, sortOrder));
    }

    @Test
    @DisplayName("Filter series: nested runtime exception")
    void givenFilters_whenFilterSeries_thenThrowNestedRuntimeException() {

        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "title";
        boolean sortOrder = true;

        BDDMockito.given(actorJPARepository.findByName(anyString())).willReturn(List.of(actor));
        BDDMockito.given(directorJPARepository.findByName(anyString())).willReturn(List.of(director));
        BDDMockito.given(producerJPARepository.findByName(anyString())).willReturn(List.of(producer));

        BDDMockito.given(
                        serieCriteriaRepository.filterSeries(List.of(serieBO.getTitle()), List.of(serieBO.getReleaseYear()),
                                List.of(director), List.of(producer), List.of(actor),
                                PageRequest.of(pageNumber, pageSize).withSort((Sort.by(sortBy).descending()))))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class,
                () -> serieService.filterSeries(List.of(serieBO.getTitle()), List.of(serieBO.getReleaseYear()),
                        List.of(director.getName()), List.of(producer.getName()), List.of(actor.getName()), pageNumber,
                        pageSize, sortBy, sortOrder));
    }

}


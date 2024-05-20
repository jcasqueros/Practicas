package com.viewnext.films.businesslayer.service;

import com.viewnext.films.businesslayer.bo.ActorBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.impl.ActorServiceImpl;
import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.repository.criteria.ActorCriteriaRepository;
import com.viewnext.films.persistencelayer.repository.jpa.ActorJPARepository;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ActorServiceImplTest {

    @Mock
    private ActorCriteriaRepository actorCriteriaRepository;

    @Mock
    private ActorJPARepository actorJPARepository;

    @Mock
    private Converter converter;

    @InjectMocks
    private ActorServiceImpl actorService;

    private Actor actor;
    private ActorBO actorBO;

    @BeforeEach
    void setup() {
        actor = new Actor();
        actor.setId(1L);
        actor.setAge(18);
        actor.setName("Jhon");
        actor.setNationality("spain");
        actorBO = new ActorBO();
        actorBO.setId(1L);
        actorBO.setAge(18);
        actorBO.setName("Jhon");
        actorBO.setNationality("spain");
    }

    @Test
    @DisplayName("Criteria get by id: correct case")
    void givenId_whenCriteriaGetById_thenReturnActorBO() throws ServiceException {
        BDDMockito.given(actorCriteriaRepository.getActorById(1L)).willReturn(Optional.of(actor));
        BDDMockito.given(converter.actorEntityToBO(actor)).willReturn(actorBO);

        ActorBO result = actorService.criteriaGetById(1L);

        assertThat(result).isNotNull().isEqualTo(actorBO);
    }

    @Test
    @DisplayName("Criteria get by id: not found")
    void givenId_whenCriteriaGetById_thenThrowNotFoundException() {
        BDDMockito.given(actorCriteriaRepository.getActorById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> actorService.criteriaGetById(1L));
    }

    @Test
    @DisplayName("Criteria get by id: nested runtime exception")
    void givenId_whenCriteriaGetById_thenThrowNestedRuntimeException() {
        BDDMockito.given(actorCriteriaRepository.getActorById(1L)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> actorService.criteriaGetById(1L));
    }

    @Test
    @DisplayName("Criteria get all: correct case")
    void givenNothing_whenCriteriaGetAll_thenReturnListWithAllActorsBO() throws ServiceException {
        List<Actor> actors = List.of(actor);
        BDDMockito.given(actorCriteriaRepository.getAllActors(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willReturn(actors);
        BDDMockito.given(converter.actorEntityToBO(actor)).willReturn(actorBO);

        List<ActorBO> result = actorService.criteriaGetAll(0, 10, "name", false);

        assertThat(result).isNotNull().hasSize(1).contains(actorBO);
    }

    @Test
    @DisplayName("Criteria get all: no actors found")
    void givenNothing_whenCriteriaGetAll_thenThrowNotFoundException() {
        BDDMockito.given(actorCriteriaRepository.getAllActors(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willReturn(List.of());

        assertThrows(NotFoundException.class, () -> actorService.criteriaGetAll(0, 10, "name", false));
    }

    @Test
    @DisplayName("Criteria get all: nested runtime exception")
    void givenNothing_whenCriteriaGetAll_thenThrowNestedRuntimeException() {
        BDDMockito.given(actorCriteriaRepository.getAllActors(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> actorService.criteriaGetAll(0, 10, "name", false));
    }

    @Test
    @DisplayName("Criteria delete by id: correct case")
    void givenId_whenCriteriaDeleteById_thenDeleteActor() throws ServiceException {
        BDDMockito.willDoNothing().given(actorCriteriaRepository).deleteActor(1L);

        actorService.criteriaDeleteById(1L);

        verify(actorCriteriaRepository, times(1)).deleteActor(1L);
    }

    @Test
    @DisplayName("Criteria delete by id: nested runtime exception")
    void givenId_whenCriteriaDeleteById_thenThrowNestedRuntimeException() {
        willThrow(InvalidDataAccessApiUsageException.class).given(actorCriteriaRepository).deleteActor(1L);
        assertThrows(ServiceException.class, () -> actorService.criteriaDeleteById(1L));
    }

    @Test
    @DisplayName("Criteria update: correct case")
    void givenActorBO_whenCriteriaUpdate_thenReturnUpdatedActorBO() throws ServiceException {
        BDDMockito.given(actorCriteriaRepository.getActorById(1L)).willReturn(Optional.of(actor));
        BDDMockito.given(actorCriteriaRepository.updateActor(actor)).willReturn(actor);
        BDDMockito.given(converter.actorEntityToBO(actor)).willReturn(actorBO);
        BDDMockito.given(converter.actorBOToEntity(actorBO)).willReturn(actor);

        ActorBO result = actorService.criteriaUpdate(actorBO);

        assertThat(result).isNotNull().isEqualTo(actorBO);
    }

    @Test
    @DisplayName("Criteria update: nested runtime exception")
    void givenActorBO_whenCriteriaUpdate_thenThrowNestedRuntimeException() {
        BDDMockito.given(actorCriteriaRepository.getActorById(1L)).willReturn(Optional.of(actor));
        BDDMockito.given(converter.actorEntityToBO(actor)).willReturn(actorBO);
        BDDMockito.given(actorCriteriaRepository.updateActor(actor))
                .willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.actorBOToEntity(actorBO)).willReturn(actor);

        assertThrows(ServiceException.class, () -> actorService.criteriaUpdate(actorBO));
    }

    @Test
    @DisplayName("Criteria create: correct case")
    void givenActorBO_whenCriteriaCreate_thenReturnCreatedActorBO() throws ServiceException {
        BDDMockito.given(actorCriteriaRepository.createActor(actor)).willReturn(actor);
        BDDMockito.given(converter.actorEntityToBO(actor)).willReturn(actorBO);
        BDDMockito.given(converter.actorBOToEntity(actorBO)).willReturn(actor);

        ActorBO result = actorService.criteriaCreate(actorBO);

        assertThat(result).isNotNull().isEqualTo(actorBO);
    }

    @Test
    @DisplayName("Criteria create: nested runtime exception")
    void givenActorBO_whenCriteriaCreate_thenThrowNestedRuntimeException() {
        BDDMockito.given(actorCriteriaRepository.createActor(actor))
                .willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.actorBOToEntity(actorBO)).willReturn(actor);

        assertThrows(ServiceException.class, () -> actorService.criteriaCreate(actorBO));
    }

    @Test
    @DisplayName("JPA get by id: correct case")
    void givenId_whenJpaGetById_thenReturnActorBO() throws ServiceException {
        BDDMockito.given(actorJPARepository.findById(1L)).willReturn(Optional.of(actor));
        BDDMockito.given(converter.actorEntityToBO(actor)).willReturn(actorBO);

        ActorBO result = actorService.jpaGetById(1L);

        assertThat(result).isNotNull().isEqualTo(actorBO);
    }

    @Test
    @DisplayName("JPA get by id: not found")
    void givenId_whenJpaGetById_thenThrowNotFoundException() {
        BDDMockito.given(actorJPARepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> actorService.jpaGetById(1L));
    }

    @Test
    @DisplayName("JPA delete by id: correct case")
    void givenId_whenJpaDeleteById_thenDeleteActor() throws ServiceException {
        BDDMockito.willDoNothing().given(actorJPARepository).deleteById(1L);

        actorService.jpaDeleteById(1L);

        verify(actorJPARepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("JPA update: correct case")
    void givenActorBO_whenJpaUpdate_thenReturnUpdatedActorBO() throws ServiceException {
        BDDMockito.given(actorJPARepository.existsById(actorBO.getId())).willReturn(true);
        BDDMockito.given(actorJPARepository.save(actor)).willReturn(actor);
        BDDMockito.given(converter.actorEntityToBO(actor)).willReturn(actorBO);
        BDDMockito.given(converter.actorBOToEntity(actorBO)).willReturn(actor);

        ActorBO result = actorService.jpaUpdate(actorBO);

        assertThat(result).isNotNull().isEqualTo(actorBO);
    }

    @Test
    @DisplayName("JPA update: not found")
    void givenActorBO_whenJpaUpdate_thenThrowNotFoundException() {
        BDDMockito.given(actorJPARepository.existsById(actorBO.getId())).willReturn(false);

        assertThrows(NotFoundException.class, () -> actorService.jpaUpdate(actorBO));
    }

    @Test
    @DisplayName("JPA create: correct case")
    void givenActorBO_whenJpaCreate_thenReturnCreatedActorBO() throws ServiceException {
        BDDMockito.given(actorJPARepository.save(actor)).willReturn(actor);
        BDDMockito.given(converter.actorEntityToBO(actor)).willReturn(actorBO);
        BDDMockito.given(converter.actorBOToEntity(actorBO)).willReturn(actor);

        ActorBO result = actorService.jpaCreate(actorBO);

        assertThat(result).isNotNull().isEqualTo(actorBO);
    }

    @Test
    @DisplayName("JPA get by id: nested runtime exception")
    void givenId_whenJpaGetById_thenThrowNestedRuntimeException() {
        BDDMockito.given(actorJPARepository.findById(1L)).willThrow(InvalidDataAccessApiUsageException.class);
        assertThrows(ServiceException.class, () -> actorService.jpaGetById(1L));
    }

    @Test
    @DisplayName("JPA get all: nested runtime exception")
    void givenNothing_whenJpaGetAll_thenThrowNestedRuntimeException() {
        BDDMockito.given(actorJPARepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> actorService.jpaGetAll(0, 10, "name", false));
    }

    @Test
    @DisplayName("JPA get all: correct case")
    void givenNothing_whenJpaGetAll_thenReturnListWithAllActorsBO() throws ServiceException {
        List<Actor> actors = List.of(actor);
        BDDMockito.given(actorJPARepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willReturn(new PageImpl<>(actors));
        BDDMockito.given(converter.actorEntityToBO(actor)).willReturn(actorBO);

        List<ActorBO> result = actorService.jpaGetAll(0, 10, "name", false);

        assertThat(result).isNotNull().hasSize(1).contains(actorBO);
    }

    @Test
    @DisplayName("JPA get all: no actors found")
    void givenNothing_whenJpaGetAll_thenThrowNotFoundException() {
        BDDMockito.given(actorJPARepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willReturn(new PageImpl<>(List.of()));

        assertThrows(NotFoundException.class, () -> actorService.jpaGetAll(0, 10, "name", false));
    }

    @Test
    @DisplayName("JPA delete by id: nested runtime exception")
    void givenId_whenJpaDeleteById_thenThrowNestedRuntimeException() {
        willThrow(InvalidDataAccessApiUsageException.class).given(actorJPARepository).deleteById(1L);

        assertThrows(ServiceException.class, () -> actorService.jpaDeleteById(1L));
    }

    @Test
    @DisplayName("JPA update: nested runtime exception")
    void givenActorBO_whenJpaUpdate_thenThrowNestedRuntimeException() {
        BDDMockito.given(actorJPARepository.existsById(actorBO.getId())).willReturn(true);
        BDDMockito.given(actorJPARepository.save(actor)).willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.actorBOToEntity(actorBO)).willReturn(actor);

        assertThrows(ServiceException.class, () -> actorService.jpaUpdate(actorBO));
    }

    @Test
    @DisplayName("JPA create: nested runtime exception")
    void givenActorBO_whenJpaCreate_thenThrowNestedRuntimeException() {
        BDDMockito.given(actorJPARepository.save(actor)).willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.actorBOToEntity(actorBO)).willReturn(actor);
        assertThrows(ServiceException.class, () -> actorService.jpaCreate(actorBO));
    }

    @Test
    @DisplayName("Filter actors: correct case")
    void givenFilters_whenFilterActors_thenReturnListWithFilteredActorsBO() throws ServiceException {

        List<String> names = List.of("Jhon", "Jhon");
        List<Integer> ages = List.of(18, 25);
        List<String> nationalities = List.of("spain", "usa");
        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "name";
        boolean sortOrder = true;

        List<Actor> actors = List.of(actor);
        BDDMockito.given(actorCriteriaRepository.filterActors(names, ages, nationalities,
                PageRequest.of(pageNumber, pageSize).withSort((Sort.by(sortBy).descending())))).willReturn(actors);
        BDDMockito.given(converter.actorEntityToBO(actor)).willReturn(actorBO);

        List<ActorBO> result = actorService.filterActors(names, ages, nationalities, pageNumber, pageSize, sortBy,
                sortOrder);

        assertThat(result).isNotNull().hasSize(1).contains(actorBO);
    }

    @Test
    @DisplayName("Filter actors: no actors found")
    void givenFilters_whenFilterActors_thenThrowNotFoundException() {

        List<String> names = List.of("Jhon", "Jhon");
        List<Integer> ages = List.of(18, 25);
        List<String> nationalities = List.of("spain", "usa");
        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "name";
        boolean sortOrder = true;

        BDDMockito.given(actorCriteriaRepository.filterActors(names, ages, nationalities,
                PageRequest.of(pageNumber, pageSize).withSort((Sort.by(sortBy).descending())))).willReturn(List.of());

        assertThrows(NotFoundException.class,
                () -> actorService.filterActors(names, ages, nationalities, pageNumber, pageSize, sortBy, sortOrder));
    }

    @Test
    @DisplayName("Filter actors: nested runtime exception")
    void givenFilters_whenFilterActors_thenThrowNestedRuntimeException() {

        List<String> names = List.of("Jhon", "Jhon");
        List<Integer> ages = List.of(18, 25);
        List<String> nationalities = List.of("spain", "usa");
        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "name";
        boolean sortOrder = true;

        BDDMockito.given(actorCriteriaRepository.filterActors(names, ages, nationalities,
                        PageRequest.of(pageNumber, pageSize).withSort((Sort.by(sortBy).descending()))))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class,
                () -> actorService.filterActors(names, ages, nationalities, pageNumber, pageSize, sortBy, sortOrder));
    }

    @Test
    @DisplayName("Criteria find by name and age: correct case")
    void givenNameAndAge_whenCriteriaFindByNameAndAge_thenReturnListWithActorsBO() throws ServiceException {
        String name = "Jhon";
        int age = 18;
        List<Actor> actors = List.of(actor);
        BDDMockito.given(actorCriteriaRepository.getActorsByNameAndAge(name, age)).willReturn(actors);
        BDDMockito.given(converter.actorEntityToBO(actor)).willReturn(actorBO);

        List<ActorBO> result = actorService.criteriaFindByNameAndAge(name, age);

        assertThat(result).isNotNull().hasSize(1).contains(actorBO);
    }

    @Test
    @DisplayName("Criteria find by name and age: not found")
    void givenNameAndAge_whenCriteriaFindByNameAndAge_thenThrowNotFoundException() {
        String name = "Jhon";
        int age = 25;
        BDDMockito.given(actorCriteriaRepository.getActorsByNameAndAge(name, age)).willReturn(List.of());

        assertThrows(NotFoundException.class, () -> actorService.criteriaFindByNameAndAge(name, age));
    }

    @Test
    @DisplayName("Criteria find by name and age: nested runtime exception")
    void givenNameAndAge_whenCriteriaFindByNameAndAge_thenThrowNestedRuntimeException() {
        String name = "Jhon";
        int age = 30;
        BDDMockito.given(actorCriteriaRepository.getActorsByNameAndAge(name, age))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> actorService.criteriaFindByNameAndAge(name, age));
    }

    @Test
    @DisplayName("JPA find by name and age: correct case")
    void givenNameAndAge_whenJpaFindByNameAndAge_thenReturnListWithActorsBO() throws ServiceException {
        String name = "Jhon";
        int age = 18;
        List<Actor> actors = List.of(actor);
        BDDMockito.given(actorJPARepository.findByNameAndAge(name, age)).willReturn(actors);
        BDDMockito.given(converter.actorEntityToBO(actor)).willReturn(actorBO);

        List<ActorBO> result = actorService.jpaFindByNameAndAge(name, age);

        assertThat(result).isNotNull().hasSize(1).contains(actorBO);
    }

    @Test
    @DisplayName("JPA find by name and age: not found")
    void givenNameAndAge_whenJpaFindByNameAndAge_thenThrowNotFoundException() {
        String name = "Jhon";
        int age = 25;
        BDDMockito.given(actorJPARepository.findByNameAndAge(name, age)).willReturn(List.of());

        assertThrows(NotFoundException.class, () -> actorService.jpaFindByNameAndAge(name, age));
    }

    @Test
    @DisplayName("JPA find by name and age: nested runtime exception")
    void givenNameAndAge_whenJpaFindByNameAndAge_thenThrowNestedRuntimeException() {
        String name = "Jhon";
        int age = 30;
        BDDMockito.given(actorJPARepository.findByNameAndAge(name, age))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> actorService.jpaFindByNameAndAge(name, age));
    }

}


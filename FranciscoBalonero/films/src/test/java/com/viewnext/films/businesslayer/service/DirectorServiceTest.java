package com.viewnext.films.businesslayer.service;

import com.viewnext.films.businesslayer.bo.DirectorBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.impl.DirectorServiceImpl;
import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.repository.criteria.DirectorCriteriaRepository;
import com.viewnext.films.persistencelayer.repository.jpa.DirectorJPARepository;
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
class DirectorServiceImplTest {

    @Mock
    private DirectorCriteriaRepository directorCriteriaRepository;

    @Mock
    private DirectorJPARepository directorJPARepository;

    @Mock
    private Converter converter;

    @InjectMocks
    private DirectorServiceImpl directorService;

    private Director director;
    private DirectorBO directorBO;

    @BeforeEach
    void setup() {
        director = new Director();
        director.setId(1L);
        director.setAge(18);
        director.setName("Jhon");
        director.setNationality("spain");
        directorBO = new DirectorBO();
        directorBO.setId(1L);
        directorBO.setAge(18);
        directorBO.setName("Jhon");
        directorBO.setNationality("spain");
    }

    @Test
    @DisplayName("Criteria get by id: correct case")
    void givenId_whenCriteriaGetById_thenReturnDirectorBO() throws ServiceException {
        BDDMockito.given(directorCriteriaRepository.getDirectorById(1L)).willReturn(Optional.of(director));
        BDDMockito.given(converter.directorEntityToBO(director)).willReturn(directorBO);

        DirectorBO result = directorService.criteriaGetById(1L);

        assertThat(result).isNotNull().isEqualTo(directorBO);
    }

    @Test
    @DisplayName("Criteria get by id: not found")
    void givenId_whenCriteriaGetById_thenThrowNotFoundException() throws ServiceException {
        BDDMockito.given(directorCriteriaRepository.getDirectorById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> directorService.criteriaGetById(1L));
    }

    @Test
    @DisplayName("Criteria get by id: nested runtime exception")
    void givenId_whenCriteriaGetById_thenThrowNestedRuntimeException() throws ServiceException {
        BDDMockito.given(directorCriteriaRepository.getDirectorById(1L))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> directorService.criteriaGetById(1L));
    }

    @Test
    @DisplayName("Criteria get all: correct case")
    void givenNothing_whenCriteriaGetAll_thenReturnListWithAllDirectorsBO() throws ServiceException {
        List<Director> directors = List.of(director);
        BDDMockito.given(directorCriteriaRepository.getAllDirectors(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willReturn(directors);
        BDDMockito.given(converter.directorEntityToBO(director)).willReturn(directorBO);

        List<DirectorBO> result = directorService.criteriaGetAll(0, 10, "name", false);

        assertThat(result).isNotNull().hasSize(1).contains(directorBO);
    }

    @Test
    @DisplayName("Criteria get all: no directors found")
    void givenNothing_whenCriteriaGetAll_thenThrowNotFoundException() throws ServiceException {
        BDDMockito.given(directorCriteriaRepository.getAllDirectors(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willReturn(List.of());

        assertThrows(NotFoundException.class, () -> directorService.criteriaGetAll(0, 10, "name", false));
    }

    @Test
    @DisplayName("Criteria get all: nested runtime exception")
    void givenNothing_whenCriteriaGetAll_thenThrowNestedRuntimeException() throws ServiceException {
        BDDMockito.given(directorCriteriaRepository.getAllDirectors(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> directorService.criteriaGetAll(0, 10, "name", false));
    }

    @Test
    @DisplayName("Criteria delete by id: correct case")
    void givenId_whenCriteriaDeleteById_thenDeleteDirector() throws ServiceException {
        BDDMockito.willDoNothing().given(directorCriteriaRepository).deleteDirector(1L);

        directorService.criteriaDeleteById(1L);

        verify(directorCriteriaRepository, times(1)).deleteDirector(1L);
    }

    @Test
    @DisplayName("Criteria delete by id: nested runtime exception")
    void givenId_whenCriteriaDeleteById_thenThrowNestedRuntimeException() throws ServiceException {
        willThrow(InvalidDataAccessApiUsageException.class).given(directorCriteriaRepository).deleteDirector(1L);
        assertThrows(ServiceException.class, () -> directorService.criteriaDeleteById(1L));
    }

    @Test
    @DisplayName("Criteria update: correct case")
    void givenDirectorBO_whenCriteriaUpdate_thenReturnUpdatedDirectorBO() throws ServiceException {
        BDDMockito.given(directorCriteriaRepository.getDirectorById(1L)).willReturn(Optional.of(director));
        BDDMockito.given(directorCriteriaRepository.updateDirector(director)).willReturn(director);
        BDDMockito.given(converter.directorEntityToBO(director)).willReturn(directorBO);
        BDDMockito.given(converter.directorBOToEntity(directorBO)).willReturn(director);

        DirectorBO result = directorService.criteriaUpdate(directorBO);

        assertThat(result).isNotNull().isEqualTo(directorBO);
    }

    @Test
    @DisplayName("Criteria update: nested runtime exception")
    void givenDirectorBO_whenCriteriaUpdate_thenThrowNestedRuntimeException() throws ServiceException {
        BDDMockito.given(directorCriteriaRepository.getDirectorById(1L)).willReturn(Optional.of(director));
        BDDMockito.given(converter.directorEntityToBO(director)).willReturn(directorBO);
        BDDMockito.given(directorCriteriaRepository.updateDirector(director))
                .willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.directorBOToEntity(directorBO)).willReturn(director);

        assertThrows(ServiceException.class, () -> directorService.criteriaUpdate(directorBO));
    }

    @Test
    @DisplayName("Criteria create: correct case")
    void givenDirectorBO_whenCriteriaCreate_thenReturnCreatedDirectorBO() throws ServiceException {
        BDDMockito.given(directorCriteriaRepository.createDirector(director)).willReturn(director);
        BDDMockito.given(converter.directorEntityToBO(director)).willReturn(directorBO);
        BDDMockito.given(converter.directorBOToEntity(directorBO)).willReturn(director);

        DirectorBO result = directorService.criteriaCreate(directorBO);

        assertThat(result).isNotNull().isEqualTo(directorBO);
    }

    @Test
    @DisplayName("Criteria create: nested runtime exception")
    void givenDirectorBO_whenCriteriaCreate_thenThrowNestedRuntimeException() throws ServiceException {
        BDDMockito.given(directorCriteriaRepository.createDirector(director))
                .willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.directorBOToEntity(directorBO)).willReturn(director);

        assertThrows(ServiceException.class, () -> directorService.criteriaCreate(directorBO));
    }

    @Test
    @DisplayName("JPA get by id: correct case")
    void givenId_whenJpaGetById_thenReturnDirectorBO() throws ServiceException {
        BDDMockito.given(directorJPARepository.findById(1L)).willReturn(Optional.of(director));
        BDDMockito.given(converter.directorEntityToBO(director)).willReturn(directorBO);

        DirectorBO result = directorService.jpaGetById(1L);

        assertThat(result).isNotNull().isEqualTo(directorBO);
    }

    @Test
    @DisplayName("JPA get by id: not found")
    void givenId_whenJpaGetById_thenThrowNotFoundException() {
        BDDMockito.given(directorJPARepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> directorService.jpaGetById(1L));
    }

    @Test
    @DisplayName("JPA delete by id: correct case")
    void givenId_whenJpaDeleteById_thenDeleteDirector() throws ServiceException {
        BDDMockito.willDoNothing().given(directorJPARepository).deleteById(1L);

        directorService.jpaDeleteById(1L);

        verify(directorJPARepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("JPA update: correct case")
    void givenDirectorBO_whenJpaUpdate_thenReturnUpdatedDirectorBO() throws ServiceException {
        BDDMockito.given(directorJPARepository.existsById(directorBO.getId())).willReturn(true);
        BDDMockito.given(directorJPARepository.save(director)).willReturn(director);
        BDDMockito.given(converter.directorEntityToBO(director)).willReturn(directorBO);
        BDDMockito.given(converter.directorBOToEntity(directorBO)).willReturn(director);

        DirectorBO result = directorService.jpaUpdate(directorBO);

        assertThat(result).isNotNull().isEqualTo(directorBO);
    }

    @Test
    @DisplayName("JPA update: not found")
    void givenDirectorBO_whenJpaUpdate_thenThrowNotFoundException() throws ServiceException {
        BDDMockito.given(directorJPARepository.existsById(directorBO.getId())).willReturn(false);

        assertThrows(NotFoundException.class, () -> directorService.jpaUpdate(directorBO));
    }

    @Test
    @DisplayName("JPA create: correct case")
    void givenDirectorBO_whenJpaCreate_thenReturnCreatedDirectorBO() throws ServiceException {
        BDDMockito.given(directorJPARepository.save(director)).willReturn(director);
        BDDMockito.given(converter.directorEntityToBO(director)).willReturn(directorBO);
        BDDMockito.given(converter.directorBOToEntity(directorBO)).willReturn(director);

        DirectorBO result = directorService.jpaCreate(directorBO);

        assertThat(result).isNotNull().isEqualTo(directorBO);
    }

    @Test
    @DisplayName("JPA get by id: nested runtime exception")
    void givenId_whenJpaGetById_thenThrowNestedRuntimeException() {
        BDDMockito.given(directorJPARepository.findById(1L)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> directorService.jpaGetById(1L));
    }

    @Test
    @DisplayName("JPA get all: nested runtime exception")
    void givenNothing_whenJpaGetAll_thenThrowNestedRuntimeException() {
        BDDMockito.given(directorJPARepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> directorService.jpaGetAll(0, 10, "name", false));
    }

    @Test
    @DisplayName("JPA get all: correct case")
    void givenNothing_whenJpaGetAll_thenReturnListWithAllDirectorsBO() throws ServiceException {
        List<Director> directors = List.of(director);
        BDDMockito.given(directorJPARepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willReturn(new PageImpl<>(directors));
        BDDMockito.given(converter.directorEntityToBO(director)).willReturn(directorBO);

        List<DirectorBO> result = directorService.jpaGetAll(0, 10, "name", false);

        assertThat(result).isNotNull().hasSize(1).contains(directorBO);
    }

    @Test
    @DisplayName("JPA get all: no directors found")
    void givenNothing_whenJpaGetAll_thenThrowNotFoundException() {
        BDDMockito.given(directorJPARepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willReturn(new PageImpl<>(List.of()));

        assertThrows(NotFoundException.class, () -> directorService.jpaGetAll(0, 10, "name", false));
    }

    @Test
    @DisplayName("JPA delete by id: nested runtime exception")
    void givenId_whenJpaDeleteById_thenThrowNestedRuntimeException() {
        willThrow(InvalidDataAccessApiUsageException.class).given(directorJPARepository).deleteById(1L);

        assertThrows(ServiceException.class, () -> directorService.jpaDeleteById(1L));
    }

    @Test
    @DisplayName("JPA update: nested runtime exception")
    void givenDirectorBO_whenJpaUpdate_thenThrowNestedRuntimeException() {
        BDDMockito.given(directorJPARepository.existsById(directorBO.getId())).willReturn(true);
        BDDMockito.given(directorJPARepository.save(director)).willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.directorBOToEntity(directorBO)).willReturn(director);

        assertThrows(ServiceException.class, () -> directorService.jpaUpdate(directorBO));
    }

    @Test
    @DisplayName("JPA create: nested runtime exception")
    void givenDirectorBO_whenJpaCreate_thenThrowNestedRuntimeException() {
        BDDMockito.given(directorJPARepository.save(director)).willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.directorBOToEntity(directorBO)).willReturn(director);

        assertThrows(ServiceException.class, () -> directorService.jpaCreate(directorBO));
    }

}


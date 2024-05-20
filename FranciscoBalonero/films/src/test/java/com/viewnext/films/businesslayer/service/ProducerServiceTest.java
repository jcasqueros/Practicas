package com.viewnext.films.businesslayer.service;

import com.viewnext.films.businesslayer.bo.ProducerBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.impl.ProducerServiceImpl;
import com.viewnext.films.persistencelayer.entity.Producer;
import com.viewnext.films.persistencelayer.repository.criteria.ProducerCriteriaRepository;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProducerServiceImplTest {

    @Mock
    private ProducerCriteriaRepository producerCriteriaRepository;

    @Mock
    private ProducerJPARepository producerJPARepository;

    @Mock
    private Converter converter;

    @InjectMocks
    private ProducerServiceImpl producerService;

    private Producer producer;
    private ProducerBO producerBO;

    @BeforeEach
    void setup() {
        producer = new Producer();
        producer.setId(1L);
        producer.setName("Producer 1");
        producer.setFoundationYear(2000);
        producerBO = new ProducerBO();
        producerBO.setId(1L);
        producerBO.setName("Producer 1");
        producerBO.setFoundationYear(2000);
    }

    @Test
    @DisplayName("Criteria get by id: correct case")
    void givenId_whenCriteriaGetById_thenReturnProducerBO() throws ServiceException {
        BDDMockito.given(producerCriteriaRepository.getProducerById(1L)).willReturn(Optional.of(producer));
        BDDMockito.given(converter.producerEntityToBO(producer)).willReturn(producerBO);

        ProducerBO result = producerService.criteriaGetById(1L);

        assertThat(result).isNotNull().isEqualTo(producerBO);
    }

    @Test
    @DisplayName("Criteria get by id: not found")
    void givenId_whenCriteriaGetById_thenThrowNotFoundException() {
        BDDMockito.given(producerCriteriaRepository.getProducerById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> producerService.criteriaGetById(1L));
    }

    @Test
    @DisplayName("Criteria get by id: nested runtime exception")
    void givenId_whenCriteriaGetById_thenThrowNestedRuntimeException() {
        BDDMockito.given(producerCriteriaRepository.getProducerById(1L))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> producerService.criteriaGetById(1L));
    }

    @Test
    @DisplayName("Criteria get all: correct case")
    void givenNothing_whenCriteriaGetAll_thenReturnListWithAllProducersBO() throws ServiceException {
        List<Producer> producers = List.of(producer);
        BDDMockito.given(producerCriteriaRepository.getAllProducers(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willReturn(producers);
        BDDMockito.given(converter.producerEntityToBO(producer)).willReturn(producerBO);

        List<ProducerBO> result = producerService.criteriaGetAll(0, 10, "name", false);

        assertThat(result).isNotNull().hasSize(1).contains(producerBO);
    }

    @Test
    @DisplayName("Criteria get all: no producers found")
    void givenNothing_whenCriteriaGetAll_thenThrowNotFoundException() {
        BDDMockito.given(producerCriteriaRepository.getAllProducers(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willReturn(List.of());

        assertThrows(NotFoundException.class, () -> producerService.criteriaGetAll(0, 10, "name", false));
    }

    @Test
    @DisplayName("Criteria get all: nested runtime exception")
    void givenNothing_whenCriteriaGetAll_thenThrowNestedRuntimeException() {
        BDDMockito.given(producerCriteriaRepository.getAllProducers(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> producerService.criteriaGetAll(0, 10, "name", false));
    }

    @Test
    @DisplayName("Criteria delete by id: correct case")
    void givenId_whenCriteriaDeleteById_thenDeleteProducer() throws ServiceException {
        BDDMockito.willDoNothing().given(producerCriteriaRepository).deleteProducer(1L);

        producerService.criteriaDeleteById(1L);

        verify(producerCriteriaRepository, times(1)).deleteProducer(1L);
    }

    @Test
    @DisplayName("Criteria delete by id: nested runtime exception")
    void givenId_whenCriteriaDeleteById_thenThrowNestedRuntimeException() {
        willThrow(InvalidDataAccessApiUsageException.class).given(producerCriteriaRepository).deleteProducer(1L);
        assertThrows(ServiceException.class, () -> producerService.criteriaDeleteById(1L));
    }

    @Test
    @DisplayName("Criteria update: correct case")
    void givenProducerBO_whenCriteriaUpdate_thenReturnUpdatedProducerBO() throws ServiceException {
        BDDMockito.given(producerCriteriaRepository.getProducerById(1L)).willReturn(Optional.of(producer));
        BDDMockito.given(producerCriteriaRepository.updateProducer(producer)).willReturn(producer);
        BDDMockito.given(converter.producerEntityToBO(producer)).willReturn(producerBO);
        BDDMockito.given(converter.producerBOToEntity(producerBO)).willReturn(producer);

        ProducerBO result = producerService.criteriaUpdate(producerBO);

        assertThat(result).isNotNull().isEqualTo(producerBO);
    }

    @Test
    @DisplayName("Criteria update: nested runtime exception")
    void givenProducerBO_whenCriteriaUpdate_thenThrowNestedRuntimeException() {
        BDDMockito.given(producerCriteriaRepository.getProducerById(1L)).willReturn(Optional.of(producer));
        BDDMockito.given(converter.producerEntityToBO(producer)).willReturn(producerBO);
        BDDMockito.given(producerCriteriaRepository.updateProducer(producer))
                .willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.producerBOToEntity(producerBO)).willReturn(producer);

        assertThrows(ServiceException.class, () -> producerService.criteriaUpdate(producerBO));
    }

    @Test
    @DisplayName("Criteria create: correct case")
    void givenProducerBO_whenCriteriaCreate_thenReturnCreatedProducerBO() throws ServiceException {
        BDDMockito.given(producerCriteriaRepository.createProducer(producer)).willReturn(producer);
        BDDMockito.given(converter.producerEntityToBO(producer)).willReturn(producerBO);
        BDDMockito.given(converter.producerBOToEntity(producerBO)).willReturn(producer);

        ProducerBO result = producerService.criteriaCreate(producerBO);

        assertThat(result).isNotNull().isEqualTo(producerBO);
    }

    @Test
    @DisplayName("Criteria create: nested runtime exception")
    void givenProducerBO_whenCriteriaCreate_thenThrowNestedRuntimeException() {
        BDDMockito.given(producerCriteriaRepository.createProducer(producer))
                .willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.producerBOToEntity(producerBO)).willReturn(producer);

        assertThrows(ServiceException.class, () -> producerService.criteriaCreate(producerBO));
    }

    @Test
    @DisplayName("JPA get by id: correct case")
    void givenId_whenJpaGetById_thenReturnProducerBO() throws ServiceException {
        BDDMockito.given(producerJPARepository.findById(1L)).willReturn(Optional.of(producer));
        BDDMockito.given(converter.producerEntityToBO(producer)).willReturn(producerBO);

        ProducerBO result = producerService.jpaGetById(1L);

        assertThat(result).isNotNull().isEqualTo(producerBO);
    }

    @Test
    @DisplayName("JPA get by id: not found")
    void givenId_whenJpaGetById_thenThrowNotFoundException() {
        BDDMockito.given(producerJPARepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> producerService.jpaGetById(1L));
    }

    @Test
    @DisplayName("JPA delete by id: correct case")
    void givenId_whenJpaDeleteById_thenDeleteProducer() throws ServiceException {
        BDDMockito.willDoNothing().given(producerJPARepository).deleteById(1L);

        producerService.jpaDeleteById(1L);

        verify(producerJPARepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("JPA update: correct case")
    void givenProducerBO_whenJpaUpdate_thenReturnUpdatedProducerBO() throws ServiceException {
        BDDMockito.given(producerJPARepository.existsById(producerBO.getId())).willReturn(true);
        BDDMockito.given(producerJPARepository.save(producer)).willReturn(producer);
        BDDMockito.given(converter.producerEntityToBO(producer)).willReturn(producerBO);
        BDDMockito.given(converter.producerBOToEntity(producerBO)).willReturn(producer);

        ProducerBO result = producerService.jpaUpdate(producerBO);

        assertThat(result).isNotNull().isEqualTo(producerBO);
    }

    @Test
    @DisplayName("JPA update: not found")
    void givenProducerBO_whenJpaUpdate_thenThrowNotFoundException() {
        BDDMockito.given(producerJPARepository.existsById(producerBO.getId())).willReturn(false);

        assertThrows(NotFoundException.class, () -> producerService.jpaUpdate(producerBO));
    }

    @Test
    @DisplayName("JPA create: correct case")
    void givenProducerBO_whenJpaCreate_thenReturnCreatedProducerBO() throws ServiceException {
        BDDMockito.given(producerJPARepository.save(producer)).willReturn(producer);
        BDDMockito.given(converter.producerEntityToBO(producer)).willReturn(producerBO);
        BDDMockito.given(converter.producerBOToEntity(producerBO)).willReturn(producer);

        ProducerBO result = producerService.jpaCreate(producerBO);

        assertThat(result).isNotNull().isEqualTo(producerBO);
    }

    @Test
    @DisplayName("JPA get by id: nested runtime exception")
    void givenId_whenJpaGetById_thenThrowNestedRuntimeException() {
        BDDMockito.given(producerJPARepository.findById(1L)).willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> producerService.jpaGetById(1L));
    }

    @Test
    @DisplayName("JPA get all: nested runtime exception")
    void givenNothing_whenJpaGetAll_thenThrowNestedRuntimeException() {
        BDDMockito.given(producerJPARepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class, () -> producerService.jpaGetAll(0, 10, "name", false));
    }

    @Test
    @DisplayName("JPA get all: correct case")
    void givenNothing_whenJpaGetAll_thenReturnListWithAllProducersBO() throws ServiceException {
        List<Producer> producers = List.of(producer);
        BDDMockito.given(producerJPARepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willReturn(new PageImpl<>(producers));
        BDDMockito.given(converter.producerEntityToBO(producer)).willReturn(producerBO);

        List<ProducerBO> result = producerService.jpaGetAll(0, 10, "name", false);

        assertThat(result).isNotNull().hasSize(1).contains(producerBO);
    }

    @Test
    @DisplayName("JPA get all: no producers found")
    void givenNothing_whenJpaGetAll_thenThrowNotFoundException() {
        BDDMockito.given(producerJPARepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending())))
                .willReturn(new PageImpl<>(List.of()));

        assertThrows(NotFoundException.class, () -> producerService.jpaGetAll(0, 10, "name", false));
    }

    @Test
    @DisplayName("JPA delete by id: nested runtime exception")
    void givenId_whenJpaDeleteById_thenThrowNestedRuntimeException() {
        willThrow(InvalidDataAccessApiUsageException.class).given(producerJPARepository).deleteById(1L);

        assertThrows(ServiceException.class, () -> producerService.jpaDeleteById(1L));
    }

    @Test
    @DisplayName("JPA update: nested runtime exception")
    void givenProducerBO_whenJpaUpdate_thenThrowNestedRuntimeException() {
        BDDMockito.given(producerJPARepository.existsById(producerBO.getId())).willReturn(true);
        BDDMockito.given(producerJPARepository.save(producer)).willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.producerBOToEntity(producerBO)).willReturn(producer);

        assertThrows(ServiceException.class, () -> producerService.jpaUpdate(producerBO));
    }

    @Test
    @DisplayName("JPA create: nested runtime exception")
    void givenProducerBO_whenJpaCreate_thenThrowNestedRuntimeException() {
        BDDMockito.given(producerJPARepository.save(producer)).willThrow(InvalidDataAccessApiUsageException.class);
        BDDMockito.given(converter.producerBOToEntity(producerBO)).willReturn(producer);

        assertThrows(ServiceException.class, () -> producerService.jpaCreate(producerBO));
    }

    @Test
    @DisplayName("Filter producers: correct case")
    void givenFilters_whenFilterProducers_thenReturnListWithFilteredProducersBO() throws ServiceException {

        List<String> names = List.of("Producer 1", "PRV16");
        List<Integer> foundationYears = List.of(2000, 2010);
        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "name";
        boolean sortOrder = true;

        List<Producer> producers = List.of(producer);
        BDDMockito.given(producerCriteriaRepository.filterProducers(names, foundationYears,
                PageRequest.of(pageNumber, pageSize).withSort((Sort.by(sortBy).descending())))).willReturn(producers);
        BDDMockito.given(converter.producerEntityToBO(producer)).willReturn(producerBO);

        List<ProducerBO> result = producerService.filterProducers(names, foundationYears, pageNumber, pageSize, sortBy,
                sortOrder);

        assertThat(result).isNotNull().hasSize(1).contains(producerBO);
    }

    @Test
    @DisplayName("Filter producers: no producers found")
    void givenFilters_whenFilterProducers_thenThrowNotFoundException() {

        List<String> names = List.of("Producer 1", "PRV18");
        List<Integer> foundationYears = List.of(2000, 2010);
        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "name";
        boolean sortOrder = true;

        BDDMockito.given(producerCriteriaRepository.filterProducers(names, foundationYears,
                PageRequest.of(pageNumber, pageSize).withSort((Sort.by(sortBy).descending())))).willReturn(List.of());

        assertThrows(NotFoundException.class,
                () -> producerService.filterProducers(names, foundationYears, pageNumber, pageSize, sortBy, sortOrder));
    }

    @Test
    @DisplayName("Filter producers: nested runtime exception")
    void givenFilters_whenFilterProducers_thenThrowNestedRuntimeException() {

        List<String> names = List.of("Producer 1", "PRV20");
        List<Integer> foundationYears = List.of(2000, 2010);
        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "name";
        boolean sortOrder = true;

        BDDMockito.given(producerCriteriaRepository.filterProducers(names, foundationYears,
                        PageRequest.of(pageNumber, pageSize).withSort((Sort.by(sortBy).descending()))))
                .willThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(ServiceException.class,
                () -> producerService.filterProducers(names, foundationYears, pageNumber, pageSize, sortBy, sortOrder));
    }

}


package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(showSql = false)
@ComponentScan(basePackages = "com.viewnext.films.persistencelayer.repository.criteria")
@Sql(scripts = "/no-data.sql")
class ProducerCriteriaRepositoryTest {

    @Autowired
    ProducerCriteriaRepository producerCriteriaRepository;
    private Producer producer;

    @BeforeEach
    void setup() {
        producer = new Producer();
        producer.setName("Producer 1");
        producer.setFoundationYear(2000);
    }

    @Test
    @DisplayName("Create producer operation")
    void givenProducerObject_whenCreateProducer_thenReturnCreatedProducer() {

        Producer createdProducer = producerCriteriaRepository.createProducer(producer);

        assertThat(createdProducer).isNotNull();
        assertThat(createdProducer.getName()).isEqualTo(producer.getName());
        assertThat(createdProducer.getFoundationYear()).isEqualTo(producer.getFoundationYear());
        assertThat(createdProducer.getId()).isNotZero();

    }

    @Test
    @DisplayName("Get all producers operation")
    void givenNothing_whenGetAllProducers_thenReturnPageWithAllProducers() {

        Producer createdProducer = producerCriteriaRepository.createProducer(producer);

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 producers
        List<Producer> foundProducers = producerCriteriaRepository.getAllProducers(pageable);

        assertThat(foundProducers).isNotNull();
        assertThat(foundProducers.get(0)).isEqualTo(createdProducer);

    }

    @Test
    @DisplayName("Get producer by id operation")
    void givenId_whenGetProducerById_thenReturnFoundProducer() {

        Producer createdProducer = producerCriteriaRepository.createProducer(producer);

        Optional<Producer> foundProducer = producerCriteriaRepository.getProducerById(createdProducer.getId());

        assertThat(foundProducer).isPresent();
        assertThat(foundProducer).contains(createdProducer);
    }

    @Test
    @DisplayName("Update producer operation")
    void givenProducer_whenUpdateProducer_thenReturnUpdatedProducer() {

        Producer createdProducer = producerCriteriaRepository.createProducer(producer);
        createdProducer.setName("Producer 2");
        createdProducer.setFoundationYear(2010);

        Producer updatedProducer = producerCriteriaRepository.updateProducer(createdProducer);

        assertThat(updatedProducer).isNotNull();
        assertThat(updatedProducer.getId()).isEqualTo(createdProducer.getId());
        assertThat(updatedProducer.getName()).isEqualTo("Producer 2");
        assertThat(updatedProducer.getFoundationYear()).isEqualTo(2010);
    }

    @Test
    @DisplayName("Delete producer operation")
    void givenId_whenDeleteProducer_thenDeleteProducer() {

        Producer createdProducer = producerCriteriaRepository.createProducer(producer);

        producerCriteriaRepository.deleteProducer(createdProducer.getId());
        Optional<Producer> foundProducer = producerCriteriaRepository.getProducerById(createdProducer.getId());

        assertThat(foundProducer).isEmpty();

    }

    @Test
    @DisplayName("Filter producers by name")
    void givenName_whenFilterProducers_thenReturnFilteredProducers() {
        // Arrange
        Producer createdProducer = producerCriteriaRepository.createProducer(producer);
        List<String> names = List.of("Producer 1");
        List<Integer> foundationYears = null;
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Producer> filteredProducers = producerCriteriaRepository.filterProducers(names, foundationYears, pageable);

        // Assert
        assertThat(filteredProducers).isNotNull();
        assertEquals(1, filteredProducers.size());
        assertThat(filteredProducers.get(0).getName()).isEqualTo("Producer 1");
    }

    @Test
    @DisplayName("Filter producers by foundation year")
    void givenFoundationYear_whenFilterProducers_thenReturnFilteredProducers() {
        // Arrange
        Producer createdProducer = producerCriteriaRepository.createProducer(producer);
        List<String> names = null;
        List<Integer> foundationYears = List.of(2000);
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Producer> filteredProducers = producerCriteriaRepository.filterProducers(names, foundationYears, pageable);

        // Assert
        assertThat(filteredProducers).isNotNull();
        assertEquals(1, filteredProducers.size());
        assertThat(filteredProducers.get(0).getFoundationYear()).isEqualTo(2000);
    }

    @Test
    @DisplayName("Filter producers by multiple criteria")
    void givenMultipleCriteria_whenFilterProducers_thenReturnFilteredProducers() {
        // Arrange
        Producer createdProducer = producerCriteriaRepository.createProducer(producer);
        List<String> names = List.of("Producer 1");
        List<Integer> foundationYears = List.of(2000);
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Producer> filteredProducers = producerCriteriaRepository.filterProducers(names, foundationYears, pageable);

        // Assert
        assertThat(filteredProducers).isNotNull();
        assertEquals(1, filteredProducers.size());
        assertThat(filteredProducers.get(0).getName()).isEqualTo("Producer 1");
        assertThat(filteredProducers.get(0).getFoundationYear()).isEqualTo(2000);
    }

    @Test
    @DisplayName("Filter producers with no criteria")
    void givenNoCriteria_whenFilterProducers_thenReturnAllProducers() {
        // Arrange
        Producer createdProducer = producerCriteriaRepository.createProducer(producer);
        List<String> names = null;
        List<Integer> foundationYears = null;
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        List<Producer> filteredProducers = producerCriteriaRepository.filterProducers(names, foundationYears, pageable);

        // Assert
        assertThat(filteredProducers).isNotNull();
        assertEquals(1, filteredProducers.size());
    }

}

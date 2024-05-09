package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        assertThat(createdProducer.getId()).isNotNull();

    }

    @Test
    @DisplayName("Get all producers operation")
    void givenNothing_whenGetAllProducers_thenReturnListWithAllProducers() {

        Producer createdProducer = producerCriteriaRepository.createProducer(producer);

        List<Producer> foundProducers = producerCriteriaRepository.getAllProducers();

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
}

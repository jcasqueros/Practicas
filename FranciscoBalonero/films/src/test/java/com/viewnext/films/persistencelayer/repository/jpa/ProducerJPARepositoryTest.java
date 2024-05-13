package com.viewnext.films.persistencelayer.repository.jpa;

import com.viewnext.films.persistencelayer.entity.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@ComponentScan(basePackages = "com.OGR23.films.persistencelayer.repository.jpa")
@Sql(scripts = "/no-data.sql")
class ProducerJPARepositoryTest {
    @Autowired
    ProducerJPARepository producerJPARepository;
    private Producer producer;

    @BeforeEach
    void setup() {
        producer = new Producer();
        producer.setName("Producer 1");
        producer.setFoundationYear(2000);
    }

    @Test
    @DisplayName("Save producer operation")
    void givenProducerObject_whenSaveProducer_thenReturnSavedProducer() {

        Producer savedProducer = producerJPARepository.save(producer);

        assertThat(savedProducer).isNotNull();
        assertThat(savedProducer.getName()).isEqualTo(producer.getName());
        assertThat(savedProducer.getFoundationYear()).isEqualTo(producer.getFoundationYear());
        assertThat(savedProducer.getId()).isNotNull();

    }

    @Test
    @DisplayName("Find producer by id operation")
    void givenId_whenFindProducerById_thenReturnFoundProducer() {

        Producer savedProducer = producerJPARepository.save(producer);

        Optional<Producer> foundProducer = producerJPARepository.findById(savedProducer.getId());

        assertThat(foundProducer).isPresent();
        assertThat(foundProducer).contains(savedProducer);
    }

    @Test
    @DisplayName("Find all producers operation")
    void givenNothing_whenFindAllProducers_thenReturnPageWithAllProducers() {

        Producer savedProducer = producerJPARepository.save(producer);

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 producers
        Page<Producer> foundProducersPage = producerJPARepository.findAll(pageable);

        assertThat(foundProducersPage).isNotNull();
        assertThat(foundProducersPage.getContent().get(0)).isEqualTo(savedProducer);

    }

    @Test
    @DisplayName("Delete producer by id operation")
    void givenId_whenDeleteProducerById_thenDeleteProducer() {

        Producer savedProducer = producerJPARepository.save(producer);

        producerJPARepository.deleteById(savedProducer.getId());
        Optional<Producer> foundProducer = producerJPARepository.findById(savedProducer.getId());

        assertThat(foundProducer).isEmpty();

    }
}

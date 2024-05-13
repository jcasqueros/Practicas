package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Producer;
import com.pracs.films.persistence.repositories.criteria.impl.ProducerRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ComponentScan("com.pracs.films.persistence.repositories.criteria")
class ProducerRepositoryImplTest {

    @Autowired
    private ProducerRepositoryImpl producerRepository;

    private Producer producer;

    @BeforeEach
    void setup() {

        producer = new Producer();
        producer.setName("prueba");
        producer.setDebut(2020);
    }

    @DisplayName("JUnit test for save a producer")
    @Test
    void givenProducerObject_whenSaveProducer_thenReturnProducerObject() {

        Producer savedProducer = producerRepository.saveProducer(producer);

        assertEquals(producer, savedProducer);
    }

    @DisplayName("JUnit test for update a producer")
    @Test
    void givenProducerObject_whenUpdateProducer_thenReturnProducerObject() {

        producerRepository.saveProducer(producer);

        producer.setName("update");
        producer.setDebut(2000);

        producerRepository.updateProducer(producer);

        Optional<Producer> savedProducer = producerRepository.findProducerById(producer.getId());

        assertEquals("update", savedProducer.get().getName());
        assertEquals(2000, savedProducer.get().getDebut());
    }

    @DisplayName("JUnit test for get a producer by his id")
    @Test
    void givenProducerId_whenFindProducerById_thenReturnProducerObject() {

        Optional<Producer> savedProducer = producerRepository.findProducerById(1L);

        assertEquals(1L, savedProducer.get().getId());
    }

    @DisplayName("JUnit test for get all producers")
    @Test
    void givenNothing_whenFindAllProducer_thenReturnProducerList() {

        Page<Producer> savedProducers = producerRepository.findAllProducer(
                PageRequest.of(0, 5, Sort.by("name").ascending()));

        assertEquals(5, savedProducers.getNumberOfElements());
    }

    @DisplayName("JUnit test for get all actors filtered")
    @Test
    void givenPageableAndAttributesList_whenFindAllActorFilter_thenReturnActorList() {

        Page<Producer> savedProducer = producerRepository.findAllFilter(
                PageRequest.of(0, 5, Sort.by("name").ascending()), List.of(), List.of(2020));

        assertEquals(5, savedProducer.getNumberOfElements());
    }

    @DisplayName("JUnit test for delete a producer")
    @Test
    void givenProducerId_whenDeleteProducerByid_thenDelete() {

        producerRepository.deleteProducerById(producer);

        Optional<Producer> foundProducer = producerRepository.findProducerById(producer.getId());

        assertThat(foundProducer).isEmpty();
    }
}
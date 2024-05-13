package com.viewnext.films.businesslayer.service.impl;

import com.viewnext.films.businesslayer.bo.ProducerBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.ProducerService;
import com.viewnext.films.persistencelayer.entity.Producer;
import com.viewnext.films.persistencelayer.repository.criteria.ProducerCriteriaRepository;
import com.viewnext.films.persistencelayer.repository.jpa.ProducerJPARepository;
import com.viewnext.films.util.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing producers.
 *
 * <p>This class provides implementations for the methods declared in the {@link ProducerService} interface.</p>
 *
 * <p>It uses the {@link ProducerCriteriaRepository} and {@link ProducerJPARepository} to interact with the persistence
 * layer, and the {@link Converter} to convert between entity and business objects.</p>
 *
 * <p>It also handles exceptions and provides meaningful error messages.</p>
 *
 * @author Francisco Balonero Olivera
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerServiceImpl implements ProducerService {

    // Inyección de dependencias
    /**
     * The producer criteria repository.
     */
    private final ProducerCriteriaRepository producerCriteriaRepository;
    /**
     * The producer JPA repository.
     */
    private final ProducerJPARepository producerJPARepository;
    /**
     * The converter for converting between entity and business objects.
     */
    private final Converter converter;

    // Métodos para interactuar con la capa de persistencia utilizando Criteria API
    @Override
    public ProducerBO criteriaGetById(long id) throws ServiceException {
        try {
            // Busca un producer por ID utilizando Criteria API
            return converter.producerEntityToBO(
                    producerCriteriaRepository.getProducerById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching producer by id: {}", id, e);
            throw new ServiceException("The producer could not be searched", e);
        }
    }

    @Override
    public List<ProducerBO> criteriaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder)
            throws ServiceException {
        try {
            // Crea un objeto Pageable con la información de paginación y ordenación
            Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber)
                    .withSort((sortOrder ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
            // Busca todos los produceres utilizando Criteria API
            List<Producer> producers = producerCriteriaRepository.getAllProducers(pageable);
            if (!producers.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return producers.stream().map(converter::producerEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching all producers", e);
            throw new ServiceException("The producers could not be searched", e);
        }
    }

    @Override
    public void criteriaDeleteById(Long id) throws ServiceException {
        try {
            // Elimina un producer por ID utilizando Criteria API
            producerCriteriaRepository.deleteProducer(id);
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error deleting producer by id: {}", id, e);
            throw new ServiceException("The producer could not be deleted", e);
        }
    }

    @Override
    public ProducerBO criteriaUpdate(ProducerBO producerBO) throws ServiceException {
        // Verifica si el producer existe antes de actualizarlo
        criteriaGetById(producerBO.getId());
        try {
            // Actualiza un producer utilizando Criteria API
            return converter.producerEntityToBO(
                    producerCriteriaRepository.updateProducer(converter.producerBOToEntity(producerBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error updating producer: {}", producerBO, e);
            throw new ServiceException("The producer could not be updated", e);
        }
    }

    @Override
    public ProducerBO criteriaCreate(ProducerBO producerBO) throws ServiceException {
        try {
            // Crea un producer utilizando Criteria API
            return converter.producerEntityToBO(
                    producerCriteriaRepository.createProducer(converter.producerBOToEntity(producerBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error creating producer: {}", producerBO, e);
            throw new ServiceException("The producer could not be created", e);
        }
    }

    // Métodos para interactuar con la capa de persistencia utilizando JPA
    @Override
    public ProducerBO jpaGetById(long id) throws ServiceException {
        try {
            // Busca un producer por ID utilizando JPA
            return converter.producerEntityToBO(producerJPARepository.findById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching producer by id: {}", id, e);
            throw new ServiceException("The producer could not be searched", e);
        }
    }

    @Override
    public List<ProducerBO> jpaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder)
            throws ServiceException {
        try {
            // Crea un objeto Pageable con la información de paginación y ordenación
            Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber)
                    .withSort((sortOrder ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
            // Busca todos los produceres utilizando JPA
            List<Producer> producers = producerJPARepository.findAll(pageable).getContent();
            if (!producers.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return producers.stream().map(converter::producerEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching all producers", e);
            throw new ServiceException("The producers could not be searched", e);
        }
    }

    @Override
    public void jpaDeleteById(Long id) throws ServiceException {
        try {
            // Elimina un producer por ID utilizando JPA
            producerJPARepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error deleting producer by id: {}", id, e);
            throw new ServiceException("The producer could not be deleted", e);
        }
    }

    @Override
    public ProducerBO jpaUpdate(ProducerBO producerBO) throws ServiceException {
        try {
            // Verifica si el producer existe antes de actualizarlo
            if (producerJPARepository.existsById(producerBO.getId())) {
                // Actualiza un producer utilizando JPA
                return converter.producerEntityToBO(
                        producerJPARepository.save(converter.producerBOToEntity(producerBO)));
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error updating producer: {}", producerBO, e);
            throw new ServiceException("The producer could not be updated", e);
        }
    }

    @Override
    public ProducerBO jpaCreate(ProducerBO producerBO) throws ServiceException {
        try {
            // Crea un producer utilizando JPA
            return converter.producerEntityToBO(producerJPARepository.save(converter.producerBOToEntity(producerBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error creating producer: {}", producerBO, e);
            throw new ServiceException("The producer could not be created", e);
        }
    }
}

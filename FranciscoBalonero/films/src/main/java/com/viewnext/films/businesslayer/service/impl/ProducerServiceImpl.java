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
@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

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

    @Override
    public ProducerBO criteriaGetById(long id) throws ServiceException {
        try {
            return converter.producerEntityToBO(
                    producerCriteriaRepository.getProducerById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            log.error("Error searching producer by id: {}", id, e);
            throw new ServiceException("The producer could not be searched", e);
        }
    }

    @Override
    public List<ProducerBO> criteriaGetAll() throws ServiceException {
        try {
            List<Producer> producers = producerCriteriaRepository.getAllProducers();
            if (!producers.isEmpty()) {
                return producers.stream().map(converter::producerEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            log.error("Error searching all producers", e);
            throw new ServiceException("The producers could not be searched", e);
        }
    }

    @Override
    public void criteriaDeleteById(Long id) throws ServiceException {
        try {
            producerCriteriaRepository.deleteProducer(id);
        } catch (NestedRuntimeException e) {
            log.error("Error deleting producer by id: {}", id, e);
            throw new ServiceException("The producer could not be deleted", e);
        }
    }

    @Override
    public ProducerBO criteriaUpdate(ProducerBO producerBO) throws ServiceException {
        criteriaGetById(producerBO.getId());
        try {
            return converter.producerEntityToBO(
                    producerCriteriaRepository.updateProducer(converter.producerBOToEntity(producerBO)));
        } catch (NestedRuntimeException e) {
            log.error("Error updating producer: {}", producerBO, e);
            throw new ServiceException("The producer could not be updated", e);
        }
    }

    @Override
    public ProducerBO criteriaCreate(ProducerBO producerBO) throws ServiceException {
        try {
            return converter.producerEntityToBO(
                    producerCriteriaRepository.createProducer(converter.producerBOToEntity(producerBO)));
        } catch (NestedRuntimeException e) {
            log.error("Error creating producer: {}", producerBO, e);
            throw new ServiceException("The producer could not be created", e);
        }
    }

    @Override
    public ProducerBO jpaGetById(long id) throws ServiceException {
        try {
            return converter.producerEntityToBO(producerJPARepository.findById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            log.error("Error searching producer by id: {}", id, e);
            throw new ServiceException("The producer could not be searched", e);
        }
    }

    @Override
    public List<ProducerBO> jpaGetAll() throws ServiceException {
        try {
            List<Producer> producers = producerJPARepository.findAll();
            if (!producers.isEmpty()) {
                return producers.stream().map(converter::producerEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            log.error("Error searching all producers", e);
            throw new ServiceException("The producers could not be searched", e);
        }
    }

    @Override
    public void jpaDeleteById(Long id) throws ServiceException {
        try {
            producerJPARepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            log.error("Error deleting producer by id: {}", id, e);
            throw new ServiceException("The producer could not be deleted", e);
        }
    }

    @Override
    public ProducerBO jpaUpdate(ProducerBO producerBO) throws ServiceException {
        try {
            if (producerJPARepository.existsById(producerBO.getId())) {
                return converter.producerEntityToBO(
                        producerJPARepository.save(converter.producerBOToEntity(producerBO)));
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            log.error("Error updating producer: {}", producerBO, e);
            throw new ServiceException("The producer could not be updated", e);
        }
    }

    @Override
    public ProducerBO jpaCreate(ProducerBO producerBO) throws ServiceException {
        try {
            return converter.producerEntityToBO(producerJPARepository.save(converter.producerBOToEntity(producerBO)));
        } catch (NestedRuntimeException e) {
            log.error("Error creating producer: {}", producerBO, e);
            throw new ServiceException("The producer could not be created", e);
        }
    }
}

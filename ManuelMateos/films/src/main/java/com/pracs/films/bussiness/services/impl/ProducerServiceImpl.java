package com.pracs.films.bussiness.service.impl;

import com.pracs.films.bussiness.bo.ProducerBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.service.ProducerService;
import com.pracs.films.exceptions.DuplicatedIdException;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.repositories.jpa.ProducerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the interface {@link ProducerService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final ModelToBoConverter modelToBoConverter;

    private final BoToModelConverter boToModelConverter;

    private final ProducerRepository producerRepository;

    private static final String errorProducer = "Producer not found";

    private static final String errorService = "Error in service layer";

    @Override
    public ProducerBO save(ProducerBO producerBO) throws ServiceException {
        try {

            if (producerRepository.existsById(producerBO.getId())) {
                throw new DuplicatedIdException("Existing production");
            }

            return modelToBoConverter.producerModelToBo(
                    producerRepository.save(boToModelConverter.producerBoToModel(producerBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public ProducerBO update(ProducerBO producerBO) throws ServiceException {
        try {
            ProducerBO savedproducerBO = modelToBoConverter.producerModelToBo(
                    producerRepository.findById(producerBO.getId())
                            .orElseThrow(() -> new EntityNotFoundException(errorProducer)));
            savedproducerBO.setName(producerBO.getName());
            savedproducerBO.setDebut(producerBO.getDebut());
            return modelToBoConverter.producerModelToBo(
                    producerRepository.save(boToModelConverter.producerBoToModel(savedproducerBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public ProducerBO findById(long id) throws ServiceException {
        try {
            return modelToBoConverter.producerModelToBo(
                    producerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorProducer)));
        } catch (NestedRuntimeException e) {
            log.error("Error en la capa de servicio");
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<ProducerBO> findAll() throws ServiceException {
        List<ProducerBO> producersBO = new ArrayList<>();

        try {
            producersBO = producerRepository.findAll().stream().map(modelToBoConverter::producerModelToBo).toList();

            if (producersBO.isEmpty()) {
                throw new EmptyException("No films");
            }

            return producersBO;
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {

            if (!producerRepository.existsById(id)) {
                log.error("EntityNotFoundException");
                throw new EntityNotFoundException(errorProducer);
            }

            producerRepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }
}

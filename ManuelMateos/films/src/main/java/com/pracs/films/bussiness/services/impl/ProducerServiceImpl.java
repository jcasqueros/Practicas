package com.pracs.films.bussiness.services.impl;

import com.pracs.films.bussiness.bo.ProducerBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.ProducerService;
import com.pracs.films.exceptions.DuplicatedIdException;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Producer;
import com.pracs.films.persistence.repositories.criteria.impl.ProducerRepositoryImpl;
import com.pracs.films.persistence.repositories.jpa.ProducerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    private final ProducerRepositoryImpl producerRepositoryCriteria;

    private static final String errorProducer = "Producer not found";

    private static final String errorService = "Error in service layer";

    @Override
    public ProducerBO save(ProducerBO producerBO) throws ServiceException {
        try {
            //Comprobar si existe ya un productor registrado con el mismo id.
            if (producerRepository.existsById(producerBO.getId())) {
                throw new DuplicatedIdException("Existing production");
            }

            // Conversión de model a bo del resultado de crear un productor.
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
            // Búsqueda de un productor con el id introducido para comprobar que existe
            ProducerBO savedproducerBO = modelToBoConverter.producerModelToBo(
                    producerRepository.findById(producerBO.getId())
                            .orElseThrow(() -> new EntityNotFoundException(errorProducer)));

            //Actualización con los campos introducidos
            savedproducerBO.setName(producerBO.getName());
            savedproducerBO.setDebut(producerBO.getDebut());

            // Conversion de model a bo del resultado de guardar un productor
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
            //Comprobar si existe ya un productor registrado con el mismo id.
            return modelToBoConverter.producerModelToBo(
                    producerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorProducer)));
        } catch (NestedRuntimeException e) {
            log.error("Error en la capa de servicio");
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<ProducerBO> findAll(Pageable pageable) throws ServiceException {
        try {
            //Búsqueda de los todos lo productors, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            Page<Producer> filmPage = producerRepository.findAll(pageable);
            List<ProducerBO> prodoucerBOList = filmPage.stream().map(modelToBoConverter::producerModelToBo).toList();

            Page<ProducerBO> producerBOPage = new PageImpl<>(prodoucerBOList, filmPage.getPageable(),
                    filmPage.getTotalPages());

            if (prodoucerBOList.isEmpty()) {
                throw new EmptyException("No producers");
            }

            return producerBOPage;
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {
            //Comprobar si el productor no existe
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

    @Override
    public ProducerBO saveCriteria(ProducerBO producerBO) throws ServiceException {
        try {
            //Comprobar si existe ya un productor registrado con el mismo id.
            if (!producerRepositoryCriteria.findProducerById(producerBO.getId()).isEmpty()) {
                throw new DuplicatedIdException("Existing production");
            }

            // Conversión de model a bo del resultado de crear un productor.
            return modelToBoConverter.producerModelToBo(
                    producerRepositoryCriteria.saveProducer(boToModelConverter.producerBoToModel(producerBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public ProducerBO updateCriteria(ProducerBO producerBO) throws ServiceException {
        try {
            // Búsqueda de un productor con el id introducido para comprobar que existe
            ProducerBO savedproducerBO = modelToBoConverter.producerModelToBo(
                    producerRepositoryCriteria.findProducerById(producerBO.getId())
                            .orElseThrow(() -> new EntityNotFoundException(errorProducer)));

            //Actualización con los campos introducidos
            savedproducerBO.setName(producerBO.getName());
            savedproducerBO.setDebut(producerBO.getDebut());

            // Conversion de model a bo del resultado de guardar un productor
            return modelToBoConverter.producerModelToBo(
                    producerRepositoryCriteria.updateProducer(boToModelConverter.producerBoToModel(savedproducerBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public ProducerBO findByIdCriteria(long id) throws ServiceException {
        try {
            // Conversión de model a bo del resultado de buscar un productor por id.
            return modelToBoConverter.producerModelToBo(producerRepositoryCriteria.findProducerById(id)
                    .orElseThrow(() -> new EntityNotFoundException(errorProducer)));
        } catch (NestedRuntimeException e) {
            log.error("Error en la capa de servicio");
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<ProducerBO> findAllCriteria(Pageable pageable) throws ServiceException {
        try {
            //Búsqueda de los todos lo productors, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            Page<Producer> filmPage = producerRepository.findAll(pageable);
            List<ProducerBO> prodoucerBOList = filmPage.stream().map(modelToBoConverter::producerModelToBo).toList();

            Page<ProducerBO> producerBOPage = new PageImpl<>(prodoucerBOList, filmPage.getPageable(),
                    filmPage.getTotalPages());

            if (prodoucerBOList.isEmpty()) {
                throw new EmptyException("No producers");
            }

            return producerBOPage;
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteByIdCriteria(long id) throws ServiceException {
        try {
            //Comprobar si existe el productor con el id pasado
            if (producerRepositoryCriteria.findProducerById(id).isEmpty()) {
                log.error("EntityNotFoundException");
                throw new EntityNotFoundException(errorProducer);
            }

            producerRepositoryCriteria.deleteProducerById(producerRepositoryCriteria.findProducerById(id).get());
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }
}

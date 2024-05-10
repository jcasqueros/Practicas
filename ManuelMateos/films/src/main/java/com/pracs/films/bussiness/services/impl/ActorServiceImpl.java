package com.pracs.films.bussiness.services.impl;

import com.pracs.films.bussiness.bo.ActorBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.ActorService;
import com.pracs.films.exceptions.DuplicatedIdException;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.repositories.criteria.impl.ActorRepositoryImpl;
import com.pracs.films.persistence.repositories.jpa.ActorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the interface {@link ActorService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ModelToBoConverter modelToBoConverter;

    private final BoToModelConverter boToModelConverter;

    private final ActorRepository actorRepository;

    private final ActorRepositoryImpl actorRepositoryCriteria;

    private static final String errorPerson = "Person not found";

    private static final String errorService = "Error in service layer";

    @Override
    public ActorBO save(ActorBO actorBO) throws ServiceException {
        try {

            //Comprobar si existe ya un actor registrado con el mismo id.
            if (actorRepository.existsById(actorBO.getId())) {
                throw new DuplicatedIdException("Existing person");
            }

            // Conversión de model a bo del resultado de crear un actor.
            return modelToBoConverter.actorModelToBo(actorRepository.save(boToModelConverter.actorBoToModel(actorBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public ActorBO update(ActorBO actorBO) throws ServiceException {
        try {
            // Búsqueda de un actor con el id introducido para comprobar que existe
            ActorBO savedActorBO = modelToBoConverter.actorModelToBo(actorRepository.findById(actorBO.getId())
                    .orElseThrow(() -> new EntityNotFoundException(errorPerson)));

            //Actualización con los campos introducidos
            savedActorBO.setName(actorBO.getName());
            savedActorBO.setAge(actorBO.getAge());
            savedActorBO.setNationality(actorBO.getNationality());

            // Conversion de model a bo del resultado de guardar un actor
            return modelToBoConverter.actorModelToBo(
                    actorRepository.save(boToModelConverter.actorBoToModel(savedActorBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public ActorBO findById(long id) throws ServiceException {
        try {
            // Conversión de model a bo del resultado de buscar un actor por id.
            return modelToBoConverter.actorModelToBo(
                    actorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorPerson)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<ActorBO> findAll(Pageable pageable) throws ServiceException {
        try {
            //Búsqueda de los todos lo actors, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            Page<Actor> actorPage = actorRepository.findAll(pageable);
            List<ActorBO> actorBOList = actorPage.stream().map(modelToBoConverter::actorModelToBo).toList();

            Page<ActorBO> actorBOPage = new PageImpl<>(actorBOList, actorPage.getPageable(), actorPage.getTotalPages());

            if (actorBOList.isEmpty()) {
                throw new EmptyException("No actors");
            }

            return actorBOPage;
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {

            //Comprobar si el actor no existe
            if (!actorRepository.existsById(id)) {
                throw new EntityNotFoundException(errorPerson);
            }

            actorRepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public ActorBO saveCriteria(ActorBO actorBO) throws ServiceException {
        try {
            //Comprobar si existe ya un actor registrado con el mismo id.
            if (!actorRepositoryCriteria.findActorById(actorBO.getId()).isEmpty()) {
                throw new DuplicatedIdException("Existing person");
            }

            // Conversión de model a bo del resultado de crear un actor.
            return modelToBoConverter.actorModelToBo(
                    actorRepositoryCriteria.saveActor(boToModelConverter.actorBoToModel(actorBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public ActorBO updateCriteria(ActorBO actorBO) throws ServiceException {
        try {
            // Búsqueda de un actor con el id introducido para comprobar que existe
            ActorBO savedActorBO = modelToBoConverter.actorModelToBo(
                    actorRepositoryCriteria.findActorById(actorBO.getId())
                            .orElseThrow(() -> new EntityNotFoundException(errorPerson)));
            //Actualización con los campos introducidos
            savedActorBO.setName(actorBO.getName());
            savedActorBO.setAge(actorBO.getAge());
            savedActorBO.setNationality(actorBO.getNationality());

            // Conversion de model a bo del resultado de guardar un actor
            return modelToBoConverter.actorModelToBo(
                    actorRepositoryCriteria.updateActor(boToModelConverter.actorBoToModel(savedActorBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public ActorBO findByIdCriteria(long id) throws ServiceException {
        try {
            // Conversión de model a bo del resultado de buscar un actor por id.
            return modelToBoConverter.actorModelToBo(actorRepositoryCriteria.findActorById(id)
                    .orElseThrow(() -> new EntityNotFoundException(errorPerson)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<ActorBO> findAllCriteria() throws ServiceException {
        List<ActorBO> actorsBO = new ArrayList<>();

        try {
            //Búsqueda de los todos lo actors, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            actorsBO = actorRepositoryCriteria.findAllActor().stream().map(modelToBoConverter::actorModelToBo).toList();

            if (actorsBO.isEmpty()) {
                throw new EmptyException("No actors");
            }

            return actorsBO;
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteByIdCriteria(long id) throws ServiceException {
        try {
            //Comprobar si existe el actor con el id pasado
            if (actorRepositoryCriteria.findActorById(id).isEmpty()) {
                log.error("EntityNotFoundException");
                throw new EntityNotFoundException(errorPerson);
            }
            actorRepositoryCriteria.deleteActorById(actorRepositoryCriteria.findActorById(id).get());
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }
}

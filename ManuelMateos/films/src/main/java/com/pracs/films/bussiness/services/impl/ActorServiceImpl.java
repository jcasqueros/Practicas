package com.pracs.films.bussiness.services.impl;

import com.pracs.films.bussiness.bo.ActorBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.ActorService;
import com.pracs.films.exceptions.DuplicatedIdException;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.repositories.jpa.ActorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
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

    private static final String errorPerson = "Person not found";

    private static final String errorService = "Error in service layer";

    @Override
    public ActorBO save(ActorBO actorBO) throws ServiceException {
        try {

            if (actorRepository.existsById(actorBO.getId())) {
                throw new DuplicatedIdException("Existing person");
            }

            return modelToBoConverter.actorModelToBo(actorRepository.save(boToModelConverter.actorBoToModel(actorBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public ActorBO update(ActorBO actorBO) throws ServiceException {
        try {
            ActorBO savedActorBO = modelToBoConverter.actorModelToBo(actorRepository.findById(actorBO.getId())
                    .orElseThrow(() -> new EntityNotFoundException(errorPerson)));
            savedActorBO.setName(actorBO.getName());
            savedActorBO.setAge(actorBO.getAge());
            savedActorBO.setNationality(actorBO.getNationality());
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
            return modelToBoConverter.actorModelToBo(
                    actorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorPerson)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<ActorBO> findAll() throws ServiceException {
        List<ActorBO> actorsBO = new ArrayList<>();

        try {
            actorsBO = actorRepository.findAll().stream().map(modelToBoConverter::actorModelToBo).toList();

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
    public void deleteById(long id) throws ServiceException {
        try {

            if (!actorRepository.existsById(id)) {
                throw new EntityNotFoundException(errorPerson);
            }

            actorRepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }
}

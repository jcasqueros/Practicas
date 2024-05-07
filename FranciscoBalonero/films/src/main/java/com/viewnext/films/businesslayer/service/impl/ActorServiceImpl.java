package com.viewnext.films.businesslayer.service.impl;

import com.viewnext.films.businesslayer.bo.ActorBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.ActorService;
import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.repository.criteria.ActorCriteriaRepository;
import com.viewnext.films.persistencelayer.repository.jpa.ActorJPARepository;
import com.viewnext.films.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing actors.
 *
 * <p>This class provides implementations for the methods declared in the {@link ActorService} interface.</p>
 *
 * <p>It uses the {@link ActorCriteriaRepository} and {@link ActorJPARepository} to interact with the persistence
 * layer, and the {@link Converter} to convert between entity and business objects.</p>
 *
 * <p>It also handles exceptions and provides meaningful error messages.</p>
 *
 * @author Francisco Balonero Olivera
 */
@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    /**
     * The actor criteria repository.
     */
    private final ActorCriteriaRepository actorCriteriaRepository;

    /**
     * The actor JPA repository.
     */
    private final ActorJPARepository actorJPARepository;

    /**
     * The converter for converting between entity and business objects.
     */
    private final Converter converter;

    @Override
    public ActorBO criteriaGetById(long id) throws ServiceException {
        try {
            return converter.actorEntityToBO(
                    actorCriteriaRepository.getActorById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The actor could not be searched", e);
        }

    }

    @Override
    public List<ActorBO> criteriaGetAll() throws ServiceException {
        try {
            List<Actor> actors = actorCriteriaRepository.getAllActors();
            if (!actors.isEmpty()) {
                return actors.stream().map(converter::actorEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }

        } catch (NestedRuntimeException e) {
            throw new ServiceException("The actors could not be searched", e);
        }

    }

    @Override
    public void criteriaDeleteById(Long id) throws ServiceException {
        try {
            actorCriteriaRepository.deleteActor(id);
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The actor could not be deleted", e);
        }
    }

    @Override
    public ActorBO criteriaUpdate(ActorBO actorBO) throws ServiceException {
        criteriaGetById(actorBO.getId());
        try {
            return converter.actorEntityToBO(actorCriteriaRepository.updateActor(converter.actorBOToEntity(actorBO)));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The actor could not be updated", e);
        }
    }

    @Override
    public ActorBO criteriaCreate(ActorBO actorBO) throws ServiceException {
        try {
            return converter.actorEntityToBO(actorCriteriaRepository.createActor(converter.actorBOToEntity(actorBO)));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The actor could not be created", e);
        }

    }

    @Override
    public ActorBO jpaGetById(long id) throws ServiceException {
        try {
            return converter.actorEntityToBO(actorJPARepository.findById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The actor could not be searched", e);
        }

    }

    @Override
    public List<ActorBO> jpaGetAll() throws ServiceException {
        try {
            List<Actor> actors = actorJPARepository.findAll();
            if (!actors.isEmpty()) {
                return actors.stream().map(converter::actorEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The actors could not be searched", e);
        }
    }

    @Override
    public void jpaDeleteById(Long id) throws ServiceException {
        try {
            actorJPARepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The actor could not be deleted", e);
        }
    }

    @Override
    public ActorBO jpaUpdate(ActorBO actorBO) throws ServiceException {
        try {
            if (actorJPARepository.existsById(actorBO.getId())) {
                return converter.actorEntityToBO(actorJPARepository.save(converter.actorBOToEntity(actorBO)));
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The actor could not be updated", e);
        }

    }

    @Override
    public ActorBO jpaCreate(ActorBO actorBO) throws ServiceException {
        try {
            return converter.actorEntityToBO(actorJPARepository.save(converter.actorBOToEntity(actorBO)));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The actor could not be created", e);
        }
    }

}

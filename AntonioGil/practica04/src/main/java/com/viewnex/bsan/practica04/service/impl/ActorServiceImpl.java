package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.entity.Actor;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnex.bsan.practica04.repository.ActorRepository;
import com.viewnex.bsan.practica04.service.ActorService;
import com.viewnex.bsan.practica04.util.constants.LogMessages;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelActorMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code ActorService} interface.
 *
 * @author Antonio Gil
 */
@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository repository;
    private final ServiceLevelActorMapper mapper;

    public ActorServiceImpl(ActorRepository repository, ServiceLevelActorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ActorBo> getAll() {
        return repository.findAll().stream().map(mapper::entityToBo).toList();
    }

    @Override
    public List<ActorBo> customGetAll() {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }

    @Override
    public ActorBo getById(long id) {
        Optional<Actor> foundEntity = repository.findById(id);

        if (foundEntity.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.ACTOR_ENTITY_NAME, id)
            );
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public ActorBo customGetById(long id) {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }

    @Override
    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new MissingRequiredFieldException(
                    String.format(LogMessages.REQUIRED_FIELD, "name"), "name"
            );
        }
    }

    @Override
    public void validateAge(int age) {
        if (age < 0) {
            throw new BadInputDataException(
                    String.format(LogMessages.NEGATIVE_NUMBER_NOT_ALLOWED, "age")
            );
        }
    }

    @Override
    public void validateNationality(String nationality) {
        if (nationality == null || nationality.isBlank()) {
            throw new MissingRequiredFieldException(
                    String.format(LogMessages.REQUIRED_FIELD, "nationality"), "nationality"
            );
        }
    }

    @Override
    public void validateActor(ActorBo actor) {
        if (actor == null) {
            throw new MissingRequiredFieldException(
                    String.format(LogMessages.NULL_NOT_ALLOWED, LogMessages.ACTOR_ENTITY_NAME),
                    LogMessages.ACTOR_ENTITY_NAME
            );
        }

        validateName(actor.getName());
        validateAge(actor.getAge());
        validateNationality(actor.getNationality());
    }

    @Override
    public ActorBo create(ActorBo actor) {
        validateActor(actor);

        if (repository.existsById(actor.getId())) {
            throw new DuplicateUniqueFieldException(
                    String.format(LogMessages.RESOURCE_ALREADY_EXISTS, LogMessages.ACTOR_ENTITY_NAME, actor.getId()),
                    "id"
            );
        }

        Actor entityToSave = mapper.boToEntity(actor);
        Actor savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public ActorBo customCreate(ActorBo actor) {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }

    @Override
    public ActorBo update(long id, ActorBo newActor) {
        validateActor(newActor);
        newActor.setId(id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.ACTOR_ENTITY_NAME, id)
            );
        }

        Actor entityToSave = mapper.boToEntity(newActor);
        Actor savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public ActorBo customUpdate(long id, ActorBo newActor) {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.ACTOR_ENTITY_NAME, id)
            );
        }

        repository.deleteById(id);
    }

    @Override
    public void customDeleteById(long id) {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }

}

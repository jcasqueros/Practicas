package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.entity.Actor;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnex.bsan.practica04.repository.ActorRepository;
import com.viewnex.bsan.practica04.repository.custom.CustomActorRepository;
import com.viewnex.bsan.practica04.service.ActorService;
import com.viewnex.bsan.practica04.util.constants.LogMessages;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelActorMapper;
import jakarta.transaction.Transactional;
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
    private final CustomActorRepository customRepository;
    private final ServiceLevelActorMapper mapper;

    public ActorServiceImpl(ActorRepository repository, CustomActorRepository customRepository,
                            ServiceLevelActorMapper mapper) {
        this.repository = repository;
        this.customRepository = customRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ActorBo> getAll() {
        return repository.findAll().stream().map(mapper::entityToBo).toList();
    }

    @Override
    public List<ActorBo> customGetAll() {
        return customRepository.getAll().stream().map(mapper::entityToBo).toList();
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
        Optional<Actor> foundEntity = customRepository.getById(id);

        if (foundEntity.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.ACTOR_ENTITY_NAME, id)
            );
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
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
    @Transactional
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
    @Transactional
    public ActorBo customCreate(ActorBo actor) {
        validateActor(actor);

        if (customRepository.existsById(actor.getId())) {
            throw new DuplicateUniqueFieldException(
                    String.format(LogMessages.RESOURCE_ALREADY_EXISTS, LogMessages.ACTOR_ENTITY_NAME, actor.getId()),
                    "id"
            );
        }

        Actor entityToSave = mapper.boToEntity(actor);
        Actor savedEntity = customRepository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    @Transactional
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
    @Transactional
    public ActorBo customUpdate(long id, ActorBo newActor) {
        validateActor(newActor);
        newActor.setId(id);

        if (!customRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.ACTOR_ENTITY_NAME, id)
            );
        }

        Actor entityToSave = mapper.boToEntity(newActor);
        Actor savedEntity = customRepository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.ACTOR_ENTITY_NAME, id)
            );
        }

        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void customDeleteById(long id) {
        if (!customRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.ACTOR_ENTITY_NAME, id)
            );
        }

        customRepository.deleteById(id);
    }

}

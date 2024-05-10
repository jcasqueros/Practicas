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
import com.viewnex.bsan.practica04.util.MessageBuilder;
import com.viewnex.bsan.practica04.util.constants.Messages;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelActorMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorServiceImpl.class.getCanonicalName());

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
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.ACTOR_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public ActorBo customGetById(long id) {
        Optional<Actor> foundEntity = customRepository.getById(id);

        if (foundEntity.isEmpty()) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.ACTOR_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            String message = MessageBuilder.buildMissingRequiredFieldMessage("name");
            LOGGER.warn(message);
            throw new MissingRequiredFieldException(message, "name");
        }
    }

    @Override
    public void validateAge(int age) {
        if (age < 0) {
            String message = MessageBuilder.negativeNumberNotAllowedMessage("age");
            LOGGER.warn(message);
            throw new BadInputDataException(message);
        }
    }

    @Override
    public void validateNationality(String nationality) {
        if (nationality == null || nationality.isBlank()) {
            String message = MessageBuilder.buildMissingRequiredFieldMessage("nationality");
            LOGGER.warn(message);
            throw new MissingRequiredFieldException(message, "nationality");
        }
    }

    @Override
    public void validateActor(ActorBo actor) {
        if (actor == null) {
            String message = MessageBuilder.buildNullNotAllowedMessage(Messages.ACTOR_ENTITY_NAME);
            LOGGER.warn(message);
            throw new MissingRequiredFieldException(message, Messages.ACTOR_ENTITY_NAME);
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
            String message =
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.ACTOR_ENTITY_NAME, actor.getId());
            LOGGER.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
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
            String message =
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.ACTOR_ENTITY_NAME, actor.getId());
            LOGGER.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
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
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.ACTOR_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
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
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.ACTOR_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        Actor entityToSave = mapper.boToEntity(newActor);
        Actor savedEntity = customRepository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.ACTOR_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void customDeleteById(long id) {
        if (!customRepository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.ACTOR_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        customRepository.deleteById(id);
    }

}

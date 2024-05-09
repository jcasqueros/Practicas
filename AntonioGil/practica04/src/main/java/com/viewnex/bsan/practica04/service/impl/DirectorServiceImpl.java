package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.entity.Director;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnex.bsan.practica04.repository.DirectorRepository;
import com.viewnex.bsan.practica04.repository.custom.CustomDirectorRepository;
import com.viewnex.bsan.practica04.service.DirectorService;
import com.viewnex.bsan.practica04.util.MessageBuilder;
import com.viewnex.bsan.practica04.util.constants.Messages;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelDirectorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code DirectorService} interface.
 *
 * @author Antonio Gil
 */
@Service
public class DirectorServiceImpl implements DirectorService {

    private static final Logger LOGGER = LoggerFactory.getLogger("DirectorService");

    private final DirectorRepository repository;
    private final CustomDirectorRepository customRepository;
    private final ServiceLevelDirectorMapper mapper;

    public DirectorServiceImpl(DirectorRepository repository, CustomDirectorRepository customRepository,
                               ServiceLevelDirectorMapper mapper) {
        this.repository = repository;
        this.customRepository = customRepository;
        this.mapper = mapper;
    }

    @Override
    public List<DirectorBo> getAll() {
        return repository.findAll().stream().map(mapper::entityToBo).toList();
    }

    @Override
    public List<DirectorBo> customGetAll() {
        return customRepository.getAll().stream().map(mapper::entityToBo).toList();
    }

    @Override
    public DirectorBo getById(long id) {
        Optional<Director> foundEntity = repository.findById(id);

        if (foundEntity.isEmpty()) {
            throw new ResourceNotFoundException(
                    MessageBuilder.buildResourceNotFoundMessage(Messages.DIRECTOR_ENTITY_NAME, id)
            );
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public DirectorBo customGetById(long id) {
        Optional<Director> foundEntity = customRepository.getById(id);

        if (foundEntity.isEmpty()) {
            throw new ResourceNotFoundException(
                    MessageBuilder.buildResourceNotFoundMessage(Messages.DIRECTOR_ENTITY_NAME, id)
            );
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new MissingRequiredFieldException(
                    MessageBuilder.buildMissingRequiredFieldMessage("name")
            );
        }
    }

    @Override
    public void validateAge(int age) {
        if (age < 0) {
            throw new BadInputDataException(
                    MessageBuilder.negativeNumberNotAllowedMessage("age")
            );
        }
    }

    @Override
    public void validateNationality(String nationality) {
        if (nationality == null || nationality.isBlank()) {
            throw new MissingRequiredFieldException(
                    MessageBuilder.buildMissingRequiredFieldMessage("nationality")
            );
        }
    }

    @Override
    public void validateDirector(DirectorBo director) {
        if (director == null) {
            throw new MissingRequiredFieldException(
                    MessageBuilder.buildNullNotAllowedMessage(Messages.DIRECTOR_ENTITY_NAME), Messages.DIRECTOR_ENTITY_NAME
            );
        }

        validateName(director.getName());
        validateAge(director.getAge());
        validateNationality(director.getNationality());
    }

    @Override
    public DirectorBo create(DirectorBo director) {
        validateDirector(director);

        if (repository.existsById(director.getId())) {
            throw new DuplicateUniqueFieldException(
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.DIRECTOR_ENTITY_NAME, director.getId()),
                    "id"
            );
        }

        Director entityToSave = mapper.boToEntity(director);
        Director savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public DirectorBo customCreate(DirectorBo director) {
        validateDirector(director);

        if (customRepository.existsById(director.getId())) {
            throw new DuplicateUniqueFieldException(
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.DIRECTOR_ENTITY_NAME, director.getId()),
                    "id"
            );
        }

        Director entityToSave = mapper.boToEntity(director);
        Director savedEntity = customRepository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public DirectorBo update(long id, DirectorBo newDirector) {
        validateDirector(newDirector);
        newDirector.setId(id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    MessageBuilder.buildResourceNotFoundMessage(Messages.DIRECTOR_ENTITY_NAME, id)
            );
        }

        Director entityToSave = mapper.boToEntity(newDirector);
        Director savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public DirectorBo customUpdate(long id, DirectorBo newDirector) {
        validateDirector(newDirector);
        newDirector.setId(id);

        if (!customRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    MessageBuilder.buildResourceNotFoundMessage(Messages.DIRECTOR_ENTITY_NAME, id)
            );
        }

        Director entityToSave = mapper.boToEntity(newDirector);
        Director savedEntity = customRepository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    MessageBuilder.buildResourceNotFoundMessage(Messages.DIRECTOR_ENTITY_NAME, id)
            );
        }

        repository.deleteById(id);
    }

    @Override
    public void customDeleteById(long id) {
        if (!customRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    MessageBuilder.buildResourceNotFoundMessage(Messages.DIRECTOR_ENTITY_NAME, id)
            );
        }

        customRepository.deleteById(id);
    }

}

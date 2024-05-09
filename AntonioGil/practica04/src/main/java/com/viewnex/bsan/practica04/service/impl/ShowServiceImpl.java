package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.bo.ShowBo;
import com.viewnex.bsan.practica04.entity.Show;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnex.bsan.practica04.repository.ShowRepository;
import com.viewnex.bsan.practica04.repository.custom.CustomShowRepository;
import com.viewnex.bsan.practica04.service.ShowService;
import com.viewnex.bsan.practica04.util.MessageBuilder;
import com.viewnex.bsan.practica04.util.constants.Messages;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelShowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code ShowService} interface.
 *
 * @author Antonio Gil
 */
@Service
public class ShowServiceImpl implements ShowService {

    private static final Logger LOGGER = LoggerFactory.getLogger("ShowService");

    private final ShowRepository repository;
    private final CustomShowRepository customRepository;
    private final ServiceLevelShowMapper mapper;

    public ShowServiceImpl(ShowRepository repository, CustomShowRepository customRepository,
                           ServiceLevelShowMapper mapper) {
        this.repository = repository;
        this.customRepository = customRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ShowBo> getAll() {
        return repository.findAll().stream().map(mapper::entityToBo).toList();
    }

    @Override
    public List<ShowBo> customGetAll() {
        return customRepository.getAll().stream().map(mapper::entityToBo).toList();
    }

    @Override
    public ShowBo getById(long id) {
        Optional<Show> foundEntity = repository.findById(id);

        if (foundEntity.isEmpty()) {
            throw new ResourceNotFoundException(

                    MessageBuilder.buildResourceNotFoundMessage(Messages.SHOW_ENTITY_NAME, id)
            );
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public ShowBo customGetById(long id) {
        Optional<Show> foundEntity = customRepository.getById(id);

        if (foundEntity.isEmpty()) {
            throw new ResourceNotFoundException(

                    MessageBuilder.buildResourceNotFoundMessage(Messages.SHOW_ENTITY_NAME, id)
            );
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }


    @Override
    public void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new MissingRequiredFieldException(
                    MessageBuilder.buildMissingRequiredFieldMessage("title"), "title"
            );
        }
    }

    @Override
    public void validateYear(int year) {
        if (year < 0) {
            throw new BadInputDataException(
                    MessageBuilder.negativeNumberNotAllowedMessage("year")
            );
        }
    }

    @Override
    public void validateShow(ShowBo show) {
        if (show == null) {
            throw new MissingRequiredFieldException(
                    MessageBuilder.buildNullNotAllowedMessage(Messages.SHOW_ENTITY_NAME), Messages.SHOW_ENTITY_NAME
            );
        }

        validateTitle(show.getTitle());
        validateYear(show.getYear());
    }

    @Override
    public ShowBo create(ShowBo show) {
        validateShow(show);

        if (repository.existsById(show.getId())) {
            throw new DuplicateUniqueFieldException(
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.SHOW_ENTITY_NAME, show.getId()), "id"
            );
        }

        Show entityToSave = mapper.boToEntity(show);
        Show savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public ShowBo customCreate(ShowBo show) {
        validateShow(show);

        if (customRepository.existsById(show.getId())) {
            throw new DuplicateUniqueFieldException(
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.SHOW_ENTITY_NAME, show.getId()), "id"
            );
        }

        Show entityToSave = mapper.boToEntity(show);
        Show savedEntity = customRepository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public ShowBo update(long id, ShowBo newShow) {
        validateShow(newShow);
        newShow.setId(id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(

                    MessageBuilder.buildResourceNotFoundMessage(Messages.SHOW_ENTITY_NAME, id)
            );
        }

        Show entityToSave = mapper.boToEntity(newShow);
        Show savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public ShowBo customUpdate(long id, ShowBo newShow) {
        validateShow(newShow);
        newShow.setId(id);

        if (!customRepository.existsById(id)) {
            throw new ResourceNotFoundException(

                    MessageBuilder.buildResourceNotFoundMessage(Messages.SHOW_ENTITY_NAME, id)
            );
        }

        Show entityToSave = mapper.boToEntity(newShow);
        Show savedEntity = customRepository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(

                    MessageBuilder.buildResourceNotFoundMessage(Messages.SHOW_ENTITY_NAME, id)
            );
        }

        repository.deleteById(id);
    }

    @Override
    public void customDeleteById(long id) {
        if (!customRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    MessageBuilder.buildResourceNotFoundMessage(Messages.SHOW_ENTITY_NAME, id)
            );
        }

        customRepository.deleteById(id);
    }
}

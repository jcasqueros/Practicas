package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.bo.ShowBo;
import com.viewnex.bsan.practica04.entity.Show;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnex.bsan.practica04.repository.ShowRepository;
import com.viewnex.bsan.practica04.service.ShowService;
import com.viewnex.bsan.practica04.util.constants.LogMessages;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelShowMapper;
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

    private final ShowRepository repository;
    private final ServiceLevelShowMapper mapper;

    public ShowServiceImpl(ShowRepository repository, ServiceLevelShowMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ShowBo> getAll() {
        return repository.findAll().stream().map(mapper::entityToBo).toList();
    }

    @Override
    public List<ShowBo> customGetAll() {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }

    @Override
    public ShowBo getById(long id) {
        Optional<Show> foundEntity = repository.findById(id);

        if (foundEntity.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.SHOW_ENTITY_NAME, id)
            );
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public ShowBo customGetById(long id) {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }


    @Override
    public void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new MissingRequiredFieldException(
                    String.format(LogMessages.REQUIRED_FIELD, "title"), "title"
            );
        }
    }

    @Override
    public void validateYear(int year) {
        if (year < 0) {
            throw new BadInputDataException(
                    String.format(LogMessages.NEGATIVE_NUMBER_NOT_ALLOWED, "year")
            );
        }
    }

    @Override
    public void validateShow(ShowBo show) {
        if (show == null) {
            throw new MissingRequiredFieldException(
                    String.format(LogMessages.NULL_NOT_ALLOWED, LogMessages.SHOW_ENTITY_NAME),
                    LogMessages.SHOW_ENTITY_NAME
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
                    String.format(LogMessages.RESOURCE_ALREADY_EXISTS, LogMessages.SHOW_ENTITY_NAME, show.getId()),
                    "id"
            );
        }

        Show entityToSave = mapper.boToEntity(show);
        Show savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public ShowBo customCreate(ShowBo show) {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }

    @Override
    public ShowBo update(long id, ShowBo newShow) {
        validateShow(newShow);
        newShow.setId(id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.ACTOR_ENTITY_NAME, id)
            );
        }

        Show entityToSave = mapper.boToEntity(newShow);
        Show savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public ShowBo customUpdate(long id, ShowBo newShow) {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.SHOW_ENTITY_NAME, id)
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

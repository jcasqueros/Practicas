package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.bo.FilmBo;
import com.viewnex.bsan.practica04.entity.Film;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnex.bsan.practica04.repository.FilmRepository;
import com.viewnex.bsan.practica04.repository.custom.CustomFilmRepository;
import com.viewnex.bsan.practica04.service.FilmService;
import com.viewnex.bsan.practica04.util.MessageBuilder;
import com.viewnex.bsan.practica04.util.constants.Messages;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelFilmMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code FilmService} interface.
 *
 * @author Antonio Gil
 */
@Service
public class FilmServiceImpl implements FilmService {

    private static final Logger LOGGER = LoggerFactory.getLogger("FilmService");

    private final FilmRepository repository;
    private final CustomFilmRepository customRepository;
    private final ServiceLevelFilmMapper mapper;

    public FilmServiceImpl(FilmRepository repository, CustomFilmRepository customRepository,
                           ServiceLevelFilmMapper mapper) {
        this.repository = repository;
        this.customRepository = customRepository;
        this.mapper = mapper;
    }

    @Override
    public List<FilmBo> getAll() {
        return repository.findAll().stream().map(mapper::entityToBo).toList();
    }

    @Override
    public List<FilmBo> customGetAll() {
        return customRepository.getAll().stream().map(mapper::entityToBo).toList();
    }

    @Override
    public FilmBo getById(long id) {
        Optional<Film> foundEntity = repository.findById(id);

        if (foundEntity.isEmpty()) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.FILM_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public FilmBo customGetById(long id) {
        Optional<Film> foundEntity = customRepository.getById(id);

        if (foundEntity.isEmpty()) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.FILM_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            String message = MessageBuilder.buildMissingRequiredFieldMessage("title");
            LOGGER.warn(message);
            throw new MissingRequiredFieldException(message, "title");
        }
    }

    @Override
    public void validateYear(int year) {
        if (year < 0) {
            String message = MessageBuilder.negativeNumberNotAllowedMessage("year");
            LOGGER.warn(message);
            throw new BadInputDataException(message);
        }
    }

    @Override
    public void validateFilm(FilmBo film) {
        if (film == null) {
            String message = MessageBuilder.buildNullNotAllowedMessage(Messages.FILM_ENTITY_NAME);
            LOGGER.warn(message);
            throw new MissingRequiredFieldException(message, Messages.FILM_ENTITY_NAME);
        }

        validateTitle(film.getTitle());
        validateYear(film.getYear());
    }

    @Override
    public FilmBo create(FilmBo film) {
        if (repository.existsById(film.getId())) {
            String message = MessageBuilder.buildResourceAlreadyExistsMessage(Messages.FILM_ENTITY_NAME, film.getId());
            LOGGER.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        validateFilm(film);

        Film entityToSave = mapper.boToEntity(film);
        Film savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public FilmBo customCreate(FilmBo film) {
        if (customRepository.existsById(film.getId())) {
            String message = MessageBuilder.buildResourceAlreadyExistsMessage(Messages.FILM_ENTITY_NAME, film.getId());
            LOGGER.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        validateFilm(film);

        Film entityToSave = mapper.boToEntity(film);
        Film savedEntity = customRepository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public FilmBo update(long id, FilmBo newFilm) {
        newFilm.setId(id);

        if (!repository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.FILM_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        validateFilm(newFilm);

        Film entityToSave = mapper.boToEntity(newFilm);
        Film savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public FilmBo customUpdate(long id, FilmBo newFilm) {
        newFilm.setId(id);

        if (!customRepository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.FILM_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        validateFilm(newFilm);

        Film entityToSave = mapper.boToEntity(newFilm);
        Film savedEntity = customRepository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.FILM_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        repository.deleteById(id);
    }

    @Override
    public void customDeleteById(long id) {
        if (!customRepository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.FILM_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        customRepository.deleteById(id);
    }
}

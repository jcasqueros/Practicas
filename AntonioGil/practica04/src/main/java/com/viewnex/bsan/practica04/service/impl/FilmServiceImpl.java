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
import com.viewnex.bsan.practica04.util.constants.LogMessages;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelFilmMapper;
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
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.FILM_ENTITY_NAME, id)
            );
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public FilmBo customGetById(long id) {
        Optional<Film> foundEntity = customRepository.getById(id);

        if (foundEntity.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.FILM_ENTITY_NAME, id)
            );
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
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
    public void validateFilm(FilmBo film) {
        if (film == null) {
            throw new MissingRequiredFieldException(
                    String.format(LogMessages.NULL_NOT_ALLOWED, LogMessages.FILM_ENTITY_NAME),
                    LogMessages.FILM_ENTITY_NAME
            );
        }

        validateTitle(film.getTitle());
        validateYear(film.getYear());
    }

    @Override
    public FilmBo create(FilmBo film) {
        if (repository.existsById(film.getId())) {
            throw new DuplicateUniqueFieldException(
                    String.format(LogMessages.RESOURCE_ALREADY_EXISTS, LogMessages.FILM_ENTITY_NAME, film.getId()),
                    "id"
            );
        }

        validateFilm(film);

        Film entityToSave = mapper.boToEntity(film);
        Film savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public FilmBo customCreate(FilmBo film) {
        if (customRepository.existsById(film.getId())) {
            throw new DuplicateUniqueFieldException(
                    String.format(LogMessages.RESOURCE_ALREADY_EXISTS, LogMessages.FILM_ENTITY_NAME, film.getId()),
                    "id"
            );
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
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.FILM_ENTITY_NAME, id)
            );
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
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.FILM_ENTITY_NAME, id)
            );
        }

        validateFilm(newFilm);

        Film entityToSave = mapper.boToEntity(newFilm);
        Film savedEntity = customRepository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.FILM_ENTITY_NAME, id)
            );
        }

        repository.deleteById(id);
    }

    @Override
    public void customDeleteById(long id) {
        if (!customRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.FILM_ENTITY_NAME, id)
            );
        }

        customRepository.deleteById(id);
    }
}

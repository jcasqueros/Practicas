package com.viewnext.bsan.practica04.service.impl;

import com.viewnext.bsan.practica04.bo.FilmBo;
import com.viewnext.bsan.practica04.dto.request.QueryOptionsDto;
import com.viewnext.bsan.practica04.dto.request.WatchableFilterDto;
import com.viewnext.bsan.practica04.entity.Film;
import com.viewnext.bsan.practica04.exception.service.BadInputDataException;
import com.viewnext.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnext.bsan.practica04.repository.FilmRepository;
import com.viewnext.bsan.practica04.repository.custom.CustomFilmRepository;
import com.viewnext.bsan.practica04.service.FilmService;
import com.viewnext.bsan.practica04.util.MessageBuilder;
import com.viewnext.bsan.practica04.util.constants.Messages;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelFilmMapper;
import com.viewnext.bsan.practica04.repository.custom.CustomFilmRepository;
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
    public List<FilmBo> getAll(WatchableFilterDto filter, QueryOptionsDto queryOptions) {
        // TODO: Re-do this method
        return List.of();
    }

    @Override
    public FilmBo getById(Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public FilmBo create(FilmBo film, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public FilmBo update(long id, FilmBo film, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public void validateFilm(FilmBo film) {
        // TODO: Re-do this method
    }

    @Override
    public void validateTitle(String title) {
        // TODO: Re-do this method
    }

    @Override
    public void validateYear(int year) {
        // TODO: Re-do this method
    }

    @Override
    public void deleteById(long id, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
    }

}

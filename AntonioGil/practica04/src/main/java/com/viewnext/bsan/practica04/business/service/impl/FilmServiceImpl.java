package com.viewnext.bsan.practica04.business.service.impl;

import com.viewnext.bsan.practica04.business.bo.FilmBo;
import com.viewnext.bsan.practica04.business.exception.BadInputDataException;
import com.viewnext.bsan.practica04.business.exception.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.business.exception.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.business.exception.ResourceNotFoundException;
import com.viewnext.bsan.practica04.business.service.FilmService;
import com.viewnext.bsan.practica04.persistence.entity.Film;
import com.viewnext.bsan.practica04.persistence.repository.FilmRepository;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomFilmRepository;
import com.viewnext.bsan.practica04.persistence.specification.FilmSpecifications;
import com.viewnext.bsan.practica04.presentation.request.QueryOptions;
import com.viewnext.bsan.practica04.presentation.request.WatchableFilter;
import com.viewnext.bsan.practica04.util.MessageBuilder;
import com.viewnext.bsan.practica04.util.constants.Messages;
import com.viewnext.bsan.practica04.util.constants.RestApiDefaultParams;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelFilmMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code FilmService} interface.
 *
 * @author Antonio Gil
 */
@Slf4j
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
    public List<FilmBo> getAll(WatchableFilter filter, QueryOptions queryOptions) {
        boolean useCustomRepository = queryOptions.getUseCustomRepository().orElse(false);
        int pageNumber = queryOptions.getPageNumber().orElse(0);
        int pageSize = queryOptions.getPageSize().orElse(RestApiDefaultParams.DEFAULT_PAGE_SIZE);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return (useCustomRepository ? customGetAll(filter, pageable) : regularGetAll(filter, pageable));
    }

    private List<FilmBo> customGetAll(WatchableFilter filter, Pageable pageable) {
        List<Film> entityList;
        if (filter.isEmpty()) {
            entityList = customRepository.findAll(pageable);
        } else {
            Specification<Film> spec = Specification.where(null);

            if (filter.getTitle().isPresent()) {
                String title = filter.getTitle().orElseThrow();
                spec = spec.and(FilmSpecifications.titleContainsIgnoreCase(title));
            }
            if (filter.getYear().isPresent()) {
                int year = filter.getYear().orElseThrow();
                spec = spec.and(FilmSpecifications.yearIsEqualTo(year));
            }

            entityList = customRepository.findAll(spec, pageable);
        }

        return entityList.stream().map(mapper::entityToBo).toList();
    }

    private List<FilmBo> regularGetAll(WatchableFilter filter, Pageable pageable) {
        List<Film> entityList;
        if (filter.isEmpty()) {
            entityList = repository.findAll(pageable).toList();
        } else {
            Specification<Film> spec = Specification.where(null);

            if (filter.getTitle().isPresent()) {
                String title = filter.getTitle().orElseThrow();
                spec = spec.and(FilmSpecifications.titleContainsIgnoreCase(title));
            }
            if (filter.getYear().isPresent()) {
                int year = filter.getYear().orElseThrow();
                spec = spec.and(FilmSpecifications.yearIsEqualTo(year));
            }

            entityList = repository.findAll(spec, pageable).toList();
        }

        return entityList.stream().map(mapper::entityToBo).toList();
    }

    @Override
    public FilmBo getById(long id, Optional<Boolean> useCustomRepository) {
        boolean custom = useCustomRepository.orElse(false);
        Optional<Film> foundEntity = (custom ? customRepository.findById(id) : repository.findById(id));

        if (foundEntity.isEmpty()) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.FILM_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public FilmBo create(FilmBo film, Optional<Boolean> useCustomRepository) {
        validateFilm(film);

        boolean custom = useCustomRepository.orElse(false);
        return (custom ? customCreate(film) : regularCreate(film));
    }

    private FilmBo customCreate(FilmBo film) {
        if (customRepository.existsById(film.getId())) {
            String message =
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.FILM_ENTITY_NAME, film.getId());
            log.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        Film entity = mapper.boToEntity(film);
        customRepository.save(entity);

        return mapper.entityToBo(entity);
    }

    private FilmBo regularCreate(FilmBo film) {
        if (repository.existsById(film.getId())) {
            String message =
                    MessageBuilder.buildResourceAlreadyExistsMessage(Messages.FILM_ENTITY_NAME, film.getId());
            log.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        Film entity = mapper.boToEntity(film);
        repository.save(entity);

        return mapper.entityToBo(entity);
    }

    @Override
    public FilmBo update(long id, FilmBo film, Optional<Boolean> useCustomRepository) {
        validateFilm(film);
        film.setId(id);

        boolean custom = useCustomRepository.orElse(false);
        return (custom ? customUpdate(film) : regularUpdate(film));
    }

    private FilmBo customUpdate(FilmBo film) {
        if (!customRepository.existsById(film.getId())) {
            String message =
                    MessageBuilder.buildResourceNotFoundMessage(Messages.FILM_ENTITY_NAME, film.getId());
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        Film entity = mapper.boToEntity(film);
        customRepository.save(entity);

        return mapper.entityToBo(entity);
    }

    private FilmBo regularUpdate(FilmBo film) {
        if (!repository.existsById(film.getId())) {
            String message =
                    MessageBuilder.buildResourceNotFoundMessage(Messages.FILM_ENTITY_NAME, film.getId());
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        Film entity = mapper.boToEntity(film);
        repository.save(entity);

        return mapper.entityToBo(entity);
    }

    @Override
    public void validateFilm(FilmBo film) {
        if (film == null) {
            String message = MessageBuilder.buildNullNotAllowedMessage(Messages.FILM_ENTITY_NAME);
            log.warn(message);
            throw new MissingRequiredFieldException(message, Messages.FILM_ENTITY_NAME);
        }

        validateTitle(film.getTitle());
        validateYear(film.getYear());
    }

    @Override
    public void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            String message = MessageBuilder.buildMissingRequiredFieldMessage("title");
            log.warn(message);
            throw new MissingRequiredFieldException(message, "title");
        }
    }

    @Override
    public void validateYear(int year) {
        if (year < 0) {
            String message = MessageBuilder.buildNegativeNumberNotAllowedMessage("year");
            log.warn(message);
            throw new BadInputDataException(message);
        }
    }

    @Override
    public void deleteById(long id, Optional<Boolean> useCustomRepository) {
        boolean custom = useCustomRepository.orElse(false);
        if (custom) {
            customDeleteById(id);
        } else {
            regularDeleteById(id);
        }
    }

    private void customDeleteById(long id) {
        if (!customRepository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.FILM_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        customRepository.deleteById(id);
    }

    private void regularDeleteById(long id) {
        if (!repository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.FILM_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        repository.deleteById(id);
    }

}

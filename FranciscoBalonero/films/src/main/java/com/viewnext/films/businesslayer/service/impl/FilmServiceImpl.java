package com.viewnext.films.businesslayer.service.impl;

import com.viewnext.films.businesslayer.bo.FilmBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.FilmService;
import com.viewnext.films.persistencelayer.entity.Film;
import com.viewnext.films.persistencelayer.repository.criteria.FilmCriteriaRepository;
import com.viewnext.films.persistencelayer.repository.jpa.FilmJPARepository;
import com.viewnext.films.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing films.
 *
 * <p>This class provides implementations for the methods declared in the {@link FilmService} interface.</p>
 *
 * <p>It uses the {@link FilmCriteriaRepository} and {@link FilmJPARepository} to interact with the persistence
 * layer, and the {@link Converter} to convert between entity and business objects.</p>
 *
 * <p>It also handles exceptions and provides meaningful error messages.</p>
 *
 * @author Francisco Balonero Olivera
 */
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    /**
     * The film criteria repository.
     */
    private final FilmCriteriaRepository filmCriteriaRepository;

    /**
     * The film JPA repository.
     */
    private final FilmJPARepository filmJPARepository;

    /**
     * The converter for converting between entity and business objects.
     */
    private final Converter converter;

    @Override
    public FilmBO criteriaGetById(long id) throws ServiceException {
        try {
            return converter.filmEntityToBO(filmCriteriaRepository.getFilmById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The film could not be searched", e);
        }

    }

    @Override
    public List<FilmBO> criteriaGetAll() throws ServiceException {
        try {
            List<Film> films = filmCriteriaRepository.getAllFilms();
            if (!films.isEmpty()) {
                return films.stream().map(converter::filmEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }

        } catch (NestedRuntimeException e) {
            throw new ServiceException("The films could not be searched", e);
        }

    }

    @Override
    public void criteriaDeleteById(Long id) throws ServiceException {
        try {
            filmCriteriaRepository.deleteFilm(id);
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The film could not be deleted", e);
        }
    }

    @Override
    public FilmBO criteriaUpdate(FilmBO filmBO) throws ServiceException {
        criteriaGetById(filmBO.getId());
        try {
            return converter.filmEntityToBO(filmCriteriaRepository.updateFilm(converter.filmBOToEntity(filmBO)));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The film could not be updated", e);
        }
    }

    @Override
    public FilmBO criteriaCreate(FilmBO filmBO) throws ServiceException {
        try {
            return converter.filmEntityToBO(filmCriteriaRepository.createFilm(converter.filmBOToEntity(filmBO)));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The film could not be created", e);
        }

    }

    @Override
    public FilmBO jpaGetById(long id) throws ServiceException {
        try {
            return converter.filmEntityToBO(filmJPARepository.findById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The film could not be searched", e);
        }

    }

    @Override
    public List<FilmBO> jpaGetAll() throws ServiceException {
        try {
            List<Film> films = filmJPARepository.findAll();
            if (!films.isEmpty()) {
                return films.stream().map(converter::filmEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The films could not be searched", e);
        }
    }

    @Override
    public void jpaDeleteById(Long id) throws ServiceException {
        try {
            filmJPARepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The film could not be deleted", e);
        }
    }

    @Override
    public FilmBO jpaUpdate(FilmBO filmBO) throws ServiceException {
        try {
            if (filmJPARepository.existsById(filmBO.getId())) {
                return converter.filmEntityToBO(filmJPARepository.save(converter.filmBOToEntity(filmBO)));
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The film could not be updated", e);
        }

    }

    @Override
    public FilmBO jpaCreate(FilmBO filmBO) throws ServiceException {
        try {
            return converter.filmEntityToBO(filmJPARepository.save(converter.filmBOToEntity(filmBO)));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The film could not be created", e);
        }
    }

}

package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.repository.criteria.FilmCriteriaRepository;
import com.santander.peliculacrud.repository.jpa.ActorRepository;
import com.santander.peliculacrud.repository.jpa.DirectorRepository;
import com.santander.peliculacrud.repository.jpa.FilmRepository;

import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.model.entity.Film;
import com.santander.peliculacrud.service.FilmServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.exception.GenericException;
import com.santander.peliculacrud.util.mapper.bo.ActorBOMapper;
import com.santander.peliculacrud.util.mapper.bo.DirectorBOMapper;
import com.santander.peliculacrud.util.mapper.bo.FilmBOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * The type Film service.
 */
@Service
public class FilmService implements FilmServiceInterface {

    private final FilmRepository filmRepository;
    private final FilmCriteriaRepository filmCriteriaRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final FilmBOMapper filmBOMapper;
    private final CommonOperation commonOperation;

    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);

    /**
     * Instantiates a new Film service.
     *
     * @param actorRepository
     *         the actor repository
     * @param directorRepository
     *         the director repository
     * @param commonOperation
     *         the common operation
     * @param filmBOMapper
     *         the film bo mapper
     * @param filmRepository
     *         the film repository
     */
    @Autowired
    public FilmService(ActorRepository actorRepository, DirectorRepository directorRepository,
            CommonOperation commonOperation, FilmBOMapper filmBOMapper, FilmRepository filmRepository,
            FilmCriteriaRepository filmCriteriaRepository) {
        this.actorRepository = actorRepository;
        this.commonOperation = commonOperation;
        this.directorRepository = directorRepository;
        this.filmBOMapper = filmBOMapper;
        this.filmRepository = filmRepository;
        this.filmCriteriaRepository = filmCriteriaRepository;

    }

    /**
     * Create film boolean.
     *
     * @param filmBO
     *         the film out
     * @return the boolean
     */

    public FilmBO createFilm(FilmBO filmBO) throws GenericException {

        FilmBO filmBOReturn;
        Director director = directorRepository.findById(filmBO.getDirector().getId()).orElse(null);

        //Se comprueba que los directores y actores se encuentran en el repositorio
        List<Long> idActors = commonOperation.getIdObject(filmBO.getActors());
        List<Actor> actors = actorRepository.findAllById(idActors);

        if (director != null && !actors.isEmpty()) {

            Film film = filmBOMapper.boToEntity(filmBO);

            Film filmAux = filmRepository.save(film);
            filmBOReturn = filmBOMapper.entityToBo(filmAux);

        } else {
            throw new GenericException("Failed to create actor: Invalid director or actor");
        }

        return filmBOReturn;
    }

    @Override
    public FilmBO updateFilm(Long id, FilmBO filmBO) throws GenericException {
        FilmBO filmBOReturn = FilmBO.builder().build();
        if (filmRepository.existsById(id)) {

            Director director = directorRepository.findById(filmBO.getDirector().getId()).orElse(null);
            List<Long> idActors = commonOperation.getIdObject(filmBO.getActors());
            List<Actor> actors = actorRepository.findAllById(idActors);

            if (director != null && !actors.isEmpty()) {

                Film filmUpdate = filmBOMapper.boToEntity(filmBO);
                filmUpdate.setId(id);
                Film film = filmRepository.save(filmUpdate);
                filmBOReturn = filmBOMapper.entityToBo(film);

            } else {
                logger.error("Failed to update actor invalid director or actor");
                throw new GenericException("Failed to update actor invalid director or actor");
            }
        } else {
            filmNotFound();
        }

        return filmBOReturn;
    }

    @Override
    public boolean deleteFilm(Long id) throws GenericException {
        boolean deleted = false;
        if (id != null) {

            if (filmRepository.existsById(id)) {

                filmRepository.deleteById(id);
                deleted = true;

            } else {
                filmNotFound();
            }
        } else {
            throw new GenericException("Invalid film id: null");

        }

        return deleted;
    }

    @Override
    public FilmBO getFilmById(Long id) {
        Film film = filmRepository.findById(id).orElse(null);

        return filmBOMapper.entityToBo(film);
    }

    @Override
    public List<FilmBO> getAllFilm(int page) {
        Pageable pageable = PageRequest.of(page, 5);

        Page<Film> filmsPage = filmRepository.findAll(pageable);
        List<FilmBO> films = filmBOMapper.listEntityListBo(filmsPage);

        return films.stream().sorted(Comparator.comparing(FilmBO::getTitle)).toList();
    }

    @Override
    public List<FilmBO> getFilmByTitle(String title, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> filmPage = filmRepository.findAllByTitle(title, pageable);
        List<FilmBO> films = filmBOMapper.listEntityListBo(filmPage);

        return films.stream().sorted(Comparator.comparing(FilmBO::getTitle)).toList();
    }

    @Override
    public List<FilmBO> getFilmByCreated(int created, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> filmPage = filmRepository.findAllByCreated(created, pageable);
        List<FilmBO> films = filmBOMapper.listEntityListBo(filmPage);

        return films.stream().sorted(Comparator.comparingInt(FilmBO::getCreated)).toList();
    }

    @Override
    public List<FilmBO> getFilmByActors(List<String> actorsName, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        List<Actor> actors = actorRepository.findByNameIn(actorsName);
        Page<Film> filmsPage = filmRepository.findAllByActorsIn(actors, pageable);
        List<FilmBO> films = filmBOMapper.listEntityListBo(filmsPage);

        return films.stream().sorted(Comparator.comparingInt(FilmBO::getCreated)).toList();
    }

    @Override
    public List<FilmBO> getFilmByDirectors(List<String> directorsName, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        List<Director> directors = directorRepository.findByNameIn(directorsName);
        Page<Film> filmsPage = filmRepository.findAllByDirectorIn(directors, pageable);
        List<FilmBO> films = filmBOMapper.listEntityListBo(filmsPage);

        return films.stream().sorted(Comparator.comparingInt(FilmBO::getCreated)).toList();
    }

    @Override
    public List<FilmBO> getFilmByAllFilter(List<String> title, List<Integer> created, List<String> actorsName,
            List<String> directorsName, int page) {



        List<Actor> actors = actorRepository.findByNameIn(actorsName);
        List<Director> directors = directorRepository.findByNameIn(directorsName);
        Pageable pageable = PageRequest.of(page, 5);

        Page<Film> films = filmCriteriaRepository.findAllFilter(title,created, actors, directors, pageable);
        List<FilmBO> actorsBOS = filmBOMapper.listEntityListBo(films);
        return actorsBOS.stream().sorted(Comparator.comparing(FilmBO::getTitle)).toList();
    }

    private void filmNotFound() throws GenericException {
        logger.error("Film not found");
        throw new GenericException("Film not found");

    }

}


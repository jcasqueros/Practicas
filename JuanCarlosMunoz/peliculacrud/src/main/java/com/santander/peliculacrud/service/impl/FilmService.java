package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.ActorRepository;
import com.santander.peliculacrud.model.api.DirectorRepository;
import com.santander.peliculacrud.model.api.FilmRepository;
import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.model.entity.Film;
import com.santander.peliculacrud.service.FilmServiceInterface;
import com.santander.peliculacrud.util.mapper.FilmBOMapper;
import com.santander.peliculacrud.util.mapper.SeriesBOMapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Film service.
 */

@Service
public class FilmService implements FilmServiceInterface {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private final FilmBOMapper filmBOMapper = Mappers.getMapper(FilmBOMapper.class);


    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);

    /**
     * Create film boolean.
     *
     * @param filmBO
     *         the film out
     * @return the boolean
     */

    public FilmBO createFilm(FilmBO filmBO) {

        FilmBO filmBOReturn = FilmBO.builder().build();
        //Se comprueba que los directores y actores se encuentran en el repositorio
        try {
            Director director = directorRepository.findById(filmBO.getDirector().getId()).orElse(null);
            List<Long> idActors = getIdActors(filmBO.getActors());
            List<Actor> actors = actorRepository.findAllById(idActors);

            if (director != null && !actors.isEmpty()) {

                Film film = filmBOMapper.boToEntity(filmBO);

                Film filmAux = filmRepository.save(film);
                filmBOReturn = filmBOMapper.entityToBo(filmAux);

            }
        } catch (DataAccessException e) {
            logger.error("Failed to create actor", e);
            throw new RuntimeException("Failed to create actor: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Failed to create actor: {}", e.getMessage());
            throw new RuntimeException("Failed to create actor: ", e);
        }

        return filmBOReturn;
    }

    @Override
    public FilmBO updateFilm(Long id, FilmBO filmBO) {
        FilmBO filmBOReturn = FilmBO.builder().build();
        try {
            if (filmRepository.existsById(id)) {

                Film filmUpdate = filmBOMapper.boToEntity(filmBO);

                if (filmUpdate.getDirector() != null && !filmUpdate.getActors().isEmpty()) {

                    filmUpdate.setId(id);
                    Film film = filmRepository.save(filmUpdate);
                    filmBOReturn = filmBOMapper.entityToBo(film);
                }

            }else {
                filmNotfound();
            }
        } catch (DataAccessException e) {
            logger.error("Failed to update actor", e);
            throw new RuntimeException("Failed to update actor: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Failed to update actor: {}", e.getMessage());
            throw new RuntimeException("Failed to update actor: ", e);
        }

        return filmBOReturn;
    }

    @Override
    public List<FilmBO> getAllFilm() {
        return List.of();
    }

    @Override
    public FilmBO filmOut(Long id) {
        return null;
    }

    @Override
    public boolean deleteFilm(Long id) {
        return false;
    }

    @Override
    public FilmBO getFilmById(Long id) {
        return null;
    }

    @Override
    public boolean existsFilmById(Long id) {
        return false;
    }
/*
    public boolean updateFilm(Long id, @Valid FilmBO filmBO) {


    }

    public List<FilmBO> getAllFilm() {

        List<Film> films = filmRepository.findAll();
        List<FilmBO> filmBOS = new ArrayList<>();
        for (Film film : films) {

            FilmBO filmBO = transformObjects.filmToFilmOut(film);
            filmBOS.add(filmBO);

        }

        return filmBOS;
    }

    public FilmBO filmOut(Long id) {

        Film film = filmRepository.findById(id).orElse(null);
        FilmBO filmBO = null;
        if (film != null) {
            filmBO = transformObjects.filmToFilmOut(film);
        }

        return filmBO;
    }

    public boolean deleteFilm(Long id) {
        boolean delete = false;

        if (id == null) {
            throw new RuntimeException("Invalid film id: null");
        }

        if (!filmRepository.existsById(id)) {
            filmNotfound();
        }

        try {
            filmRepository.deleteById(id);
            delete = !filmRepository.existsById(id);
        } catch (Exception e) {
            logger.error("Failed to delete film: {}", e.getMessage());
            throw new RuntimeException("Failed to delete film: {}", e);
        }

        return delete;
    }

    public Film getLastFilm() {

        List<Film> films = filmRepository.findLastFilm();
        long idLastFilm = films.get(0).getId();
        return filmRepository.findById(idLastFilm).orElse(null);
    }

    public Film getFilmById(Long id) {
        return filmRepository.findById(id).orElse(null);

    }*/

    private List<Long> getIdActors(List<ActorBO> actorBOS) {
        return actorBOS.stream().map(ActorBO::getId).collect(Collectors.toList());
    }

    private void filmNotfound() {
        logger.error("Film not found");
        throw new RuntimeException("Film not found");

    }

}


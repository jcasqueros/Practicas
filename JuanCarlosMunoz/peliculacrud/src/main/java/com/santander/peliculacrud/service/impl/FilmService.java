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
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        Director director = directorRepository.findById(filmBO.getDirector().getId()).orElse(null);
        List<Long> idActors = getIdActors(filmBO.getActors());
        List<Actor> actors = actorRepository.findAllById(idActors);

        if (director != null && !actors.isEmpty()) {
            try {

                Film film = filmBOMapper.boToEntity(filmBO);

                Film filmAux = filmRepository.save(film);
                filmBOReturn = filmBOMapper.entityToBo(filmAux);

            } catch (Exception e) {
                logger.error("Failed to create actor: {}", e.getMessage());
                throw new RuntimeException("Failed to create actor: ", e);
            }
        } else {
            logger.error("Failed to create actor invalid director or actor");
            throw new RuntimeException("Failed to create actor invalid director or actor");
        }

        return filmBOReturn;
    }

    @Override
    public FilmBO updateFilm(Long id, FilmBO filmBO) {
        FilmBO filmBOReturn = FilmBO.builder().build();
        if (filmRepository.existsById(id)) {

            Director director = directorRepository.findById(filmBO.getDirector().getId()).orElse(null);
            List<Long> idActors = getIdActors(filmBO.getActors());
            List<Actor> actors = actorRepository.findAllById(idActors);

            if (director != null && !actors.isEmpty()) {

                Film filmUpdate = filmBOMapper.boToEntity(filmBO);
                filmUpdate.setId(id);
                Film film = filmRepository.save(filmUpdate);
                filmBOReturn = filmBOMapper.entityToBo(film);

            } else {
                logger.error("Failed to update actor invalid director or actor");
                throw new RuntimeException("Failed to update actor invalid director or actor");
            }
        } else {
            filmNotfound();
        }

        return filmBOReturn;
    }

    @Override
    public List<FilmBO> getAllFilm() {
        List<Film> films = filmRepository.findAll();

        return filmBOMapper.listEntityListBo(films);
    }

    @Override
    public boolean deleteFilm(Long id) {
        boolean deleted = false;
        if (id != null) {

            if (filmRepository.existsById(id)) {

                    filmRepository.deleteById(id);
                    deleted = true;

            } else {
                filmNotfound();
            }
        } else {
            throw new RuntimeException("Invalid film id: null");

        }

        return deleted;
    }

    @Override
    public FilmBO getFilmById(Long id) {
        Film film = filmRepository.findById(id).orElse(null);

        return filmBOMapper.entityToBo(film);
    }

    private List<Long> getIdActors(List<ActorBO> actorBOS) {
        return actorBOS.stream().map(ActorBO::getId).collect(Collectors.toList());
    }

    private void filmNotfound() {
        logger.error("Film not found");
        throw new RuntimeException("Film not found");

    }

}


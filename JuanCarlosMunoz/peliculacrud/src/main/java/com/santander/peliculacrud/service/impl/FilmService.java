package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.ActorRepository;
import com.santander.peliculacrud.model.api.DirectorRepository;
import com.santander.peliculacrud.model.api.FilmRepository;
import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.input.Director;
import com.santander.peliculacrud.model.input.Film;
import com.santander.peliculacrud.model.output.FilmModelController;
import com.santander.peliculacrud.model.output.FilmModelService;
import com.santander.peliculacrud.service.FilmServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.TransformObjects;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

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
    private TransformObjects transformObjects;

    @Autowired
    private Validator validator;

    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);

    @Autowired
    private CommonOperation commonOperation;

    /**
     * Create film boolean.
     *
     * @param filmModelController
     *         the film out
     * @return the boolean
     */
    public boolean createFilm(@Valid FilmModelController filmModelController) {

        boolean create = false;

        BindingResult result = new BeanPropertyBindingResult(filmModelController, "FilmModelController");
        validator.validate(filmModelController, result);

        if (result.hasErrors()) {
            commonOperation.showErrorModel(logger, result);
            throw new RuntimeException("Invalid actor data: " + result.getAllErrors());
        } else {

            Director director = directorRepository.findById(filmModelController.getIdDirector()).orElse(null);
            List<Actor> actors = actorRepository.findAllById(filmModelController.getIdActor());

            if (director != null && !actors.isEmpty()) {

                Film film = transformObjects.filmInToFilm(filmModelController);

                try {
                    filmRepository.save(film);
                    create = true;

                } catch (Exception e) {
                    logger.error("Failed to create actor: {}", e.getMessage());
                    throw new RuntimeException("Failed to create actor: ", e);
                }
            }
        }

        return create;
    }

    /**
     * Update film boolean.
     *
     * @param id
     *         the id
     * @param filmModelController
     *         the film out
     * @return the boolean
     */
    public boolean updateFilm(Long id, @Valid FilmModelController filmModelController) {

        boolean update = false;
        if (filmRepository.existsById(id)) {

            BindingResult result = new BeanPropertyBindingResult(filmModelController, "filmModelController");
            validator.validate(filmModelController, result);

            if (result.hasErrors()) {
                commonOperation.showErrorModel(logger, result);
                throw new RuntimeException("Invalid actor data: " + result.getAllErrors());
            } else {

                try {
                    Film filmUpdate = transformObjects.filmInToFilm(filmModelController);
                    if (filmUpdate.getDirector() != null && !filmUpdate.getActors().isEmpty()) {

                        filmUpdate.setId(id);
                        filmRepository.save(filmUpdate);
                        update = filmRepository.existsById(id);
                    }
                } catch (Exception e) {

                    logger.error("Failed to update film: {}", e.getMessage());
                    throw new RuntimeException("Failed to update film: ", e);

                }
            }

        } else {
            filmNotfound();
            throw new RuntimeException("Actor not found");
        }
        return update;
    }

    /**
     * Gets all film.
     *
     * @return the all film
     */
    public List<FilmModelService> getAllFilm() {

        List<Film> films = filmRepository.findAll();
        List<FilmModelService> filmModelServices = new ArrayList<>();
        for (Film film : films) {

            FilmModelService filmModelService = transformObjects.filmToFilmOut(film);
            filmModelServices.add(filmModelService);

        }

        return filmModelServices;
    }

    /**
     * Get a film show.
     *
     * @param id
     *         the id
     * @return the film show
     */
    public FilmModelService filmOut(Long id) {

        Film film = filmRepository.findById(id).orElse(null);
        FilmModelService filmModelService = null;
        if (film != null) {
            filmModelService = transformObjects.filmToFilmOut(film);
        }

        return filmModelService;
    }

    /**
     * Delete film boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
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

    /**
     * Gets last film.
     *
     * @return the last film
     */
    public Film getLastFilm() {

        List<Film> films = filmRepository.findLastFilm();
        long idLastFilm = films.get(0).getId();
        return filmRepository.findById(idLastFilm).orElse(null);
    }

    /**
     * Gets film by id.
     *
     * @param id
     *         the id
     * @return the film by id
     */
    public Film getFilmById(Long id) {
        return filmRepository.findById(id).orElse(null);

    }

    /**
     * Exists film by id boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    public boolean existsFilmById(Long id) {
        return filmRepository.existsById(id);
    }

    /**
     * Films list size int.
     *
     * @return the int
     */
    public int getListSize() {
        return filmRepository.findAll().size();
    }

    private void filmNotfound() {
        logger.error("Film not found");
        throw new RuntimeException("Film not found");

    }

}

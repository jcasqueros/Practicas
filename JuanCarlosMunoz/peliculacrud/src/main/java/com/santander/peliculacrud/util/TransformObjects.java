package com.santander.peliculacrud.util;

import com.santander.peliculacrud.model.api.ActorRepository;
import com.santander.peliculacrud.model.api.DirectorRepository;
import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.input.Director;
import com.santander.peliculacrud.model.input.Film;
import com.santander.peliculacrud.model.input.Series;
import com.santander.peliculacrud.model.output.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Transform objects.
 */
@Component
public class TransformObjects {

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private CommonOperation commonOperation;


    private static final Logger logger = LoggerFactory.getLogger(TransformObjects.class);

    /**
     * Actor out to actor actor.
     *
     * @param actorModelController
     *         the actor out
     * @return the actor
     */
    public Actor actorOutToActor(ActorModelController actorModelController) {

        return Actor.builder().name(actorModelController.getName()).age(actorModelController.getAge()).nation(
                actorModelController.getNation()).build();
    }

    /**
     * Actor to actor out actor out.
     *
     * @param actor
     *         the actor
     * @return the actor out
     */
    public ActorModelController actorToActorOut(Actor actor) {

        return ActorModelController.builder().name(actor.getName()).age(actor.getAge()).nation(actor.getNation()).build();
    }

    /**
     * Actors to actors out list.
     *
     * @param actors
     *         the actors
     * @return the list
     */
    public List<ActorModelController> actorsToActorsOut(List<Actor> actors) {

        List<ActorModelController> actorsOut = new ArrayList<>();

        for (Actor actor : actors) {
            ActorModelController actorModelController = this.actorToActorOut(actor);
            actorsOut.add(actorModelController);
        }
        return actorsOut;
    }

    /**
     * Directors to directors out list.
     *
     * @param directors
     *         the directors
     * @return the list
     */
    public List<DirectorModelService> directorsToDirectorsOut(List<Director> directors) {

        List<DirectorModelService> directorsOut = new ArrayList<>();

        for (Director director : directors) {
            DirectorModelService directorModelService = this.directorToDirectorOut(director);
            directorsOut.add(directorModelService);
        }
        return directorsOut;
    }

    /**
     * Director out to director director.
     *
     * @param directorModelService
     *         the director out
     * @return the director
     */
    public Director directorOutToDirector(DirectorModelService directorModelService) {
        //commonOperation.validateModel(directorModelService, logger);

        return Director.builder().name(directorModelService.getName()).age(directorModelService.getAge()).nation(
                        directorModelService.getNation())
                .build();
    }

    /**
     * Director to director out director out.
     *
     * @param director
     *         the director
     * @return the director out
     */
    public DirectorModelService directorToDirectorOut(Director director) {

        return DirectorModelService.builder().name(director.getName()).age(director.getAge()).nation(director.getNation())
                .build();
    }

    /**
     * Film out to film film.
     *
     * @param filmModelController
     *         the film out
     * @return the film
     */
    public Film filmInToFilm(FilmModelController filmModelController) {

        Director director = directorRepository.findById(filmModelController.getIdDirector()).orElse(null);
        List<Actor> actors = actorRepository.findByIdIn(filmModelController.getIdActor());

        return Film.builder().title(filmModelController.getTitle()).created(filmModelController.getCreated()).director(director).actors(actors)
                .build();

    }

    /**
     * Film out to film film.
     *
     * @param seriesModelController
     *         the series in
     * @return the film
     */
    public Series seriesOutToSeries(SeriesModelController seriesModelController) {

        Director director = directorRepository.findById(seriesModelController.getIdDirector()).orElse(null);
        List<Actor> actors = actorRepository.findByIdIn(seriesModelController.getIdActor());

        return Series.builder().title(seriesModelController.getTitle()).created(seriesModelController.getCreated()).director(director)
                .actors(actors).build();

    }

    /**
     * Film to film show film show.
     *
     * @param series
     *         the series
     * @return the film show
     */
    public SeriesModelService seriesToSeriesOut(Series series) {

        Director director = directorRepository.findById(series.getDirector().getId()).orElse(null);

        DirectorModelService directorModelService = null;
        if (director != null) {
            directorModelService = this.directorToDirectorOut(director);
        }

        List<ActorModelController> actorModelControllers = this.actorsToActorsOut(series.getActors());

        return SeriesModelService.builder().title(series.getTitle()).created(series.getCreated()).director(directorModelService)
                .actors(actorModelControllers).build();

    }

    /**
     * Film to film show film show.
     *
     * @param film
     *         the film
     * @return the film show
     */
    public FilmModelService filmToFilmOut(Film film) {


        Director director = directorRepository.findById(film.getDirector().getId()).orElse(null);

        DirectorModelService directorModelService = null;
        if (director != null) {
            directorModelService = this.directorToDirectorOut(director);
        }

        List<ActorModelController> actorModelControllers = this.actorsToActorsOut(film.getActors());

        return FilmModelService.builder().title(film.getTitle()).created(film.getCreated()).director(directorModelService)
                .actors(actorModelControllers).build();

    }

}

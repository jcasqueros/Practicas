package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.ActorRepository;
import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.output.ActorModelController;

import com.santander.peliculacrud.service.ActorServiceInterface;
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

import java.util.List;

/**
 * The type Actor service.
 */
@Service
public class ActorService implements ActorServiceInterface{

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private TransformObjects transformObjects;

    @Autowired
    private Validator validator;

    private static final Logger logger = LoggerFactory.getLogger(ActorService.class);

    @Autowired
    private CommonOperation commonOperation;

    /**
     * Create actor.
     *
     * @param actorModelController
     *         the actor out
     */


    public Actor createActor(@Valid ActorModelController actorModelController) {
        Actor actor = Actor.builder().build();
        BindingResult result = new BeanPropertyBindingResult(actorModelController, "actorModelController");
        validator.validate(actorModelController, result);

        if (result.hasErrors()) {
            commonOperation.showErrorModel(logger, result);
            throw new RuntimeException("Invalid actor data: " + result.getAllErrors());
        } else {
            actor = transformObjects.actorOutToActor(actorModelController);
            try {
                actorRepository.save(actor);
            } catch (Exception e) {
                logger.error("Failed to create actor: {}", e.getMessage());
                throw new RuntimeException("Failed to create actor: ", e);
            }
        }


        return actor;
    }


    /**
     * Update actor boolean.
     *
     * @param id
     *         the id
     * @param actorModelController
     *         the actor out
     * @return the boolean
     */
    public boolean updateActor(Long id, @Valid ActorModelController actorModelController) {
        boolean update = false;
        if (actorRepository.existsById(id)) {

            BindingResult result = new BeanPropertyBindingResult(actorModelController, "actorModelController");
            validator.validate(actorModelController, result);

            if (result.hasErrors()) {
                commonOperation.showErrorModel(logger, result);
                throw new RuntimeException("Invalid actor data: " + result.getAllErrors());
            } else {

                try {
                    Actor actor = transformObjects.actorOutToActor(actorModelController);
                    actor.setId(id);
                    actorRepository.save(actor);
                    update = actorRepository.existsById(id);

                } catch (Exception e) {

                    logger.error("Failed to update actor: {}", e.getMessage());
                    throw new RuntimeException("Failed to update actor: ", e);

                }
            }

        } else {
            actorNotfound();
            throw new RuntimeException("Actor not found");
        }
        return update;

    }

    /**
     * Delete actor boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    public boolean deleteActor(Long id) {
        boolean delete = false;
        if (actorRepository.existsById(id)) {
            try {
                actorRepository.deleteById(id);

                delete = !actorRepository.existsById(id);

            } catch (Exception e) {
                logger.error("Failed to delete user: {}", e.getMessage());
                throw new RuntimeException("Failed to delete user: {}", e);
            }

        } else {
            actorNotfound();
        }

        return delete;
    }

    /**
     * Gets all actors.
     *
     * @return the all actors
     */
    public List<ActorModelController> getAllActors() {

        List<Actor> actors = actorRepository.findAll();

        return transformObjects.actorsToActorsOut(actors);
    }

    /**
     * Gets actor by id.
     *
     * @param id
     *         the id
     * @return the actor by id
     */
    public ActorModelController getActorById(Long id) {

        Actor actor = actorRepository.findById(id).orElse(null);

        ActorModelController actorModelController = null;

        if (actor != null) {
            actorModelController = transformObjects.actorToActorOut(actor);
        }

        return actorModelController;
    }

    /**
     * Gets last actor.
     *
     * @return the last actor
     */
    public Actor getLastActor() {
        List<Actor> actors = actorRepository.findLastActor();
        return actors.get(0);
    }

    /**
     * Actors list size int.
     *
     * @return the int
     */
    public int getListSize() {
        return actorRepository.findAll().size();
    }

    private void actorNotfound() {
        logger.error("Actor not found");
        throw new RuntimeException("Actor not found");

    }

}

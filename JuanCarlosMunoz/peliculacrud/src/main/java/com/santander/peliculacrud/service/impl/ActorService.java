package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.ActorRepository;
import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.entity.Actor;

import com.santander.peliculacrud.service.ActorServiceInterface;
import com.santander.peliculacrud.util.mapper.ActorBOMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * The type Actor service.
 */
@Service
public class ActorService implements ActorServiceInterface {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ActorBOMapper actorBOMapper;


    private static final Logger logger = LoggerFactory.getLogger(ActorService.class);


    @Override
    public ActorBO createActor(ActorBO actorBO) {
        Actor actor = Actor.builder().build();

        actor = actorBOMapper.boToEntity(actorBO);

        try {
            actor = actorRepository.save(actor);
            actorBO = actorBOMapper.entityToBo(actor);

        } catch (DataAccessException e) {
            logger.error("Failed to create actor", e);
            throw new RuntimeException("Failed to create actor: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Failed to create actor: {}", e.getMessage());
            throw new RuntimeException("Failed to create actor: ", e);
        }

        return actorBO;

    }

    @Override
    public List<ActorBO> getAllActors() {
        List<Actor> actors = actorRepository.findAll();
        return actorBOMapper.listEntityListBo(actors);
    }

    @Override
    public ActorBO getActorById(long id) {
        Actor actor = actorRepository.findById(id).orElse(null);
        ActorBO actorBO = ActorBO.builder().build();
        if (actor != null) {
            actorBO = actorBOMapper.entityToBo(actor);
        }

        return actorBO;
    }

    @Override
    public boolean updateActor(long id, ActorBO actorBO) {
        boolean update = false;
        if (actorRepository.existsById(id)) {

            try {
                Actor actor = actorBOMapper.boToEntity(actorBO);
                actor.setId(id);
                actorRepository.save(actor);
                update = actorRepository.existsById(id);

            } catch (Exception e) {

                logger.error("Failed to update actor: {}", e.getMessage());
                throw new RuntimeException("Failed to update actor: ", e);

            }

        } else {
            actorNotfound();
            throw new RuntimeException("Actor not found");
        }
        return update;
    }

    @Override
    public boolean deleteActor(long id) {

        boolean delete = false;
        if (actorRepository.existsById(id)) {
            try {
                actorRepository.deleteById(id);

                delete = !actorRepository.existsById(id);

            } catch (Exception e) {
                logger.error("Failed to delete actor: {}", e.getMessage());
                throw new RuntimeException("Failed to delete actor: {}", e);
            }

        } else {
            actorNotfound();
        }

        return delete;
    }

    private void actorNotfound() {
        logger.error("Actor not found");
        throw new RuntimeException("Actor not found");

    }

}

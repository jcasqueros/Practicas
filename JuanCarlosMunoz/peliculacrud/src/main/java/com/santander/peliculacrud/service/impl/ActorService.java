package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.ActorRepository;
import com.santander.peliculacrud.model.bo.ActorBO;

import com.santander.peliculacrud.model.entity.Actor;

import com.santander.peliculacrud.service.ActorServiceInterface;
import com.santander.peliculacrud.util.exception.GenericException;
import com.santander.peliculacrud.util.mapper.bo.ActorBOMapper;

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
 * The type Actor service.
 */
@Service
public class ActorService implements ActorServiceInterface {

    private final ActorRepository actorRepository;
    private final ActorBOMapper actorBOMapper;

    private static final Logger logger = LoggerFactory.getLogger(ActorService.class);

    /**
     * Instantiates a new Actor service.
     *
     * @param actorBOMapper
     *         the actor bo mapper
     * @param actorRepository
     *         the actor repository
     */
    @Autowired
    public ActorService(ActorBOMapper actorBOMapper, ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
        this.actorBOMapper = actorBOMapper;

    }

    @Override
    public ActorBO createActor(ActorBO actorBO) throws GenericException {
        Actor actor;

        actor = actorBOMapper.boToEntity(actorBO);

        try {
            actor = actorRepository.save(actor);
            actorBO = actorBOMapper.entityToBo(actor);

        } catch (Exception e) {
            throw new GenericException("Failed to create actor: ", e);
        }

        return actorBO;

    }

    @Override
    public List<ActorBO> getAllActors(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> actorsPage = actorRepository.findAll(pageable);
        List<ActorBO> actors = actorBOMapper.listEntitytoListBo(actorsPage);

        return actors.stream().sorted(Comparator.comparing(ActorBO::getName)).toList();
    }

    @Override
    public ActorBO getActorById(long id) {
        Actor actor = actorRepository.findById(id).orElse(null);
        ActorBO actorBO = null;
        if (actor != null) {
            actorBO = actorBOMapper.entityToBo(actor);
        }

        return actorBO;
    }

    @Override
    public boolean updateActor(long id, ActorBO actorBO) throws GenericException {
        boolean update = false;
        if (actorRepository.existsById(id)) {

            try {
                Actor actor = actorBOMapper.boToEntity(actorBO);
                actor.setId(id);
                actorRepository.save(actor);
                update = actorRepository.existsById(id);

            } catch (Exception e) {

                throw new GenericException("Failed to update actor: ", e);

            }

        } else {
            actorNotfound();
        }
        return update;
    }

    @Override
    public boolean deleteActor(long id) throws GenericException {

        boolean delete = false;
        if (actorRepository.existsById(id)) {
            try {
                actorRepository.deleteById(id);

                delete = true;

            } catch (Exception e) {
                throw new GenericException("Failed to delete actor: {}", e);
            }

        } else {
            actorNotfound();
        }

        return delete;
    }

    @Override
    public List<ActorBO> getActorByName(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> actorsPage = actorRepository.findAllByName(name, pageable);
        List<ActorBO> actors = actorBOMapper.listEntitytoListBo(actorsPage);

        return actors.stream().sorted(Comparator.comparingInt(ActorBO::getAge)).toList();
    }

    @Override
    public List<ActorBO> getActorByAge(int age, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> actorsPage = actorRepository.findAllByAgeEquals(age, pageable);
        List<ActorBO> actors = actorBOMapper.listEntitytoListBo(actorsPage);

        return actors.stream().sorted(Comparator.comparing(ActorBO::getName)).toList();
    }

    @Override
    public List<ActorBO> getActorByNation(String nation, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> actorsPage = actorRepository.findAllByNationEquals(nation, pageable);
        List<ActorBO> actors = actorBOMapper.listEntitytoListBo(actorsPage);

        return actors.stream().sorted(Comparator.comparing(ActorBO::getName)).toList();
    }

    private void actorNotfound() throws GenericException {
        logger.error("Actor not found");
        throw new GenericException("Actor not found");

    }

}

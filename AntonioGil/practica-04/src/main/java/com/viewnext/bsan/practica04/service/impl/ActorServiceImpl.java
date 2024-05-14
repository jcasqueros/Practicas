package com.viewnext.bsan.practica04.service.impl;

import com.viewnext.bsan.practica04.bo.ActorBo;
import com.viewnext.bsan.practica04.util.request.PersonFilter;
import com.viewnext.bsan.practica04.util.request.QueryOptions;
import com.viewnext.bsan.practica04.repository.ActorRepository;
import com.viewnext.bsan.practica04.repository.custom.CustomActorRepository;
import com.viewnext.bsan.practica04.service.ActorService;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelActorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code ActorService} interface.
 *
 * @author Antonio Gil
 */
@Service
public class ActorServiceImpl implements ActorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorServiceImpl.class.getCanonicalName());

    private final ActorRepository repository;
    private final CustomActorRepository customRepository;
    private final ServiceLevelActorMapper mapper;

    public ActorServiceImpl(ActorRepository repository, CustomActorRepository customRepository,
                            ServiceLevelActorMapper mapper) {
        this.repository = repository;
        this.customRepository = customRepository;
        this.mapper = mapper;
    }


    @Override
    public List<ActorBo> getAll(PersonFilter filter, QueryOptions queryOptions) {
        // TODO: Re-do this method
        return List.of();
    }

    @Override
    public ActorBo getById(Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public ActorBo create(ActorBo actor, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public ActorBo update(long id, ActorBo actor, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public void validateActor(ActorBo actor) {
        // TODO: Re-do this method
    }

    @Override
    public void validateName(String name) {
        // TODO: Re-do this method
    }

    @Override
    public void validateAge(int age) {
        // TODO: Re-do this method
    }

    @Override
    public void validateNationality(String nationality) {
        // TODO: Re-do this method
    }

    @Override
    public void deleteById(long id, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
    }

}

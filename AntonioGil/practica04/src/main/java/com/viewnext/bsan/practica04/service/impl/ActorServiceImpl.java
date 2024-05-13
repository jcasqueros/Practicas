package com.viewnext.bsan.practica04.service.impl;

import com.viewnext.bsan.practica04.bo.ActorBo;
import com.viewnext.bsan.practica04.dto.request.PersonFilterDto;
import com.viewnext.bsan.practica04.dto.request.QueryOptionsDto;
import com.viewnext.bsan.practica04.entity.Actor;
import com.viewnext.bsan.practica04.exception.service.BadInputDataException;
import com.viewnext.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnext.bsan.practica04.repository.ActorRepository;
import com.viewnext.bsan.practica04.repository.custom.CustomActorRepository;
import com.viewnext.bsan.practica04.service.ActorService;
import com.viewnext.bsan.practica04.util.MessageBuilder;
import com.viewnext.bsan.practica04.util.constants.Messages;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelActorMapper;
import com.viewnext.bsan.practica04.repository.custom.CustomActorRepository;
import jakarta.transaction.Transactional;
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
    public List<ActorBo> getAll(PersonFilterDto filter, QueryOptionsDto queryOptions) {
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

package com.viewnext.bsan.practica04.service.impl;

import com.viewnext.bsan.practica04.bo.DirectorBo;
import com.viewnext.bsan.practica04.dto.request.PersonFilterDto;
import com.viewnext.bsan.practica04.dto.request.QueryOptionsDto;
import com.viewnext.bsan.practica04.entity.Director;
import com.viewnext.bsan.practica04.exception.service.BadInputDataException;
import com.viewnext.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnext.bsan.practica04.repository.DirectorRepository;
import com.viewnext.bsan.practica04.repository.custom.CustomDirectorRepository;
import com.viewnext.bsan.practica04.service.DirectorService;
import com.viewnext.bsan.practica04.util.MessageBuilder;
import com.viewnext.bsan.practica04.util.constants.Messages;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelDirectorMapper;
import com.viewnext.bsan.practica04.repository.custom.CustomDirectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code DirectorService} interface.
 *
 * @author Antonio Gil
 */
@Service
public class DirectorServiceImpl implements DirectorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectorServiceImpl.class.getCanonicalName());

    private final DirectorRepository repository;
    private final CustomDirectorRepository customRepository;
    private final ServiceLevelDirectorMapper mapper;

    public DirectorServiceImpl(DirectorRepository repository, CustomDirectorRepository customRepository,
                               ServiceLevelDirectorMapper mapper) {
        this.repository = repository;
        this.customRepository = customRepository;
        this.mapper = mapper;
    }

    @Override
    public List<DirectorBo> getAll(PersonFilterDto filter, QueryOptionsDto queryOptions) {
        // TODO: Re-do this method
        return List.of();
    }

    @Override
    public DirectorBo getById(Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public DirectorBo create(DirectorBo director, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public DirectorBo update(long id, DirectorBo director, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public void validateDirector(DirectorBo director) {
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

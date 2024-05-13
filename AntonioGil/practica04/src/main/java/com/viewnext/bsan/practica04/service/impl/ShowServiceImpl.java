package com.viewnext.bsan.practica04.service.impl;

import com.viewnext.bsan.practica04.bo.ShowBo;
import com.viewnext.bsan.practica04.dto.request.QueryOptionsDto;
import com.viewnext.bsan.practica04.dto.request.WatchableFilterDto;
import com.viewnext.bsan.practica04.entity.Show;
import com.viewnext.bsan.practica04.exception.service.BadInputDataException;
import com.viewnext.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnext.bsan.practica04.repository.ShowRepository;
import com.viewnext.bsan.practica04.repository.custom.CustomShowRepository;
import com.viewnext.bsan.practica04.service.ShowService;
import com.viewnext.bsan.practica04.util.MessageBuilder;
import com.viewnext.bsan.practica04.util.constants.Messages;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelShowMapper;
import com.viewnext.bsan.practica04.repository.custom.CustomShowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code ShowService} interface.
 *
 * @author Antonio Gil
 */
@Service
public class ShowServiceImpl implements ShowService {

    private static final Logger LOGGER = LoggerFactory.getLogger("ShowService");

    private final ShowRepository repository;
    private final CustomShowRepository customRepository;
    private final ServiceLevelShowMapper mapper;

    public ShowServiceImpl(ShowRepository repository, CustomShowRepository customRepository,
                           ServiceLevelShowMapper mapper) {
        this.repository = repository;
        this.customRepository = customRepository;
        this.mapper = mapper;
    }


    @Override
    public List<ShowBo> getAll(WatchableFilterDto filter, QueryOptionsDto queryOptions) {
        // TODO: Re-do this method
        return List.of();
    }

    @Override
    public ShowBo getById(Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public ShowBo create(ShowBo film, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public ShowBo update(long id, ShowBo show, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public void validateShow(ShowBo show) {
        // TODO: Re-do this method
    }

    @Override
    public void validateTitle(String title) {
        // TODO: Re-do this method
    }

    @Override
    public void validateYear(int year) {
        // TODO: Re-do this method
    }

    @Override
    public void deleteById(long id, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
    }
}

package com.pracs.films.bussiness.services.impl;

import com.pracs.films.bussiness.bo.DirectorBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.DirectorService;
import com.pracs.films.exceptions.DuplicatedIdException;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.repositories.jpa.DirectorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the interface {@link DirectorService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final ModelToBoConverter modelToBoConverter;

    private final BoToModelConverter boToModelConverter;

    private final DirectorRepository directorRepository;

    private static final String errorPerson = "Person not found";

    private static final String errorService = "Error in service layer";

    @Override
    public DirectorBO save(DirectorBO directorBO) throws ServiceException {
        try {

            if (directorRepository.existsById(directorBO.getId())) {
                throw new DuplicatedIdException("Existing person");
            }

            return modelToBoConverter.directorModelToBo(
                    directorRepository.save(boToModelConverter.directorBoToModel(directorBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public DirectorBO update(DirectorBO directorBO) throws ServiceException {
        try {
            DirectorBO savedDirectorBO = modelToBoConverter.directorModelToBo(
                    directorRepository.findById(directorBO.getId())
                            .orElseThrow(() -> new EntityNotFoundException(errorPerson)));
            savedDirectorBO.setName(directorBO.getName());
            savedDirectorBO.setAge(directorBO.getAge());
            savedDirectorBO.setNationality(directorBO.getNationality());
            return modelToBoConverter.directorModelToBo(
                    directorRepository.save(boToModelConverter.directorBoToModel(savedDirectorBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public DirectorBO findById(long id) throws ServiceException {
        try {
            return modelToBoConverter.directorModelToBo(
                    directorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorPerson)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<DirectorBO> findAll() throws ServiceException {
        List<DirectorBO> directorsBO = new ArrayList<>();

        try {
            directorsBO = directorRepository.findAll().stream().map(modelToBoConverter::directorModelToBo).toList();

            if (directorsBO.isEmpty()) {
                throw new EmptyException("No directors");
            }

            return directorsBO;
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {

            if (!directorRepository.existsById(id)) {
                log.error("EntityNotFoundException");
                throw new EntityNotFoundException(errorPerson);
            }

            directorRepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }
}

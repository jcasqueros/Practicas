package com.viewnext.films.businesslayer.service.impl;

import com.viewnext.films.businesslayer.bo.DirectorBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.DirectorService;
import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.repository.criteria.DirectorCriteriaRepository;
import com.viewnext.films.persistencelayer.repository.jpa.DirectorJPARepository;
import com.viewnext.films.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing directors.
 *
 * <p>This class provides implementations for the methods declared in the {@link DirectorService} interface.</p>
 *
 * <p>It uses the {@link DirectorCriteriaRepository} and {@link DirectorJPARepository} to interact with the persistence
 * layer, and the {@link Converter} to convert between entity and business objects.</p>
 *
 * <p>It also handles exceptions and provides meaningful error messages.</p>
 *
 * @author Francisco Balonero Olivera
 */
@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    /**
     * The director criteria repository.
     */
    private final DirectorCriteriaRepository directorCriteriaRepository;

    /**
     * The director JPA repository.
     */
    private final DirectorJPARepository directorJPARepository;

    /**
     * The converter for converting between entity and business objects.
     */
    private final Converter converter;

    @Override
    public DirectorBO criteriaGetById(long id) throws ServiceException {
        try {
            return converter.directorEntityToBO(
                    directorCriteriaRepository.getDirectorById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The director could not be searched", e);
        }

    }

    @Override
    public List<DirectorBO> criteriaGetAll() throws ServiceException {
        try {
            List<Director> directors = directorCriteriaRepository.getAllDirectors();
            if (!directors.isEmpty()) {
                return directors.stream().map(converter::directorEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }

        } catch (NestedRuntimeException e) {
            throw new ServiceException("The directors could not be searched", e);
        }

    }

    @Override
    public void criteriaDeleteById(Long id) throws ServiceException {
        try {
            directorCriteriaRepository.deleteDirector(id);
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The director could not be deleted", e);
        }
    }

    @Override
    public DirectorBO criteriaUpdate(DirectorBO directorBO) throws ServiceException {
        criteriaGetById(directorBO.getId());
        try {
            return converter.directorEntityToBO(
                    directorCriteriaRepository.updateDirector(converter.directorBOToEntity(directorBO)));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The director could not be updated", e);
        }
    }

    @Override
    public DirectorBO criteriaCreate(DirectorBO directorBO) throws ServiceException {
        try {
            return converter.directorEntityToBO(
                    directorCriteriaRepository.createDirector(converter.directorBOToEntity(directorBO)));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The director could not be created", e);
        }

    }

    @Override
    public DirectorBO jpaGetById(long id) throws ServiceException {
        try {
            return converter.directorEntityToBO(directorJPARepository.findById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The director could not be searched", e);
        }

    }

    @Override
    public List<DirectorBO> jpaGetAll() throws ServiceException {
        try {
            List<Director> directors = directorJPARepository.findAll();
            if (!directors.isEmpty()) {
                return directors.stream().map(converter::directorEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The directors could not be searched", e);
        }
    }

    @Override
    public void jpaDeleteById(Long id) throws ServiceException {
        try {
            directorJPARepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The director could not be deleted", e);
        }
    }

    @Override
    public DirectorBO jpaUpdate(DirectorBO directorBO) throws ServiceException {
        try {
            if (directorJPARepository.existsById(directorBO.getId())) {
                return converter.directorEntityToBO(
                        directorJPARepository.save(converter.directorBOToEntity(directorBO)));
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The director could not be updated", e);
        }

    }

    @Override
    public DirectorBO jpaCreate(DirectorBO directorBO) throws ServiceException {
        try {
            return converter.directorEntityToBO(directorJPARepository.save(converter.directorBOToEntity(directorBO)));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The director could not be created", e);
        }
    }

}

package com.viewnext.films.businesslayer.service.impl;

import com.viewnext.films.businesslayer.bo.SerieBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.SerieService;
import com.viewnext.films.persistencelayer.entity.Serie;
import com.viewnext.films.persistencelayer.repository.criteria.SerieCriteriaRepository;
import com.viewnext.films.persistencelayer.repository.jpa.SerieJPARepository;
import com.viewnext.films.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing series.
 *
 * <p>This class provides implementations for the methods declared in the {@link SerieService} interface.</p>
 *
 * <p>It uses the {@link SerieCriteriaRepository} and {@link SerieJPARepository} to interact with the persistence
 * layer, and the {@link Converter} to convert between entity and business objects.</p>
 *
 * <p>It also handles exceptions and provides meaningful error messages.</p>
 *
 * @author Francisco Balonero Olivera
 */
@Service
@RequiredArgsConstructor
public class SerieServiceImpl implements SerieService {

    /**
     * The serie criteria repository.
     */
    private final SerieCriteriaRepository serieCriteriaRepository;

    /**
     * The serie JPA repository.
     */
    private final SerieJPARepository serieJPARepository;

    /**
     * The converter for converting between entity and business objects.
     */
    private final Converter converter;

    @Override
    public SerieBO criteriaGetById(long id) throws ServiceException {
        try {
            return converter.serieEntityToBO(
                    serieCriteriaRepository.getSerieById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The serie could not be searched", e);
        }

    }

    @Override
    public List<SerieBO> criteriaGetAll() throws ServiceException {
        try {
            List<Serie> series = serieCriteriaRepository.getAllSeries();
            if (!series.isEmpty()) {
                return series.stream().map(converter::serieEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }

        } catch (NestedRuntimeException e) {
            throw new ServiceException("The series could not be searched", e);
        }

    }

    @Override
    public void criteriaDeleteById(Long id) throws ServiceException {
        try {
            serieCriteriaRepository.deleteSerie(id);
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The serie could not be deleted", e);
        }
    }

    @Override
    public SerieBO criteriaUpdate(SerieBO serieBO) throws ServiceException {
        criteriaGetById(serieBO.getId());
        try {
            return converter.serieEntityToBO(serieCriteriaRepository.updateSerie(converter.serieBOToEntity(serieBO)));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The serie could not be updated", e);
        }
    }

    @Override
    public SerieBO criteriaCreate(SerieBO serieBO) throws ServiceException {
        try {
            return converter.serieEntityToBO(serieCriteriaRepository.createSerie(converter.serieBOToEntity(serieBO)));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The serie could not be created", e);
        }

    }

    @Override
    public SerieBO jpaGetById(long id) throws ServiceException {
        try {
            return converter.serieEntityToBO(serieJPARepository.findById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The serie could not be searched", e);
        }

    }

    @Override
    public List<SerieBO> jpaGetAll() throws ServiceException {
        try {
            List<Serie> series = serieJPARepository.findAll();
            if (!series.isEmpty()) {
                return series.stream().map(converter::serieEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The series could not be searched", e);
        }
    }

    @Override
    public void jpaDeleteById(Long id) throws ServiceException {
        try {
            serieJPARepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The serie could not be deleted", e);
        }
    }

    @Override
    public SerieBO jpaUpdate(SerieBO serieBO) throws ServiceException {
        try {
            if (serieJPARepository.existsById(serieBO.getId())) {
                return converter.serieEntityToBO(serieJPARepository.save(converter.serieBOToEntity(serieBO)));
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The serie could not be updated", e);
        }

    }

    @Override
    public SerieBO jpaCreate(SerieBO serieBO) throws ServiceException {
        try {
            return converter.serieEntityToBO(serieJPARepository.save(converter.serieBOToEntity(serieBO)));
        } catch (NestedRuntimeException e) {
            throw new ServiceException("The serie could not be created", e);
        }
    }

}

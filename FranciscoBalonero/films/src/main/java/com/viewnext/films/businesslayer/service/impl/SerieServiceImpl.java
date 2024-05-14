package com.viewnext.films.businesslayer.service.impl;

import com.viewnext.films.businesslayer.bo.SerieBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.SerieService;
import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.entity.Producer;
import com.viewnext.films.persistencelayer.entity.Serie;
import com.viewnext.films.persistencelayer.repository.criteria.SerieCriteriaRepository;
import com.viewnext.films.persistencelayer.repository.jpa.ActorJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.DirectorJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.ProducerJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.SerieJPARepository;
import com.viewnext.films.util.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
@Slf4j
public class SerieServiceImpl implements SerieService {

    // Inyección de dependencias
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
    private final ActorJPARepository actorJPARepository;
    private final DirectorJPARepository directorJPARepository;
    private final ProducerJPARepository producerJPARepository;

    // Métodos para interactuar con la capa de persistencia utilizando Criteria API
    @Override
    public SerieBO criteriaGetById(long id) throws ServiceException {
        try {
            // Busca un serie por ID utilizando Criteria API
            return converter.serieEntityToBO(
                    serieCriteriaRepository.getSerieById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching serie by id: {}", id, e);
            throw new ServiceException("The serie could not be searched", e);
        }
    }

    @Override
    public List<SerieBO> criteriaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder)
            throws ServiceException {
        try {
            // Crea un objeto Pageable con la información de paginación y ordenación
            Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber)
                    .withSort((sortOrder ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
            // Busca todos los seriees utilizando Criteria API
            List<Serie> series = serieCriteriaRepository.getAllSeries(pageable);
            if (!series.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return series.stream().map(converter::serieEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching all series", e);
            throw new ServiceException("The series could not be searched", e);
        }
    }

    @Override
    public void criteriaDeleteById(Long id) throws ServiceException {
        try {
            // Elimina un serie por ID utilizando Criteria API
            serieCriteriaRepository.deleteSerie(id);
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error deleting serie by id: {}", id, e);
            throw new ServiceException("The serie could not be deleted", e);
        }
    }

    @Override
    public SerieBO criteriaUpdate(SerieBO serieBO) throws ServiceException {
        // Verifica si el serie existe antes de actualizarlo
        criteriaGetById(serieBO.getId());
        try {
            // Actualiza un serie utilizando Criteria API
            return converter.serieEntityToBO(serieCriteriaRepository.updateSerie(converter.serieBOToEntity(serieBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error updating serie: {}", serieBO, e);
            throw new ServiceException("The serie could not be updated", e);
        }
    }

    @Override
    public SerieBO criteriaCreate(SerieBO serieBO) throws ServiceException {
        try {
            // Crea un serie utilizando Criteria API
            return converter.serieEntityToBO(serieCriteriaRepository.createSerie(converter.serieBOToEntity(serieBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error creating serie: {}", serieBO, e);
            throw new ServiceException("The serie could not be created", e);
        }
    }

    // Métodos para interactuar con la capa de persistencia utilizando JPA
    @Override
    public SerieBO jpaGetById(long id) throws ServiceException {
        try {
            // Busca un serie por ID utilizando JPA
            return converter.serieEntityToBO(serieJPARepository.findById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching serie by id: {}", id, e);
            throw new ServiceException("The serie could not be searched", e);
        }
    }

    @Override
    public List<SerieBO> jpaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder)
            throws ServiceException {
        try {
            // Crea un objeto Pageable con la información de paginación y ordenación
            Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber)
                    .withSort((sortOrder ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
            // Busca todos los seriees utilizando JPA
            List<Serie> series = serieJPARepository.findAll(pageable).getContent();
            if (!series.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return series.stream().map(converter::serieEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching all series", e);
            throw new ServiceException("The series could not be searched", e);
        }
    }

    @Override
    public void jpaDeleteById(Long id) throws ServiceException {
        try {
            // Elimina un serie por ID utilizando JPA
            serieJPARepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error deleting serie by id: {}", id, e);
            throw new ServiceException("The serie could not be deleted", e);
        }
    }

    @Override
    public SerieBO jpaUpdate(SerieBO serieBO) throws ServiceException {
        try {
            // Verifica si el serie existe antes de actualizarlo
            if (serieJPARepository.existsById(serieBO.getId())) {
                // Actualiza un serie utilizando JPA
                return converter.serieEntityToBO(serieJPARepository.save(converter.serieBOToEntity(serieBO)));
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error updating serie: {}", serieBO, e);
            throw new ServiceException("The serie could not be updated", e);
        }
    }

    @Override
    public SerieBO jpaCreate(SerieBO serieBO) throws ServiceException {
        try {
            // Crea un serie utilizando JPA
            return converter.serieEntityToBO(serieJPARepository.save(converter.serieBOToEntity(serieBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error creating serie: {}", serieBO, e);
            throw new ServiceException("The serie could not be created", e);
        }
    }

    @Override
    public List<SerieBO> filterSeries(List<String> titles, List<Integer> releaseYears, List<String> directors,
            List<String> producers, List<String> actors, int pageNumber, int pageSize, String sortBy, boolean sortOrder)
            throws ServiceException {
        try {
            // Crea un objeto Pageable con la información de paginación y ordenación
            Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber)
                    .withSort((sortOrder ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
            List<Director> foundDirectors = new ArrayList<>();
            List<Actor> foundActors = new ArrayList<>();
            List<Producer> foundProducers = new ArrayList<>();

            if (directors != null && !directors.isEmpty()) {
                directors.forEach(s -> foundDirectors.addAll(directorJPARepository.findByName(s)));
            }
            if (producers != null && !producers.isEmpty()) {
                producers.forEach(s -> foundProducers.addAll(producerJPARepository.findByName(s)));
            }
            if (actors != null && !actors.isEmpty()) {
                actors.forEach(s -> foundActors.addAll(actorJPARepository.findByName(s)));
            }
            // Busca películas que coinciden con los filtros utilizando Criteria API
            List<Serie> series = serieCriteriaRepository.filterSeries(titles, releaseYears, foundDirectors,
                    foundProducers, foundActors, pageable);

            if (!series.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return series.stream().map(converter::serieEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error filtering series", e);
            throw new ServiceException("The series could not be filtered", e);
        }
    }
}

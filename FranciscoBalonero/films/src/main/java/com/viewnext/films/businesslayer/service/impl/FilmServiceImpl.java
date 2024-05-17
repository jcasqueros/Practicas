package com.viewnext.films.businesslayer.service.impl;

import com.viewnext.films.businesslayer.bo.ActorBO;
import com.viewnext.films.businesslayer.bo.FilmBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.FilmService;
import com.viewnext.films.businesslayer.service.WebClientService;
import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.entity.Film;
import com.viewnext.films.persistencelayer.entity.Producer;
import com.viewnext.films.persistencelayer.repository.criteria.FilmCriteriaRepository;
import com.viewnext.films.persistencelayer.repository.jpa.ActorJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.DirectorJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.FilmJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.ProducerJPARepository;
import com.viewnext.films.util.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for managing films.
 *
 * <p>This class provides implementations for the methods declared in the {@link FilmService} interface.</p>
 *
 * <p>It uses the {@link FilmCriteriaRepository} and {@link FilmJPARepository} to interact with the persistence
 * layer, and the {@link Converter} to convert between entity and business objects.</p>
 *
 * <p>It also handles exceptions and provides meaningful error messages.</p>
 *
 * @author Francisco Balonero Olivera
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {

    // Inyección de dependencias

    private final WebClient webClient;
    /**
     * The film criteria repository.
     */
    private final FilmCriteriaRepository filmCriteriaRepository;
    /**
     * The film JPA repository.
     */
    private final FilmJPARepository filmJPARepository;
    /**
     * The converter for converting between entity and business objects.
     */
    private final Converter converter;
    /**
     * The Actor JPA repository.
     */
    private final ActorJPARepository actorJPARepository;
    /**
     * The Director JPA repository.
     */
    private final DirectorJPARepository directorJPARepository;
    /**
     * The Producer JPA repository.
     */
    private final ProducerJPARepository producerJPARepository;

    private final WebClientService webClientService;

    // Métodos para interactuar con la capa de persistencia utilizando Criteria API
    @Override
    public FilmBO criteriaGetById(long id) throws ServiceException {
        try {
            // Busca un film por ID utilizando Criteria API
            return converter.filmEntityToBO(filmCriteriaRepository.getFilmById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching film by id: {}", id, e);
            throw new ServiceException("The film could not be searched", e);
        }
    }

    @Override
    public List<FilmBO> criteriaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder)
            throws ServiceException {
        try {
            // Crea un objeto Pageable con la información de paginación y ordenación
            Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber)
                    .withSort((sortOrder ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
            // Busca todos los filmes utilizando Criteria API
            List<Film> films = filmCriteriaRepository.getAllFilms(pageable);
            if (!films.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return films.stream().map(converter::filmEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching all films", e);
            throw new ServiceException("The films could not be searched", e);
        }
    }

    @Override
    public void criteriaDeleteById(Long id) throws ServiceException {
        try {
            // Elimina un film por ID utilizando Criteria API
            filmCriteriaRepository.deleteFilm(id);
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error deleting film by id: {}", id, e);
            throw new ServiceException("The film could not be deleted", e);
        }
    }

    @Override
    public FilmBO criteriaUpdate(FilmBO filmBO) throws ServiceException {
        // Verifica si el film existe antes de actualizarlo
        criteriaGetById(filmBO.getId());
        try {
            // Actualiza un film utilizando Criteria API
            return converter.filmEntityToBO(filmCriteriaRepository.updateFilm(converter.filmBOToEntity(filmBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error updating film: {}", filmBO, e);
            throw new ServiceException("The film could not be updated", e);
        }
    }

    @Override
    public FilmBO criteriaCreate(FilmBO filmBO) throws ServiceException {
        try {
            for (ActorBO actorBO : filmBO.getActors()) {
                webClientService.existsActor(actorBO.getId());
            }
            webClientService.existsDirector(filmBO.getDirector().getId());
            webClientService.existsProducer(filmBO.getProducer().getId());
            // Crea un film utilizando Criteria API
            return converter.filmEntityToBO(filmCriteriaRepository.createFilm(converter.filmBOToEntity(filmBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error creating film: {}", filmBO, e);
            throw new ServiceException("The film could not be created", e);
        }
    }

    // Métodos para interactuar con la capa de persistencia utilizando JPA
    @Override
    public FilmBO jpaGetById(long id) throws ServiceException {
        try {
            // Busca un film por ID utilizando JPA
            return converter.filmEntityToBO(filmJPARepository.findById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching film by id: {}", id, e);
            throw new ServiceException("The film could not be searched", e);
        }
    }

    @Override
    public List<FilmBO> jpaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder)
            throws ServiceException {
        try {
            // Crea un objeto Pageable con la información de paginación y ordenación
            Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber)
                    .withSort((sortOrder ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
            // Busca todos los filmes utilizando JPA
            List<Film> films = filmJPARepository.findAll(pageable).getContent();
            if (!films.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return films.stream().map(converter::filmEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching all films", e);
            throw new ServiceException("The films could not be searched", e);
        }
    }

    @Override
    public void jpaDeleteById(Long id) throws ServiceException {
        try {
            // Elimina un film por ID utilizando JPA
            filmJPARepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error deleting film by id: {}", id, e);
            throw new ServiceException("The film could not be deleted", e);
        }
    }

    @Override
    public FilmBO jpaUpdate(FilmBO filmBO) throws ServiceException {
        try {
            // Verifica si el film existe antes de actualizarlo
            if (filmJPARepository.existsById(filmBO.getId())) {
                // Actualiza un film utilizando JPA
                return converter.filmEntityToBO(filmJPARepository.save(converter.filmBOToEntity(filmBO)));
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error updating film: {}", filmBO, e);
            throw new ServiceException("The film could not be updated", e);
        }
    }

    @Override
    public FilmBO jpaCreate(FilmBO filmBO) throws ServiceException {
        try {
            for (ActorBO actorBO : filmBO.getActors()) {
                webClientService.existsActor(actorBO.getId());
            }
            webClientService.existsDirector(filmBO.getDirector().getId());
            webClientService.existsProducer(filmBO.getProducer().getId());
            // Crea un film utilizando JPA
            return converter.filmEntityToBO(filmJPARepository.save(converter.filmBOToEntity(filmBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error creating film: {}", filmBO, e);
            throw new ServiceException("The film could not be created", e);
        }
    }

    @Override
    public List<FilmBO> filterFilms(List<String> titles, List<Integer> releaseYears, List<String> directors,
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
            List<Film> films = filmCriteriaRepository.filterFilms(titles, releaseYears, foundDirectors, foundProducers,
                    foundActors, pageable);

            if (!films.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return films.stream().map(converter::filmEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error filtering films", e);
            throw new ServiceException("The films could not be filtered", e);
        }
    }

}

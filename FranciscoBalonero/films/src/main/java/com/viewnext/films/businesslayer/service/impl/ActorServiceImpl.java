package com.viewnext.films.businesslayer.service.impl;

import com.viewnext.films.businesslayer.bo.ActorBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.ActorService;
import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.repository.criteria.ActorCriteriaRepository;
import com.viewnext.films.persistencelayer.repository.jpa.ActorJPARepository;
import com.viewnext.films.util.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing actors.
 *
 * <p>This class provides implementations for the methods declared in the {@link ActorService} interface.</p>
 *
 * <p>It uses the {@link ActorCriteriaRepository} and {@link ActorJPARepository} to interact with the persistence
 * layer, and the {@link Converter} to convert between entity and business objects.</p>
 *
 * <p>It also handles exceptions and provides meaningful error messages.</p>
 *
 * @author Francisco Balonero Olivera
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ActorServiceImpl implements ActorService {

    // Inyección de dependencias
    /**
     * The actor criteria repository.
     */
    private final ActorCriteriaRepository actorCriteriaRepository;
    /**
     * The actor JPA repository.
     */
    private final ActorJPARepository actorJPARepository;
    /**
     * The converter for converting between entity and business objects.
     */
    private final Converter converter;

    // Métodos para interactuar con la capa de persistencia utilizando Criteria API
    @Override
    public ActorBO criteriaGetById(long id) throws ServiceException {
        try {
            // Busca un actor por ID utilizando Criteria API
            return converter.actorEntityToBO(
                    actorCriteriaRepository.getActorById(id).orElseThrow(NotFoundException::new));

        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching actor by id: {}", id, e);
            throw new ServiceException("The actor could not be searched", e);
        }
    }

    @Override
    public List<ActorBO> criteriaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder)
            throws ServiceException {
        try {
            // Crea un objeto Pageable con la información de paginación y ordenación
            Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber)
                    .withSort((sortOrder ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
            // Busca todos los actores utilizando Criteria API
            List<Actor> actors = actorCriteriaRepository.getAllActors(pageable);
            if (!actors.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return actors.stream().map(converter::actorEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching all actors", e);
            throw new ServiceException("The actors could not be searched", e);
        }
    }

    @Override
    public void criteriaDeleteById(Long id) throws ServiceException {
        try {
            // Elimina un actor por ID utilizando Criteria API
            actorCriteriaRepository.deleteActor(id);
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error deleting actor by id: {}", id, e);
            throw new ServiceException("The actor could not be deleted", e);
        }
    }

    @Override
    public ActorBO criteriaUpdate(ActorBO actorBO) throws ServiceException {
        // Verifica si el actor existe antes de actualizarlo
        criteriaGetById(actorBO.getId());
        try {
            // Actualiza un actor utilizando Criteria API
            return converter.actorEntityToBO(actorCriteriaRepository.updateActor(converter.actorBOToEntity(actorBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error updating actor: {}", actorBO, e);
            throw new ServiceException("The actor could not be updated", e);
        }
    }

    @Override
    public ActorBO criteriaCreate(ActorBO actorBO) throws ServiceException {
        try {
            // Crea un actor utilizando Criteria API
            return converter.actorEntityToBO(actorCriteriaRepository.createActor(converter.actorBOToEntity(actorBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error creating actor: {}", actorBO, e);
            throw new ServiceException("The actor could not be created", e);
        }
    }

    // Métodos para interactuar con la capa de persistencia utilizando JPA
    @Override
    public ActorBO jpaGetById(long id) throws ServiceException {
        try {
            // Busca un actor por ID utilizando JPA
            return converter.actorEntityToBO(actorJPARepository.findById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching actor by id: {}", id, e);
            throw new ServiceException("The actor could not be searched", e);
        }
    }

    @Override
    public List<ActorBO> jpaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder)
            throws ServiceException {
        try {
            // Crea un objeto Pageable con la información de paginación y ordenación
            Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber)
                    .withSort((sortOrder ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
            // Busca todos los actores utilizando JPA
            List<Actor> actors = actorJPARepository.findAll(pageable).getContent();
            if (!actors.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return actors.stream().map(converter::actorEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching all actors", e);
            throw new ServiceException("The actors could not be searched", e);
        }
    }

    @Override
    public List<ActorBO> filterActors(List<String> names, List<Integer> ages, List<String> nationalities,
            int pageNumber, int pageSize, String sortBy, boolean sortOrder) throws ServiceException {
        try {
            // Crea un objeto Pageable con la información de paginación y ordenación
            Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber)
                    .withSort((sortOrder ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
            // Busca actores que coinciden con los filtros utilizando Criteria API
            List<Actor> actors = actorCriteriaRepository.filterActors(names, ages, nationalities, pageable);
            if (!actors.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return actors.stream().map(converter::actorEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error filtering actors", e);
            throw new ServiceException("The actors could not be filtered", e);
        }
    }

    @Override
    public void jpaDeleteById(Long id) throws ServiceException {
        try {
            // Elimina un actor por ID utilizando JPA
            actorJPARepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error deleting actor by id: {}", id, e);
            throw new ServiceException("The actor could not be deleted", e);
        }
    }

    @Override
    public ActorBO jpaUpdate(ActorBO actorBO) throws ServiceException {
        try {
            // Verifica si el actor existe antes de actualizarlo
            if (actorJPARepository.existsById(actorBO.getId())) {
                // Actualiza un actor utilizando JPA
                return converter.actorEntityToBO(actorJPARepository.save(converter.actorBOToEntity(actorBO)));
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error updating actor: {}", actorBO, e);
            throw new ServiceException("The actor could not be updated", e);
        }
    }

    @Override
    public ActorBO jpaCreate(ActorBO actorBO) throws ServiceException {
        try {
            // Crea un actor utilizando JPA
            return converter.actorEntityToBO(actorJPARepository.save(converter.actorBOToEntity(actorBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error creating actor: {}", actorBO, e);
            throw new ServiceException("The actor could not be created", e);
        }
    }

}

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@Slf4j
public class DirectorServiceImpl implements DirectorService {
    public static final String DIRECTORS_COULD_NOT_BE_SEARCHED = "The directors could not be searched";

    // Inyección de dependencias
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

    // Métodos para interactuar con la capa de persistencia utilizando Criteria API
    @Override
    public DirectorBO criteriaGetById(long id) throws ServiceException {
        try {
            // Busca un director por ID utilizando Criteria API
            return converter.directorEntityToBO(
                    directorCriteriaRepository.getDirectorById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching director by id: {}", id, e);
            throw new ServiceException("The director could not be searched", e);
        }
    }

    @Override
    public List<DirectorBO> criteriaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder)
            throws ServiceException {
        try {
            // Crea un objeto Pageable con la información de paginación y ordenación
            Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber)
                    .withSort((sortOrder ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
            // Busca todos los directores utilizando Criteria API
            List<Director> directors = directorCriteriaRepository.getAllDirectors(pageable);
            if (!directors.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return directors.stream().map(converter::directorEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching all directors", e);
            throw new ServiceException(DIRECTORS_COULD_NOT_BE_SEARCHED, e);
        }
    }

    @Override
    public void criteriaDeleteById(Long id) throws ServiceException {
        try {
            // Elimina un director por ID utilizando Criteria API
            directorCriteriaRepository.deleteDirector(id);
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error deleting director by id: {}", id, e);
            throw new ServiceException("The director could not be deleted", e);
        }
    }

    @Override
    public DirectorBO criteriaUpdate(DirectorBO directorBO) throws ServiceException {
        // Verifica si el director existe antes de actualizarlo
        criteriaGetById(directorBO.getId());
        try {
            // Actualiza un director utilizando Criteria API
            return converter.directorEntityToBO(
                    directorCriteriaRepository.updateDirector(converter.directorBOToEntity(directorBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error updating director: {}", directorBO, e);
            throw new ServiceException("The director could not be updated", e);
        }
    }

    @Override
    public DirectorBO criteriaCreate(DirectorBO directorBO) throws ServiceException {
        try {
            // Crea un director utilizando Criteria API
            return converter.directorEntityToBO(
                    directorCriteriaRepository.createDirector(converter.directorBOToEntity(directorBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error creating director: {}", directorBO, e);
            throw new ServiceException("The director could not be created", e);
        }
    }

    @Override
    public List<DirectorBO> criteriaFindByNameAndAge(String name, int age) throws ServiceException {
        try {
            // Busca directores que coinciden con el nombre y la edad utilizando Criteria API
            List<Director> directors = directorCriteriaRepository.getDirectorsByNameAndAge(name, age);
            if (!directors.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return directors.stream().map(converter::directorEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching directors by name and age", e);
            throw new ServiceException(DIRECTORS_COULD_NOT_BE_SEARCHED, e);
        }
    }

    // Métodos para interactuar con la capa de persistencia utilizando JPA
    @Override
    public DirectorBO jpaGetById(long id) throws ServiceException {
        try {
            // Busca un director por ID utilizando JPA
            return converter.directorEntityToBO(directorJPARepository.findById(id).orElseThrow(NotFoundException::new));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching director by id: {}", id, e);
            throw new ServiceException("The director could not be searched", e);
        }
    }

    @Override
    public List<DirectorBO> jpaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder)
            throws ServiceException {
        try {
            // Crea un objeto Pageable con la información de paginación y ordenación
            Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber)
                    .withSort((sortOrder ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
            // Busca todos los directores utilizando JPA
            List<Director> directors = directorJPARepository.findAll(pageable).getContent();
            if (!directors.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return directors.stream().map(converter::directorEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching all directors", e);
            throw new ServiceException(DIRECTORS_COULD_NOT_BE_SEARCHED, e);
        }
    }

    @Override
    public void jpaDeleteById(Long id) throws ServiceException {
        try {
            // Elimina un director por ID utilizando JPA
            directorJPARepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error deleting director by id: {}", id, e);
            throw new ServiceException("The director could not be deleted", e);
        }
    }

    @Override
    public DirectorBO jpaUpdate(DirectorBO directorBO) throws ServiceException {
        try {
            // Verifica si el director existe antes de actualizarlo
            if (directorJPARepository.existsById(directorBO.getId())) {
                // Actualiza un director utilizando JPA
                return converter.directorEntityToBO(
                        directorJPARepository.save(converter.directorBOToEntity(directorBO)));
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error updating director: {}", directorBO, e);
            throw new ServiceException("The director could not be updated", e);
        }
    }

    @Override
    public DirectorBO jpaCreate(DirectorBO directorBO) throws ServiceException {
        try {
            // Crea un director utilizando JPA
            return converter.directorEntityToBO(directorJPARepository.save(converter.directorBOToEntity(directorBO)));
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error creating director: {}", directorBO, e);
            throw new ServiceException("The director could not be created", e);
        }
    }

    @Override
    public List<DirectorBO> filterDirectors(List<String> names, List<Integer> ages, List<String> nationalities,
            int pageNumber, int pageSize, String sortBy, boolean sortOrder) throws ServiceException {
        try {
            // Crea un objeto Pageable con la información de paginación y ordenación
            Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNumber)
                    .withSort((sortOrder ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
            // Busca directores que coinciden con los filtros utilizando Criteria API
            List<Director> directors = directorCriteriaRepository.filterDirectors(names, ages, nationalities, pageable);
            if (!directors.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return directors.stream().map(converter::directorEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error filtering directors", e);
            throw new ServiceException("The directors could not be filtered", e);
        }
    }

    @Override
    public List<DirectorBO> jpaFindByNameAndAge(String name, int age) throws ServiceException {
        try {
            // Busca directores que coinciden con el nombre y la edad utilizando JPA
            List<Director> directors = directorJPARepository.findByNameAndAge(name, age);
            if (!directors.isEmpty()) {
                // Convierte la lista de entidades a una lista de objetos de negocio
                return directors.stream().map(converter::directorEntityToBO).toList();
            } else {
                throw new NotFoundException();
            }
        } catch (NestedRuntimeException e) {
            // Maneja excepciones y registra un error en el log
            log.error("Error searching directors by name and age", e);
            throw new ServiceException(DIRECTORS_COULD_NOT_BE_SEARCHED, e);
        }
    }
}

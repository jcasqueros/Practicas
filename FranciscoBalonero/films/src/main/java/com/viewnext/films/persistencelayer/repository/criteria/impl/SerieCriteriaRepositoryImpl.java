package com.viewnext.films.persistencelayer.repository.criteria.impl;

import com.viewnext.films.persistencelayer.entity.Serie;
import com.viewnext.films.persistencelayer.repository.criteria.SerieCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link SerieCriteriaRepository} interface.
 *
 * <p>This class provides a implementation of the CRUD operations for series using JPA Criteria API.</p>
 *
 * @author Francisco Balonero Olivera
 * @see SerieCriteriaRepository
 */

@Repository
public class SerieCriteriaRepositoryImpl implements SerieCriteriaRepository {

    /**
     * The entity manager for performing database operations.
     */
    //Objeto que se encarga de interactuar con la base de datos
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Serie createSerie(Serie serie) {
        // Persiste el serie en la base de datos
        entityManager.persist(serie);
        return serie;
    }

    @Override
    public List<Serie> getAllSeries(Pageable pageable) {
        // Crea un objeto CriteriaBuilder para construir la consulta
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Crea un objeto CriteriaQuery para definir la consulta
        CriteriaQuery<Serie> criteriaQuery = criteriaBuilder.createQuery(Serie.class);

        // Define la raíz de la consulta (la entidad Serie)
        Root<Serie> root = criteriaQuery.from(Serie.class);

        // Agrega ordenación
        if (pageable.getSort().isSorted()) {
            List<Order> orders = pageable.getSort().stream().map(order -> order.isAscending()
                    ? criteriaBuilder.asc(root.get(order.getProperty()))
                    : criteriaBuilder.desc(root.get(order.getProperty()))).toList();
            criteriaQuery.orderBy(orders);
        }

        // Agrega paginación
        TypedQuery<Serie> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        // Ejecuta la consulta y devuelve la lista de seriees
        return query.getResultList();
    }

    @Override
    public Optional<Serie> getSerieById(Long id) {
        // Crea un objeto CriteriaBuilder para construir la consulta
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Crea un objeto CriteriaQuery para definir la consulta
        CriteriaQuery<Serie> criteriaQuery = criteriaBuilder.createQuery(Serie.class);

        // Define la raíz de la consulta (la entidad Serie)
        Root<Serie> root = criteriaQuery.from(Serie.class);

        // Agrega una condición de filtrado para buscar el serie por ID
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        // Crea un objeto TypedQuery para ejecutar la consulta
        TypedQuery<Serie> query = entityManager.createQuery(criteriaQuery);

        // Ejecuta la consulta y devuelve el serie encontrado (o un objeto vacío si no se encuentra)
        return query.getResultStream().findFirst();
    }

    @Override
    @Transactional
    public Serie updateSerie(Serie serie) {
        // Actualiza el serie en la base de datos
        return entityManager.merge(serie);
    }

    @Override
    @Transactional
    public void deleteSerie(Long id) {
        // Busca el serie por ID
        Serie serie = entityManager.find(Serie.class, id);

        // Si se encuentra el serie, lo elimina de la base de datos
        if (serie != null) {
            entityManager.remove(serie);
        }
    }
}
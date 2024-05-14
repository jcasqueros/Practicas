package com.viewnext.films.persistencelayer.repository.criteria.impl;

import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.entity.Producer;
import com.viewnext.films.persistencelayer.entity.Serie;
import com.viewnext.films.persistencelayer.repository.criteria.SerieCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    public List<Serie> filterSeries(List<String> titles, List<Integer> releaseYears, List<Director> directors,
            List<Producer> producers, List<Actor> actors, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> criteriaQuery = criteriaBuilder.createQuery(Serie.class);
        Root<Serie> root = criteriaQuery.from(Serie.class);

        // Agrega condiciones de filtrado
        List<Predicate> predicates = new ArrayList<>();
        if (titles != null && !titles.isEmpty()) {
            predicates.add(root.get("title").in(titles));
        }
        if (releaseYears != null && !releaseYears.isEmpty()) {
            predicates.add(root.get("releaseYear").in(releaseYears));
        }
        if (directors != null && !directors.isEmpty()) {
            predicates.add(root.get("director").in(directors));
        }
        if (producers != null && !producers.isEmpty()) {
            predicates.add(root.get("producer").in(producers));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        if (actors != null && !actors.isEmpty()) {
            Join<Serie, Actor> actorJoin = root.join("actors", JoinType.INNER);
            predicates.add(actorJoin.in(actors));
        }
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

        return query.getResultList();
    }
}
package com.viewnext.films.persistencelayer.repository.criteria.impl;

import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.repository.criteria.ActorCriteriaRepository;
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
 * Implementation of the {@link ActorCriteriaRepository} interface.
 *
 * <p>This class provides a implementation of the CRUD operations for actors using JPA Criteria API.</p>
 *
 * @author Francisco Balonero Olivera
 * @see ActorCriteriaRepository
 */

@Repository
public class ActorCriteriaRepositoryImpl implements ActorCriteriaRepository {

    /**
     * The entity manager for performing database operations.
     */
    //Objeto que se encarga de interactuar con la base de datos
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Actor createActor(Actor actor) {
        // Persiste el actor en la base de datos
        entityManager.persist(actor);
        return actor;
    }

    @Override
    public List<Actor> getAllActors(Pageable pageable) {
        // Crea un objeto CriteriaBuilder para construir la consulta
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Crea un objeto CriteriaQuery para definir la consulta
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);

        // Define la raíz de la consulta (la entidad Actor)
        Root<Actor> root = criteriaQuery.from(Actor.class);

        // Agrega ordenación
        if (pageable.getSort().isSorted()) {
            List<Order> orders = pageable.getSort().stream().map(order -> order.isAscending()
                    ? criteriaBuilder.asc(root.get(order.getProperty()))
                    : criteriaBuilder.desc(root.get(order.getProperty()))).toList();
            criteriaQuery.orderBy(orders);
        }

        // Agrega paginación
        TypedQuery<Actor> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        // Ejecuta la consulta y devuelve la lista de actores
        return query.getResultList();
    }

    @Override
    public Optional<Actor> getActorById(Long id) {
        // Crea un objeto CriteriaBuilder para construir la consulta
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Crea un objeto CriteriaQuery para definir la consulta
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);

        // Define la raíz de la consulta (la entidad Actor)
        Root<Actor> root = criteriaQuery.from(Actor.class);

        // Agrega una condición de filtrado para buscar el actor por ID
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        // Crea un objeto TypedQuery para ejecutar la consulta
        TypedQuery<Actor> query = entityManager.createQuery(criteriaQuery);

        // Ejecuta la consulta y devuelve el actor encontrado (o un objeto vacío si no se encuentra)
        return query.getResultStream().findFirst();
    }

    @Override
    @Transactional
    public Actor updateActor(Actor actor) {
        // Actualiza el actor en la base de datos
        return entityManager.merge(actor);
    }

    @Override
    @Transactional
    public void deleteActor(Long id) {
        // Busca el actor por ID
        Actor actor = entityManager.find(Actor.class, id);

        // Si se encuentra el actor, lo elimina de la base de datos
        if (actor != null) {
            entityManager.remove(actor);
        }
    }

    @Override
    public List<Actor> filterActors(List<String> names, List<Integer> ages, List<String> nationalities,
            Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
        Root<Actor> root = criteriaQuery.from(Actor.class);

        // Agrega condiciones de filtrado
        List<Predicate> predicates = new ArrayList<>();
        if (names != null && !names.isEmpty()) {
            predicates.add(root.get("name").in(names));
        }
        if (nationalities != null && !nationalities.isEmpty()) {
            predicates.add(root.get("nationality").in(nationalities));
        }
        if (ages != null && !ages.isEmpty()) {
            predicates.add(root.get("age").in(ages));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        // Agrega ordenación
        if (pageable.getSort().isSorted()) {
            List<Order> orders = pageable.getSort().stream().map(order -> order.isAscending()
                    ? criteriaBuilder.asc(root.get(order.getProperty()))
                    : criteriaBuilder.desc(root.get(order.getProperty()))).toList();
            criteriaQuery.orderBy(orders);
        }

        // Agrega paginación
        TypedQuery<Actor> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }
}
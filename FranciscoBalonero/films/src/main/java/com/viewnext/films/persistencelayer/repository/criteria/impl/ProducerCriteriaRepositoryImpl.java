package com.viewnext.films.persistencelayer.repository.criteria.impl;

import com.viewnext.films.persistencelayer.entity.Producer;
import com.viewnext.films.persistencelayer.repository.criteria.ProducerCriteriaRepository;
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
 * Implementation of the {@link ProducerCriteriaRepository} interface.
 *
 * <p>This class provides a implementation of the CRUD operations for producers using JPA Criteria API.</p>
 *
 * @author Francisco Balonero Olivera
 * @see ProducerCriteriaRepository
 */

@Repository
public class ProducerCriteriaRepositoryImpl implements ProducerCriteriaRepository {

    /**
     * The entity manager for performing database operations.
     */
    //Objeto que se encarga de interactuar con la base de datos
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Producer createProducer(Producer producer) {
        // Persiste el producer en la base de datos
        entityManager.persist(producer);
        return producer;
    }

    @Override
    public List<Producer> getAllProducers(Pageable pageable) {
        // Crea un objeto CriteriaBuilder para construir la consulta
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Crea un objeto CriteriaQuery para definir la consulta
        CriteriaQuery<Producer> criteriaQuery = criteriaBuilder.createQuery(Producer.class);

        // Define la raíz de la consulta (la entidad Producer)
        Root<Producer> root = criteriaQuery.from(Producer.class);

        // Agrega ordenación
        if (pageable.getSort().isSorted()) {
            List<Order> orders = pageable.getSort().stream().map(order -> order.isAscending()
                    ? criteriaBuilder.asc(root.get(order.getProperty()))
                    : criteriaBuilder.desc(root.get(order.getProperty()))).toList();
            criteriaQuery.orderBy(orders);
        }

        // Agrega paginación
        TypedQuery<Producer> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        // Ejecuta la consulta y devuelve la lista de produceres
        return query.getResultList();
    }

    @Override
    public Optional<Producer> getProducerById(Long id) {
        // Crea un objeto CriteriaBuilder para construir la consulta
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Crea un objeto CriteriaQuery para definir la consulta
        CriteriaQuery<Producer> criteriaQuery = criteriaBuilder.createQuery(Producer.class);

        // Define la raíz de la consulta (la entidad Producer)
        Root<Producer> root = criteriaQuery.from(Producer.class);

        // Agrega una condición de filtrado para buscar el producer por ID
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        // Crea un objeto TypedQuery para ejecutar la consulta
        TypedQuery<Producer> query = entityManager.createQuery(criteriaQuery);

        // Ejecuta la consulta y devuelve el producer encontrado (o un objeto vacío si no se encuentra)
        return query.getResultStream().findFirst();
    }

    @Override
    @Transactional
    public Producer updateProducer(Producer producer) {
        // Actualiza el producer en la base de datos
        return entityManager.merge(producer);
    }

    @Override
    @Transactional
    public void deleteProducer(Long id) {
        // Busca el producer por ID
        Producer producer = entityManager.find(Producer.class, id);

        // Si se encuentra el producer, lo elimina de la base de datos
        if (producer != null) {
            entityManager.remove(producer);
        }
    }

    @Override
    public List<Producer> filterProducers(List<String> names, List<Integer> foundationYears, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producer> criteriaQuery = criteriaBuilder.createQuery(Producer.class);
        Root<Producer> root = criteriaQuery.from(Producer.class);

        // Agrega condiciones de filtrado
        List<Predicate> predicates = new ArrayList<>();
        if (names != null && !names.isEmpty()) {
            predicates.add(root.get("name").in(names));
        }
        if (foundationYears != null && !foundationYears.isEmpty()) {
            predicates.add(root.get("foundationYear").in(foundationYears));
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
        TypedQuery<Producer> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }
}
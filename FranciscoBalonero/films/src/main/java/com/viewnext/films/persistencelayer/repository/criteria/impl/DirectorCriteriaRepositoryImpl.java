package com.viewnext.films.persistencelayer.repository.criteria.impl;

import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.repository.criteria.DirectorCriteriaRepository;
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
 * Implementation of the {@link DirectorCriteriaRepository} interface.
 *
 * <p>This class provides a implementation of the CRUD operations for directors using JPA Criteria API.</p>
 *
 * @author Francisco Balonero Olivera
 * @see DirectorCriteriaRepository
 */

@Repository
public class DirectorCriteriaRepositoryImpl implements DirectorCriteriaRepository {

    /**
     * The entity manager for performing database operations.
     */
    //Objeto que se encarga de interactuar con la base de datos
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Director createDirector(Director director) {
        // Persiste el director en la base de datos
        entityManager.persist(director);
        return director;
    }

    @Override
    public List<Director> getAllDirectors(Pageable pageable) {
        // Crea un objeto CriteriaBuilder para construir la consulta
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Crea un objeto CriteriaQuery para definir la consulta
        CriteriaQuery<Director> criteriaQuery = criteriaBuilder.createQuery(Director.class);

        // Define la raíz de la consulta (la entidad Director)
        Root<Director> root = criteriaQuery.from(Director.class);

        // Agrega ordenación
        if (pageable.getSort().isSorted()) {
            List<Order> orders = pageable.getSort().stream().map(order -> order.isAscending()
                    ? criteriaBuilder.asc(root.get(order.getProperty()))
                    : criteriaBuilder.desc(root.get(order.getProperty()))).toList();
            criteriaQuery.orderBy(orders);
        }
        // Agrega paginación
        TypedQuery<Director> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        // Ejecuta la consulta y devuelve la lista de directores
        return query.getResultList();
    }

    @Override
    public Optional<Director> getDirectorById(Long id) {
        // Crea un objeto CriteriaBuilder para construir la consulta
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Crea un objeto CriteriaQuery para definir la consulta
        CriteriaQuery<Director> criteriaQuery = criteriaBuilder.createQuery(Director.class);

        // Define la raíz de la consulta (la entidad Director)
        Root<Director> root = criteriaQuery.from(Director.class);

        // Agrega una condición de filtrado para buscar el director por ID
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        // Crea un objeto TypedQuery para ejecutar la consulta
        TypedQuery<Director> query = entityManager.createQuery(criteriaQuery);

        // Ejecuta la consulta y devuelve el director encontrado (o un objeto vacío si no se encuentra)
        return query.getResultStream().findFirst();
    }

    @Override
    @Transactional
    public Director updateDirector(Director director) {
        // Actualiza el director en la base de datos
        return entityManager.merge(director);
    }

    @Override
    @Transactional
    public void deleteDirector(Long id) {
        // Busca el director por ID
        Director director = entityManager.find(Director.class, id);

        // Si se encuentra el director, lo elimina de la base de datos
        if (director != null) {
            entityManager.remove(director);
        }
    }

    @Override
    public List<Director> filterDirectors(List<String> names, List<Integer> ages, List<String> nationalities,
            Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Director> criteriaQuery = criteriaBuilder.createQuery(Director.class);
        Root<Director> root = criteriaQuery.from(Director.class);

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
        TypedQuery<Director> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }
}

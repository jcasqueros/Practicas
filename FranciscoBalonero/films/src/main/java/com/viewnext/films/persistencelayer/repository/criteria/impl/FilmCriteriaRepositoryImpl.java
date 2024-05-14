package com.viewnext.films.persistencelayer.repository.criteria.impl;

import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.entity.Film;
import com.viewnext.films.persistencelayer.entity.Producer;
import com.viewnext.films.persistencelayer.repository.criteria.FilmCriteriaRepository;
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
 * Implementation of the {@link FilmCriteriaRepository} interface.
 *
 * <p>This class provides a implementation of the CRUD operations for films using JPA Criteria API.</p>
 *
 * @author Francisco Balonero Olivera
 * @see FilmCriteriaRepository
 */

@Repository
public class FilmCriteriaRepositoryImpl implements FilmCriteriaRepository {

    /**
     * The entity manager for performing database operations.
     */
    //Objeto que se encarga de interactuar con la base de datos
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Film createFilm(Film film) {
        // Persiste el film en la base de datos
        entityManager.persist(film);
        return film;
    }

    @Override
    public List<Film> getAllFilms(Pageable pageable) {
        // Crea un objeto CriteriaBuilder para construir la consulta
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Crea un objeto CriteriaQuery para definir la consulta
        CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);

        // Define la raíz de la consulta (la entidad Film)
        Root<Film> root = criteriaQuery.from(Film.class);

        // Agrega ordenación
        if (pageable.getSort().isSorted()) {
            List<Order> orders = pageable.getSort().stream().map(order -> order.isAscending()
                    ? criteriaBuilder.asc(root.get(order.getProperty()))
                    : criteriaBuilder.desc(root.get(order.getProperty()))).toList();
            criteriaQuery.orderBy(orders);
        }

        // Agrega paginación
        TypedQuery<Film> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        // Ejecuta la consulta y devuelve la lista de filmes
        return query.getResultList();
    }

    @Override
    public Optional<Film> getFilmById(Long id) {
        // Crea un objeto CriteriaBuilder para construir la consulta
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Crea un objeto CriteriaQuery para definir la consulta
        CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);

        // Define la raíz de la consulta (la entidad Film)
        Root<Film> root = criteriaQuery.from(Film.class);

        // Agrega una condición de filtrado para buscar el film por ID
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        // Crea un objeto TypedQuery para ejecutar la consulta
        TypedQuery<Film> query = entityManager.createQuery(criteriaQuery);

        // Ejecuta la consulta y devuelve el film encontrado (o un objeto vacío si no se encuentra)
        return query.getResultStream().findFirst();
    }

    @Override
    @Transactional
    public Film updateFilm(Film film) {
        // Actualiza el film en la base de datos
        return entityManager.merge(film);
    }

    @Override
    @Transactional
    public void deleteFilm(Long id) {
        // Busca el film por ID
        Film film = entityManager.find(Film.class, id);

        // Si se encuentra el film, lo elimina de la base de datos
        if (film != null) {
            entityManager.remove(film);
        }
    }

    @Override
    public List<Film> filterFilms(List<String> titles, List<Integer> releaseYears, List<Director> directors,
            List<Producer> producers, List<Actor> actors, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);
        Root<Film> root = criteriaQuery.from(Film.class);

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
            Join<Film, Actor> actorJoin = root.join("actors", JoinType.INNER);
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
        TypedQuery<Film> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }
}


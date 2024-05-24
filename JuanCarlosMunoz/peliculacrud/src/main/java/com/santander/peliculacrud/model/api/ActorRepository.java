package com.santander.peliculacrud.model.api;

import com.santander.peliculacrud.model.entity.Actor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Actor repository.
 */
@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    @NonNull
    Page<Actor> findAll(@NonNull Pageable pageable);

    /**
     * Find all by age equals a and order by name page.
     *
     * @param age
     *         the age
     * @param pageable
     *         the pageable
     * @return the page
     */
    Page<Actor> findAllByAgeEquals(int age, Pageable pageable);

    /**
     * Find all by name and order by age page.
     *
     * @param name
     *         the name
     * @param pageable
     *         the pageable
     * @return the page
     */
    Page<Actor> findAllByName(String name, Pageable pageable);

    /**
     * Find all by nation equals a and order by name page.
     *
     * @param nation
     *         the nation
     * @param pageable
     *         the pageable
     * @return the page
     */
    Page<Actor> findAllByNationEquals(String nation, Pageable pageable);

    /**
     * Find by name in list.
     *
     * @param nameActor
     *         the name actor
     * @return the list
     */
    List<Actor> findByNameIn(List<String> nameActor);
}

package com.viewnext.bsan.practica04.persistence.repository;

import com.viewnext.bsan.practica04.persistence.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * The {@code ShowRepository} class is a JPA repository class that provides data access logic for shows.
 *
 * @author Antonio Gil
 */
@Repository
public interface ShowRepository extends JpaRepository<Show, Long>, JpaSpecificationExecutor<Show> {
}
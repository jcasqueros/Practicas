package com.viewnex.bsan.practica04.repository;

import com.viewnex.bsan.practica04.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code ShowRepository} class is a JPA repository class that provides data access logic for shows.
 *
 * @author Antonio Gil
 */
@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

}

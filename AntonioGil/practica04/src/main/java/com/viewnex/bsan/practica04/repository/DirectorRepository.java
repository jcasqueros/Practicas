package com.viewnex.bsan.practica04.repository;

import com.viewnex.bsan.practica04.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code DirectorRepository} class is a JPA repository class that provides data access logic for directors.
 *
 * @author Antonio Gil
 */
@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

}

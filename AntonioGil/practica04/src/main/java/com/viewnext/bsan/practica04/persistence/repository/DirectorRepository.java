package com.viewnext.bsan.practica04.persistence.repository;

import com.viewnext.bsan.practica04.persistence.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * The {@code DirectorRepository} class is a JPA repository class that provides data access logic for directors.
 *
 * @author Antonio Gil
 */
@Repository
public interface DirectorRepository extends JpaRepository<Director, Long>, JpaSpecificationExecutor<Director> {
}

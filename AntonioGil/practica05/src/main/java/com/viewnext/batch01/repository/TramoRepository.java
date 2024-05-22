package com.viewnext.batch01.repository;

import com.viewnext.batch01.model.Tramo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface that provides data access to {@code Tramo} entities.
 *
 * @author Antonio Gil
 */
@Repository
public interface TramoRepository extends JpaRepository<Tramo, Long> {
}
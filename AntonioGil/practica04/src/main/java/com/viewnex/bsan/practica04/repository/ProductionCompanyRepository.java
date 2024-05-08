package com.viewnex.bsan.practica04.repository;

import com.viewnex.bsan.practica04.entity.ProductionCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code ProductionCompanyRepository} class is a JPA repository class that provides data access logic for
 * production companies.
 *
 * @author Antonio Gil
 */
@Repository
public interface ProductionCompanyRepository extends JpaRepository<ProductionCompany, Long> {

}

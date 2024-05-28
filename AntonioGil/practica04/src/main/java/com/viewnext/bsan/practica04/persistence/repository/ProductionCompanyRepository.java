package com.viewnext.bsan.practica04.persistence.repository;

import com.viewnext.bsan.practica04.persistence.entity.ProductionCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * The {@code ProductionCompanyRepository} class is a JPA repository class that provides data access logic for
 * production companies.
 *
 * @author Antonio Gil
 */
@Repository
public interface ProductionCompanyRepository extends JpaRepository<ProductionCompany, Long>,
        JpaSpecificationExecutor<ProductionCompany> {
}

package com.viewnext.bsan.practica04.persistence.repository.custom;

import com.viewnext.bsan.practica04.persistence.entity.ProductionCompany;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomProductionCompanyRepository} interface is a custom repository class that uses the Criteria API to
 * provide data access logic for production companies.
 *
 * @author Antonio Gil
 */
public interface CustomProductionCompanyRepository {

    List<ProductionCompany> findAll(Pageable pageable);

    List<ProductionCompany> findAll(Specification<ProductionCompany> spec, Pageable pageable);

    boolean existsById(long id);

    Optional<ProductionCompany> findById(long id);

    ProductionCompany save(ProductionCompany company);

    boolean deleteById(long id);

}

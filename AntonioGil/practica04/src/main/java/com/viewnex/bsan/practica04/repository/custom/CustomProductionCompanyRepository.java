package com.viewnex.bsan.practica04.repository.custom;

import com.viewnex.bsan.practica04.entity.ProductionCompany;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomProductionCompanyRepository} interface is a custom repository class that uses the Criteria API to
 * provide data access logic for production companies.
 *
 * @author Antonio Gil
 */
public interface CustomProductionCompanyRepository {

    List<ProductionCompany> getAll();

    boolean existsById(long id);

    Optional<ProductionCompany> getById(long id);

    ProductionCompany save(ProductionCompany company);

    boolean deleteById(long id);

}

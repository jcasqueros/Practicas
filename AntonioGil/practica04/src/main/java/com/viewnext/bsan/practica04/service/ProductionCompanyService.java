package com.viewnext.bsan.practica04.service;

import com.viewnext.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnext.bsan.practica04.dto.request.ProductionCompanyFilterDto;
import com.viewnext.bsan.practica04.dto.request.QueryOptionsDto;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ProductionCompanyService} interface is a service class that defines business logic for operations with
 * production companies.
 *
 * @author Antonio Gil
 */
public interface ProductionCompanyService {

    List<ProductionCompanyBo> getAll(ProductionCompanyFilterDto filter, QueryOptionsDto queryOptions);

    ProductionCompanyBo getById(Optional<Boolean> useCustomRepository);

    ProductionCompanyBo create(ProductionCompanyBo company, Optional<Boolean> useCustomRepository);

    ProductionCompanyBo update(long id, ProductionCompanyBo company, Optional<Boolean> useCustomRepository);

    void validateCompany(ProductionCompanyBo company);

    void validateName(String name);

    void validateYear(int year);

    void deleteById(long id, Optional<Boolean> useCustomRepository);

}

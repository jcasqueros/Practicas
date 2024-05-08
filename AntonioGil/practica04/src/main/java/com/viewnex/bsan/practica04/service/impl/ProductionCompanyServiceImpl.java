package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.entity.ProductionCompany;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnex.bsan.practica04.repository.ProductionCompanyRepository;
import com.viewnex.bsan.practica04.service.ProductionCompanyService;
import com.viewnex.bsan.practica04.util.constants.LogMessages;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelProductionCompanyMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code ProductionCompanyService} interface.
 *
 * @author Antonio Gil
 */
@Service
public class ProductionCompanyServiceImpl implements ProductionCompanyService {

    private final ProductionCompanyRepository repository;
    private final ServiceLevelProductionCompanyMapper mapper;

    public ProductionCompanyServiceImpl(ProductionCompanyRepository repository,
                                        ServiceLevelProductionCompanyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ProductionCompanyBo> getAll() {
        return repository.findAll().stream().map(mapper::entityToBo).toList();
    }

    @Override
    public List<ProductionCompanyBo> customGetAll() {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }

    @Override
    public ProductionCompanyBo getById(long id) {
        Optional<ProductionCompany> foundEntity = repository.findById(id);

        if (foundEntity.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.PRODUCTION_COMPANY_ENTITY_NAME, id)
            );
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public ProductionCompanyBo customGetById(long id) {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }

    @Override
    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new MissingRequiredFieldException(
                    String.format(LogMessages.REQUIRED_FIELD, "name"), "name"
            );
        }
    }

    @Override
    public void validateYear(int year) {
        if (year < 0) {
            throw new BadInputDataException(
                    String.format(LogMessages.NEGATIVE_NUMBER_NOT_ALLOWED, "age")
            );
        }
    }

    @Override
    public void validateCompany(ProductionCompanyBo company) {
        if (company == null) {
            throw new MissingRequiredFieldException(
                    String.format(LogMessages.NULL_NOT_ALLOWED, LogMessages.PRODUCTION_COMPANY_ENTITY_NAME),
                    LogMessages.PRODUCTION_COMPANY_ENTITY_NAME
            );
        }
    }

    @Override
    public ProductionCompanyBo create(ProductionCompanyBo company) {
        validateCompany(company);

        if (repository.existsById(company.getId())) {
            throw new DuplicateUniqueFieldException(
                    String.format(LogMessages.RESOURCE_ALREADY_EXISTS, LogMessages.PRODUCTION_COMPANY_ENTITY_NAME,
                            company.getId()),
                    "id"
            );
        }

        ProductionCompany entityToSave = mapper.boToEntity(company);
        ProductionCompany savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public ProductionCompanyBo customCreate(ProductionCompanyBo company) {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }

    @Override
    public ProductionCompanyBo update(long id, ProductionCompanyBo newCompany) {
        validateCompany(newCompany);
        newCompany.setId(id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.PRODUCTION_COMPANY_ENTITY_NAME, id)
            );
        }

        ProductionCompany entityToSave = mapper.boToEntity(newCompany);
        ProductionCompany savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public ProductionCompanyBo customUpdate(long id, ProductionCompanyBo newCompany) {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format(LogMessages.RESOURCE_NOT_FOUND, LogMessages.PRODUCTION_COMPANY_ENTITY_NAME, id)
            );
        }

        repository.deleteById(id);
    }

    @Override
    public void customDeleteById(long id) {
        // TODO Implement custom repositories and the corresponding service operations
        throw new UnsupportedOperationException(LogMessages.NOT_YET_IMPLEMENTED);
    }
}

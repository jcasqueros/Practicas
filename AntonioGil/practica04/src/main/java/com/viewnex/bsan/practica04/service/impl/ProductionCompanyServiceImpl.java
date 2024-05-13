package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.entity.ProductionCompany;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnex.bsan.practica04.repository.ProductionCompanyRepository;
import com.viewnex.bsan.practica04.repository.custom.CustomProductionCompanyRepository;
import com.viewnex.bsan.practica04.service.ProductionCompanyService;
import com.viewnex.bsan.practica04.util.MessageBuilder;
import com.viewnex.bsan.practica04.util.constants.Messages;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelProductionCompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger("ProductionCompanyService");

    private final ProductionCompanyRepository repository;
    private final CustomProductionCompanyRepository customRepository;
    private final ServiceLevelProductionCompanyMapper mapper;

    public ProductionCompanyServiceImpl(ProductionCompanyRepository repository,
                                        CustomProductionCompanyRepository customRepository,
                                        ServiceLevelProductionCompanyMapper mapper) {
        this.repository = repository;
        this.customRepository = customRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ProductionCompanyBo> getAll() {
        return repository.findAll().stream().map(mapper::entityToBo).toList();
    }

    @Override
    public List<ProductionCompanyBo> customGetAll() {
        return customRepository.findAll().stream().map(mapper::entityToBo).toList();
    }

    @Override
    public ProductionCompanyBo getById(long id) {
        Optional<ProductionCompany> foundEntity = repository.findById(id);

        if (foundEntity.isEmpty()) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public ProductionCompanyBo customGetById(long id) {
        Optional<ProductionCompany> foundEntity = customRepository.findById(id);

        if (foundEntity.isEmpty()) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            String message = MessageBuilder.buildMissingRequiredFieldMessage("name");
            LOGGER.warn(message);
            throw new MissingRequiredFieldException(message, "name");
        }
    }

    @Override
    public void validateYear(int year) {
        if (year < 0) {
            String message = MessageBuilder.negativeNumberNotAllowedMessage("year");
            LOGGER.warn(message);
            throw new BadInputDataException(message);
        }
    }

    @Override
    public void validateCompany(ProductionCompanyBo company) {
        if (company == null) {
            String message =
                    MessageBuilder.buildMissingRequiredFieldMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME);
            LOGGER.warn(message);
            throw new MissingRequiredFieldException(message, Messages.PRODUCTION_COMPANY_ENTITY_NAME);
        }
    }

    @Override
    public ProductionCompanyBo create(ProductionCompanyBo company) {
        validateCompany(company);

        if (repository.existsById(company.getId())) {
            String message = MessageBuilder.buildResourceAlreadyExistsMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME,
                    company.getId());
            LOGGER.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        ProductionCompany entityToSave = mapper.boToEntity(company);
        ProductionCompany savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public ProductionCompanyBo customCreate(ProductionCompanyBo company) {
        validateCompany(company);

        if (customRepository.existsById(company.getId())) {
            String message = MessageBuilder.buildResourceAlreadyExistsMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME,
                    company.getId());
            LOGGER.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        ProductionCompany entityToSave = mapper.boToEntity(company);
        ProductionCompany savedEntity = customRepository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public ProductionCompanyBo update(long id, ProductionCompanyBo newCompany) {
        validateCompany(newCompany);
        newCompany.setId(id);

        if (!repository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        ProductionCompany entityToSave = mapper.boToEntity(newCompany);
        ProductionCompany savedEntity = repository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public ProductionCompanyBo customUpdate(long id, ProductionCompanyBo newCompany) {
        validateCompany(newCompany);
        newCompany.setId(id);

        if (!customRepository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        ProductionCompany entityToSave = mapper.boToEntity(newCompany);
        ProductionCompany savedEntity = customRepository.save(entityToSave);

        return mapper.entityToBo(savedEntity);
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        repository.deleteById(id);
    }

    @Override
    public void customDeleteById(long id) {
        if (!customRepository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME, id);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        customRepository.deleteById(id);
    }
}

package com.viewnext.bsan.practica04.business.service.impl;

import com.viewnext.bsan.practica04.business.bo.ProductionCompanyBo;
import com.viewnext.bsan.practica04.business.exception.BadInputDataException;
import com.viewnext.bsan.practica04.business.exception.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.business.exception.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.business.exception.ResourceNotFoundException;
import com.viewnext.bsan.practica04.business.service.ProductionCompanyService;
import com.viewnext.bsan.practica04.persistence.entity.ProductionCompany;
import com.viewnext.bsan.practica04.persistence.repository.ProductionCompanyRepository;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomProductionCompanyRepository;
import com.viewnext.bsan.practica04.persistence.specification.ProductionCompanySpecifications;
import com.viewnext.bsan.practica04.presentation.request.ProductionCompanyFilter;
import com.viewnext.bsan.practica04.presentation.request.QueryOptions;
import com.viewnext.bsan.practica04.util.MessageBuilder;
import com.viewnext.bsan.practica04.util.constants.Messages;
import com.viewnext.bsan.practica04.util.constants.RestApiDefaultParams;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelProductionCompanyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code ProductionCompanyService} interface.
 *
 * @author Antonio Gil
 */
@Slf4j
@Service
public class ProductionCompanyServiceImpl implements ProductionCompanyService {

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
    public List<ProductionCompanyBo> getAll(ProductionCompanyFilter filter, QueryOptions queryOptions) {
        boolean useCustomRepository = queryOptions.getUseCustomRepository().orElse(false);
        int pageNumber = queryOptions.getPageNumber().orElse(0);
        int pageSize = queryOptions.getPageSize().orElse(RestApiDefaultParams.DEFAULT_PAGE_SIZE);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return (useCustomRepository ? customGetAll(filter, pageable) : regularGetAll(filter, pageable));
    }

    private List<ProductionCompanyBo> customGetAll(ProductionCompanyFilter filter, Pageable pageable) {
        List<ProductionCompany> entityList;
        if (filter.isEmpty()) {
            entityList = customRepository.findAll(pageable);
        } else {
            Specification<ProductionCompany> spec = Specification.where(null);

            if (filter.getName().isPresent()) {
                String title = filter.getName().orElseThrow();
                spec = spec.and(ProductionCompanySpecifications.nameContainsIgnoreCase(title));
            }
            if (filter.getYearFounded().isPresent()) {
                int year = filter.getYearFounded().orElseThrow();
                spec = spec.and(ProductionCompanySpecifications.yearFoundedIsEqualTo(year));
            }

            entityList = customRepository.findAll(spec, pageable);
        }

        return entityList.stream().map(mapper::entityToBo).toList();
    }

    private List<ProductionCompanyBo> regularGetAll(ProductionCompanyFilter filter, Pageable pageable) {
        List<ProductionCompany> entityList;
        if (filter.isEmpty()) {
            entityList = repository.findAll(pageable).toList();
        } else {
            Specification<ProductionCompany> spec = Specification.where(null);

            if (filter.getName().isPresent()) {
                String title = filter.getName().orElseThrow();
                spec = spec.and(ProductionCompanySpecifications.nameContainsIgnoreCase(title));
            }
            if (filter.getYearFounded().isPresent()) {
                int year = filter.getYearFounded().orElseThrow();
                spec = spec.and(ProductionCompanySpecifications.yearFoundedIsEqualTo(year));
            }

            entityList = repository.findAll(spec, pageable).toList();
        }

        return entityList.stream().map(mapper::entityToBo).toList();
    }

    @Override
    public ProductionCompanyBo getById(long id, Optional<Boolean> useCustomRepository) {
        boolean custom = useCustomRepository.orElse(false);
        Optional<ProductionCompany> foundEntity = (custom ? customRepository.findById(id) : repository.findById(id));

        if (foundEntity.isEmpty()) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public ProductionCompanyBo create(ProductionCompanyBo company, Optional<Boolean> useCustomRepository) {
        validateCompany(company);

        boolean custom = useCustomRepository.orElse(false);
        return (custom ? customCreate(company) : regularCreate(company));
    }

    private ProductionCompanyBo customCreate(ProductionCompanyBo company) {
        if (customRepository.existsById(company.getId())) {
            String message = MessageBuilder.buildResourceAlreadyExistsMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME,
                    company.getId());
            log.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        ProductionCompany entity = mapper.boToEntity(company);
        customRepository.save(entity);

        return mapper.entityToBo(entity);
    }

    private ProductionCompanyBo regularCreate(ProductionCompanyBo company) {
        if (repository.existsById(company.getId())) {
            String message = MessageBuilder.buildResourceAlreadyExistsMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME,
                    company.getId());
            log.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        ProductionCompany entity = mapper.boToEntity(company);
        repository.save(entity);

        return mapper.entityToBo(entity);
    }

    @Override
    public ProductionCompanyBo update(long id, ProductionCompanyBo company, Optional<Boolean> useCustomRepository) {
        validateCompany(company);
        company.setId(id);

        boolean custom = useCustomRepository.orElse(false);
        return (custom ? customUpdate(company) : regularUpdate(company));
    }

    private ProductionCompanyBo customUpdate(ProductionCompanyBo company) {
        if (!customRepository.existsById(company.getId())) {
            String message =
                    MessageBuilder.buildResourceNotFoundMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME,
                            company.getId());
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        ProductionCompany entity = mapper.boToEntity(company);
        customRepository.save(entity);

        return mapper.entityToBo(entity);
    }

    private ProductionCompanyBo regularUpdate(ProductionCompanyBo company) {
        if (!repository.existsById(company.getId())) {
            String message =
                    MessageBuilder.buildResourceNotFoundMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME,
                            company.getId());
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        ProductionCompany entity = mapper.boToEntity(company);
        repository.save(entity);

        return mapper.entityToBo(entity);
    }


    @Override
    public void validateCompany(ProductionCompanyBo company) {
        if (company == null) {
            String message = MessageBuilder.buildNullNotAllowedMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME);
            log.warn(message);
            throw new MissingRequiredFieldException(message, Messages.PRODUCTION_COMPANY_ENTITY_NAME);
        }

        validateName(company.getName());
        validateYear(company.getYearFounded());
    }

    @Override
    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            String message = MessageBuilder.buildMissingRequiredFieldMessage("name");
            log.warn(message);
            throw new MissingRequiredFieldException(message, "name");
        }
    }

    @Override
    public void validateYear(int year) {
        if (year < 0) {
            String message = MessageBuilder.buildNegativeNumberNotAllowedMessage("year");
            log.warn(message);
            throw new BadInputDataException(message);
        }
    }

    @Override
    public void deleteById(long id, Optional<Boolean> useCustomRepository) {
        boolean custom = useCustomRepository.orElse(false);
        if (custom) {
            customDeleteById(id);
        } else {
            regularDeleteById(id);
        }
    }

    private void customDeleteById(long id) {
        if (!customRepository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        customRepository.deleteById(id);
    }

    private void regularDeleteById(long id) {
        if (!repository.existsById(id)) {
            String message = MessageBuilder.buildResourceNotFoundMessage(Messages.PRODUCTION_COMPANY_ENTITY_NAME, id);
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }

        repository.deleteById(id);
    }

}

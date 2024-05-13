package com.viewnext.bsan.practica04.service.impl;

import com.viewnext.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnext.bsan.practica04.dto.request.ProductionCompanyFilterDto;
import com.viewnext.bsan.practica04.dto.request.QueryOptionsDto;
import com.viewnext.bsan.practica04.entity.ProductionCompany;
import com.viewnext.bsan.practica04.exception.service.BadInputDataException;
import com.viewnext.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnext.bsan.practica04.repository.ProductionCompanyRepository;
import com.viewnext.bsan.practica04.repository.custom.CustomProductionCompanyRepository;
import com.viewnext.bsan.practica04.service.ProductionCompanyService;
import com.viewnext.bsan.practica04.util.MessageBuilder;
import com.viewnext.bsan.practica04.util.constants.Messages;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelProductionCompanyMapper;
import com.viewnext.bsan.practica04.repository.custom.CustomProductionCompanyRepository;
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
    public List<ProductionCompanyBo> getAll(ProductionCompanyFilterDto filter, QueryOptionsDto queryOptions) {
        // TODO: Re-do this method
        return List.of();
    }

    @Override
    public ProductionCompanyBo getById(Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public ProductionCompanyBo create(ProductionCompanyBo company, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public ProductionCompanyBo update(long id, ProductionCompanyBo company, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public void validateCompany(ProductionCompanyBo company) {
        // TODO: Re-do this method
    }

    @Override
    public void validateName(String name) {
        // TODO: Re-do this method
    }

    @Override
    public void validateYear(int year) {
        // TODO: Re-do this method
    }

    @Override
    public void deleteById(long id, Optional<Boolean> useCustomRepository) {
        // TODO: Re-do this method
    }

}

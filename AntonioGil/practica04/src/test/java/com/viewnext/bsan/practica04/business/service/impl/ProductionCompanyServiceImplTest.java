package com.viewnext.bsan.practica04.business.service.impl;

import com.viewnext.bsan.practica04.persistence.repository.ProductionCompanyRepository;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomProductionCompanyRepository;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelProductionCompanyMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for {@code ProductionCompanyServiceImpl}.
 *
 * @author Antonio Gil
 */
@ExtendWith(MockitoExtension.class)
class ProductionCompanyServiceImplTest {

    @Mock
    ProductionCompanyRepository repository;

    @Mock
    CustomProductionCompanyRepository customRepository;

    @Mock
    ServiceLevelProductionCompanyMapper mapper;

    @InjectMocks
    ProductionCompanyServiceImpl service;

    @DisplayName("[ProductionCompanyServiceImpl] getAll (success)")
    @Test
    void xxxxx() {
        // TODO: Re-do this test
        fail("Not yet implemented");
    }

}

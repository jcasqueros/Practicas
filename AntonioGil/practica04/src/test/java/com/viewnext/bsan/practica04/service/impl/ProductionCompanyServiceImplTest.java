package com.viewnext.bsan.practica04.service.impl;

import com.viewnext.bsan.practica04.repository.ProductionCompanyRepository;
import com.viewnext.bsan.practica04.repository.custom.CustomProductionCompanyRepository;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelProductionCompanyMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.viewnext.bsan.practica04.sampledata.ProductionCompanySampleData.SAMPLE_COMPANIES;
import static org.junit.jupiter.api.Assertions.*;

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

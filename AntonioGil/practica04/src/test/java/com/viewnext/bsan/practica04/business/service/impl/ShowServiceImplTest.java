package com.viewnext.bsan.practica04.business.service.impl;

import com.viewnext.bsan.practica04.persistence.repository.ShowRepository;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomShowRepository;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelShowMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for {@code ShowServiceImpl}.
 *
 * @author Antonio Gil
 */
@ExtendWith(MockitoExtension.class)
class ShowServiceImplTest {

    @Mock
    ShowRepository repository;

    @Mock
    CustomShowRepository customRepository;

    @Mock
    ServiceLevelShowMapper mapper;

    @InjectMocks
    ShowServiceImpl service;

    @DisplayName("[ShowServiceImpl] getAll (success)")
    @Test
    void xxxxx() {
        // TODO: Re-do this test
        fail("Not yet implemented");
    }

}

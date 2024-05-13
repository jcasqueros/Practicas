package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.entity.ProductionCompany;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnex.bsan.practica04.repository.ProductionCompanyRepository;
import com.viewnex.bsan.practica04.repository.custom.CustomProductionCompanyRepository;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelProductionCompanyMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.viewnex.bsan.practica04.sampledata.ProductionCompanySampleData.SAMPLE_COMPANIES;
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
    void givenCompanies_whenGetAll_thenReturnCompanyList() {
        Mockito.when(repository.findAll()).thenReturn(SAMPLE_COMPANIES);
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(0))).thenReturn(
                ProductionCompanyBo.builder().id(1L).name("PRODUCTION_COMPANY1").yearFounded(1995).build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(1))).thenReturn(
                ProductionCompanyBo.builder().id(2L).name("PRODUCTION_COMPANY2").yearFounded(1975).build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(2))).thenReturn(
                ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3").yearFounded(1980).build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(3))).thenReturn(
                ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4").yearFounded(2000).build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(4))).thenReturn(
                ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5").yearFounded(1990).build()
        );

        List<ProductionCompanyBo> expectedCompanies = List.of(
                ProductionCompanyBo.builder().id(1L).name("PRODUCTION_COMPANY1").yearFounded(1995).build(),
                ProductionCompanyBo.builder().id(2L).name("PRODUCTION_COMPANY2").yearFounded(1975).build(),
                ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3").yearFounded(1980).build(),
                ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4").yearFounded(2000).build(),
                ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5").yearFounded(1990).build()
        );
        List<ProductionCompanyBo> foundCompanies = service.getAll();

        assertFalse(foundCompanies.isEmpty());
        assertEquals(SAMPLE_COMPANIES.size(), foundCompanies.size());
        assertTrue(foundCompanies.containsAll(expectedCompanies));
    }

    @DisplayName("[ProductionCompanyServiceImpl] customGetAll (success)")
    @Test
    void givenCompanies_whenCustomGetAll_thenReturnCompanyList() {
        Mockito.when(customRepository.findAll()).thenReturn(SAMPLE_COMPANIES);
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(0))).thenReturn(
                ProductionCompanyBo.builder().id(1L).name("PRODUCTION_COMPANY1").yearFounded(1995).build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(1))).thenReturn(
                ProductionCompanyBo.builder().id(2L).name("PRODUCTION_COMPANY2").yearFounded(1975).build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(2))).thenReturn(
                ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3").yearFounded(1980).build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(3))).thenReturn(
                ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4").yearFounded(2000).build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(4))).thenReturn(
                ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5").yearFounded(1990).build()
        );

        List<ProductionCompanyBo> expectedCompanies = List.of(
                ProductionCompanyBo.builder().id(1L).name("PRODUCTION_COMPANY1").yearFounded(1995).build(),
                ProductionCompanyBo.builder().id(2L).name("PRODUCTION_COMPANY2").yearFounded(1975).build(),
                ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3").yearFounded(1980).build(),
                ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4").yearFounded(2000).build(),
                ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5").yearFounded(1990).build()
        );
        List<ProductionCompanyBo> foundCompanies = service.customGetAll();

        assertFalse(foundCompanies.isEmpty());
        assertEquals(SAMPLE_COMPANIES.size(), foundCompanies.size());
        assertTrue(foundCompanies.containsAll(expectedCompanies));
    }

    @DisplayName("[ProductionCompanyServiceImpl] getById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentCompanyId_whenGetById_thenThrow() {
        final long id = -1;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @DisplayName("[ProductionCompanyServiceImpl] getById (should return company)")
    @Test
    void givenCompanyId_whenGetById_thenReturnCompanyObject() {
        final long id = 4;
        final ProductionCompanyBo expectedCompany =
                ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4").yearFounded(2000).build();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(SAMPLE_COMPANIES.get(3)));
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(3))).thenReturn(expectedCompany);

        final ProductionCompanyBo foundCompany = service.getById(id);

        assertNotNull(foundCompany);
        assertEquals(id, foundCompany.getId());
        assertEquals(expectedCompany.getName(), foundCompany.getName());
        assertEquals(expectedCompany.getYearFounded(), foundCompany.getYearFounded());
    }

    @DisplayName("[ProductionCompanyServiceImpl] customGetById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentCompanyId_whenCustomGetById_thenThrow() {
        final long id = -1;

        Mockito.when(customRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.customGetById(id));
    }

    @DisplayName("[ProductionCompanyServiceImpl] customGetById (should return company)")
    @Test
    void givenNonExistentCompanyId_whenCustomGetById_thenReturnCompanyObject() {
        final long id = 4;
        final ProductionCompanyBo expectedCompany =
                ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4").yearFounded(2000).build();

        Mockito.when(customRepository.findById(id)).thenReturn(Optional.of(SAMPLE_COMPANIES.get(3)));
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(3))).thenReturn(expectedCompany);

        final ProductionCompanyBo foundCompany = service.customGetById(id);

        assertNotNull(foundCompany);
        assertEquals(id, foundCompany.getId());
        assertEquals(expectedCompany.getName(), foundCompany.getName());
        assertEquals(expectedCompany.getYearFounded(), foundCompany.getYearFounded());
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateName (should throw MissingRequiredFieldException)")
    @Test
    void givenInvalidName_whenValidateName_thenThrow() {
        final String name = "\n";

        assertThrows(MissingRequiredFieldException.class, () -> service.validateName(name));
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateName (should not throw)")
    @Test
    void givenName_whenValidateName_thenDoesNotThrow() {
        final String name = "PARAMOUNT PICTURES";

        assertDoesNotThrow(() -> service.validateName(name));
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateYear (should throw BadInputDataException)")
    @Test
    void givenInvalidYear_whenValidateYear_thenThrow() {
        final int year = -1923;

        assertThrows(BadInputDataException.class, () -> service.validateYear(year));
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateYear (should not throw)")
    @Test
    void givenAge_whenValidateAge_thenDoesNotThrow() {
        final int year = 1923;

        assertDoesNotThrow(() -> service.validateYear(year));
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateCompany (should throw BadInputDataException)")
    @Test
    void givenNullCompany_whenValidateCompany_thenThrow() {
        assertThrows(BadInputDataException.class, () -> service.validateCompany(null));
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateCompany (should not throw)")
    @Test
    void givenCompany_whenValidateCompany_thenDoesNotThrow() {
        final ProductionCompanyBo company =
                ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4").yearFounded(2000).build();

        assertDoesNotThrow(() -> service.validateCompany(company));
    }

    @DisplayName("[ProductionCompanyServiceImpl] create (throws DuplicateUniqueFieldException)")
    @Test
    void givenCompanyWithDuplicateData_whenCreate_thenThrow() {
        final long id = 4;
        final ProductionCompanyBo company =
                ProductionCompanyBo.builder().id(id).name("PRODUCTION_COMPANY4").yearFounded(2000).build();

        Mockito.when(repository.existsById(id)).thenReturn(true);

        assertThrows(DuplicateUniqueFieldException.class, () -> service.create(company));
    }

    @DisplayName("[ProductionCompanyServiceImpl] create (success)")
    @Test
    void givenCompany_whenCreate_thenCompanyIsSaved() {
        final long id = 4;
        final ProductionCompanyBo company =
                ProductionCompanyBo.builder().id(id).name("PRODUCTION_COMPANY4").yearFounded(2000).build();

        Mockito.when(repository.existsById(id)).thenReturn(false);
        Mockito.when(mapper.boToEntity(company)).thenReturn(SAMPLE_COMPANIES.get(3));
        Mockito.when(repository.save(SAMPLE_COMPANIES.get(3))).thenReturn(SAMPLE_COMPANIES.get(3));
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(3))).thenReturn(company);

        final ProductionCompanyBo savedCompany = service.create(company);

        assertEquals(company.getId(), savedCompany.getId());
        assertEquals(company.getName(), savedCompany.getName());
        assertEquals(company.getYearFounded(), savedCompany.getYearFounded());
    }

    @DisplayName("[ProductionCompanyServiceImpl] customCreate (throws DuplicateUniqueFieldException)")
    @Test
    void givenCompanyWithDuplicateData_whenCustomCreate_thenThrow() {
        final long id = 4;
        final ProductionCompanyBo company =
                ProductionCompanyBo.builder().id(id).name("PRODUCTION_COMPANY4").yearFounded(2000).build();

        Mockito.when(customRepository.existsById(id)).thenReturn(true);

        assertThrows(DuplicateUniqueFieldException.class, () -> service.customCreate(company));
    }

    @DisplayName("[ProductionCompanyServiceImpl] customCreate (success)")
    @Test
    void givenCompany_whenCustomCreate_thenCompanyIsSaved() {
        final long id = 4;
        final ProductionCompanyBo company =
                ProductionCompanyBo.builder().id(id).name("PRODUCTION_COMPANY4").yearFounded(2000).build();

        Mockito.when(customRepository.existsById(id)).thenReturn(false);
        Mockito.when(mapper.boToEntity(company)).thenReturn(SAMPLE_COMPANIES.get(3));
        Mockito.when(customRepository.save(SAMPLE_COMPANIES.get(3))).thenReturn(SAMPLE_COMPANIES.get(3));
        Mockito.when(mapper.entityToBo(SAMPLE_COMPANIES.get(3))).thenReturn(company);

        final ProductionCompanyBo savedCompany = service.customCreate(company);

        assertEquals(company.getId(), savedCompany.getId());
        assertEquals(company.getName(), savedCompany.getName());
        assertEquals(company.getYearFounded(), savedCompany.getYearFounded());
    }

    @DisplayName("[ProductionCompanyServiceImpl] update (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentCompanyId_whenUpdate_thenThrow() {
        final long id = -4;
        final ProductionCompanyBo company =
                ProductionCompanyBo.builder().id(id).name("PRODUCTION_COMPANY4").yearFounded(2000).build();

        Mockito.when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.update(id, company));
    }

    @DisplayName("[ProductionCompanyServiceImpl] update (success)")
    @Test
    void givenCompanyId_whenUpdate_thenCompanyIsUpdated() {
        final long id = 4;
        final ProductionCompanyBo company =
                ProductionCompanyBo.builder().id(id).name("PRODUCTION_COMPANY04").yearFounded(2000).build();

        Mockito.when(repository.existsById(id)).thenReturn(true);
        Mockito.when(mapper.boToEntity(company)).thenReturn(
                ProductionCompany.builder().id(id).name("PRODUCTION_COMPANY04").yearFounded(2000).build()
        );
        Mockito.when(repository.save(ProductionCompany.builder().id(id).name("PRODUCTION_COMPANY04").yearFounded(2000)
                        .build()))
                .thenReturn(ProductionCompany.builder().id(id).name("PRODUCTION_COMPANY04").yearFounded(2000).build());
        Mockito.when(mapper.entityToBo(ProductionCompany.builder().id(id).name("PRODUCTION_COMPANY04").yearFounded(2000)
                        .build()))
                .thenReturn(company);

        final ProductionCompanyBo savedCompany = service.update(id, company);

        assertEquals(company.getId(), savedCompany.getId());
        assertEquals(company.getName(), savedCompany.getName());
        assertEquals(company.getYearFounded(), savedCompany.getYearFounded());
    }

    @DisplayName("[ProductionCompanyServiceImpl] customUpdate (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentCompanyId_whenCustomUpdate_thenThrow() {
        final long id = -4;
        final ProductionCompanyBo company =
                ProductionCompanyBo.builder().id(id).name("PRODUCTION_COMPANY4").yearFounded(2000).build();

        Mockito.when(customRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.customUpdate(id, company));
    }

    @DisplayName("[ProductionCompanyServiceImpl] customUpdate (success)")
    @Test
    void givenCompanyId_whenCustomUpdate_thenCompanyIsUpdated() {
        final long id = 4;
        final ProductionCompanyBo company =
                ProductionCompanyBo.builder().id(id).name("PRODUCTION_COMPANY04").yearFounded(2000).build();

        Mockito.when(customRepository.existsById(id)).thenReturn(true);
        Mockito.when(mapper.boToEntity(company)).thenReturn(
                ProductionCompany.builder().id(id).name("PRODUCTION_COMPANY04").yearFounded(2000).build()
        );
        Mockito.when(customRepository.save(ProductionCompany.builder().id(id).name("PRODUCTION_COMPANY04")
                        .yearFounded(2000).build()))
                .thenReturn(ProductionCompany.builder().id(id).name("PRODUCTION_COMPANY04").yearFounded(2000).build());
        Mockito.when(mapper.entityToBo(ProductionCompany.builder().id(id).name("PRODUCTION_COMPANY04").yearFounded(2000)
                        .build()))
                .thenReturn(company);

        final ProductionCompanyBo savedCompany = service.customUpdate(id, company);

        assertEquals(company.getId(), savedCompany.getId());
        assertEquals(company.getName(), savedCompany.getName());
        assertEquals(company.getYearFounded(), savedCompany.getYearFounded());
    }

    @DisplayName("[ProductionCompanyServiceImpl] deleteById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentCompanyId_whenDeleteById_thenThrow() {
        final long id = -1;

        Mockito.when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(id));
    }

    @DisplayName("[ProductionCompanyServiceImpl] deleteById (success)")
    @Test
    void givenCompanyId_whenDeleteById_thenCompanyIsRemoved() {
        final long id = 1;

        Mockito.when(repository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteById(id));
    }

    @DisplayName("[ProductionCompanyServiceImpl] customDeleteById (should throw ResourceNotFoundexception)")
    @Test
    void givenNonExistentCompanyId_whenCustomDeleteById_thenThrow() {
        final long id = -1;

        Mockito.when(customRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.customDeleteById(id));
    }

    @DisplayName("[ProductionCompanyServiceImpl] customDeleteById (success)")
    @Test
    void givenCompanyId_whenCustomDeleteById_thenCompanyIsRemoved() {
        final long id = 1;

        Mockito.when(customRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> service.customDeleteById(id));
    }

}

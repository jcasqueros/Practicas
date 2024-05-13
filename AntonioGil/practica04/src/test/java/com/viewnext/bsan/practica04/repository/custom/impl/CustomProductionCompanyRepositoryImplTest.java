package com.viewnext.bsan.practica04.repository.custom.impl;

import com.viewnex.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnex.bsan.practica04.entity.ProductionCompany;
import com.viewnex.bsan.practica04.sampledata.ProductionCompanySampleData;
import com.viewnext.bsan.practica04.entity.ProductionCompany;
import com.viewnext.bsan.practica04.sampledata.ProductionCompanySampleData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code CustomActorRepositoryImpl}.
 *
 * @author Antonio Gil
 */
@DataJpaTest
@Import(CrudPeliculasAppTestConfig.class)
class CustomProductionCompanyRepositoryImplTest {

    private static final List<ProductionCompany> SAMPLE_COMPANIES = ProductionCompanySampleData.SAMPLE_COMPANIES;

    private final CustomProductionCompanyRepositoryImpl repository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public CustomProductionCompanyRepositoryImplTest(CustomProductionCompanyRepositoryImpl repository,
                                                     TestEntityManager testEntityManager) {
        this.repository = repository;
        this.testEntityManager = testEntityManager;
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] findAll (should find empty list)")
    @Test
    void givenNoCompanies_whenFindAll_thenReturnEmptyList() {
        final List<ProductionCompany> foundCompanies = repository.findAll();

        assertTrue(foundCompanies.isEmpty());
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] findAll (should find companies)")
    @Test
    void givenCompanies_whenFindAll_thenReturnCompanyList() {
        SAMPLE_COMPANIES.forEach(testEntityManager::persist);

        final List<ProductionCompany> foundCompanies = repository.findAll();

        assertFalse(foundCompanies.isEmpty());
        assertEquals(SAMPLE_COMPANIES.size(), foundCompanies.size());
        assertTrue(foundCompanies.containsAll(SAMPLE_COMPANIES));
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] existsById (nonexistent company)")
    @Test
    void givenNonExistentCompanyId_whenExistsById_thenReturnFalse() {
        final long id = -1;

        assertFalse(repository.existsById(id));
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] existsById (existing company)")
    @Test
    void givenCompanyId_whenExistsById_thenReturnTrue() {
        SAMPLE_COMPANIES.forEach(testEntityManager::persist);

        final Long id = SAMPLE_COMPANIES.get(3).getId();

        assertTrue(repository.existsById(id));
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] findById (nonexistent company)")
    @Test
    void givenNonExistentCompanyId_whenFindById_thenReturnEmpty() {
        final long id = -1;

        final Optional<ProductionCompany> foundCompany = repository.findById(id);

        assertTrue(foundCompany.isEmpty());
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] findById (existing company)")
    @Test
    void givenCompanyId_whenFindById_thenReturnCompanyObject() {
        SAMPLE_COMPANIES.forEach(testEntityManager::persist);

        final ProductionCompany company = SAMPLE_COMPANIES.get(1);
        final Long id = company.getId();

        final ProductionCompany foundCompany = repository.findById(id).orElseThrow();

        assertEquals(id, foundCompany.getId());
        assertEquals(company.getName(), foundCompany.getName());
        assertEquals(company.getYearFounded(), foundCompany.getYearFounded());
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] save (invalid data)")
    @Test
    void givenInvalidCompanyData_whenSave_thenThrow() {
        final ProductionCompany invalidCompany = ProductionCompany.builder().name("PRODUCTION_COMPANY6")
                .yearFounded(1960).build();

        assertThrows(RuntimeException.class, () -> repository.save(invalidCompany));
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] save (valid data)")
    @Test
    void givenCompanyData_whenSave_thenCompanyIsSaved() {
        final ProductionCompany company = ProductionCompany.builder().id(6L).name("PRODUCTION_COMPANY6")
                .yearFounded(1960).build();

        repository.save(company);

        final ProductionCompany foundCompany = testEntityManager.find(ProductionCompany.class, company.getId());

        assertEquals(company.getId(), foundCompany.getId());
        assertEquals(company.getName(), foundCompany.getName());
        assertEquals(company.getYearFounded(), foundCompany.getYearFounded());
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] deleteById (nonexistent ID)")
    @Test
    void givenNonExistentCompanyId_whenDeleteById_thenNothingHappens() {
        SAMPLE_COMPANIES.forEach(testEntityManager::persist);
        Predicate<ProductionCompany> companyIsPresent = company ->
                testEntityManager.find(ProductionCompany.class, company.getId()) != null;

        assertTrue(SAMPLE_COMPANIES.stream().allMatch(companyIsPresent));

        final long id = -1;

        assertFalse(repository.deleteById(id));
        testEntityManager.clear();

        assertTrue(SAMPLE_COMPANIES.stream().allMatch(companyIsPresent));
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] deleteById (existing ID)")
    @Test
    void givenCompanyId_whenDeleteById_thenCompanyIsRemoved() {
        SAMPLE_COMPANIES.forEach(testEntityManager::persist);
        Predicate<ProductionCompany> companyIsPresent = company ->
                testEntityManager.find(ProductionCompany.class, company.getId()) != null;

        assertTrue(SAMPLE_COMPANIES.stream().allMatch(companyIsPresent));

        final ProductionCompany company = SAMPLE_COMPANIES.get(4);
        final Long id = company.getId();

        assertTrue(repository.deleteById(id));
        testEntityManager.clear();

        assertFalse(SAMPLE_COMPANIES.stream().allMatch(companyIsPresent));
        assertFalse(companyIsPresent.test(company));
    }

}
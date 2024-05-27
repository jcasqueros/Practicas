package com.viewnext.bsan.practica04.persistence.repository.custom.impl;

import com.viewnext.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnext.bsan.practica04.persistence.entity.ProductionCompany;
import com.viewnext.bsan.practica04.persistence.specification.ProductionCompanySpecifications;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.viewnext.bsan.practica04.sampledata.ProductionCompanySampleData.SAMPLE_COMPANIES;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code CustomActorRepositoryImpl}.
 *
 * @author Antonio Gil
 */
@DataJpaTest
@Import(CrudPeliculasAppTestConfig.class)
class CustomProductionCompanyRepositoryImplTest {

    private final CustomProductionCompanyRepositoryImpl repository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public CustomProductionCompanyRepositoryImplTest(CustomProductionCompanyRepositoryImpl repository,
                                                     TestEntityManager testEntityManager) {
        this.repository = repository;
        this.testEntityManager = testEntityManager;
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] findAll (no filters)")
    @Test
    void givenCompaniesAndNoFilters_whenFindAll_thenReturnPage() {
        // Arrange
        SAMPLE_COMPANIES.forEach(testEntityManager::persist);

        final int pageSize = 3;
        final PageRequest pageRequest = PageRequest.of(0, pageSize);
        final List<ProductionCompany> expectedCompanies = List.of(
                ProductionCompany.builder().id(1L).name("PRODUCTION_COMPANY1").yearFounded(1995).build(),
                ProductionCompany.builder().id(2L).name("PRODUCTION_COMPANY2").yearFounded(1975).build(),
                ProductionCompany.builder().id(3L).name("PRODUCTION_COMPANY3").yearFounded(1980).build()
        );

        // Act
        final List<ProductionCompany> foundCompanies = repository.findAll(pageRequest);

        // Assert
        assertFalse(foundCompanies.isEmpty());
        assertEquals(expectedCompanies.size(), foundCompanies.size());
        assertTrue(foundCompanies.containsAll(expectedCompanies));
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] findAll (filtering by year founded)")
    @Test
    void givenCompaniesAndFilters_whenFindAll_thenReturnPage() {
        // Arrange
        SAMPLE_COMPANIES.forEach(testEntityManager::persist);

        final int pageSize = 3;
        final PageRequest pageRequest = PageRequest.of(0, pageSize);
        final List<ProductionCompany> expectedCompanies = List.of(
                ProductionCompany.builder().id(3L).name("PRODUCTION_COMPANY3").yearFounded(1980).build()
        );
        final Specification<ProductionCompany> yearFoundedMatches =
                ProductionCompanySpecifications.yearFoundedIsEqualTo(1980);

        // Act
        final List<ProductionCompany> foundCompanies = repository.findAll(yearFoundedMatches, pageRequest);

        // Assert
        assertFalse(foundCompanies.isEmpty());
        assertEquals(expectedCompanies.size(), foundCompanies.size());
        assertTrue(foundCompanies.containsAll(expectedCompanies));
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] existsById (nonexistent company)")
    @Test
    void givenNonExistentCompanyId_whenExistsById_thenReturnFalse() {
        // Arrange
        final long id = -1;

        // Act + Assert
        assertFalse(repository.existsById(id));
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] existsById (existing company)")
    @Test
    void givenCompanyId_whenExistsById_thenReturnTrue() {
        // Arrange
        SAMPLE_COMPANIES.forEach(testEntityManager::persist);

        final Long id = SAMPLE_COMPANIES.get(3).getId();

        // Act + Assert
        assertTrue(repository.existsById(id));
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] findById (nonexistent company)")
    @Test
    void givenNonExistentCompanyId_whenFindById_thenReturnEmpty() {
        // Arrange
        final long id = -1;

        // Act
        final Optional<ProductionCompany> foundCompany = repository.findById(id);

        // Assert
        assertTrue(foundCompany.isEmpty());
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] findById (existing company)")
    @Test
    void givenCompanyId_whenFindById_thenReturnCompanyObject() {
        // Arrange
        SAMPLE_COMPANIES.forEach(testEntityManager::persist);
        final ProductionCompany company = SAMPLE_COMPANIES.get(1);
        final Long id = company.getId();

        // Act
        final ProductionCompany foundCompany = repository.findById(id).orElseThrow();

        // Assert
        assertEquals(id, foundCompany.getId());
        assertEquals(company.getName(), foundCompany.getName());
        assertEquals(company.getYearFounded(), foundCompany.getYearFounded());
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] save (invalid data)")
    @Test
    void givenInvalidCompanyData_whenSave_thenThrow() {
        // Arrange
        final ProductionCompany invalidCompany = ProductionCompany.builder().name("PRODUCTION_COMPANY6")
                .yearFounded(1960).build();

        // Act + Assert
        assertThrows(RuntimeException.class, () -> repository.save(invalidCompany));
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] save (valid data)")
    @Test
    void givenCompanyData_whenSave_thenCompanyIsSaved() {
        // Arrange
        final ProductionCompany company = ProductionCompany.builder().id(6L).name("PRODUCTION_COMPANY6")
                .yearFounded(1960).build();

        // Act
        repository.save(company);

        // Assert
        final ProductionCompany foundCompany = testEntityManager.find(ProductionCompany.class, company.getId());
        assertEquals(company.getId(), foundCompany.getId());
        assertEquals(company.getName(), foundCompany.getName());
        assertEquals(company.getYearFounded(), foundCompany.getYearFounded());
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] deleteById (nonexistent ID)")
    @Test
    void givenNonExistentCompanyId_whenDeleteById_thenNothingHappens() {
        // Arrange
        SAMPLE_COMPANIES.forEach(testEntityManager::persist);
        Predicate<ProductionCompany> companyIsPresent = company ->
                testEntityManager.find(ProductionCompany.class, company.getId()) != null;

        assertTrue(SAMPLE_COMPANIES.stream().allMatch(companyIsPresent));

        final long id = -1;

        // Act + Assert
        assertFalse(repository.deleteById(id));
        testEntityManager.clear();

        assertTrue(SAMPLE_COMPANIES.stream().allMatch(companyIsPresent));
    }

    @DisplayName("[CustomProductionCompanyRepositoryImpl] deleteById (existing ID)")
    @Test
    void givenCompanyId_whenDeleteById_thenCompanyIsRemoved() {
        // Arrange
        SAMPLE_COMPANIES.forEach(testEntityManager::persist);
        Predicate<ProductionCompany> companyIsPresent = company ->
                testEntityManager.find(ProductionCompany.class, company.getId()) != null;

        assertTrue(SAMPLE_COMPANIES.stream().allMatch(companyIsPresent));

        final ProductionCompany company = SAMPLE_COMPANIES.get(4);
        final Long id = company.getId();

        // Act + Assert
        assertTrue(repository.deleteById(id));
        testEntityManager.clear();

        assertFalse(SAMPLE_COMPANIES.stream().allMatch(companyIsPresent));
        assertFalse(companyIsPresent.test(company));
    }

}

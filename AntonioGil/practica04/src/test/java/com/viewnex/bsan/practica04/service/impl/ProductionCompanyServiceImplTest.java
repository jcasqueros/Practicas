package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.repository.ProductionCompanyRepository;
import com.viewnex.bsan.practica04.repository.custom.CustomProductionCompanyRepository;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelProductionCompanyMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.fail;

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
    void givenActors_whenGetAll_thenReturnActorList() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] getById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenGetById_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] getById (should return actor)")
    @Test
    void givenActorId_whenGetById_thenReturnActorObject() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] customGetById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenCustomGetById_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] customGetById (should return actor)")
    @Test
    void givenNonExistentActorId_whenCustomGetById_thenReturnActorObject() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateName (should throw MissingRequiredFieldException)")
    @Test
    void givenInvalidName_whenValidateName_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateName (should not throw)")
    @Test
    void givenName_whenValidateName_thenDoesNotThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateAge (should throw BadInputDataException)")
    @Test
    void givenInvalidAge_whenValidateAge_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateAge (should not throw)")
    @Test
    void givenAge_whenValidateAge_thenDoesNotThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateNationality (should throw MissingRequiredFieldException)")
    @Test
    void givenInvalidNationality_whenValidateNationality_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateNationality (should not throw)")
    @Test
    void givenNationality_whenValidateNationality_thenDoesNotThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateActor (should throw BadInputDataException)")
    @Test
    void givenNullActor_whenValidateActor_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] validateActor (should not throw)")
    @Test
    void givenActor_whenValidateActor_thenDoesNotThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] create (throws DuplicateUniqueFieldException)")
    @Test
    void givenActorWithDuplicateData_whenCreate_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] create (success)")
    @Test
    void givenActor_whenCreate_thenActorIsSaved() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] customCreate (throws DuplicateUniqueFieldException)")
    @Test
    void givenActorWithDuplicateData_whenCustomCreate_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] customCreate (success)")
    @Test
    void givenActor_whenCustomCreate_thenActorIsSaved() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] update (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenUpdate_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] update (success)")
    @Test
    void givenActorId_whenUpdate_thenActorIsUpdated() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] customUpdate (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenCustomUpdate_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] customUpdate (success)")
    @Test
    void givenActorId_whenCustomUpdate_thenActorIsUpdated() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] deleteById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenDeleteById_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] deleteById (success)")
    @Test
    void givenActorId_whenDeleteById_thenActorIsRemoved() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] customDeleteById (should throw ResourceNotFoundexception)")
    @Test
    void givenNonExistentActorId_whenCustomDeleteById_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[ProductionCompanyServiceImpl] customDeleteById (success)")
    @Test
    void givenActorId_whenCustomDeleteById_thenActorIsRemoved() {
        fail("Not yet implemented");
    }

}

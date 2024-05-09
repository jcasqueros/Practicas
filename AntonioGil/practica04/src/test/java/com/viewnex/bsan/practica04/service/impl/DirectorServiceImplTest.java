package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.repository.DirectorRepository;
import com.viewnex.bsan.practica04.repository.custom.CustomDirectorRepository;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelDirectorMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DirectorServiceImplTest {

    @Mock
    DirectorRepository repository;

    @Mock
    CustomDirectorRepository customRepository;

    @Mock
    ServiceLevelDirectorMapper mapper;

    @InjectMocks
    DirectorServiceImpl service;

    @DisplayName("[DirectorServiceImpl] getAll (success)")
    @Test
    void givenActors_whenGetAll_thenReturnActorList() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] getById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenGetById_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] getById (should return actor)")
    @Test
    void givenActorId_whenGetById_thenReturnActorObject() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] customGetById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenCustomGetById_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] customGetById (should return actor)")
    @Test
    void givenNonExistentActorId_whenCustomGetById_thenReturnActorObject() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] validateName (should throw MissingRequiredFieldException)")
    @Test
    void givenInvalidName_whenValidateName_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] validateName (should not throw)")
    @Test
    void givenName_whenValidateName_thenDoesNotThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] validateAge (should throw BadInputDataException)")
    @Test
    void givenInvalidAge_whenValidateAge_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] validateAge (should not throw)")
    @Test
    void givenAge_whenValidateAge_thenDoesNotThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] validateNationality (should throw MissingRequiredFieldException)")
    @Test
    void givenInvalidNationality_whenValidateNationality_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] validateNationality (should not throw)")
    @Test
    void givenNationality_whenValidateNationality_thenDoesNotThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] validateActor (should throw BadInputDataException)")
    @Test
    void givenNullActor_whenValidateActor_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] validateActor (should not throw)")
    @Test
    void givenActor_whenValidateActor_thenDoesNotThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] create (throws DuplicateUniqueFieldException)")
    @Test
    void givenActorWithDuplicateData_whenCreate_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] create (success)")
    @Test
    void givenActor_whenCreate_thenActorIsSaved() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] customCreate (throws DuplicateUniqueFieldException)")
    @Test
    void givenActorWithDuplicateData_whenCustomCreate_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] customCreate (success)")
    @Test
    void givenActor_whenCustomCreate_thenActorIsSaved() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] update (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenUpdate_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] update (success)")
    @Test
    void givenActorId_whenUpdate_thenActorIsUpdated() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] customUpdate (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenCustomUpdate_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] customUpdate (success)")
    @Test
    void givenActorId_whenCustomUpdate_thenActorIsUpdated() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] deleteById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenDeleteById_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] deleteById (success)")
    @Test
    void givenActorId_whenDeleteById_thenActorIsRemoved() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] customDeleteById (should throw ResourceNotFoundexception)")
    @Test
    void givenNonExistentActorId_whenCustomDeleteById_thenThrow() {
        fail("Not yet implemented");
    }

    @DisplayName("[DirectorServiceImpl] customDeleteById (success)")
    @Test
    void givenActorId_whenCustomDeleteById_thenActorIsRemoved() {
        fail("Not yet implemented");
    }

}

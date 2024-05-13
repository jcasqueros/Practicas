package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.entity.Director;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnex.bsan.practica04.repository.DirectorRepository;
import com.viewnex.bsan.practica04.repository.custom.CustomDirectorRepository;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelDirectorMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.viewnex.bsan.practica04.sampledata.DirectorSampleData.SAMPLE_DIRECTORS;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code DirectorServiceImpl}.
 *
 * @author Antonio Gil
 */
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
    void givenDirectors_whenGetAll_thenReturnDirectorList() {
        Mockito.when(repository.findAll()).thenReturn(SAMPLE_DIRECTORS);
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(0))).thenReturn(
                DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(1))).thenReturn(
                DirectorBo.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(2))).thenReturn(
                DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(3))).thenReturn(
                DirectorBo.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(4))).thenReturn(
                DirectorBo.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build()
        );

        List<DirectorBo> expectedDirectors = List.of(
                DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build(),
                DirectorBo.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build(),
                DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build(),
                DirectorBo.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build(),
                DirectorBo.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build()
        );
        List<DirectorBo> foundDirectors = service.getAll();

        assertFalse(foundDirectors.isEmpty());
        assertEquals(SAMPLE_DIRECTORS.size(), foundDirectors.size());
        assertTrue(foundDirectors.containsAll(expectedDirectors));
    }

    @DisplayName("[DirectorServiceImpl] getAll (success)")
    @Test
    void givenDirectors_whenCustomGetAll_thenReturnDirectorList() {
        Mockito.when(customRepository.getAll()).thenReturn(SAMPLE_DIRECTORS);
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(0))).thenReturn(
                DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(1))).thenReturn(
                DirectorBo.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(2))).thenReturn(
                DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(3))).thenReturn(
                DirectorBo.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(4))).thenReturn(
                DirectorBo.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build()
        );

        List<DirectorBo> expectedDirectors = List.of(
                DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build(),
                DirectorBo.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build(),
                DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build(),
                DirectorBo.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build(),
                DirectorBo.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build()
        );
        List<DirectorBo> foundDirectors = service.customGetAll();

        assertFalse(foundDirectors.isEmpty());
        assertEquals(SAMPLE_DIRECTORS.size(), foundDirectors.size());
        assertTrue(foundDirectors.containsAll(expectedDirectors));
    }

    @DisplayName("[DirectorServiceImpl] getById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentDirectorId_whenGetById_thenThrow() {
        final long id = -1;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @DisplayName("[DirectorServiceImpl] getById (should return director)")
    @Test
    void givenDirectorId_whenGetById_thenReturnDirectorObject() {
        final long id = 3;
        final DirectorBo expectedDirector =
                DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(SAMPLE_DIRECTORS.get(2)));
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(2))).thenReturn(expectedDirector);

        final DirectorBo foundDirector = service.getById(id);

        assertNotNull(foundDirector);
        assertEquals(expectedDirector.getId(), foundDirector.getId());
        assertEquals(expectedDirector.getName(), foundDirector.getName());
        assertEquals(expectedDirector.getAge(), foundDirector.getAge());
        assertEquals(expectedDirector.getNationality(), foundDirector.getNationality());
    }

    @DisplayName("[DirectorServiceImpl] customGetById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentDirectorId_whenCustomGetById_thenThrow() {
        final long id = -1;

        Mockito.when(customRepository.getById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.customGetById(id));
    }

    @DisplayName("[DirectorServiceImpl] customGetById (should return director)")
    @Test
    void givenNonExistentDirectorId_whenCustomGetById_thenReturnDirectorObject() {
        final long id = 3;
        final DirectorBo expectedDirector =
                DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build();

        Mockito.when(customRepository.getById(id)).thenReturn(Optional.of(SAMPLE_DIRECTORS.get(2)));
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(2))).thenReturn(expectedDirector);

        final DirectorBo foundDirector = service.customGetById(id);

        assertNotNull(foundDirector);
        assertEquals(expectedDirector.getId(), foundDirector.getId());
        assertEquals(expectedDirector.getName(), foundDirector.getName());
        assertEquals(expectedDirector.getAge(), foundDirector.getAge());
        assertEquals(expectedDirector.getNationality(), foundDirector.getNationality());
    }

    @DisplayName("[DirectorServiceImpl] validateName (should throw MissingRequiredFieldException)")
    @Test
    void givenInvalidName_whenValidateName_thenThrow() {
        final String name = "    \t     \t";

        assertThrows(MissingRequiredFieldException.class, () -> service.validateName(name));
    }

    @DisplayName("[DirectorServiceImpl] validateName (should not throw)")
    @Test
    void givenName_whenValidateName_thenDoesNotThrow() {
        final String name = "FRANCIS FORD COPPOLA";

        assertDoesNotThrow(() -> service.validateName(name));
    }

    @DisplayName("[DirectorServiceImpl] validateAge (should throw BadInputDataException)")
    @Test
    void givenInvalidAge_whenValidateAge_thenThrow() {
        final int age = -33;

        assertThrows(BadInputDataException.class, () -> service.validateAge(age));
    }

    @DisplayName("[DirectorServiceImpl] validateAge (should not throw)")
    @Test
    void givenAge_whenValidateAge_thenDoesNotThrow() {
        final int age = 33;

        assertDoesNotThrow(() -> service.validateAge(age));
    }

    @DisplayName("[DirectorServiceImpl] validateNationality (should throw MissingRequiredFieldException)")
    @Test
    void givenInvalidNationality_whenValidateNationality_thenThrow() {
        final String nationality = "     \t";

        assertThrows(MissingRequiredFieldException.class, () -> service.validateNationality(nationality));
    }

    @DisplayName("[DirectorServiceImpl] validateNationality (should not throw)")
    @Test
    void givenNationality_whenValidateNationality_thenDoesNotThrow() {
        final String nationality = "KOR";

        assertDoesNotThrow(() -> service.validateNationality(nationality));
    }

    @DisplayName("[DirectorServiceImpl] validateActor (should throw BadInputDataException)")
    @Test
    void givenNullActor_whenValidateActor_thenThrow() {
        assertThrows(BadInputDataException.class, () -> service.validateDirector(null));
    }

    @DisplayName("[DirectorServiceImpl] validateActor (should not throw)")
    @Test
    void givenActor_whenValidateActor_thenDoesNotThrow() {
        final DirectorBo director = DirectorBo.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build();

        assertDoesNotThrow(() -> service.validateDirector(director));
    }

    @DisplayName("[DirectorServiceImpl] create (throws DuplicateUniqueFieldException)")
    @Test
    void givenActorWithDuplicateData_whenCreate_thenThrow() {
        final long id = 5;
        final DirectorBo director = DirectorBo.builder().id(id).name("DIRECTOR5").age(45).nationality("USA").build();

        Mockito.when(repository.existsById(id)).thenReturn(true);

        assertThrows(DuplicateUniqueFieldException.class, () -> service.create(director));
    }

    @DisplayName("[DirectorServiceImpl] create (success)")
    @Test
    void givenActor_whenCreate_thenActorIsSaved() {
        final long id = 5;
        final DirectorBo director = DirectorBo.builder().id(id).name("DIRECTOR5").age(45).nationality("USA").build();

        Mockito.when(repository.existsById(id)).thenReturn(false);
        Mockito.when(mapper.boToEntity(director)).thenReturn(SAMPLE_DIRECTORS.get(4));
        Mockito.when(repository.save(SAMPLE_DIRECTORS.get(4))).thenReturn(SAMPLE_DIRECTORS.get(4));
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(4))).thenReturn(director);

        final DirectorBo savedDirector = service.create(director);

        assertEquals(director.getId(), savedDirector.getId());
        assertEquals(director.getName(), savedDirector.getName());
        assertEquals(director.getAge(), savedDirector.getAge());
        assertEquals(director.getNationality(), savedDirector.getNationality());
    }

    @DisplayName("[DirectorServiceImpl] customCreate (throws DuplicateUniqueFieldException)")
    @Test
    void givenActorWithDuplicateData_whenCustomCreate_thenThrow() {
        final long id = 5;
        final DirectorBo director = DirectorBo.builder().id(id).name("DIRECTOR5").age(45).nationality("USA").build();

        Mockito.when(customRepository.existsById(id)).thenReturn(true);

        assertThrows(DuplicateUniqueFieldException.class, () -> service.customCreate(director));
    }

    @DisplayName("[DirectorServiceImpl] customCreate (success)")
    @Test
    void givenActor_whenCustomCreate_thenActorIsSaved() {
        final long id = 5;
        final DirectorBo director = DirectorBo.builder().id(id).name("DIRECTOR5").age(45).nationality("USA").build();

        Mockito.when(customRepository.existsById(id)).thenReturn(false);
        Mockito.when(mapper.boToEntity(director)).thenReturn(SAMPLE_DIRECTORS.get(4));
        Mockito.when(customRepository.save(SAMPLE_DIRECTORS.get(4))).thenReturn(SAMPLE_DIRECTORS.get(4));
        Mockito.when(mapper.entityToBo(SAMPLE_DIRECTORS.get(4))).thenReturn(director);

        final DirectorBo savedDirector = service.customCreate(director);

        assertEquals(director.getId(), savedDirector.getId());
        assertEquals(director.getName(), savedDirector.getName());
        assertEquals(director.getAge(), savedDirector.getAge());
        assertEquals(director.getNationality(), savedDirector.getNationality());
    }

    @DisplayName("[DirectorServiceImpl] update (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenUpdate_thenThrow() {
        final long id = -1;
        final DirectorBo director = DirectorBo.builder().id(id).name("DIRECTOR5").age(45).nationality("USA").build();

        Mockito.when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.update(id, director));
    }

    @DisplayName("[DirectorServiceImpl] update (success)")
    @Test
    void givenActorId_whenUpdate_thenActorIsUpdated() {
        final long id = 5;
        final DirectorBo director =
                DirectorBo.builder().id(id).name("DIRECTOR05").age(45).nationality("USA").build();

        Mockito.when(repository.existsById(id)).thenReturn(true);
        Mockito.when(mapper.boToEntity(director)).thenReturn(
                Director.builder().id(id).name("DIRECTOR05").age(45).nationality("USA").build()
        );
        Mockito.when(repository.save(Director.builder().id(id).name("DIRECTOR05").age(45).nationality("USA").build()))
                .thenReturn(Director.builder().id(id).name("DIRECTOR05").age(45).nationality("USA").build());
        Mockito.when(mapper.entityToBo(Director.builder().id(id).name("DIRECTOR05").age(45).nationality("USA").build()))
                .thenReturn(director);

        final DirectorBo savedDirector = service.update(id, director);

        assertEquals(director.getId(), savedDirector.getId());
        assertEquals(director.getName(), savedDirector.getName());
        assertEquals(director.getAge(), savedDirector.getAge());
        assertEquals(director.getNationality(), savedDirector.getNationality());
    }

    @DisplayName("[DirectorServiceImpl] customUpdate (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentActorId_whenCustomUpdate_thenThrow() {
        final long id = -1;
        final DirectorBo director = DirectorBo.builder().id(id).name("DIRECTOR5").age(45).nationality("USA").build();

        Mockito.when(customRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.customUpdate(id, director));
    }

    @DisplayName("[DirectorServiceImpl] customUpdate (success)")
    @Test
    void givenActorId_whenCustomUpdate_thenActorIsUpdated() {
        final long id = 5;
        final DirectorBo director = DirectorBo.builder().id(id).name("DIRECTOR05").age(45).nationality("USA").build();

        Mockito.when(customRepository.existsById(id)).thenReturn(true);
        Mockito.when(mapper.boToEntity(director)).thenReturn(
                Director.builder().id(id).name("DIRECTOR05").age(45).nationality("USA").build()
        );
        Mockito.when(customRepository.save(Director.builder().id(id).name("DIRECTOR05").age(45).nationality("USA")
                        .build()))
                .thenReturn(Director.builder().id(id).name("DIRECTOR05").age(45).nationality("USA").build());
        Mockito.when(mapper.entityToBo(Director.builder().id(id).name("DIRECTOR05").age(45).nationality("USA").build()))
                .thenReturn(director);

        final DirectorBo savedDirector = service.customUpdate(id, director);

        assertEquals(director.getId(), savedDirector.getId());
        assertEquals(director.getName(), savedDirector.getName());
        assertEquals(director.getAge(), savedDirector.getAge());
        assertEquals(director.getNationality(), savedDirector.getNationality());
    }

    @DisplayName("[DirectorServiceImpl] deleteById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentDirectorId_whenDeleteById_thenThrow() {
        final long id = -1;

        Mockito.when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(id));
    }

    @DisplayName("[DirectorServiceImpl] deleteById (success)")
    @Test
    void givenDirectorId_whenDeleteById_thenDirectorIsRemoved() {
        final long id = 1;

        Mockito.when(repository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteById(id));
    }

    @DisplayName("[DirectorServiceImpl] customDeleteById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentDirectorId_whenCustomDeleteById_thenThrow() {
        final long id = -1;

        Mockito.when(customRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.customDeleteById(id));
    }

    @DisplayName("[DirectorServiceImpl] customDeleteById (success)")
    @Test
    void givenDirectorId_whenCustomDeleteById_thenDirectorIsRemoved() {
        final long id = 1;

        Mockito.when(customRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> service.customDeleteById(id));
    }

}

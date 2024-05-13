package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.bo.FilmBo;
import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.entity.Film;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnex.bsan.practica04.repository.FilmRepository;
import com.viewnex.bsan.practica04.repository.custom.CustomFilmRepository;
import com.viewnex.bsan.practica04.sampledata.ActorSampleData;
import com.viewnex.bsan.practica04.sampledata.DirectorSampleData;
import com.viewnex.bsan.practica04.sampledata.ProductionCompanySampleData;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelFilmMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.viewnex.bsan.practica04.sampledata.FilmSampleData.SAMPLE_FILMS;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code FilmServiceImpl}.
 *
 * @author Antonio Gil
 */
@ExtendWith(MockitoExtension.class)
class FilmServiceImplTest {

    @Mock
    FilmRepository repository;

    @Mock
    CustomFilmRepository customRepository;

    @Mock
    ServiceLevelFilmMapper mapper;

    @InjectMocks
    FilmServiceImpl service;

    @DisplayName("[FilmServiceImpl] getAll (success)")
    @Test
    void givenFilms_whenGetAll_thenReturnFilmList() {
        Mockito.when(repository.findAll()).thenReturn(SAMPLE_FILMS);
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(0))).thenReturn(FilmBo.builder().id(1L).title("FILM1")
                .year(2005).director(DirectorBo.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build())
                .productionCompany(ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4")
                        .yearFounded(2000).build())
                .actors(Set.of(ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
                        ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(1))).thenReturn(FilmBo.builder().id(2L).title("FILM2")
                .year(2010).director(DirectorBo.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3").yearFounded(1980)
                        .build())
                .actors(Set.of(ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
                        ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(2))).thenReturn(FilmBo.builder().id(3L).title("FILM3")
                .year(2000).director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(2L).name("PRODUCTION_COMPANY2")
                        .yearFounded(1975).build())
                .actors(Set.of(ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build(),
                        ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(3))).thenReturn(FilmBo.builder().id(4L).title("FILM4")
                .year(2015).director(DirectorBo.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build())
                .productionCompany(ProductionCompanyBo.builder().id(1L).name("PRODUCTION_COMPANY1")
                        .yearFounded(1995).build())
                .actors(Set.of(ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build(),
                        ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(4))).thenReturn(FilmBo.builder().id(5L).title("FILM5")
                .year(1995).director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build()
        );

        List<FilmBo> expectedFilms = List.of(
                FilmBo.builder().id(1L).title("FILM1").year(2005)
                        .director(DirectorBo.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4")
                                .yearFounded(2000).build())
                        .actors(Set.of(ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
                                ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build()))
                        .build(),
                FilmBo.builder().id(2L).title("FILM2").year(2010)
                        .director(DirectorBo.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build())
                        .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                                .yearFounded(1980).build())
                        .actors(Set.of(ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
                                ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build()))
                        .build(),
                FilmBo.builder().id(3L).title("FILM3").year(2000)
                        .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(2L).name("PRODUCTION_COMPANY2")
                                .yearFounded(1975).build())
                        .actors(Set.of(ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build(),
                                ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build()))
                        .build(),
                FilmBo.builder().id(4L).title("FILM4").year(2015)
                        .director(DirectorBo.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(1L).name("PRODUCTION_COMPANY1")
                                .yearFounded(1995).build())
                        .actors(Set.of(ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build(),
                                ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build()))
                        .build(),
                FilmBo.builder().id(5L).title("FILM5").year(1995)
                        .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                                .yearFounded(1990).build())
                        .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                                ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                        .build()
        );
        List<FilmBo> foundFilms = service.getAll();

        assertFalse(foundFilms.isEmpty());
        assertEquals(SAMPLE_FILMS.size(), foundFilms.size());
        assertTrue(foundFilms.containsAll(expectedFilms));
    }

    @DisplayName("[FilmServiceImpl] customGetAll (success)")
    @Test
    void givenFilms_whenCustomGetAll_thenReturnFilmList() {
        Mockito.when(customRepository.getAll()).thenReturn(SAMPLE_FILMS);
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(0))).thenReturn(FilmBo.builder().id(1L).title("FILM1")
                .year(2005).director(DirectorBo.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build())
                .productionCompany(ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4")
                        .yearFounded(2000).build())
                .actors(Set.of(ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
                        ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(1))).thenReturn(FilmBo.builder().id(2L).title("FILM2")
                .year(2010).director(DirectorBo.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3").yearFounded(1980)
                        .build())
                .actors(Set.of(ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
                        ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(2))).thenReturn(FilmBo.builder().id(3L).title("FILM3")
                .year(2000).director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(2L).name("PRODUCTION_COMPANY2")
                        .yearFounded(1975).build())
                .actors(Set.of(ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build(),
                        ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(3))).thenReturn(FilmBo.builder().id(4L).title("FILM4")
                .year(2015).director(DirectorBo.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build())
                .productionCompany(ProductionCompanyBo.builder().id(1L).name("PRODUCTION_COMPANY1")
                        .yearFounded(1995).build())
                .actors(Set.of(ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build(),
                        ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(4))).thenReturn(FilmBo.builder().id(5L).title("FILM5")
                .year(1995).director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build()
        );

        List<FilmBo> expectedFilms = List.of(
                FilmBo.builder().id(1L).title("FILM1").year(2005)
                        .director(DirectorBo.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4")
                                .yearFounded(2000).build())
                        .actors(Set.of(ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
                                ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build()))
                        .build(),
                FilmBo.builder().id(2L).title("FILM2").year(2010)
                        .director(DirectorBo.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build())
                        .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                                .yearFounded(1980).build())
                        .actors(Set.of(ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
                                ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build()))
                        .build(),
                FilmBo.builder().id(3L).title("FILM3").year(2000)
                        .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(2L).name("PRODUCTION_COMPANY2")
                                .yearFounded(1975).build())
                        .actors(Set.of(ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build(),
                                ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build()))
                        .build(),
                FilmBo.builder().id(4L).title("FILM4").year(2015)
                        .director(DirectorBo.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(1L).name("PRODUCTION_COMPANY1")
                                .yearFounded(1995).build())
                        .actors(Set.of(ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build(),
                                ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build()))
                        .build(),
                FilmBo.builder().id(5L).title("FILM5").year(1995)
                        .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                                .yearFounded(1990).build())
                        .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                                ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                        .build()
        );
        List<FilmBo> foundFilms = service.customGetAll();

        assertFalse(foundFilms.isEmpty());
        assertEquals(SAMPLE_FILMS.size(), foundFilms.size());
        assertTrue(foundFilms.containsAll(expectedFilms));
    }

    @DisplayName("[FilmServiceImpl] getById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentFilmId_whenGetById_thenThrow() {
        final long id = -1;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @DisplayName("[FilmServiceImpl] getById (should return film)")
    @Test
    void givenFilmId_whenGetById_thenReturnFilmObject() {
        final long id = 5;
        final FilmBo expectedFilm = FilmBo.builder().id(id).title("FILM5").year(1995)
                .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(SAMPLE_FILMS.get(4)));
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(4))).thenReturn(expectedFilm);

        final FilmBo foundFilm = service.getById(id);

        assertNotNull(foundFilm);
        assertEquals(id, foundFilm.getId());
        assertEquals(expectedFilm.getTitle(), foundFilm.getTitle());
        assertEquals(expectedFilm.getYear(), foundFilm.getYear());
        assertEquals(expectedFilm.getDirector().getId(), foundFilm.getDirector().getId());
        assertEquals(expectedFilm.getProductionCompany().getId(), foundFilm.getProductionCompany().getId());
    }

    @DisplayName("[FilmServiceImpl] customGetById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentFilmId_whenCustomGetById_thenThrow() {
        final long id = -1;

        Mockito.when(customRepository.getById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.customGetById(id));
    }

    @DisplayName("[FilmServiceImpl] customGetById (should return film)")
    @Test
    void givenNonExistentFilmId_whenCustomGetById_thenReturnFilmObject() {
        final long id = 5;
        final FilmBo expectedFilm = FilmBo.builder().id(id).title("FILM5").year(1995)
                .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(customRepository.getById(id)).thenReturn(Optional.of(SAMPLE_FILMS.get(4)));
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(4))).thenReturn(expectedFilm);

        final FilmBo foundFilm = service.customGetById(id);

        assertNotNull(foundFilm);
        assertEquals(id, foundFilm.getId());
        assertEquals(expectedFilm.getTitle(), foundFilm.getTitle());
        assertEquals(expectedFilm.getYear(), foundFilm.getYear());
        assertEquals(expectedFilm.getDirector().getId(), foundFilm.getDirector().getId());
        assertEquals(expectedFilm.getProductionCompany().getId(), foundFilm.getProductionCompany().getId());
    }

    @DisplayName("[FilmServiceImpl] validateTitle (should throw MissingRequiredFieldException)")
    @Test
    void givenInvalidTitle_whenValidateTitle_thenThrow() {
        final String title = "  \t      \t  \n";

        assertThrows(MissingRequiredFieldException.class, () -> service.validateTitle(title));
    }

    @DisplayName("[FilmServiceImpl] validateTitle (should not throw)")
    @Test
    void givenTitle_whenValidateTitle_thenDoesNotThrow() {
        final String title = "INCEPTION";

        assertDoesNotThrow(() -> service.validateTitle(title));
    }

    @DisplayName("[FilmServiceImpl] validateYear (should throw BadInputDataException)")
    @Test
    void givenInvalidYear_whenValidateYear_thenThrow() {
        final int year = -2010;

        assertThrows(BadInputDataException.class, () -> service.validateYear(year));
    }

    @DisplayName("[FilmServiceImpl] validateYear (should not throw)")
    @Test
    void givenYear_whenValidateYear_thenDoesNotThrow() {
        final int year = 2010;

        assertDoesNotThrow(() -> service.validateYear(year));
    }

    @DisplayName("[FilmServiceImpl] validateFilm (should throw BadInputDataException)")
    @Test
    void givenNullFilm_whenValidateFilm_thenThrow() {
        assertThrows(BadInputDataException.class, () -> service.validateFilm(null));
    }

    @DisplayName("[FilmServiceImpl] validateFilm (should not throw)")
    @Test
    void givenFilm_whenValidateFilm_thenDoesNotThrow() {
        final FilmBo film = FilmBo.builder().id(5L).title("FILM5").year(1995)
                .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        assertDoesNotThrow(() -> service.validateFilm(film));
    }

    @DisplayName("[FilmServiceImpl] create (throws DuplicateUniqueFieldException)")
    @Test
    void givenFilmWithDuplicateData_whenCreate_thenThrow() {
        final long id = 5;
        final FilmBo film = FilmBo.builder().id(id).title("FILM5").year(1995)
                .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(repository.existsById(id)).thenReturn(true);

        assertThrows(DuplicateUniqueFieldException.class, () -> service.create(film));
    }

    @DisplayName("[FilmServiceImpl] create (success)")
    @Test
    void givenFilm_whenCreate_thenFilmIsSaved() {
        final long id = 5;
        final FilmBo film = FilmBo.builder().id(id).title("FILM5").year(1995)
                .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(repository.existsById(id)).thenReturn(false);
        Mockito.when(mapper.boToEntity(film)).thenReturn(SAMPLE_FILMS.get(4));
        Mockito.when(repository.save(SAMPLE_FILMS.get(4))).thenReturn(SAMPLE_FILMS.get(4));
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(4))).thenReturn(film);

        final FilmBo savedFilm = service.create(film);

        assertEquals(id, savedFilm.getId());
        assertEquals(film.getTitle(), savedFilm.getTitle());
        assertEquals(film.getYear(), savedFilm.getYear());
        assertEquals(film.getDirector().getId(), savedFilm.getDirector().getId());
        assertEquals(film.getProductionCompany().getId(), savedFilm.getProductionCompany().getId());
    }

    @DisplayName("[FilmServiceImpl] customCreate (throws DuplicateUniqueFieldException)")
    @Test
    void givenFilmWithDuplicateData_whenCustomCreate_thenThrow() {
        final long id = 5;
        final FilmBo film = FilmBo.builder().id(id).title("FILM5").year(1995)
                .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(customRepository.existsById(id)).thenReturn(true);

        assertThrows(DuplicateUniqueFieldException.class, () -> service.customCreate(film));
    }

    @DisplayName("[FilmServiceImpl] customCreate (success)")
    @Test
    void givenFilm_whenCustomCreate_thenFilmIsSaved() {
        final long id = 5;
        final FilmBo film = FilmBo.builder().id(id).title("FILM5").year(1995)
                .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(customRepository.existsById(id)).thenReturn(false);
        Mockito.when(mapper.boToEntity(film)).thenReturn(SAMPLE_FILMS.get(4));
        Mockito.when(customRepository.save(SAMPLE_FILMS.get(4))).thenReturn(SAMPLE_FILMS.get(4));
        Mockito.when(mapper.entityToBo(SAMPLE_FILMS.get(4))).thenReturn(film);

        final FilmBo savedFilm = service.customCreate(film);

        assertEquals(id, savedFilm.getId());
        assertEquals(film.getTitle(), savedFilm.getTitle());
        assertEquals(film.getYear(), savedFilm.getYear());
        assertEquals(film.getDirector().getId(), savedFilm.getDirector().getId());
        assertEquals(film.getProductionCompany().getId(), savedFilm.getProductionCompany().getId());
    }

    @DisplayName("[FilmServiceImpl] update (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentFilmId_whenUpdate_thenThrow() {
        final long id = -5;
        final FilmBo film = FilmBo.builder().id(id).title("FILM5").year(1995)
                .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.update(id, film));
    }

    @DisplayName("[FilmServiceImpl] update (success)")
    @Test
    void givenFilmId_whenUpdate_thenFilmIsUpdated() {
        final long id = 5;
        final FilmBo film = FilmBo.builder().id(id).title("FILM05").year(1995)
                .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();
        final Film filmEntity = Film.builder().id(id).title("FILM05").year(1995)
                .director(DirectorSampleData.SAMPLE_DIRECTORS.get(0))
                .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(4))
                .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(0), ActorSampleData.SAMPLE_ACTORS.get(1)))
                .build();

        Mockito.when(repository.existsById(id)).thenReturn(true);
        Mockito.when(mapper.boToEntity(film)).thenReturn(filmEntity);
        Mockito.when(repository.save(filmEntity)).thenReturn(filmEntity);
        Mockito.when(mapper.entityToBo(filmEntity)).thenReturn(film);

        final FilmBo savedFilm = service.update(id, film);

        assertEquals(id, savedFilm.getId());
        assertEquals(film.getTitle(), savedFilm.getTitle());
        assertEquals(film.getYear(), savedFilm.getYear());
        assertEquals(film.getDirector().getId(), savedFilm.getDirector().getId());
        assertEquals(film.getProductionCompany().getId(), savedFilm.getProductionCompany().getId());
    }

    @DisplayName("[FilmServiceImpl] customUpdate (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentFilmId_whenCustomUpdate_thenThrow() {
        final long id = -5;
        final FilmBo film = FilmBo.builder().id(id).title("FILM5").year(1995)
                .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(customRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.customUpdate(id, film));
    }

    @DisplayName("[FilmServiceImpl] customUpdate (success)")
    @Test
    void givenFilmId_whenCustomUpdate_thenFilmIsUpdated() {
        final long id = 5;
        final FilmBo film = FilmBo.builder().id(id).title("FILM05").year(1995)
                .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();
        final Film filmEntity = Film.builder().id(id).title("FILM05").year(1995)
                .director(DirectorSampleData.SAMPLE_DIRECTORS.get(0))
                .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(4))
                .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(0), ActorSampleData.SAMPLE_ACTORS.get(1)))
                .build();

        Mockito.when(customRepository.existsById(id)).thenReturn(true);
        Mockito.when(mapper.boToEntity(film)).thenReturn(filmEntity);
        Mockito.when(customRepository.save(filmEntity)).thenReturn(filmEntity);
        Mockito.when(mapper.entityToBo(filmEntity)).thenReturn(film);

        final FilmBo savedFilm = service.customUpdate(id, film);

        assertEquals(id, savedFilm.getId());
        assertEquals(film.getTitle(), savedFilm.getTitle());
        assertEquals(film.getYear(), savedFilm.getYear());
        assertEquals(film.getDirector().getId(), savedFilm.getDirector().getId());
        assertEquals(film.getProductionCompany().getId(), savedFilm.getProductionCompany().getId());
    }

    @DisplayName("[FilmServiceImpl] deleteById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentFilmId_whenDeleteById_thenThrow() {
        final long id = -1;

        Mockito.when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(id));
    }

    @DisplayName("[FilmServiceImpl] deleteById (success)")
    @Test
    void givenFilmId_whenDeleteById_thenFilmIsRemoved() {
        final long id = 1;

        Mockito.when(repository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteById(id));
    }

    @DisplayName("[FilmServiceImpl] customDeleteById (should throw ResourceNotFoundexception)")
    @Test
    void givenNonExistentFilmId_whenCustomDeleteById_thenThrow() {
        final long id = -1;

        Mockito.when(customRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.customDeleteById(id));
    }

    @DisplayName("[FilmServiceImpl] customDeleteById (success)")
    @Test
    void givenFilmId_whenCustomDeleteById_thenFilmIsRemoved() {
        final long id = 1;

        Mockito.when(customRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> service.customDeleteById(id));
    }

}

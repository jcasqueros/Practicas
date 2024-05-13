package com.viewnex.bsan.practica04.service.impl;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.bo.ShowBo;
import com.viewnex.bsan.practica04.entity.Show;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnex.bsan.practica04.repository.ShowRepository;
import com.viewnex.bsan.practica04.repository.custom.CustomShowRepository;
import com.viewnex.bsan.practica04.sampledata.ActorSampleData;
import com.viewnex.bsan.practica04.sampledata.DirectorSampleData;
import com.viewnex.bsan.practica04.sampledata.ProductionCompanySampleData;
import com.viewnex.bsan.practica04.util.mapper.ServiceLevelShowMapper;
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

import static com.viewnex.bsan.practica04.sampledata.ShowSampleData.SAMPLE_SHOWS;
import static org.junit.jupiter.api.Assertions.*;

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
    void givenShows_whenGetAll_thenReturnShowList() {
        Mockito.when(repository.findAll()).thenReturn(SAMPLE_SHOWS);
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(0))).thenReturn(ShowBo.builder().id(1L).title("SHOW1")
                .year(2015).director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(1L).name("PRODUCTION_COMPANY1")
                        .yearFounded(1995).build())
                .actors(Set.of(ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build(),
                        ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(1))).thenReturn(ShowBo.builder().id(2L).title("SHOW2")
                .year(2020).director(DirectorBo.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build())
                .productionCompany(ProductionCompanyBo.builder().id(2L).name("PRODUCTION_COMPANY2")
                        .yearFounded(1975).build())
                .actors(Set.of(ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build(),
                        ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(2))).thenReturn(ShowBo.builder().id(3L).title("SHOW3")
                .year(2005).director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                        .yearFounded(1980).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(3))).thenReturn(ShowBo.builder().id(4L).title("SHOW4")
                .year(2010).director(DirectorBo.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build())
                .productionCompany(ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4")
                        .yearFounded(2000).build())
                .actors(Set.of(ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
                        ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(4))).thenReturn(ShowBo.builder().id(5L).title("SHOW5")
                .year(1995).director(DirectorBo.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
                        ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build()))
                .build()
        );

        List<ShowBo> expectedShows = List.of(
                ShowBo.builder().id(1L).title("SHOW1").year(2015)
                        .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(1L).name("PRODUCTION_COMPANY1")
                                .yearFounded(1995).build())
                        .actors(Set.of(ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build(),
                                ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build()))
                        .build(),
                ShowBo.builder().id(2L).title("SHOW2").year(2020)
                        .director(DirectorBo.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(2L).name("PRODUCTION_COMPANY2")
                                .yearFounded(1975).build())
                        .actors(Set.of(ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build(),
                                ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build()))
                        .build(),
                ShowBo.builder().id(3L).title("SHOW3").year(2005)
                        .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                                .yearFounded(1980).build())
                        .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                                ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                        .build(),
                ShowBo.builder().id(4L).title("SHOW4").year(2010)
                        .director(DirectorBo.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build())
                        .productionCompany(ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4")
                                .yearFounded(2000).build())
                        .actors(Set.of(ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
                                ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build()))
                        .build(),
                ShowBo.builder().id(5L).title("SHOW5").year(1995)
                        .director(DirectorBo.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                                .yearFounded(1990).build())
                        .actors(Set.of(ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
                                ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build()))
                        .build()
        );
        List<ShowBo> foundShows = service.getAll();

        assertFalse(foundShows.isEmpty());
        assertEquals(SAMPLE_SHOWS.size(), foundShows.size());
        assertTrue(foundShows.containsAll(expectedShows));
    }

    @DisplayName("[ShowServiceImpl] customGetAll (success)")
    @Test
    void givenShows_whenCustomGetAll_thenReturnShowList() {
        Mockito.when(customRepository.getAll()).thenReturn(SAMPLE_SHOWS);
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(0))).thenReturn(ShowBo.builder().id(1L).title("SHOW1")
                .year(2015).director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(1L).name("PRODUCTION_COMPANY1")
                        .yearFounded(1995).build())
                .actors(Set.of(ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build(),
                        ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(1))).thenReturn(ShowBo.builder().id(2L).title("SHOW2")
                .year(2020).director(DirectorBo.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build())
                .productionCompany(ProductionCompanyBo.builder().id(2L).name("PRODUCTION_COMPANY2")
                        .yearFounded(1975).build())
                .actors(Set.of(ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build(),
                        ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(2))).thenReturn(ShowBo.builder().id(3L).title("SHOW3")
                .year(2005).director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                        .yearFounded(1980).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(3))).thenReturn(ShowBo.builder().id(4L).title("SHOW4")
                .year(2010).director(DirectorBo.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build())
                .productionCompany(ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4")
                        .yearFounded(2000).build())
                .actors(Set.of(ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
                        ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build()))
                .build()
        );
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(4))).thenReturn(ShowBo.builder().id(5L).title("SHOW5")
                .year(1995).director(DirectorBo.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build())
                .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                        .yearFounded(1990).build())
                .actors(Set.of(ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
                        ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build()))
                .build()
        );

        List<ShowBo> expectedShows = List.of(
                ShowBo.builder().id(1L).title("SHOW1").year(2015)
                        .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(1L).name("PRODUCTION_COMPANY1")
                                .yearFounded(1995).build())
                        .actors(Set.of(ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build(),
                                ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build()))
                        .build(),
                ShowBo.builder().id(2L).title("SHOW2").year(2020)
                        .director(DirectorBo.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(2L).name("PRODUCTION_COMPANY2")
                                .yearFounded(1975).build())
                        .actors(Set.of(ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build(),
                                ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build()))
                        .build(),
                ShowBo.builder().id(3L).title("SHOW3").year(2005)
                        .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                                .yearFounded(1980).build())
                        .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                                ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                        .build(),
                ShowBo.builder().id(4L).title("SHOW4").year(2010)
                        .director(DirectorBo.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build())
                        .productionCompany(ProductionCompanyBo.builder().id(4L).name("PRODUCTION_COMPANY4")
                                .yearFounded(2000).build())
                        .actors(Set.of(ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
                                ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build()))
                        .build(),
                ShowBo.builder().id(5L).title("SHOW5").year(1995)
                        .director(DirectorBo.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build())
                        .productionCompany(ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5")
                                .yearFounded(1990).build())
                        .actors(Set.of(ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
                                ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build()))
                        .build()
        );
        List<ShowBo> foundShows = service.customGetAll();

        assertFalse(foundShows.isEmpty());
        assertEquals(SAMPLE_SHOWS.size(), foundShows.size());
        assertTrue(foundShows.containsAll(expectedShows));
    }

    @DisplayName("[ShowServiceImpl] getById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentShowId_whenGetById_thenThrow() {
        final long id = -1;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @DisplayName("[ShowServiceImpl] getById (should return show)")
    @Test
    void givenShowId_whenGetById_thenReturnShowObject() {
        final long id = 3;
        final ShowBo expectedShow = ShowBo.builder().id(id).title("SHOW3").year(2005)
                .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                        .yearFounded(1980).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(SAMPLE_SHOWS.get(2)));
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(2))).thenReturn(expectedShow);

        final ShowBo foundShow = service.getById(id);

        assertNotNull(foundShow);
        assertEquals(id, foundShow.getId());
        assertEquals(expectedShow.getId(), foundShow.getId());
        assertEquals(expectedShow.getTitle(), foundShow.getTitle());
        assertEquals(expectedShow.getYear(), foundShow.getYear());
        assertEquals(expectedShow.getDirector().getId(), foundShow.getDirector().getId());
        assertEquals(expectedShow.getProductionCompany().getId(), foundShow.getDirector().getId());
    }

    @DisplayName("[ShowServiceImpl] customGetById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentShowId_whenCustomGetById_thenThrow() {
        final long id = -1;

        Mockito.when(customRepository.getById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.customGetById(id));
    }

    @DisplayName("[ShowServiceImpl] customGetById (should return show)")
    @Test
    void givenNonExistentShowId_whenCustomGetById_thenReturnShowObject() {
        final long id = 3;
        final ShowBo expectedShow = ShowBo.builder().id(id).title("SHOW3").year(2005)
                .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                        .yearFounded(1980).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(customRepository.getById(id)).thenReturn(Optional.of(SAMPLE_SHOWS.get(2)));
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(2))).thenReturn(expectedShow);

        final ShowBo foundShow = service.customGetById(id);

        assertNotNull(foundShow);
        assertEquals(id, foundShow.getId());
        assertEquals(expectedShow.getId(), foundShow.getId());
        assertEquals(expectedShow.getTitle(), foundShow.getTitle());
        assertEquals(expectedShow.getYear(), foundShow.getYear());
        assertEquals(expectedShow.getDirector().getId(), foundShow.getDirector().getId());
        assertEquals(expectedShow.getProductionCompany().getId(), foundShow.getDirector().getId());
    }

    @DisplayName("[ShowServiceImpl] validateTitle (should throw MissingRequiredFieldException)")
    @Test
    void givenInvalidTitle_whenValidateName_thenThrow() {
        final String title = "        \n";

        assertThrows(MissingRequiredFieldException.class, () -> service.validateTitle(title));
    }

    @DisplayName("[ShowServiceImpl] validateTitle (should not throw)")
    @Test
    void givenName_whenValidateName_thenDoesNotThrow() {
        final String title = "WHAT WE DO IN THE SHADOWS";

        assertDoesNotThrow(() -> service.validateTitle(title));
    }

    @DisplayName("[ShowServiceImpl] validateYear (should throw BadInputDataException)")
    @Test
    void givenInvalidYear_whenValidateYear_thenThrow() {
        final int year = -2019;

        assertThrows(BadInputDataException.class, () -> service.validateYear(year));
    }

    @DisplayName("[ShowServiceImpl] validateYear (should not throw)")
    @Test
    void givenYear_whenValidateYear_thenDoesNotThrow() {
        final int year = 2019;

        assertDoesNotThrow(() -> service.validateYear(year));
    }

    @DisplayName("[ShowServiceImpl] validateShow (should throw BadInputDataException)")
    @Test
    void givenNullShow_whenValidateShow_thenThrow() {
        assertThrows(BadInputDataException.class, () -> service.validateShow(null));
    }

    @DisplayName("[ShowServiceImpl] validateShow (should not throw)")
    @Test
    void givenShow_whenValidateShow_thenDoesNotThrow() {
        final ShowBo show = ShowBo.builder().id(3L).title("SHOW3").year(2005)
                .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                        .yearFounded(1980).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        assertDoesNotThrow(() -> service.validateShow(show));
    }

    @DisplayName("[ShowServiceImpl] create (throws DuplicateUniqueFieldException)")
    @Test
    void givenShowWithDuplicateData_whenCreate_thenThrow() {
        final long id = 3;
        final ShowBo show = ShowBo.builder().id(id).title("SHOW3").year(2005)
                .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                        .yearFounded(1980).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(repository.existsById(id)).thenReturn(true);

        assertThrows(DuplicateUniqueFieldException.class, () -> service.create(show));
    }

    @DisplayName("[ShowServiceImpl] create (success)")
    @Test
    void givenShow_whenCreate_thenShowIsSaved() {
        final long id = 3;
        final ShowBo show = ShowBo.builder().id(id).title("SHOW3").year(2005)
                .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                        .yearFounded(1980).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(repository.existsById(id)).thenReturn(false);
        Mockito.when(mapper.boToEntity(show)).thenReturn(SAMPLE_SHOWS.get(2));
        Mockito.when(repository.save(SAMPLE_SHOWS.get(2))).thenReturn(SAMPLE_SHOWS.get(2));
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(2))).thenReturn(show);

        final ShowBo savedShow = service.create(show);

        assertEquals(id, savedShow.getId());
        assertEquals(show.getId(), savedShow.getId());
        assertEquals(show.getTitle(), savedShow.getTitle());
        assertEquals(show.getYear(), savedShow.getYear());
        assertEquals(show.getDirector().getId(), savedShow.getDirector().getId());
        assertEquals(show.getProductionCompany().getId(), savedShow.getDirector().getId());
    }

    @DisplayName("[ShowServiceImpl] customCreate (throws DuplicateUniqueFieldException)")
    @Test
    void givenShowWithDuplicateData_whenCustomCreate_thenThrow() {
        final long id = 3;
        final ShowBo show = ShowBo.builder().id(id).title("SHOW3").year(2005)
                .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                        .yearFounded(1980).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(customRepository.existsById(id)).thenReturn(true);

        assertThrows(DuplicateUniqueFieldException.class, () -> service.customCreate(show));
    }

    @DisplayName("[ShowServiceImpl] customCreate (success)")
    @Test
    void givenShow_whenCustomCreate_thenShowIsSaved() {
        final long id = 3;
        final ShowBo show = ShowBo.builder().id(id).title("SHOW3").year(2005)
                .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                        .yearFounded(1980).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(customRepository.existsById(id)).thenReturn(false);
        Mockito.when(mapper.boToEntity(show)).thenReturn(SAMPLE_SHOWS.get(2));
        Mockito.when(customRepository.save(SAMPLE_SHOWS.get(2))).thenReturn(SAMPLE_SHOWS.get(2));
        Mockito.when(mapper.entityToBo(SAMPLE_SHOWS.get(2))).thenReturn(show);

        final ShowBo savedShow = service.customCreate(show);

        assertEquals(id, savedShow.getId());
        assertEquals(show.getId(), savedShow.getId());
        assertEquals(show.getTitle(), savedShow.getTitle());
        assertEquals(show.getYear(), savedShow.getYear());
        assertEquals(show.getDirector().getId(), savedShow.getDirector().getId());
        assertEquals(show.getProductionCompany().getId(), savedShow.getDirector().getId());
    }

    @DisplayName("[ShowServiceImpl] update (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentShowId_whenUpdate_thenThrow() {
        final long id = 3;
        final ShowBo show = ShowBo.builder().id(id).title("SHOW3").year(2005)
                .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                        .yearFounded(1980).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.update(id, show));
    }

    @DisplayName("[ShowServiceImpl] update (success)")
    @Test
    void givenShowId_whenUpdate_thenShowIsUpdated() {
        final long id = 3;
        final ShowBo show = ShowBo.builder().id(id).title("SHOW03").year(2005)
                .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                        .yearFounded(1980).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();
        final Show showEntity = Show.builder().id(id).title("SHOW03").year(2005)
                .director(DirectorSampleData.SAMPLE_DIRECTORS.get(2))
                .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(2))
                .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(0), ActorSampleData.SAMPLE_ACTORS.get(1)))
                .build();

        Mockito.when(repository.existsById(id)).thenReturn(true);
        Mockito.when(mapper.boToEntity(show)).thenReturn(showEntity);
        Mockito.when(repository.save(showEntity)).thenReturn(showEntity);
        Mockito.when(mapper.entityToBo(showEntity)).thenReturn(show);

        final ShowBo savedShow = service.update(id, show);

        assertEquals(id, savedShow.getId());
        assertEquals(show.getId(), savedShow.getId());
        assertEquals(show.getTitle(), savedShow.getTitle());
        assertEquals(show.getYear(), savedShow.getYear());
        assertEquals(show.getDirector().getId(), savedShow.getDirector().getId());
        assertEquals(show.getProductionCompany().getId(), savedShow.getDirector().getId());
    }

    @DisplayName("[ShowServiceImpl] customUpdate (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentShowId_whenCustomUpdate_thenThrow() {
        final long id = 3;
        final ShowBo show = ShowBo.builder().id(id).title("SHOW3").year(2005)
                .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                        .yearFounded(1980).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Mockito.when(customRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.customUpdate(id, show));
    }

    @DisplayName("[ShowServiceImpl] customUpdate (success)")
    @Test
    void givenShowId_whenCustomUpdate_thenShowIsUpdated() {
        final long id = 3;
        final ShowBo show = ShowBo.builder().id(id).title("SHOW3").year(2005)
                .director(DirectorBo.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3")
                        .yearFounded(1980).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();
        final Show showEntity = Show.builder().id(id).title("SHOW03").year(2005)
                .director(DirectorSampleData.SAMPLE_DIRECTORS.get(2))
                .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(2))
                .actors(Set.of(ActorSampleData.SAMPLE_ACTORS.get(0), ActorSampleData.SAMPLE_ACTORS.get(1)))
                .build();

        Mockito.when(customRepository.existsById(id)).thenReturn(true);
        Mockito.when(mapper.boToEntity(show)).thenReturn(showEntity);
        Mockito.when(customRepository.save(showEntity)).thenReturn(showEntity);
        Mockito.when(mapper.entityToBo(showEntity)).thenReturn(show);

        final ShowBo savedShow = service.customUpdate(id, show);

        assertEquals(id, savedShow.getId());
        assertEquals(show.getId(), savedShow.getId());
        assertEquals(show.getTitle(), savedShow.getTitle());
        assertEquals(show.getYear(), savedShow.getYear());
        assertEquals(show.getDirector().getId(), savedShow.getDirector().getId());
        assertEquals(show.getProductionCompany().getId(), savedShow.getDirector().getId());
    }

    @DisplayName("[ShowServiceImpl] deleteById (should throw ResourceNotFoundException)")
    @Test
    void givenNonExistentShowId_whenDeleteById_thenThrow() {
        final long id = -3;

        Mockito.when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(id));
    }

    @DisplayName("[ShowServiceImpl] deleteById (success)")
    @Test
    void givenShowId_whenDeleteById_thenShowIsRemoved() {
        final long id = -3;

        Mockito.when(repository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteById(id));
    }

    @DisplayName("[ShowServiceImpl] customDeleteById (should throw ResourceNotFoundexception)")
    @Test
    void givenNonExistentShowId_whenCustomDeleteById_thenThrow() {
        final long id = -3;

        Mockito.when(customRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.customDeleteById(id));
    }

    @DisplayName("[ShowServiceImpl] customDeleteById (success)")
    @Test
    void givenShowId_whenCustomDeleteById_thenShowIsRemoved() {
        final long id = -3;

        Mockito.when(customRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> service.customDeleteById(id));
    }

}

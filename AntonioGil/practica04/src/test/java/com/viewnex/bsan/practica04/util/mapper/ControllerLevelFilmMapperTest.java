package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.bo.FilmBo;
import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for {@code ControllerLevelFilmMapper} (implementation provided by MapStruct).
 *
 * @author Antonio Gil
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ControllerLevelFilmMapperImpl.class})
class ControllerLevelFilmMapperTest {

    private final ControllerLevelFilmMapper mapper;

    @Autowired
    public ControllerLevelFilmMapperTest(ControllerLevelFilmMapper mapper) {
        this.mapper = mapper;
    }

    @DisplayName("[ControllerLevelFilmMapper] boToReadDto")
    @Test
    void givenBo_whenBoToReadDto_thenMappingIsCorrect() {
        FilmBo bo = FilmBo.builder().id(7L).title("FILM7").year(2020)
                .director(DirectorBo.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build())
                .productionCompany(ProductionCompanyBo.builder().id(3L).name("PRODUCTION_COMPANY3").yearFounded(1980)
                        .build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
                        ActorBo.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
                        ActorBo.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build(),
                        ActorBo.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build())
                )
                .build();

        FilmReadDto resultDto = mapper.boToReadDto(bo);

        assertEquals(bo.getId(), resultDto.getId());
        assertEquals(bo.getTitle(), resultDto.getTitle());
        assertEquals(bo.getYear(), resultDto.getYear());
        assertEquals(bo.getDirector().getId(), resultDto.getDirector().getId());
        assertEquals(bo.getProductionCompany().getId(), resultDto.getProductionCompany().getId());

        Predicate<ActorReadDto> actorWasConverted = actor ->
                bo.getActors().contains(ActorBo.builder().id(actor.getId()).build());

        assertTrue(resultDto.getActors().stream().allMatch(actorWasConverted));
    }

    @DisplayName("[ControllerLevelFilmMapper] dtoToBo")
    @Test
    void givenDto_whenDtoToBo_thenMappingIsCorrect() {
        FilmUpsertDto dto = FilmUpsertDto.builder().id(10L).title("FILM10").year(2005)
                .director(DirectorUpsertDto.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyUpsertDto.builder().id(1L).name("COMPANY1").yearFounded(1995).build())
                .actors(Set.of(ActorUpsertDto.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                                ActorUpsertDto.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
                                ActorUpsertDto.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
                                ActorUpsertDto.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build(),
                                ActorUpsertDto.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build())
                )
                .build();

        FilmBo resultBo = mapper.dtoToBo(dto);

        assertEquals(dto.getId(), resultBo.getId());
        assertEquals(dto.getTitle(), resultBo.getTitle());
        assertEquals(dto.getYear(), resultBo.getYear());
        assertEquals(dto.getDirector().getId(), resultBo.getDirector().getId());
        assertEquals(dto.getProductionCompany().getId(), resultBo.getProductionCompany().getId());

        Predicate<ActorBo> actorWasConverted = actor ->
                dto.getActors().contains(ActorUpsertDto.builder().id(actor.getId()).build());

        assertTrue(resultBo.getActors().stream().allMatch(actorWasConverted));
    }

}

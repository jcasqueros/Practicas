package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.bo.FilmBo;
import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.entity.Actor;
import com.viewnex.bsan.practica04.entity.Film;
import com.viewnex.bsan.practica04.sampledata.ActorSampleData;
import com.viewnex.bsan.practica04.sampledata.DirectorSampleData;
import com.viewnex.bsan.practica04.sampledata.ProductionCompanySampleData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for {@code ServiceLevelFilmMapper} (implementation provided by MapStruct).
 *
 * @author Antonio Gil
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceLevelFilmMapperImpl.class})
class ServiceLevelFilmMapperTest {

    private final ServiceLevelFilmMapper mapper;

    @Autowired
    public ServiceLevelFilmMapperTest(ServiceLevelFilmMapper mapper) {
        this.mapper = mapper;
    }

    @DisplayName("[ServiceLevelFilmMapper] entityToBo")
    @Test
    void givenEntity_whenEntityToBo_thenMappingIsCorrect() {
        Film entity = Film.builder().id(7L).title("FILM7").year(2020)
                .director(DirectorSampleData.SAMPLE_DIRECTORS.get(1))
                .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(2))
                .actors(new HashSet<>(ActorSampleData.SAMPLE_ACTORS))
                .build();

        FilmBo resultBo = mapper.entityToBo(entity);

        assertEquals(entity.getId(), resultBo.getId());
        assertEquals(entity.getTitle(), resultBo.getTitle());
        assertEquals(entity.getYear(), resultBo.getYear());
        assertEquals(entity.getDirector().getId(), resultBo.getDirector().getId());
        assertEquals(entity.getProductionCompany().getId(), resultBo.getProductionCompany().getId());

        Predicate<ActorBo> actorWasConverted = actor ->
                entity.getActors().contains(Actor.builder().id(actor.getId()).build());

        assertTrue(resultBo.getActors().stream().allMatch(actorWasConverted));
    }

    @DisplayName("[ServiceLevelFilmMapper] boToEntity")
    @Test
    void givenBo_whenBoToEntity_thenMappingIsCorrect() {
        FilmBo bo = FilmBo.builder().id(10L).title("FILM10").year(2005)
                .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(1L).name("COMPANY1").yearFounded(1995).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Film resultEntity = mapper.boToEntity(bo);

        assertEquals(bo.getId(), resultEntity.getId());
        assertEquals(bo.getTitle(), resultEntity.getTitle());
        assertEquals(bo.getYear(), resultEntity.getYear());
        assertEquals(bo.getDirector().getId(), resultEntity.getDirector().getId());
        assertEquals(bo.getProductionCompany().getId(), resultEntity.getProductionCompany().getId());

        Predicate<Actor> actorWasConverted = actor ->
                bo.getActors().contains(ActorBo.builder().id(actor.getId()).build());

        assertTrue(resultEntity.getActors().stream().allMatch(actorWasConverted));
    }

}

package com.viewnext.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.bo.ShowBo;
import com.viewnex.bsan.practica04.entity.Actor;
import com.viewnex.bsan.practica04.entity.Show;
import com.viewnex.bsan.practica04.sampledata.ActorSampleData;
import com.viewnex.bsan.practica04.sampledata.DirectorSampleData;
import com.viewnex.bsan.practica04.sampledata.ProductionCompanySampleData;
import com.viewnext.bsan.practica04.bo.ActorBo;
import com.viewnext.bsan.practica04.bo.DirectorBo;
import com.viewnext.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnext.bsan.practica04.bo.ShowBo;
import com.viewnext.bsan.practica04.entity.Actor;
import com.viewnext.bsan.practica04.entity.Show;
import com.viewnext.bsan.practica04.sampledata.ActorSampleData;
import com.viewnext.bsan.practica04.sampledata.DirectorSampleData;
import com.viewnext.bsan.practica04.sampledata.ProductionCompanySampleData;
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
 * Unit test for {@code ServiceLevelShowMapper} (implementation provided by MapStruct).
 *
 * @author Antonio Gil
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceLevelShowMapperImpl.class})
class ServiceLevelShowMapperTest {

    private final ServiceLevelShowMapper mapper;

    @Autowired
    public ServiceLevelShowMapperTest(ServiceLevelShowMapper mapper) {
        this.mapper = mapper;
    }

    @DisplayName("[ServiceLevelShowMapper] entityToBo")
    @Test
    void givenEntity_whenEntityToBo_thenMappingIsCorrect() {
        Show entity = Show.builder().id(11L).title("SHOW11").year(2015)
                .director(DirectorSampleData.SAMPLE_DIRECTORS.get(2))
                .productionCompany(ProductionCompanySampleData.SAMPLE_COMPANIES.get(3))
                .actors(new HashSet<>(ActorSampleData.SAMPLE_ACTORS))
                .build();

        ShowBo resultBo = mapper.entityToBo(entity);

        assertEquals(entity.getId(), resultBo.getId());
        assertEquals(entity.getTitle(), resultBo.getTitle());
        assertEquals(entity.getYear(), resultBo.getYear());
        assertEquals(entity.getDirector().getId(), resultBo.getDirector().getId());
        assertEquals(entity.getProductionCompany().getId(), resultBo.getProductionCompany().getId());

        Predicate<ActorBo> actorWasConverted = actor ->
                entity.getActors().contains(Actor.builder().id(actor.getId()).build());

        assertTrue(resultBo.getActors().stream().allMatch(actorWasConverted));
    }

    @DisplayName("[ServiceLevelShowMapper] boToEntity")
    @Test
    void givenBo_whenBoToEntity_thenMappingIsCorrect() {
        ShowBo bo = ShowBo.builder().id(13L).title("13L").year(2015)
                .director(DirectorBo.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build())
                .productionCompany(ProductionCompanyBo.builder().id(1L).name("COMPANY1").yearFounded(1995).build())
                .actors(Set.of(ActorBo.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
                        ActorBo.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build()))
                .build();

        Show resultEntity = mapper.boToEntity(bo);

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

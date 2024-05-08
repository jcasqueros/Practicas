package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.entity.Actor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceLevelActorMapperImpl.class})
class ServiceLevelActorMapperTest {

    private final ServiceLevelActorMapper mapper;

    @Autowired
    public ServiceLevelActorMapperTest(ServiceLevelActorMapper mapper) {
        this.mapper = mapper;
    }

    @DisplayName("[ServiceLevelActorMapper] entityToBo")
    @Test
    void givenEntity_whenEntityToBo_thenMappingIsCorrect() {
        Actor entity = Actor.builder().id(6L).name("ACTOR6").age(60).nationality("TUR").build();

        ActorBo resultBo = mapper.entityToBo(entity);

        assertEquals(entity.getId(), resultBo.getId());
        assertEquals(entity.getName(), resultBo.getName());
        assertEquals(entity.getAge(), resultBo.getAge());
        assertEquals(entity.getNationality(), resultBo.getNationality());
    }

    @DisplayName("[ServiceLevelActorMapper] boToEntity")
    @Test
    void givenBo_whenBoToEntity_thenMappingIsCorrect() {
        ActorBo bo = ActorBo.builder().id(9L).name("ACTOR9").age(30).nationality("JPN").build();

        Actor resultEntity = mapper.boToEntity(bo);

        assertEquals(bo.getId(), resultEntity.getId());
        assertEquals(bo.getName(), resultEntity.getName());
        assertEquals(bo.getAge(), resultEntity.getAge());
        assertEquals(bo.getNationality(), resultEntity.getNationality());
    }

}

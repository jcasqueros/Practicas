package com.santander.peliculacrud.util.mapper;

import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.output.ActorModelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceActorMapperImpl.class})
class ServiceActorMapperTest {

    @Autowired
    private ServiceActorMapper mapper;

    @Test
    void testEntityToBo() {
        Actor entity = Actor.builder().id(5L).name("ACTOR05").age(30).nation("ESP").build();

        ActorModelService bo = mapper.entityToBo(entity);

        assertNotNull(bo);
        assertEquals(entity.getName(), bo.getName());
        assertEquals(entity.getAge(), bo.getAge());
        assertEquals(entity.getNation(), bo.getNation());
    }

    @Test
    void testBoToEntity() {
        ActorModelService bo = ActorModelService.builder().name("ACTOR05").age(30).nation("ESP").build();

        Actor entity = mapper.boToEntity(bo);

        assertNotNull(entity);
        assertEquals(bo.getName(), entity.getName());
        assertEquals(bo.getAge(), entity.getAge());
        assertEquals(bo.getNation(), entity.getNation());
    }

}

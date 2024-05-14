package com.santander.peliculacrud.util.mapper;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.entity.Actor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Actor bo mapper test.
 */
@ExtendWith(MockitoExtension.class)
class ActorBOMapperTest {

    private ActorBOMapper actorBOMapper = Mappers.getMapper(ActorBOMapper.class);

    /**
     * Test bo to entity.
     */
    @Test
    @DisplayName("Test mapper actorBo to entity ")
    void testBoToEntity() {
        ActorBO actorBO = ActorBO.builder().name("Actor 1").age(30).build();

        Actor actor = actorBOMapper.boToEntity(actorBO);

        assertNotNull(actor);
        assertEquals("Actor 1", actor.getName());
        assertEquals(30, actor.getAge());
    }

    /**
     * Test entity to bo.
     */
    @Test
    @DisplayName("Test mapper entity to ActorBO")
    void testEntityToBo() {
        Actor actor = Actor.builder().name("Actor 1").age(30).build();

        ActorBO actorBO = actorBOMapper.entityToBo(actor);

        assertNotNull(actorBO);
        assertEquals("Actor 1", actorBO.getName());
        assertEquals(30, actorBO.getAge());
    }

    /**
     * Test list entityto list bo.
     */
    @Test
    @DisplayName("Test mapper list entity to list ActorBO")
    void testListEntitytoListBo() {
        List<Actor> actors = Arrays.asList(Actor.builder().name("Actor 1").age(30).build(),
                Actor.builder().name("Actor 2").age(25).build());

        List<ActorBO> actorBOs = actorBOMapper.listEntitytoListBo(actors);

        assertNotNull(actorBOs);
        assertEquals(2, actorBOs.size());
        assertEquals("Actor 1", actorBOs.get(0).getName());
        assertEquals(30, actorBOs.get(0).getAge());
        assertEquals("Actor 2", actorBOs.get(1).getName());
        assertEquals(25, actorBOs.get(1).getAge());
    }
}

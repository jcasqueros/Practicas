package com.santander.peliculacrud.util.mapper.bo;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.entity.Actor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Actor bo mapper test.
 */
@ExtendWith(MockitoExtension.class)
class ActorBOMapperTest {

    @InjectMocks
    private ActorBOMapper actorBOMapper = Mappers.getMapper(ActorBOMapper.class);

    /**
     * Test dto to bo.
     */
    @Test
    @DisplayName("Test actor mapper dto to bo")
    void testDtoToBo() {
        ActorBO actorDTO = ActorBO.builder().name("John Doe").age(30).build();

        Actor actorBO = actorBOMapper.boToEntity(actorDTO);

        assertNotNull(actorBO);
        assertEquals(actorDTO.getName(), actorBO.getName());
        assertEquals(actorDTO.getAge(), actorBO.getAge());
    }

    /**
     * Test bo to dto.
     */
    @Test
    @DisplayName("Test actor mapper bo to dto")
    void testBoToDto() {
        Actor actorBO = Actor.builder().name("Jane Doe").age(25).build();

        ActorBO actorDTO = actorBOMapper.entityToBo(actorBO);

        assertNotNull(actorDTO);
        assertEquals(actorBO.getName(), actorDTO.getName());
        assertEquals(actorBO.getAge(), actorDTO.getAge());
    }

    /**
     * Test bos to dtos.
     */
    @Test
    @DisplayName("Test actor mapper bos to dtos")
    void testBosToDtos() {
        List<Actor> actorBOS = Arrays.asList(Actor.builder().name("John Doe").age(30).build(),
                Actor.builder().name("Jane Doe").age(25).build());
        Page<Actor> actorPage = new PageImpl<>(actorBOS);

        List<ActorBO> actorDTOS = actorBOMapper.listEntitytoListBo(actorPage);

        assertNotNull(actorDTOS);
        assertEquals(2, actorDTOS.size());
        assertEquals(actorBOS.get(0).getName(), actorDTOS.get(0).getName());
        assertEquals(actorBOS.get(0).getAge(), actorDTOS.get(0).getAge());
        assertEquals(actorBOS.get(1).getName(), actorDTOS.get(1).getName());
        assertEquals(actorBOS.get(1).getAge(), actorDTOS.get(1).getAge());
    }

}

package com.santander.peliculacrud.util.mapper.dto;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.dto.ActorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Actor dto mapper test.
 */
@ExtendWith(MockitoExtension.class)
class ActorDTOMapperTest {

    @InjectMocks
    private ActorDTOMapper actorDTOMapper = Mappers.getMapper(ActorDTOMapper.class);

    /**
     * Test dto to bo.
     */
    @Test
    @DisplayName("Test actor mapper dto to bo")
    void testDtoToBo() {
        ActorDTO actorDTO = ActorDTO.builder().name("John Doe").age(30).build();

        ActorBO actorBO = actorDTOMapper.dtoToBo(actorDTO);

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
        ActorBO actorBO = ActorBO.builder().name("Jane Doe").age(25).build();

        ActorDTO actorDTO = actorDTOMapper.boToDto(actorBO);

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
        List<ActorBO> actorBOS = Arrays.asList(ActorBO.builder().name("John Doe").age(30).build(),
                ActorBO.builder().name("Jane Doe").age(25).build());

        List<ActorDTO> actorDTOS = actorDTOMapper.bosToDtos(actorBOS);

        assertNotNull(actorDTOS);
        assertEquals(2, actorDTOS.size());
        assertEquals(actorBOS.get(0).getName(), actorDTOS.get(0).getName());
        assertEquals(actorBOS.get(0).getAge(), actorDTOS.get(0).getAge());
        assertEquals(actorBOS.get(1).getName(), actorDTOS.get(1).getName());
        assertEquals(actorBOS.get(1).getAge(), actorDTOS.get(1).getAge());
    }
}

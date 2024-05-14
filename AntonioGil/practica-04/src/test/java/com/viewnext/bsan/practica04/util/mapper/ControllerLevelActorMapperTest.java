package com.viewnext.bsan.practica04.util.mapper;

import com.viewnext.bsan.practica04.bo.ActorBo;
import com.viewnext.bsan.practica04.dto.ActorReadDto;
import com.viewnext.bsan.practica04.dto.ActorUpsertDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for {@code ControllerLevelActorMapper} (implementation provided by MapStruct).
 *
 * @author Antonio Gil
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ControllerLevelActorMapperImpl.class})
class ControllerLevelActorMapperTest {

    private final ControllerLevelActorMapper mapper;

    @Autowired
    public ControllerLevelActorMapperTest(ControllerLevelActorMapper mapper) {
        this.mapper = mapper;
    }

    @DisplayName("[ControllerLevelActorMapper] boToReadDto")
    @Test
    void givenBo_whenBoToReadDto_thenMappingIsCorrect() {
        ActorBo bo = ActorBo.builder().id(6L).name("ACTOR6").age(60).nationality("TUR").build();

        ActorReadDto resultDto = mapper.boToReadDto(bo);

        assertEquals(bo.getId(), resultDto.getId());
        assertEquals(bo.getName(), resultDto.getName());
        assertEquals(bo.getAge(), resultDto.getAge());
        assertEquals(bo.getNationality(), resultDto.getNationality());
    }

    @DisplayName("[ControllerLevelActorMapper] dtoToBo")
    @Test
    void givenDto_whenDtoToBo_thenMappingIsCorrect() {
        ActorUpsertDto dto = ActorUpsertDto.builder().id(6L).name("ACTOR6").age(60).nationality("TUR").build();

        ActorBo resultBo = mapper.dtoToBo(dto);

        assertEquals(dto.getId(), resultBo.getId());
        assertEquals(dto.getName(), resultBo.getName());
        assertEquals(dto.getAge(), resultBo.getAge());
        assertEquals(dto.getNationality(), resultBo.getNationality());
    }

}

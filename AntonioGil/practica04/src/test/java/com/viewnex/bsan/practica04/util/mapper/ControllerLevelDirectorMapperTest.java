package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.dto.DirectorReadDto;
import com.viewnex.bsan.practica04.dto.DirectorUpsertDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for {@code ControllerLevelDirectorMapper} (implementation provided by MapStruct).
 *
 * @author Antonio Gil
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ControllerLevelDirectorMapperImpl.class})
class ControllerLevelDirectorMapperTest {

    private final ControllerLevelDirectorMapper mapper;

    @Autowired
    public ControllerLevelDirectorMapperTest(ControllerLevelDirectorMapper mapper) {
        this.mapper = mapper;
    }

    @DisplayName("[ControllerLevelDirectorMapper] boToReadDto")
    @Test
    void givenBo_whenBoToReadDto_thenMappingIsCorrect() {
        DirectorBo bo = DirectorBo.builder().id(10L).name("DIRECTOR10").age(70).nationality("GBR").build();

        DirectorReadDto resultDto = mapper.boToReadDto(bo);

        assertEquals(bo.getId(), resultDto.getId());
        assertEquals(bo.getName(), resultDto.getName());
        assertEquals(bo.getAge(), resultDto.getAge());
        assertEquals(bo.getNationality(), resultDto.getNationality());
    }

    @DisplayName("[ControllerLevelDirectorMapper] dtoToBo")
    @Test
    void givenDto_whenDtoToBo_thenMappingIsCorrect() {
        DirectorUpsertDto dto = DirectorUpsertDto.builder().id(10L).name("DIRECTOR10").age(70).nationality("GBR")
                .build();

        DirectorBo resultBo = mapper.dtoToBo(dto);

        assertEquals(dto.getId(), resultBo.getId());
        assertEquals(dto.getName(), resultBo.getName());
        assertEquals(dto.getAge(), resultBo.getAge());
        assertEquals(dto.getNationality(), resultBo.getNationality());
    }

}

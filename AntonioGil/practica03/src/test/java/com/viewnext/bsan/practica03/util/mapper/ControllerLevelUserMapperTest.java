package com.viewnext.bsan.practica03.util.mapper;

import com.viewnext.bsan.practica03.business.bo.UserBo;
import com.viewnext.bsan.practica03.presentation.dto.UserReadDto;
import com.viewnext.bsan.practica03.presentation.dto.UserUpsertDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ControllerLevelUserMapperImpl.class})
class ControllerLevelUserMapperTest {

    private final ControllerLevelUserMapper mapper;

    @Autowired
    public ControllerLevelUserMapperTest(ControllerLevelUserMapper mapper) {
        this.mapper = mapper;
    }

    @DisplayName("[ControllerLevelUserMapper] boToReadDto")
    @Test
    void givenBo_whenBoToReadDto_thenMappingIsCorrect() {
        UserBo bo = UserBo.builder().dni("11111111H").name("JOSE").surname("DOMINGUEZ").age(35).build();

        UserReadDto resultDto = mapper.boToReadDto(bo);

        assertEquals(bo.getDni(), resultDto.getDni());
        assertEquals(bo.getName(), resultDto.getName());
        assertEquals(bo.getSurname(), resultDto.getSurname());
        assertEquals(bo.getAge(), resultDto.getAge());
    }

    @DisplayName("[ControllerLevelUserMapper] dtoToBo")
    @Test
    void givenDto_whenDtoToBo_thenMappingIsCorrect() {
        UserUpsertDto dto = UserUpsertDto.builder().dni("11111111H").name("JOSE").surname("DOMINGUEZ").age(35).build();

        UserBo resultBo = mapper.dtoToBo(dto);

        assertEquals(dto.getDni(), resultBo.getDni());
        assertEquals(dto.getName(), resultBo.getName());
        assertEquals(dto.getSurname(), resultBo.getSurname());
        assertEquals(dto.getAge(), resultBo.getAge());
    }

}

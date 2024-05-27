package com.viewnext.bsan.practica03.util.mapper;

import com.viewnext.bsan.practica03.business.bo.UserBo;
import com.viewnext.bsan.practica03.presentation.dto.UserReadDto;
import com.viewnext.bsan.practica03.presentation.dto.UserUpsertDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ControllerLevelUserMapper {

    UserReadDto boToReadDto(UserBo bo);

    UserBo dtoToBo(UserUpsertDto dto);

}

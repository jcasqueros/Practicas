package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ShowBo;
import com.viewnex.bsan.practica04.dto.ShowReadDto;
import com.viewnex.bsan.practica04.dto.ShowUpsertDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ControllerLevelShowMapper {

    ShowReadDto boToReadDto(ShowBo bo);

    ShowBo dtoToBo(ShowUpsertDto dto);

}

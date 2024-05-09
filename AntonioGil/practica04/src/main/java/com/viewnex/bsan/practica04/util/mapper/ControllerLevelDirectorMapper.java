package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.dto.DirectorReadDto;
import com.viewnex.bsan.practica04.dto.DirectorUpsertDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ControllerLevelDirectorMapper {

    DirectorReadDto boToReadDto(DirectorBo bo);

    DirectorBo dtoToBo(DirectorUpsertDto dto);

}

package com.viewnext.bsan.practica04.util.mapper;

import com.viewnext.bsan.practica04.bo.DirectorBo;
import com.viewnext.bsan.practica04.dto.DirectorReadDto;
import com.viewnext.bsan.practica04.dto.DirectorUpsertDto;
import com.viewnext.bsan.practica04.bo.DirectorBo;
import org.mapstruct.Mapper;

/**
 * The {@code ControllerLevelDirectorMapper} interface is a MapStruct mapper that handles conversions between
 * {@code DirectorBo} business objects and {@code DirectorReadDto} or {@code ActorUpsertDto} data transfer objects.
 * It is used by controllers to convert between native objects from each layer (business logic and presentation).
 *
 * @author Antonio Gil
 */
@Mapper(componentModel = "spring")
public interface ControllerLevelDirectorMapper {

    DirectorReadDto boToReadDto(DirectorBo bo);

    DirectorBo dtoToBo(DirectorUpsertDto dto);

}
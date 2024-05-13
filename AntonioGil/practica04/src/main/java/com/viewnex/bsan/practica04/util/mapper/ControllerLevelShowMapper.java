package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ShowBo;
import com.viewnex.bsan.practica04.dto.ShowReadDto;
import com.viewnex.bsan.practica04.dto.ShowUpsertDto;
import org.mapstruct.Mapper;

/**
 * The {@code ControllerLevelShowMapper} interface is a MapStruct mapper that handles conversions between
 * {@code ShowBo} business objects and {@code ShowReadDto} or {@code ShowUpsertDto} data transfer objects. It is used
 * by controllers to convert between native objects from each layer (business logic and presentation).
 *
 * @author Antonio Gil
 */
@Mapper(componentModel = "spring")
public interface ControllerLevelShowMapper {

    ShowReadDto boToReadDto(ShowBo bo);

    ShowBo dtoToBo(ShowUpsertDto dto);

}

package com.viewnext.bsan.practica04.util.mapper;

import com.viewnext.bsan.practica04.business.bo.ShowBo;
import com.viewnext.bsan.practica04.presentation.dto.ShowReadDto;
import com.viewnext.bsan.practica04.presentation.dto.ShowUpsertDto;
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

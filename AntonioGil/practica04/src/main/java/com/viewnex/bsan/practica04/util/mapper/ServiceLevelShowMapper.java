package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ShowBo;
import com.viewnex.bsan.practica04.entity.Show;
import org.mapstruct.Mapper;

/**
 * The {@code ServiceLevelShowMapper} interface is a MapStruct mapper that handles conversions between {@code Show}
 * entity objects and {@code ShowBo} business objects. It is used by service implementations to convert between
 * native objects from each layer (persistence and business logic).
 *
 * @author Antonio Gil
 */
@Mapper(componentModel = "spring")
public interface ServiceLevelShowMapper {

    ShowBo entityToBo(Show entity);

    Show boToEntity(ShowBo bo);

}

package com.viewnext.bsan.practica04.util.mapper;

import com.viewnext.bsan.practica04.business.bo.ShowBo;
import com.viewnext.bsan.practica04.persistence.entity.Show;
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

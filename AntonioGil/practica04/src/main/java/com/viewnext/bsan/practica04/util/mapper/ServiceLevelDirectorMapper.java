package com.viewnext.bsan.practica04.util.mapper;

import com.viewnext.bsan.practica04.bo.DirectorBo;
import com.viewnext.bsan.practica04.entity.Director;
import org.mapstruct.Mapper;

/**
 * The {@code ServiceLevelDirectorMapper} interface is a MapStruct mapper that handles conversions between
 * {@code Director} entity objects and {@code DirectorBo} business objects. It is used by service implementations to
 * convert between native objects from each layer (persistence and business logic).
 *
 * @author Antonio Gil
 */
@Mapper(componentModel = "spring")
public interface ServiceLevelDirectorMapper {

    DirectorBo entityToBo(Director entity);

    Director boToEntity(DirectorBo bo);

}

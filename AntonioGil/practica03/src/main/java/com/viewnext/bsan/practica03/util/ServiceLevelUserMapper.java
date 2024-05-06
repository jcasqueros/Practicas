package com.viewnext.bsan.practica03.util;

import com.viewnext.bsan.practica03.bo.UserBo;
import com.viewnext.bsan.practica03.entity.User;
import org.mapstruct.Mapper;

/**
 * The {@code ServiceLevelUserMapper} is a MapStruct mapper that handles conversions between {@code User} entity objects
 * and {@code UserBo} business objects. It is used by service implementations to convert between native objects from
 * each layer (persistence and business logic).
 *
 * @author Antonio Gil
 */
@Mapper(componentModel = "spring")
public interface ServiceLevelUserMapper {

    /**
     * Converts a {@code User} entity object to a {@code UserBo} business object.
     *
     * @param entity The source entity object
     * @return The entity object converted to a business object
     */
    UserBo entityToBo(User entity);

    /**
     * Converts a {@code UserBo} business object to a {@code User} entity object.
     * @param bo The source business object
     * @return The business object converted to an entity object
     */
    User boToEntity(UserBo bo);

}

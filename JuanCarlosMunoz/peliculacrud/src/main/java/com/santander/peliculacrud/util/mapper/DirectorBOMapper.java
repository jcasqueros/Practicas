package com.santander.peliculacrud.util.mapper;

import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.entity.Director;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * The interface Director bo mapper.
 */
@Mapper(componentModel = "spring")
public interface DirectorBOMapper {

    /**
     * Bot to entity director.
     *
     * @param directorBO
     *         the director bo
     * @return the director
     */
    Director boToEntity(DirectorBO directorBO);

    /**
     * Entity to bo director bo.
     *
     * @param director
     *         the director
     * @return the director bo
     */
    DirectorBO entityToBo(Director director);

    /**
     * List entityto list bo list.
     *
     * @return the list
     */
    List<DirectorBO> listEntitytoListBo(List<Director> directors);

}

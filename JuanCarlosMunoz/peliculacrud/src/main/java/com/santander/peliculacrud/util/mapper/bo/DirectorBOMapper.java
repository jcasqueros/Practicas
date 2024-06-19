package com.santander.peliculacrud.util.mapper.bo;

import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.entity.Director;

import org.springframework.data.domain.Page;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Director bo mapper.
 */
@Mapper(componentModel = "spring")
@Component
public interface DirectorBOMapper {

    /**
     * Bo to entity director.
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
     * @param directorsPage
     *         the directors page
     * @return the list
     */
    List<DirectorBO> listEntitytoListBo(Page<Director> directorsPage);

    List<Director> listBoToEntity(List<DirectorBO> director);
}

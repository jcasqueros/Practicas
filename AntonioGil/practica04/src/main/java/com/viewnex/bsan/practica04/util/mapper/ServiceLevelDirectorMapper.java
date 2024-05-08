package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.entity.Director;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceLevelDirectorMapper {

    DirectorBo entityToBo(Director entity);

    Director boToEntity(DirectorBo bo);

}

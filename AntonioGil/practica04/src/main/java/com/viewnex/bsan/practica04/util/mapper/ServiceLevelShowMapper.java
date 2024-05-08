package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ShowBo;
import com.viewnex.bsan.practica04.entity.Show;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceLevelShowMapper {

    ShowBo entityToBo(Show entity);

    Show boToEntity(ShowBo bo);

}

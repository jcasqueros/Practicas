package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.dto.ProductionCompanyReadDto;
import com.viewnex.bsan.practica04.dto.ProductionCompanyUpsertDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ControllerLevelProductionCompanyMapper {

    ProductionCompanyReadDto boToReadDto(ProductionCompanyBo bo);

    ProductionCompanyBo dtoToBo(ProductionCompanyUpsertDto dto);

}

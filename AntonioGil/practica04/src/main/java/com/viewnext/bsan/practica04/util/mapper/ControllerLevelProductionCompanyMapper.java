package com.viewnext.bsan.practica04.util.mapper;

import com.viewnext.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnext.bsan.practica04.dto.ProductionCompanyReadDto;
import com.viewnext.bsan.practica04.dto.ProductionCompanyUpsertDto;
import org.mapstruct.Mapper;

/**
 * The {@code ControllerLevelProductionCompanyMapper} interface is a MapStruct mapper that handles conversions between
 * {@code ProductionCompanyBo} business objects and {@code ProductionCompanyReadDto} or
 * {@code ProductionCompanyUpsertDto} data transfer objects. It is used by controllers to convert between native
 * objects from each layer (business logic and presentation).
 *
 * @author Antonio Gil
 */
@Mapper(componentModel = "spring")
public interface ControllerLevelProductionCompanyMapper {

    ProductionCompanyReadDto boToReadDto(ProductionCompanyBo bo);

    ProductionCompanyBo dtoToBo(ProductionCompanyUpsertDto dto);

}

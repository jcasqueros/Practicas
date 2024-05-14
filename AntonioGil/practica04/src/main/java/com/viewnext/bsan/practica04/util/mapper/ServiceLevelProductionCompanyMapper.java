package com.viewnext.bsan.practica04.util.mapper;

import com.viewnext.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnext.bsan.practica04.entity.ProductionCompany;
import org.mapstruct.Mapper;

/**
 * The {@code ServiceLevelProductionCompanyMapper} interface is a MapStruct mapper that handles conversions between
 * {@code ProductionCompany} entity objects and {@code ProductionCompanyBo} business objects. It is used by service
 * implementations to convert between native objects from each layer (persistence and business logic).
 *
 * @author Antonio Gil
 */
@Mapper(componentModel = "spring")
public interface ServiceLevelProductionCompanyMapper {

    ProductionCompanyBo entityToBo(ProductionCompany entity);

    ProductionCompany boToEntity(ProductionCompanyBo bo);

}

package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.entity.ProductionCompany;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceLevelProductionCompanyMapper {

    ProductionCompanyBo entityToBo(ProductionCompany entity);

    ProductionCompany boToEntity(ProductionCompanyBo bo);

}

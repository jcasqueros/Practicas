package com.viewnext.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.entity.ProductionCompany;
import com.viewnext.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnext.bsan.practica04.entity.ProductionCompany;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for {@code ServiceLevelProductionCompanyMapper} (implementation provided by MapStruct).
 *
 * @author Antonio Gil
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceLevelProductionCompanyMapperImpl.class})
class ServiceLevelProductionCompanyMapperTest {

    private ServiceLevelProductionCompanyMapper mapper;

    @Autowired
    public ServiceLevelProductionCompanyMapperTest(ServiceLevelProductionCompanyMapper mapper) {
        this.mapper = mapper;
    }

    @DisplayName("[ServiceLevelProductionCompanyMapper] entityToBo")
    @Test
    void givenEntity_whenEntityToBo_thenMappingIsCorrect() {
        ProductionCompany entity = ProductionCompany.builder().id(8L).name("PRODUCTION_COMPANY8").yearFounded(1950)
                .build();

        ProductionCompanyBo resultBo = mapper.entityToBo(entity);

        assertEquals(entity.getId(), resultBo.getId());
        assertEquals(entity.getName(), resultBo.getName());
        assertEquals(entity.getYearFounded(), resultBo.getYearFounded());
    }

    @DisplayName("[ServiceLevelProductionCompanyMapper] boToEntity")
    @Test
    void givenBo_whenBoToEntity_thenMappingIsCorrect() {
        ProductionCompanyBo bo = ProductionCompanyBo.builder().id(5L).name("PRODUCTION_COMPANY5").yearFounded(1985)
                .build();

        ProductionCompany resultEntity = mapper.boToEntity(bo);

        assertEquals(bo.getId(), resultEntity.getId());
        assertEquals(bo.getName(), resultEntity.getName());
        assertEquals(bo.getYearFounded(), resultEntity.getYearFounded());
    }

}

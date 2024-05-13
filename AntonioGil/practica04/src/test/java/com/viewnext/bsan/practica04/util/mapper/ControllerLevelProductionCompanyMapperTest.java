package com.viewnext.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.dto.ProductionCompanyReadDto;
import com.viewnex.bsan.practica04.dto.ProductionCompanyUpsertDto;
import com.viewnext.bsan.practica04.bo.ProductionCompanyBo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for {@code ControllerLevelProductionCompanyMapper} (implementation provided by MapStruct).
 *
 * @author Antonio Gil
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ControllerLevelProductionCompanyMapperImpl.class})
class ControllerLevelProductionCompanyMapperTest {

    private ControllerLevelProductionCompanyMapper mapper;

    @Autowired
    public ControllerLevelProductionCompanyMapperTest(ControllerLevelProductionCompanyMapper mapper) {
        this.mapper = mapper;
    }

    @DisplayName("[ControllerLevelProductionCompanyMapper] boToReadDto")
    @Test
    void givenBo_whenBoToReadDto_thenMappingIsCorrect() {
        ProductionCompanyBo bo = ProductionCompanyBo.builder().id(8L).name("PRODUCTION_COMPANY8").yearFounded(1950)
                .build();

        ProductionCompanyReadDto resultDto = mapper.boToReadDto(bo);

        assertEquals(bo.getId(), resultDto.getId());
        assertEquals(bo.getName(), resultDto.getName());
        assertEquals(bo.getYearFounded(), resultDto.getYearFounded());
    }

    @DisplayName("[ControllerLevelProductionCompanyMapper] dtoToBo")
    @Test
    void givenDto_whenDtoToBo_thenMappingIsCorrect() {
        ProductionCompanyUpsertDto dto = ProductionCompanyUpsertDto.builder().id(8L).name("PRODUCTION_COMPANY8")
                .yearFounded(1950).build();

        ProductionCompanyBo resultBo = mapper.dtoToBo(dto);

        assertEquals(dto.getId(), resultBo.getId());
        assertEquals(dto.getName(), resultBo.getName());
        assertEquals(dto.getYearFounded(), resultBo.getYearFounded());
    }

}

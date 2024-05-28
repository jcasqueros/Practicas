package com.santander.peliculacrud.util.mapper.dto;

import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.dto.DirectorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Director dto mapper test.
 */
@ExtendWith(MockitoExtension.class)
class DirectorDTOMapperTest {

    @InjectMocks
    private DirectorDTOMapper directorDTOMapper = Mappers.getMapper(DirectorDTOMapper.class);

    /**
     * Test dto to bo.
     */
    @Test
    @DisplayName("Test director mapper dto to bo")
    void testDtoToBo() {
        DirectorDTO directorDTO = DirectorDTO.builder().name("John Doe").age(30).build();

        DirectorBO directorBO = directorDTOMapper.dtoToBo(directorDTO);

        assertNotNull(directorBO);
        assertEquals(directorDTO.getName(), directorBO.getName());
        assertEquals(directorDTO.getAge(), directorBO.getAge());
    }

    /**
     * Test bo to dto.
     */
    @Test
    @DisplayName("Test director mapper bo to dto")
    void testBoToDto() {
        DirectorBO directorBO = DirectorBO.builder().name("Jane Doe").age(25).build();

        DirectorDTO directorDTO = directorDTOMapper.boToDto(directorBO);

        assertNotNull(directorDTO);
        assertEquals(directorBO.getName(), directorDTO.getName());
        assertEquals(directorBO.getAge(), directorDTO.getAge());
    }

    /**
     * Test bos to dtos.
     */
    @Test
    @DisplayName("Test director mapper bos to dtos")
    void testBosToDtos() {
        List<DirectorBO> directorBOS = Arrays.asList(DirectorBO.builder().name("John Doe").age(30).build(),
                DirectorBO.builder().name("Jane Doe").age(25).build());

        List<DirectorDTO> directorDTOS = directorDTOMapper.bosToDtos(directorBOS);

        assertNotNull(directorDTOS);
        assertEquals(2, directorDTOS.size());
        assertEquals(directorBOS.get(0).getName(), directorDTOS.get(0).getName());
        assertEquals(directorBOS.get(0).getAge(), directorDTOS.get(0).getAge());
        assertEquals(directorBOS.get(1).getName(), directorDTOS.get(1).getName());
        assertEquals(directorBOS.get(1).getAge(), directorDTOS.get(1).getAge());
    }
}

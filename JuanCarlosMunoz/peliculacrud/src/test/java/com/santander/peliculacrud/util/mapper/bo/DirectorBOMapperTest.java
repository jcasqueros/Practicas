package com.santander.peliculacrud.util.mapper.bo;


import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.entity.Director;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DirectorBOMapperTest {



    @InjectMocks
    private DirectorBOMapper directorBOMapper = Mappers.getMapper(DirectorBOMapper.class);

    /**
     * Test dto to bo.
     */
    @Test
    @DisplayName("Test director mapper dto to bo")
    void testDtoToBo() {
        DirectorBO directorDTO = DirectorBO.builder().name("John Doe").age(30).build();

        Director directorBO = directorBOMapper.boToEntity(directorDTO);

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
        Director directorBO = Director.builder().name("Jane Doe").age(25).build();

        DirectorBO directorDTO = directorBOMapper.entityToBo(directorBO);

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
        List<Director> directorBOS = Arrays.asList(Director.builder().name("John Doe").age(30).build(),
                Director.builder().name("Jane Doe").age(25).build());
        Page<Director> directorPage = new PageImpl<>(directorBOS);

        List<DirectorBO> directorDTOS = directorBOMapper.listEntitytoListBo(directorPage);

        assertNotNull(directorDTOS);
        assertEquals(2, directorDTOS.size());
        assertEquals(directorBOS.get(0).getName(), directorDTOS.get(0).getName());
        assertEquals(directorBOS.get(0).getAge(), directorDTOS.get(0).getAge());
        assertEquals(directorBOS.get(1).getName(), directorDTOS.get(1).getName());
        assertEquals(directorBOS.get(1).getAge(), directorDTOS.get(1).getAge());
    }

}

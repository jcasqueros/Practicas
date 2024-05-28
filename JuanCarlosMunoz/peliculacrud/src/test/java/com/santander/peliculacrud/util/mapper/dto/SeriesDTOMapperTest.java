package com.santander.peliculacrud.util.mapper.dto;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.dto.SeriesDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type SeriesDTO bo mapper test.
 */
@ExtendWith(MockitoExtension.class)
class SeriesDTOMapperTest {

    @InjectMocks
    private SeriesDTOMapper seriesDTOMapper = Mappers.getMapper(SeriesDTOMapper.class);

    /**
     * Test bo to dto.
     */
    @Test
    @DisplayName("Test Series mapper bo to dto")
    void testBoToDTO() {
        SeriesBO seriesBO = SeriesBO.builder().title("PRV8 Series").created(2022)
                .actors(Arrays.asList(ActorBO.builder().id(1L).name("PRV9 Doe").age(30).nation("USA").build(),
                        ActorBO.builder().id(2L).name("Jane Doe").age(25).nation("UK").build()))
                .director(DirectorBO.builder().id(1L).name("PRV10 Doe").age(40).nation("USA").build()).build();

        SeriesDTO seriesDTO = seriesDTOMapper.boToDTO(seriesBO);

        assertNotNull(seriesDTO);
        assertEquals(seriesBO.getTitle(), seriesDTO.getTitle());
        assertEquals(seriesBO.getCreated(), seriesDTO.getCreated());
        assertEquals(seriesBO.getActors().size(), seriesDTO.getIdActor().size());
        assertEquals(seriesBO.getDirector().getId(), seriesDTO.getIdDirector());
    }

    /**
     * Test dto to bo.
     */
    @Test
    @DisplayName("Test Series mapper dto to bo")
    void testDtoToBo() {
        SeriesDTO seriesDTO = SeriesDTO.builder().title("PRV11 Series").created(2022).idActor(Arrays.asList(1L, 2L))
                .idDirector(1L).build();

        SeriesBO seriesBO = seriesDTOMapper.dtoToBo(seriesDTO);

        assertNotNull(seriesBO);
        assertEquals(seriesDTO.getTitle(), seriesBO.getTitle());
        assertEquals(seriesDTO.getCreated(), seriesBO.getCreated());
        assertEquals(seriesDTO.getIdActor().size(), seriesBO.getActors().size());
        assertEquals(seriesDTO.getIdDirector(), seriesBO.getDirector().getId());
    }

    /**
     * Test bos to dtos.
     */
    @Test
    @DisplayName("Test Series mapper bos to dtos")
    void testBosToDtos() {
        List<SeriesBO> seriesBOS = Arrays.asList(SeriesBO.builder().title("PRV12 Series").created(2022)
                        .actors(Arrays.asList(ActorBO.builder().id(1L).name("PRV13 Doe").age(30).nation("USA").build(),
                                ActorBO.builder().id(2L).name("Jane Doe").age(25).nation("UK").build()))
                        .director(DirectorBO.builder().id(1L).name("PRV14 Doe").age(40).nation("USA").build()).build(),
                SeriesBO.builder().title("PRV15 Series").created(2023)
                        .actors(Arrays.asList(ActorBO.builder().id(3L).name("PRV16 Doe").age(35).nation("USA").build(),
                                ActorBO.builder().id(4L).name("John Doe").age(30).nation("UK").build()))
                        .director(DirectorBO.builder().id(2L).name("PRV17 Doe").age(45).nation("USA").build()).build());

        List<SeriesDTO> seriesDTOS = seriesDTOMapper.bosToDtos(seriesBOS);

        assertNotNull(seriesDTOS);
        assertEquals(2, seriesDTOS.size());
        assertEquals(seriesBOS.get(0).getTitle(), seriesDTOS.get(0).getTitle());
        assertEquals(seriesBOS.get(0).getCreated(), seriesDTOS.get(0).getCreated());
        assertEquals(seriesBOS.get(0).getActors().size(), seriesDTOS.get(0).getIdActor().size());
        assertEquals(seriesBOS.get(0).getDirector().getId(), seriesDTOS.get(0).getIdDirector());
        assertEquals(seriesBOS.get(1).getTitle(), seriesDTOS.get(1).getTitle());
        assertEquals(seriesBOS.get(1).getCreated(), seriesDTOS.get(1).getCreated());
        assertEquals(seriesBOS.get(1).getActors().size(), seriesDTOS.get(1).getIdActor().size());
        assertEquals(seriesBOS.get(1).getDirector().getId(), seriesDTOS.get(1).getIdDirector());
    }
}

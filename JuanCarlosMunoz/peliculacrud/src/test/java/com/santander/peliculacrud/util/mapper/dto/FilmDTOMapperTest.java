package com.santander.peliculacrud.util.mapper.dto;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.model.dto.FilmDTO;
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
 * The type FilmDTO bo mapper test.
 */
@ExtendWith(MockitoExtension.class)
class FilmDTOMapperTest {

    @InjectMocks
    private FilmDTOMapper filmDTOMapper = Mappers.getMapper(FilmDTOMapper.class);

    /**
     * Test bo to dto.
     */
    @Test
    @DisplayName("Test Film mapper bo to dto")
    void testBoToDTO() {
        FilmBO filmBO = FilmBO.builder().title("PRV8 Film").created(2022)
                .actors(Arrays.asList(ActorBO.builder().id(1L).name("PRV9 Doe").age(30).nation("USA").build(),
                        ActorBO.builder().id(2L).name("Jane Doe").age(25).nation("UK").build()))
                .director(DirectorBO.builder().id(1L).name("PRV10 Doe").age(40).nation("USA").build()).build();

        FilmDTO filmDTO = filmDTOMapper.boToDTO(filmBO);

        assertNotNull(filmDTO);
        assertEquals(filmBO.getTitle(), filmDTO.getTitle());
        assertEquals(filmBO.getCreated(), filmDTO.getCreated());
        assertEquals(filmBO.getActors().size(), filmDTO.getIdActor().size());
        assertEquals(filmBO.getDirector().getId(), filmDTO.getIdDirector());
    }

    /**
     * Test dto to bo.
     */
    @Test
    @DisplayName("Test Film mapper dto to bo")
    void testDtoToBo() {
        FilmDTO filmDTO = FilmDTO.builder().title("PRV11 Film").created(2022).idActor(Arrays.asList(1L, 2L))
                .idDirector(1L).build();

        FilmBO filmBO = filmDTOMapper.dtoToBo(filmDTO);

        assertNotNull(filmBO);
        assertEquals(filmDTO.getTitle(), filmBO.getTitle());
        assertEquals(filmDTO.getCreated(), filmBO.getCreated());
        assertEquals(filmDTO.getIdActor().size(), filmBO.getActors().size());
        assertEquals(filmDTO.getIdDirector(), filmBO.getDirector().getId());
    }

    /**
     * Test bos to dtos.
     */
    @Test
    @DisplayName("Test Film mapper bos to dtos")
    void testBosToDtos() {
        List<FilmBO> filmBOS = Arrays.asList(FilmBO.builder().title("PRV12 Film").created(2022)
                        .actors(Arrays.asList(ActorBO.builder().id(1L).name("PRV13 Doe").age(30).nation("USA").build(),
                                ActorBO.builder().id(2L).name("Jane Doe").age(25).nation("UK").build()))
                        .director(DirectorBO.builder().id(1L).name("PRV14 Doe").age(40).nation("USA").build()).build(),
                FilmBO.builder().title("PRV15 Film").created(2023)
                        .actors(Arrays.asList(ActorBO.builder().id(3L).name("PRV16 Doe").age(35).nation("USA").build(),
                                ActorBO.builder().id(4L).name("John Doe").age(30).nation("UK").build()))
                        .director(DirectorBO.builder().id(2L).name("PRV17 Doe").age(45).nation("USA").build()).build());

        List<FilmDTO> filmDTOS = filmDTOMapper.bosToDtos(filmBOS);

        assertNotNull(filmDTOS);
        assertEquals(2, filmDTOS.size());
        assertEquals(filmBOS.get(0).getTitle(), filmDTOS.get(0).getTitle());
        assertEquals(filmBOS.get(0).getCreated(), filmDTOS.get(0).getCreated());
        assertEquals(filmBOS.get(0).getActors().size(), filmDTOS.get(0).getIdActor().size());
        assertEquals(filmBOS.get(0).getDirector().getId(), filmDTOS.get(0).getIdDirector());
        assertEquals(filmBOS.get(1).getTitle(), filmDTOS.get(1).getTitle());
        assertEquals(filmBOS.get(1).getCreated(), filmDTOS.get(1).getCreated());
        assertEquals(filmBOS.get(1).getActors().size(), filmDTOS.get(1).getIdActor().size());
        assertEquals(filmBOS.get(1).getDirector().getId(), filmDTOS.get(1).getIdDirector());
    }
}

//package com.santander.peliculacrud.util.mapper;
//
//
//import com.santander.peliculacrud.model.bo.ActorBO;
//import com.santander.peliculacrud.model.bo.DirectorBO;
//import com.santander.peliculacrud.model.bo.SeriesBO;
//import com.santander.peliculacrud.model.entity.Actor;
//import com.santander.peliculacrud.model.entity.Director;
//import com.santander.peliculacrud.model.entity.Series;
//import com.santander.peliculacrud.util.mapper.bo.SeriesBOMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mapstruct.factory.Mappers;
//
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
///**
// * The type Series bo mapper test.
// */
//@ExtendWith(MockitoExtension.class)
//class SeriesBOMapperTest {
//
//    private final SeriesBOMapper seriesBOMapper = Mappers.getMapper(SeriesBOMapper.class);
//
//    /**
//     * Test bo to entity.
//     */
//    @Test
//    @DisplayName("Test mapper seriesBO to entity")
//    void testBoToEntity() {
//        SeriesBO seriesBO = SeriesBO.builder()
//                .title("Series 1")
//                .created(2020)
//                .actors(Arrays.asList(ActorBO.builder().name("Actor 1").build(), ActorBO.builder().name("Actor 2").build()))
//                .director(DirectorBO.builder().name("Director 1").build())
//                .build();
//
//        Series series = seriesBOMapper.boToEntity(seriesBO);
//
//        assertNotNull(series);
//        assertEquals("Series 1", series.getTitle());
//        assertEquals(2020, series.getCreated());
//        assertNotNull(series.getActors());
//        assertEquals(2, series.getActors().size());
//        assertEquals("Actor 1", series.getActors().get(0).getName());
//        assertEquals("Actor 2", series.getActors().get(1).getName());
//        assertNotNull(series.getDirector());
//        assertEquals("Director 1", series.getDirector().getName());
//    }
//
//    /**
//     * Test entity to bo.
//     */
//    @Test
//    @DisplayName("Test mapper entity to seriesBO")
//    void testEntityToBo() {
//        Series series = Series.builder()
//                .title("Series 1")
//                .created(2020)
//                .actors(Arrays.asList(Actor.builder().name("Actor 1").build(), Actor.builder().name("Actor 2").build()))
//                .director(Director.builder().name("Director 1").build())
//                .build();
//
//        SeriesBO seriesBO = seriesBOMapper.entityToBo(series);
//
//        assertNotNull(seriesBO);
//        assertEquals("Series 1", seriesBO.getTitle());
//        assertEquals(2020, seriesBO.getCreated());
//        assertNotNull(seriesBO.getActors());
//        assertEquals(2, seriesBO.getActors().size());
//        assertEquals("Actor 1", seriesBO.getActors().get(0).getName());
//        assertEquals("Actor 2", seriesBO.getActors().get(1).getName());
//        assertNotNull(seriesBO.getDirector());
//        assertEquals("Director 1", seriesBO.getDirector().getName());
//    }
//}

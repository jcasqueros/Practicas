//package com.santander.peliculacrud.util.mapper;
//
//import com.santander.peliculacrud.model.bo.DirectorBO;
//import com.santander.peliculacrud.model.entity.Director;
//import com.santander.peliculacrud.util.mapper.bo.DirectorBOMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mapstruct.factory.Mappers;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
///**
// * The type Director bo mapper test.
// */
//@ExtendWith(MockitoExtension.class)
//class DirectorBOMapperTest {
//
//    private DirectorBOMapper directorBOMapper = Mappers.getMapper(DirectorBOMapper.class);
//
//    /**
//     * Test bo to entity.
//     */
//    @Test
//    @DisplayName("Test mapper directorBo to entity ")
//    void testBoToEntity() {
//        DirectorBO directorBO = DirectorBO.builder().name("Director 1").age(30).build();
//
//        Director director = directorBOMapper.boToEntity(directorBO);
//
//        assertNotNull(director);
//        assertEquals("Director 1", director.getName());
//        assertEquals(30, director.getAge());
//    }
//
//    /**
//     * Test entity to bo.
//     */
//    @Test
//    @DisplayName("Test mapper entity to DirectorBO")
//    void testEntityToBo() {
//        Director director = Director.builder().name("Director 1").age(30).build();
//
//        DirectorBO directorBO = directorBOMapper.entityToBo(director);
//
//        assertNotNull(directorBO);
//        assertEquals("Director 1", directorBO.getName());
//        assertEquals(30, directorBO.getAge());
//    }
//
//    /**
//     * Test list entityto list bo.
//     */
//    @Test
//    @DisplayName("Test mapper list entity to list DirectorBO")
//    void testListEntitytoListBo() {
//        List<Director> directors = Arrays.asList(Director.builder().name("Director 1").age(30).build(),
//                Director.builder().name("Director 2").age(25).build());
//
//        List<DirectorBO> directorBOs = directorBOMapper.listEntitytoListBo(directors);
//
//        assertNotNull(directorBOs);
//        assertEquals(2, directorBOs.size());
//        assertEquals("Director 1", directorBOs.get(0).getName());
//        assertEquals(30, directorBOs.get(0).getAge());
//        assertEquals("Director 2", directorBOs.get(1).getName());
//        assertEquals(25, directorBOs.get(1).getAge());
//    }
//}

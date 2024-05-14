/*
package com.santander.peliculacrud.servicetest.directorservice;

import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import com.santander.peliculacrud.util.TransformObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

*/
/**
 * The type Director update service test.
 *//*

@SpringBootTest
public class DirectorUpdateServiceTest {

    @Autowired
    private DirectorServiceInterface directorService;
    @Autowired
    private TransformObjects transformObjects;

    private Director director;

    */
/**
     * Init.
     *//*

    @BeforeEach
    public void init() {
        try {
            DirectorBO directorBO = DirectorBO.builder().name("PRV23 Doe").age(18).nation("ESP").build();
            this.directorService.createDirector(directorBO);
            director = this.directorService.getLastDirector();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    */
/**
     * Test update director name.
     *//*

    @Test
    @DisplayName("Update with name valid data")
    public void testUpdateDirectorName() {
        int startSize = -1;
        DirectorBO updatedDirector = new DirectorBO();

        try {

            startSize = this.directorService.getListSize();

            director.setName("John Doe2");
            DirectorBO directorBO = transformObjects.directorToDirectorOut(director);
            this.directorService.updateDirector(director.getId(), directorBO);
            updatedDirector = this.directorService.getDirectorById(director.getId());

        } catch (Exception e) {
            e.printStackTrace();

        }

        assertEquals(updatedDirector.getName(), "John Doe2", "The name should be John Doe2");
        assertEquals(startSize, directorService.getListSize(), "There should be the same number of directors");

    }

    */
/**
     * Test update director age.
     *//*

    @Test
    @DisplayName("Update with age valid data")
    public void testUpdateDirectorAge() {
        int startSize = -1;
        DirectorBO updatedDirector = new DirectorBO();
        try {

            startSize = this.directorService.getListSize();

            director.setAge(99);
            DirectorBO directorBO = transformObjects.directorToDirectorOut(director);
            this.directorService.updateDirector(director.getId(), directorBO);
            updatedDirector = this.directorService.getDirectorById(director.getId());

        } catch (Exception e) {
            e.printStackTrace();

        }
        assertEquals(updatedDirector.getAge(), 99, "The age should be 99");
        assertEquals(startSize, directorService.getListSize(), "There should be the same number of directors");

    }

    */
/**
     * Test update director nation.
     *//*

    @Test
    @DisplayName("Update with nation valid data")
    public void testUpdateDirectorNation() {
        int startSize = -1;
        DirectorBO updatedDirector = new DirectorBO();
        try {

            startSize = this.directorService.getListSize();

            director.setNation("TEST");
            DirectorBO directorBO = transformObjects.directorToDirectorOut(director);
            this.directorService.updateDirector(director.getId(), directorBO);
            updatedDirector = this.directorService.getDirectorById(director.getId());

        } catch (Exception e) {
            e.printStackTrace();

        }

        assertEquals(startSize, directorService.getListSize(), "There should be the same number of directors");
        assertEquals(updatedDirector.getNation(), "TEST", "The nation should be TEST");

    }

}
*/

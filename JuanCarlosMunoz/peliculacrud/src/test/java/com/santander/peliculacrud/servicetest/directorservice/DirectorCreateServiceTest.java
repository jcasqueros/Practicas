package com.santander.peliculacrud.servicetest.directorservice;

import com.santander.peliculacrud.model.input.Director;
import com.santander.peliculacrud.model.output.DirectorModelService;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Director create service test.
 */
@SpringBootTest
public class DirectorCreateServiceTest {

    @Autowired
    private DirectorServiceInterface directorService;

    /**
     * Test create director valid data.
     */
    @Test
    @DisplayName("Create a new director with valid data")
    public void testCreateDirectorValidData() {
        int startSize = -1;
        try {
            startSize = this.directorService.getListSize();

            DirectorModelService directorModelService = DirectorModelService.builder().name("John Doe").age(18).nation("ESP").build();
            this.directorService.createDirector(directorModelService);

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(startSize + 1, directorService.getListSize(), "There should be only one director");
        Director director = directorService.getLastDirector();
        assertEquals(director.getName(), "John Doe");
        assertEquals(director.getAge(), 18);
        assertEquals(director.getNation(), "ESP");

    }

    /**
     * Test create director invalid data age.
     */
    @Test
    @DisplayName("Create a new director with invalid data (age < 18)")
    public void testCreateDirectorInvalidDataAge() {
        DirectorModelService directorModelService = DirectorModelService.builder().name("John Doe").age(17).nation("ESP").build();
        int startSize = -1;

        try {

            startSize = this.directorService.getListSize();
            this.directorService.createDirector(directorModelService);

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(startSize, this.directorService.getListSize(), "There should be error for invalid data (age < 18) ");

    }

    /**
     * Test create director invalid data name.
     */
    @Test
    @DisplayName("Create a new director with invalid data (name is null)")
    public void testCreateDirectorInvalidDataName() {
        DirectorModelService directorModelService = DirectorModelService.builder().age(18).nation("ESP").build();
        int startSize = -1;
        try {
            startSize = this.directorService.getListSize();

            this.directorService.createDirector(directorModelService);

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(startSize, this.directorService.getListSize(), "There should be error for invalid data name");

    }

    /**
     * Test create director invalid data nation.
     */
    @Test
    @DisplayName("Create a new director with invalid data (nation is null)")
    public void testCreateDirectorInvalidDataNation() {
        DirectorModelService directorModelService = DirectorModelService.builder().name("John Doe").age(18).build();
        int startSize = -1;
        try {
            startSize = this.directorService.getListSize();

            this.directorService.createDirector(directorModelService);

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(startSize, this.directorService.getListSize(), "There should be error for invalid data nation");

    }
}

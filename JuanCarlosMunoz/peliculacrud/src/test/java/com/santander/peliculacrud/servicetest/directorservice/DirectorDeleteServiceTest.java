package com.santander.peliculacrud.servicetest.directorservice;

import com.santander.peliculacrud.model.input.Director;
import com.santander.peliculacrud.model.output.DirectorModelService;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.fail;

/**
 * The type Director delete service test.
 */
@SpringBootTest
public class DirectorDeleteServiceTest {

    @Autowired
    private DirectorServiceInterface directorService;

    /**
     * Test delete director with valid id.
     */
    @Test
    @DisplayName("Delete a director ")
    public void testDeleteDirectorWithValidId() {
        Director director = new Director();
        int startSize = -1;
        try {
            startSize  = this.directorService.getListSize();

            DirectorModelService directorModelService = DirectorModelService.builder().name("John Doe").age(18).nation("ESP").build();
            this.directorService.createDirector(directorModelService);

            director = this.directorService.getLastDirector();

            if (director != null) {
                assertEquals(startSize + 1 , this.directorService.getListSize(), "There should be same size");
                this.directorService.deleteDirector(director.getId());

            } else {
                fail("Director should not be null");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        assertNull(directorService.getDirectorById(director.getId()), "Director should be null after deletion");
        assertEquals(startSize , directorService.getListSize(), "There should be same size");

    }

    /**
     * Test delete director with invalid id.
     */
    @Test
    @DisplayName("Delete a director with invalid id")
    public void testDeleteDirectorWithInvalidId() {
        int  startSize  = directorService.getListSize();
        Exception exception = assertThrows(Exception.class, () -> directorService.deleteDirector((long) -1));
        assertEquals("Director not found", exception.getMessage());
        assertEquals(startSize , directorService.getListSize(), "There should be same size");


    }

}

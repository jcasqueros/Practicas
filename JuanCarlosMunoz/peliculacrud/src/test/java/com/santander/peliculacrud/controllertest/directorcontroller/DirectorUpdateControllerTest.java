package com.santander.peliculacrud.controllertest.directorcontroller;

import com.santander.peliculacrud.model.input.Director;
import com.santander.peliculacrud.model.output.DirectorModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Director update controller test.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class DirectorUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DirectorServiceInterface directorService;

    private long directorId;

    @BeforeEach
    public void start() {
        Director director = directorService.getLastDirector();
        directorId = director.getId();
    }

    /**
     * Test update director existing director ok.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Update director Ok")
    public void testUpdateDirector_ExistingDirector_OK() throws Exception {

        DirectorModelService directorModelService = DirectorModelService.builder().name("Updated Name").age(25).nation("USA").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorModelService);

        ResultActions response = mockMvc.perform(
                put("/directors/{id}", directorId).contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isOk());
    }

    /**
     * Test update director non existing director not found.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Update director non existing")
    public void testUpdateDirector_NonExistingDirector_NotFound() throws Exception {
        try {
            long directorId = -1;
            DirectorModelService directorModelService = DirectorModelService.builder().name("Updated Name").age(25).nation("USA").build();

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String directorOutJson = ow.writeValueAsString(directorModelService);

            ResultActions response = mockMvc.perform(
                    put("/directors/{id}", directorId).contentType(MediaType.APPLICATION_JSON).content(directorOutJson));
            Assertions.fail("Expected RuntimeException to be thrown");

        }catch (Exception e){
            assertTrue(e.getMessage().contains("Director not found"));

        }
    }

    /**
     * Test update director invalid json bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Update director with invalid Json")
    public void testUpdateDirector_InvalidJson_BadRequest() throws Exception {

        String invalidJson = "{\"name\":\"Invalid Json\"";

        ResultActions response = mockMvc.perform(
                put("/directors/{id}", directorId).contentType(MediaType.APPLICATION_JSON).content(invalidJson));

        response.andDo(print()).andExpect(status().isBadRequest());
    }
}
/***************
 *
 *
 *
 *
 *
 * FALTA CREAR LOS TEST PARA EL CONTROLADOR D E SERIES Y FILM
 *
 *
*
 * *********/

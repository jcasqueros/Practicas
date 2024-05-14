/*
package com.santander.peliculacrud.controllertest.directorcontroller;

import com.santander.peliculacrud.service.DirectorServiceInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

*/
/**
 * The type Director delete controller test.
 *//*

@SpringBootTest
@AutoConfigureMockMvc
public class DirectorDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DirectorServiceInterface directorService;

    */
/**
     * Test delete director existing director no content.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    public void testDeleteDirector_ExistingDirector_NoContent() throws Exception {
        Long directorId = directorService.getLastDirector().getId();

        ResultActions response = mockMvc.perform(
                delete("/directors/{id}", directorId).contentType(MediaType.APPLICATION_JSON));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    */
/**
     * Test delete director non existing director not found.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    public void testDeleteDirector_NonExistingDirector_NotFound() throws Exception {
        Long directorId = 999L;
        try {
            mockMvc.perform(delete("/directors/{id}", directorId).contentType(MediaType.APPLICATION_JSON));
            Assertions.fail("Expected RuntimeException to be thrown");

        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Director not found"));

        }
    }

    */
/**
     * Test delete director invalid id bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    public void testDeleteDirector_InvalidId_BadRequest() throws Exception {
        String invalidId = "invalid-id";

        ResultActions response = mockMvc.perform(
                delete("/directors/{id}", invalidId).contentType(MediaType.APPLICATION_JSON));

        response.andDo(print()).andExpect(status().isBadRequest());
    }
}
*/

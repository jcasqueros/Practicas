/*
package com.santander.peliculacrud.controllertest.actorcontroller;

import com.santander.peliculacrud.service.ActorServiceInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.apache.commons.lang3.exception.ExceptionUtils.throwableOfType;
import static org.assertj.core.api.InstanceOfAssertFactories.throwable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

*/
/**
 * The type Actor delete controller test.
 *//*

@SpringBootTest
@AutoConfigureMockMvc
public class ActorDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActorServiceInterface actorService;

    */
/**
     * Test delete actor existing actor no content.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    public void testDeleteActor_ExistingActor_NoContent() throws Exception {
        Long actorId = actorService.getLastActor().getId();

        ResultActions response = mockMvc.perform(
                delete("/actors/{id}", actorId).contentType(MediaType.APPLICATION_JSON));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    */
/**
     * Test delete actor non existing actor not found.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    public void testDeleteActor_NonExistingActor_NotFound() throws Exception {
        Long actorId = 999L;
        try {
            mockMvc.perform(delete("/actors/{id}", actorId).contentType(MediaType.APPLICATION_JSON));
            Assertions.fail("Expected RuntimeException to be thrown");

        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Actor not found"));

        }
    }

    */
/**
     * Test delete actor invalid id bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    public void testDeleteActor_InvalidId_BadRequest() throws Exception {
        String invalidId = "invalid-id";

        ResultActions response = mockMvc.perform(
                delete("/actors/{id}", invalidId).contentType(MediaType.APPLICATION_JSON));

        response.andDo(print()).andExpect(status().isBadRequest());
    }
}
*/

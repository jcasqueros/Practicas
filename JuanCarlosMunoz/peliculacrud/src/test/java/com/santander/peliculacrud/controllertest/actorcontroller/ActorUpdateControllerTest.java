/*
package com.santander.peliculacrud.controllertest.actorcontroller;

import com.santander.peliculacrud.model.dto.ActorDTO;
import com.santander.peliculacrud.model.entity.Actor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.santander.peliculacrud.service.ActorServiceInterface;
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

*/
/**
 * The type Actor update controller test.
 *//*

@SpringBootTest
@AutoConfigureMockMvc
public class ActorUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ActorServiceInterface actorService;

    private long actorId;

    @BeforeEach
    public void start() {
        Actor actor = actorService.getLastActor();
        actorId = actor.getId();
    }

    */
/**
     * Test update actor existing actor ok.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Update actor Ok")
    public void testUpdateActor_ExistingActor_OK() throws Exception {

        ActorDTO actorDTO = ActorDTO.builder().name("Updated Name").age(25).nation("USA").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String actorOutJson = ow.writeValueAsString(actorDTO);

        ResultActions response = mockMvc.perform(
                put("/actors/{id}", actorId).contentType(MediaType.APPLICATION_JSON).content(actorOutJson));

        response.andDo(print()).andExpect(status().isOk());
    }

    */
/**
     * Test update actor non existing actor not found.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Update actor non existing")
    public void testUpdateActor_NonExistingActor_NotFound() throws Exception {
        try {
            long actorId = -1;
            ActorDTO actorDTO = ActorDTO.builder().name("Updated Name").age(25).nation("USA").build();

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String actorOutJson = ow.writeValueAsString(actorDTO);

            ResultActions response = mockMvc.perform(
                    put("/actors/{id}", actorId).contentType(MediaType.APPLICATION_JSON).content(actorOutJson));
            Assertions.fail("Expected RuntimeException to be thrown");

        }catch (Exception e){
            assertTrue(e.getMessage().contains("Actor not found"));

        }
    }

    */
/**
     * Test update actor invalid json bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Update actor with invalid Json")
    public void testUpdateActor_InvalidJson_BadRequest() throws Exception {

        String invalidJson = "{\"name\":\"Invalid Json\"";

        ResultActions response = mockMvc.perform(
                put("/actors/{id}", actorId).contentType(MediaType.APPLICATION_JSON).content(invalidJson));

        response.andDo(print()).andExpect(status().isBadRequest());
    }
}
*/

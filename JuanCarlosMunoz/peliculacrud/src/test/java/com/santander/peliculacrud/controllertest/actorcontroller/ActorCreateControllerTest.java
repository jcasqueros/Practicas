package com.santander.peliculacrud.controllertest.actorcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.santander.peliculacrud.model.output.ActorModelController;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Actor create controller test.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ActorCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;


    /**
     * Test create actor.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create a new actor with valid data")
    public void testCreateActor() throws Exception {

        ActorModelController actorModelController = ActorModelController.builder().name("PRV11 Doe").age(18).nation("ESP").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String actorOutJson = ow.writeValueAsString(actorModelController);

        ResultActions response = mockMvc.perform(
                post("/actors").contentType(MediaType.APPLICATION_JSON).content(actorOutJson));

        response.andDo(print()).andExpect(status().isCreated());

    }

    /**
     * Test create actor no name bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create actor without name")
    public void testCreateActor_NoName_BadRequest() throws Exception {

        ActorModelController actorModelControllerNoName = ActorModelController.builder().age(18).nation("ESP").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String actorOutJson = ow.writeValueAsString(actorModelControllerNoName);

        ResultActions response = mockMvc.perform(
                post("/actors").contentType(MediaType.APPLICATION_JSON).content(actorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create actor null actor bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create actor with null actor")
    public void testCreateActor_NullActor_BadRequest() throws Exception {

        ActorModelController actorModelControllerNull = null;

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String actorOutJson = ow.writeValueAsString(actorModelControllerNull);

        ResultActions response = mockMvc.perform(
                post("/actors").contentType(MediaType.APPLICATION_JSON).content(actorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create actor invalid json bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create actor with invalid json")
    public void testCreateActor_InvalidJson_BadRequest() throws Exception {

        String invalidJson = "{\"name\":\"Invalid Json\"";

        ResultActions response = mockMvc.perform(
                post("/actors").contentType(MediaType.APPLICATION_JSON).content(invalidJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create actor whitespace name bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create actor with space name")
    public void testCreateActor_WhitespaceName_BadRequest() throws Exception {

        ActorModelController actorModelControllerWhitespaceName = ActorModelController.builder().name("   ").age(18).nation("ESP").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String actorOutJson = ow.writeValueAsString(actorModelControllerWhitespaceName);

        ResultActions response = mockMvc.perform(
                post("/actors").contentType(MediaType.APPLICATION_JSON).content(actorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create actor age less than 18 bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create actor with age less than 18")
    public void testCreateActor_AgeLessThan18_BadRequest() throws Exception {

        ActorModelController actorModelControllerAgeLessThan18 = ActorModelController.builder().name("PRV11 Doe").age(17).nation("ESP").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String actorOutJson = ow.writeValueAsString(actorModelControllerAgeLessThan18);

        ResultActions response = mockMvc.perform(
                post("/actors").contentType(MediaType.APPLICATION_JSON).content(actorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create actor empty nation bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create actor with empty nation")
    public void testCreateActor_EmptyNation_BadRequest() throws Exception {

        ActorModelController actorModelControllerEmptyNation = ActorModelController.builder().name("PRV11 Doe").age(18).nation("").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String actorOutJson = ow.writeValueAsString(actorModelControllerEmptyNation);

        ResultActions response = mockMvc.perform(
                post("/actors").contentType(MediaType.APPLICATION_JSON).content(actorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create actor whitespace nation bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create actor withspace nation")
    public void testCreateActor_WhitespaceNation_BadRequest() throws Exception {

        ActorModelController actorModelControllerWhitespaceNation = ActorModelController.builder().name("PRV11 Doe").age(18).nation("   ").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String actorOutJson = ow.writeValueAsString(actorModelControllerWhitespaceNation);

        ResultActions response = mockMvc.perform(
                post("/actors").contentType(MediaType.APPLICATION_JSON).content(actorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

}

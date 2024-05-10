package com.santander.peliculacrud.controllertest.directorcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.santander.peliculacrud.model.output.DirectorModelService;

import org.junit.jupiter.api.BeforeAll;
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
 * The type Director create controller test.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class DirectorCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Init.
     */
    @BeforeAll
    public static void init() {

    }

    /**
     * Test create director.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create a new director with valid data")
    public void testCreateDirector() throws Exception {

        DirectorModelService directorModelService = DirectorModelService.builder().name("PRV11 Doe").age(18).nation("ESP").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorModelService);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isCreated());

    }

    /**
     * Test create director no name bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create director without name")
    public void testCreateDirector_NoName_BadRequest() throws Exception {

        DirectorModelService directorModelServiceNoName = DirectorModelService.builder().age(18).nation("ESP").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorModelServiceNoName);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create director null director bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create director with null director")
    public void testCreateDirector_NullDirector_BadRequest() throws Exception {

        DirectorModelService directorModelServiceNull = null;

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorModelServiceNull);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create director invalid json bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create director with invalid json")
    public void testCreateDirector_InvalidJson_BadRequest() throws Exception {

        String invalidJson = "{\"name\":\"Invalid Json\"";

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(invalidJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create director whitespace name bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create director with space name")
    public void testCreateDirector_WhitespaceName_BadRequest() throws Exception {

        DirectorModelService directorModelServiceWhitespaceName = DirectorModelService.builder().name("   ").age(18).nation("ESP").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorModelServiceWhitespaceName);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create director age less than 18 bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create director with age less than 18")
    public void testCreateDirector_AgeLessThan18_BadRequest() throws Exception {

        DirectorModelService directorModelServiceAgeLessThan18 = DirectorModelService.builder().name("PRV11 Doe").age(17).nation("ESP").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorModelServiceAgeLessThan18);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create director empty nation bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create director with empty nation")
    public void testCreateDirector_EmptyNation_BadRequest() throws Exception {

        DirectorModelService directorModelServiceEmptyNation = DirectorModelService.builder().name("PRV11 Doe").age(18).nation("").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorModelServiceEmptyNation);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create director whitespace nation bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create director withspace nation")
    public void testCreateDirector_WhitespaceNation_BadRequest() throws Exception {

        DirectorModelService directorModelServiceWhitespaceNation = DirectorModelService.builder().name("PRV11 Doe").age(18).nation("   ").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorModelServiceWhitespaceNation);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

}

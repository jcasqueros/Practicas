/*
package com.santander.peliculacrud.controllertest.directorcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.santander.peliculacrud.model.bo.DirectorBO;

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

*/
/**
 * The type Director create controller test.
 *//*

@SpringBootTest
@AutoConfigureMockMvc
public class DirectorCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    */
/**
     * Init.
     *//*

    @BeforeAll
    public static void init() {

    }

    */
/**
     * Test create director.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create a new director with valid data")
    public void testCreateDirector() throws Exception {

        DirectorBO directorBO = DirectorBO.builder().name("PRV11 Doe").age(18).nation("ESP").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorBO);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isCreated());

    }

    */
/**
     * Test create director no name bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create director without name")
    public void testCreateDirector_NoName_BadRequest() throws Exception {

        DirectorBO directorBONoName = DirectorBO.builder().age(18).nation("ESP").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorBONoName);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    */
/**
     * Test create director null director bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create director with null director")
    public void testCreateDirector_NullDirector_BadRequest() throws Exception {

        DirectorBO directorBONull = null;

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorBONull);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    */
/**
     * Test create director invalid json bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create director with invalid json")
    public void testCreateDirector_InvalidJson_BadRequest() throws Exception {

        String invalidJson = "{\"name\":\"Invalid Json\"";

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(invalidJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    */
/**
     * Test create director whitespace name bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create director with space name")
    public void testCreateDirector_WhitespaceName_BadRequest() throws Exception {

        DirectorBO directorBOWhitespaceName = DirectorBO.builder().name("   ").age(18).nation("ESP").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorBOWhitespaceName);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    */
/**
     * Test create director age less than 18 bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create director with age less than 18")
    public void testCreateDirector_AgeLessThan18_BadRequest() throws Exception {

        DirectorBO directorBOAgeLessThan18 = DirectorBO.builder().name("PRV11 Doe").age(17).nation("ESP").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorBOAgeLessThan18);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    */
/**
     * Test create director empty nation bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create director with empty nation")
    public void testCreateDirector_EmptyNation_BadRequest() throws Exception {

        DirectorBO directorBOEmptyNation = DirectorBO.builder().name("PRV11 Doe").age(18).nation("").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorBOEmptyNation);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    */
/**
     * Test create director whitespace nation bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create director withspace nation")
    public void testCreateDirector_WhitespaceNation_BadRequest() throws Exception {

        DirectorBO directorBOWhitespaceNation = DirectorBO.builder().name("PRV11 Doe").age(18).nation("   ").build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String directorOutJson = ow.writeValueAsString(directorBOWhitespaceNation);

        ResultActions response = mockMvc.perform(
                post("/directors").contentType(MediaType.APPLICATION_JSON).content(directorOutJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

}
*/

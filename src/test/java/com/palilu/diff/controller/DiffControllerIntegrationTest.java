package com.palilu.diff.controller;

import com.palilu.diff.DiffApplication;
import com.palilu.diff.model.DiffMessage;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author pmendoza
 * @since 2019-09-07
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {DiffApplication.class})
public class DiffControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testEquals() throws Exception {
        String leftRequest = getResource("/putLeftSideRequestBody.json");
        // Let's put our left file in the left
        mockMvc.perform(put("/v1/diff/42/left")
                .contentType(MediaType.APPLICATION_JSON)
                .content(leftRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        // And also in the right
        mockMvc.perform(put("/v1/diff/42/right")
                .contentType(MediaType.APPLICATION_JSON)
                .content(leftRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        // We should get the equals message
        mockMvc.perform(get("/v1/diff/42")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(DiffMessage.EQUALS.getMessage()));
    }

    @Test
    public void testDifferentSize() throws Exception {
        String leftRequest = getResource("/putLeftSideRequestBody.json");
        String differentSize = getResource("/putDifferentSizeRequestBody.json");
        // Let's put our left file in the left
        mockMvc.perform(put("/v1/diff/42/left")
                .contentType(MediaType.APPLICATION_JSON)
                .content(leftRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        // And a different size one on the right
        mockMvc.perform(put("/v1/diff/42/right")
                .contentType(MediaType.APPLICATION_JSON)
                .content(differentSize)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        // We should get the different size message
        mockMvc.perform(get("/v1/diff/42")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(DiffMessage.DIFFERENT_SIZE.getMessage()));
    }

    @Test
    public void testOffsets() throws Exception {
        String leftRequest = getResource("/putLeftSideRequestBody.json");
        String rightRequest = getResource("/putRightSideRequestBody.json");
        // Let's put our left file in the left
        mockMvc.perform(put("/v1/diff/42/left")
                .contentType(MediaType.APPLICATION_JSON)
                .content(leftRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        // And our right file on the right
        mockMvc.perform(put("/v1/diff/42/right")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rightRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        // We should get the offsets message and the offsets summary
        mockMvc.perform(get("/v1/diff/42")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(DiffMessage.OFFSETS.getMessage()))
                .andExpect(jsonPath("$.offsets").isArray())
                .andExpect(jsonPath("$.offsets", hasSize(14)))
                .andExpect(jsonPath("$.offsets[0].position", is(59)))
                .andExpect(jsonPath("$.offsets[0].size", is(10)))
                .andExpect(jsonPath("$.offsets[0].leftChunk", is("NdWxhbiBTe")))
                .andExpect(jsonPath("$.offsets[0].rightChunk", is("wb2ZmZXJ0a")));
    }

    @Test
    public void testGetForNoDiff() throws Exception {
        mockMvc.perform(get("/v1/diff/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPutBadRequest() throws Exception {
        String invalidRequest = getResource("/putInvalidRequestBody.json");
        // Let's put an invalid file in the left
        mockMvc.perform(put("/v1/diff/42/left")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        // And an invalid file on the right
        mockMvc.perform(put("/v1/diff/42/right")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String getResource(String name) throws IOException {
        return IOUtils.toString(DiffControllerIntegrationTest.class.getResourceAsStream(name));
    }

}

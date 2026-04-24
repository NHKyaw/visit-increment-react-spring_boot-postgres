package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/** Unit tests for VisitorController. */
@WebMvcTest(VisitorController.class)
class VisitorControllerTest {

    /** MockMvc for simulating HTTP requests. */
    @Autowired
    private MockMvc mockMvc;

    /** Mocked visitor repository. */
    @MockitoBean
    private VisitorRepo repo;

    /** Tests that POST /api/visit records a visit and returns confirmation. */
    @Test
    void testAddVisit() throws Exception {
        when(repo.save(any(Visitor.class))).thenReturn(new Visitor("2024-01-01"));
        mockMvc.perform(post("/api/visit"))
                .andExpect(status().isOk())
                .andExpect(content().string("Visit Recorded!"));
    }

    /** Tests that GET /api/visit/count returns the visit count. */
    @Test
    void testGetCount() throws Exception {
        when(repo.count()).thenReturn(42L);
        mockMvc.perform(get("/api/visit/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(42));
    }

    /** Tests that GET /api/visit/count returns zero when no visits exist. */
    @Test
    void testGetCountZero() throws Exception {
        when(repo.count()).thenReturn(0L);
        mockMvc.perform(get("/api/visit/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0));
    }
}

package net.lashin.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    private MockMvc mockMvc;

    @Before
    public void init() {
        IndexController indexController = new IndexController();
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    public void indexTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andDo(print());
    }

    @Test
    public void indexHeadTest() throws Exception {
        mockMvc.perform(head("/"))
                .andDo(print());
    }
}

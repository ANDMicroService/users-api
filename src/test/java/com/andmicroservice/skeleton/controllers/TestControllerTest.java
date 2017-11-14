package com.andmicroservice.skeleton.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestController.class)
public class TestControllerTest {

    private MockMvc mockMvc;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
        TestController testController = new TestController();
        this.mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
    }

    @Test
    public void testApi() throws Exception {
        this.mockMvc
                .perform(get("/test").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("RestController is up and running!")));
    }
}

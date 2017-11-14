package com.andmicroservice.users.resource;


import com.andmicroservice.users.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {UserResource.class, UserServiceImpl.class})
public class UserResourceIntTest {

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        UserResource userResource = new UserResource();
        this.mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }

    @Ignore
    @Test
    public void testApi() throws Exception {
        this.mockMvc
                .perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("[]"));
    }
}

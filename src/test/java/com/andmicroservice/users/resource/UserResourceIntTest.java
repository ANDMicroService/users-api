package com.andmicroservice.users.resource;


import com.andmicroservice.users.representation.UserDTO;
import com.andmicroservice.users.service.UserServiceImpl;
import com.andmicroservice.users.util.UserDTOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceIntTest {

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final HttpHeaders headers = new HttpHeaders();

    @LocalServerPort
    private int port;

    private String url;

    @Before
    public void setup() {
        url = "http://localhost:" + port;
        headers.setContentType(APPLICATION_JSON_UTF8);
    }

    @Test
    public void whenPostUser_withValidUser_thenReturnsUser() {
        UserDTO expectedUserDTO1 = UserDTOUtils.aTestUser();

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                createURI("/users"),
                HttpMethod.POST,
                new HttpEntity<>(expectedUserDTO1, headers),
                UserDTO.class);

        assertThat(response.getStatusCode(), equalTo(CREATED));
        assertThat(response.getBody().getId(), equalTo(expectedUserDTO1.getId()));
    }

    private String createURI(String uri) {
        return url + uri;
    }
}

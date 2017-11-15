package com.andmicroservice.users.resource;


import com.andmicroservice.users.representation.UserDTO;
import com.andmicroservice.users.util.UserDTOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
        UserDTO expectedUserDTO1 = UserDTOUtils.aTestUserBuilder().withId(null).build();

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                createURI("/users"),
                HttpMethod.POST,
                new HttpEntity<>(expectedUserDTO1, headers),
                UserDTO.class);

        assertThat(response.getStatusCode(), equalTo(CREATED));
        assertThat(response.getBody().getId(), notNullValue());
    }

    @Test
    public void whenGetUser_withNoUsers_thenReturnsEmptyList() {
        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
                createURI("/users"),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<List<UserDTO>>() {});

        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), equalTo(new ArrayList<UserDTO>()));
    }

    @Test
    public void whenGetUser_withAUserAvailable_thenReturnsThatUser() {
        UserDTO expectedUserDTO1 = UserDTOUtils.aTestUserBuilder().withId(null).build();

        ResponseEntity<UserDTO> postResponse = restTemplate.exchange(
                createURI("/users"),
                HttpMethod.POST,
                new HttpEntity<>(expectedUserDTO1, headers),
                UserDTO.class);

        UserDTO createdUser = postResponse.getBody();

        ResponseEntity<List<UserDTO>> getResponse = restTemplate.exchange(
                createURI("/users"),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<List<UserDTO>>() {});

        assertThat(getResponse.getStatusCode(), equalTo(OK));
        assertThat(getResponse.getBody().size(), equalTo(1));
        assertThat(getResponse.getBody().get(0).getId(), equalTo(createdUser.getId()));
    }

    @Test
    public void whenDeleteUser_withAUserAvailable_thenDeletesThatUser() {
        UserDTO expectedUserDTO1 = UserDTOUtils.aTestUserBuilder().withId(null).build();

        ResponseEntity<UserDTO> postResponse = restTemplate.exchange(
                createURI("/users"),
                HttpMethod.POST,
                new HttpEntity<>(expectedUserDTO1, headers),
                UserDTO.class);

        UserDTO createdUser = postResponse.getBody();

        String createdUserLogin = createdUser.getLogin();

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                createURI("/users/" + createdUserLogin),
                HttpMethod.DELETE,
                new HttpEntity<>(null, headers),
                Void.class);

        assertThat(deleteResponse.getStatusCode(), equalTo(OK));

        ResponseEntity<List<UserDTO>> getResponse = restTemplate.exchange(
                createURI("/users"),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<List<UserDTO>>() {});

        assertThat(getResponse.getStatusCode(), equalTo(OK));
        assertThat(getResponse.getBody(), equalTo(new ArrayList<UserDTO>()));
    }

    @Test
    public void whenDeleteUser_withNoUserAvailable_returnsOkHeader() {
        String login = "idontexist";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                createURI("/users/" + login),
                HttpMethod.DELETE,
                new HttpEntity<>(null, headers),
                Void.class);

        assertThat(deleteResponse.getStatusCode(), equalTo(OK));
    }

    private String createURI(String uri) {
        return url + uri;
    }
}

package com.andmicroservice.users.resource;


import com.andmicroservice.users.exception.EmailExistsException;
import com.andmicroservice.users.exception.IdProvidedException;
import com.andmicroservice.users.exception.LoginExistsException;
import com.andmicroservice.users.representation.UserDTO;
import com.andmicroservice.users.service.UserService;
import com.andmicroservice.users.util.TestUtil;
import com.andmicroservice.users.util.UserDTOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserResource userResource;

    @Captor
    private ArgumentCaptor<UserDTO> userDTOCaptor;

    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Before
    public void setup() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }

    @Test
    public void testGetUsers_whenNoUsers_returnsEmptyList() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
        verify(userService).getAllUsers();
    }

    @Test
    public void testGetUsers_whenSingleUser_returnsSingleUser() throws Exception {
        UserDTO userDTO = UserDTOUtils.aTestUser();
        List<UserDTO> userDTOS = Collections.singletonList(userDTO);
        when(userService.getAllUsers()).thenReturn(userDTOS);

        mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[0].id").value(userDTO.getId()))
                .andExpect(jsonPath("$.[0].login").value(userDTO.getLogin()))
                .andExpect(jsonPath("$.[0].firstName").value(userDTO.getFirstName()))
                .andExpect(jsonPath("$.[0].lastName").value(userDTO.getLastName()))
                .andExpect(jsonPath("$.[0].email").value(userDTO.getEmail()));
        verify(userService).getAllUsers();
    }

    @Test
    public void testGetUsers_whenMultipleUsers_returnsAllUsers() throws Exception {
        UserDTO expectedUserDTO1 = UserDTOUtils.aTestUser();
        UserDTO expectedUserDTO2 = UserDTOUtils.aSecondTestUser();
        when(userService.getAllUsers()).thenReturn(Arrays.asList(expectedUserDTO1, expectedUserDTO2));

        mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[0].id").value(expectedUserDTO1.getId()))
                .andExpect(jsonPath("$.[0].login").value(expectedUserDTO1.getLogin()))
                .andExpect(jsonPath("$.[0].firstName").value(expectedUserDTO1.getFirstName()))
                .andExpect(jsonPath("$.[0].lastName").value(expectedUserDTO1.getLastName()))
                .andExpect(jsonPath("$.[0].email").value(expectedUserDTO1.getEmail()))
                .andExpect(jsonPath("$.[1].id").value(expectedUserDTO2.getId()))
                .andExpect(jsonPath("$.[1].login").value(expectedUserDTO2.getLogin()))
                .andExpect(jsonPath("$.[1].firstName").value(expectedUserDTO2.getFirstName()))
                .andExpect(jsonPath("$.[1].lastName").value(expectedUserDTO2.getLastName()))
                .andExpect(jsonPath("$.[1].email").value(expectedUserDTO2.getEmail()));
        verify(userService).getAllUsers();
    }

    @Test
    public void testCreateUser_whenIdProvided_return400() throws Exception {
        UserDTO requestUserDTO = UserDTOUtils.aTestUser();
        when(userService.createUser(Mockito.any(UserDTO.class))).thenThrow(new IdProvidedException());

        mockMvc.perform(post("/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(requestUserDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("X-monolithApp-error", "ID must not be provided when creating new entities"));
        verify(userService).createUser(userDTOCaptor.capture());
        validateUserServiceCreateUserCalledWithRequestUser(userDTOCaptor.getValue(), requestUserDTO);
    }

    @Test
    public void testCreateUser_whenLoginExists_return400() throws Exception {
        UserDTO requestUserDTO = UserDTOUtils.aTestUserBuilder().withId(null).build();
        when(userService.createUser(Mockito.any(UserDTO.class))).thenThrow(new LoginExistsException());

        mockMvc.perform(post("/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(requestUserDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("X-monolithApp-error", "Login already exists"));
        verify(userService).createUser(userDTOCaptor.capture());
        validateUserServiceCreateUserCalledWithRequestUser(userDTOCaptor.getValue(), requestUserDTO);
    }

    @Test
    public void testCreateUser_whenEmailExists_return400() throws Exception {
        UserDTO requestUserDTO = UserDTOUtils.aTestUserBuilder().withId(null).build();
        when(userService.createUser(Mockito.any(UserDTO.class))).thenThrow(new EmailExistsException());

        mockMvc.perform(post("/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(requestUserDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("X-monolithApp-error", "Email already exists"));
        verify(userService).createUser(userDTOCaptor.capture());
        validateUserServiceCreateUserCalledWithRequestUser(userDTOCaptor.getValue(), requestUserDTO);
    }

    @Test
    public void testCreateUser_whenValidRequest_returnCreatedUser() throws Exception {
        UserDTO requestUserDTO = UserDTOUtils.aTestUserBuilder().withId(null).build();
        UserDTO expectedUserDTO = UserDTOUtils.aTestUser();
        when(userService.createUser(Mockito.any(UserDTO.class))).thenReturn(expectedUserDTO);

        mockMvc.perform(post("/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(requestUserDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(expectedUserDTO.getId()))
                .andExpect(jsonPath("$.login").value(expectedUserDTO.getLogin()))
                .andExpect(jsonPath("$.firstName").value(expectedUserDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expectedUserDTO.getLastName()))
                .andExpect(jsonPath("$.email").value(expectedUserDTO.getEmail()));
        verify(userService).createUser(userDTOCaptor.capture());
        validateUserServiceCreateUserCalledWithRequestUser(userDTOCaptor.getValue(), requestUserDTO);
    }

    private void validateUserServiceCreateUserCalledWithRequestUser(UserDTO userDTO, UserDTO requestUserDTO) {
        assertThat(userDTO.getId(), is(requestUserDTO.getId()));
        assertThat(userDTO.getLogin(), is(requestUserDTO.getLogin()));
        assertThat(userDTO.getPassword(), is(requestUserDTO.getPassword()));
        assertThat(userDTO.getEmail(), is(requestUserDTO.getEmail()));
        assertThat(userDTO.getFirstName(), is(requestUserDTO.getFirstName()));
        assertThat(userDTO.getLastName(), is(requestUserDTO.getLastName()));
    }
}

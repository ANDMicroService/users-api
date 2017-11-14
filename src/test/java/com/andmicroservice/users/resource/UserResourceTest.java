package com.andmicroservice.users.resource;


import com.andmicroservice.users.representation.UserDTO;
import com.andmicroservice.users.service.UserService;
import com.andmicroservice.users.util.UserDTOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserResource userResource;

    @Test
    public void testGetUsers_whenNoUsers_returnsEmptyList() {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        List<UserDTO> result = userResource.getUsers();

        assertThat(result, is(empty()));
        verify(userService).getAllUsers();
    }

    @Test
    public void testGetUsers_whenSingleUser_returnsSingleUser() {
        UserDTO userDTO1 = UserDTOUtils.aTestUser();
        List<UserDTO> userDTOS = Collections.singletonList(userDTO1);
        when(userService.getAllUsers()).thenReturn(userDTOS);

        List<UserDTO> result = userResource.getUsers();

        assertThat(result, is(userDTOS));
        verify(userService).getAllUsers();
    }

    @Test
    public void testGetUsers_whenMultipleUsers_returnsAllUsers() {
        UserDTO expectedUserDTO1 = UserDTOUtils.aTestUser();
        UserDTO expectedUserDTO2 = UserDTOUtils.aSecondTestUser();
        when(userService.getAllUsers()).thenReturn(Arrays.asList(expectedUserDTO1, expectedUserDTO2));

        List<UserDTO> result = userResource.getUsers();

        assertThat(result, containsInAnyOrder(expectedUserDTO1, expectedUserDTO2));
        verify(userService).getAllUsers();
    }
}

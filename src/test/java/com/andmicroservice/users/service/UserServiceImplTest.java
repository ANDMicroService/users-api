package com.andmicroservice.users.service;

import com.andmicroservice.users.repository.UserRepository;
import com.andmicroservice.users.representation.UserDTO;
import com.andmicroservice.users.representation.mapper.UserMapper;
import com.andmicroservice.users.util.UserDTOUtils;
import com.andmicroservice.users.util.UserDomainUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getAllUsers_whenNoUsers_returnsEmptyList() {
        assertThat(userService.getAllUsers(), is(empty()));
    }

    @Test
    public void getAllUsers_whenSingleUser_returnsListWithUserRepresentation() {
        com.andmicroservice.users.domain.User domainUser = UserDomainUtils.aTestUser();
        UserDTO expectedUserDTO = UserDTOUtils.aTestUser();
        when(userRepository.findAll()).thenReturn(Collections.singletonList(domainUser));
        when(userMapper.map(any(com.andmicroservice.users.domain.User.class))).thenReturn(expectedUserDTO);

        List<UserDTO> result = userService.getAllUsers();

        assertThat(result, equalTo(Collections.singletonList(expectedUserDTO)));
        verify(userRepository).findAll();
        verify(userMapper).map(eq(domainUser));
    }

    @Test
    public void getAllUsers_whenMultipleUsers_returnsListWithUsersRepresentation() {
        com.andmicroservice.users.domain.User domainUser1 = UserDomainUtils.aTestUser();
        com.andmicroservice.users.domain.User domainUser2 = UserDomainUtils.aSecondTestUser();
        UserDTO expectedUserDTO1 = UserDTOUtils.aTestUser();
        UserDTO expectedUserDTO2 = UserDTOUtils.aSecondTestUser();
        when(userRepository.findAll()).thenReturn(Arrays.asList(domainUser1, domainUser2));
        when(userMapper.map(eq(domainUser1))).thenReturn(expectedUserDTO1);
        when(userMapper.map(eq(domainUser2))).thenReturn(expectedUserDTO2);

        List<UserDTO> result = userService.getAllUsers();

        assertThat(result, containsInAnyOrder(expectedUserDTO1, expectedUserDTO2));
        verify(userRepository).findAll();
        verify(userMapper).map(eq(domainUser1));
        verify(userMapper).map(eq(domainUser2));

    }

}
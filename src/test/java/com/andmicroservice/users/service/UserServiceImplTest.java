package com.andmicroservice.users.service;

import com.andmicroservice.users.domain.User;
import com.andmicroservice.users.repository.UserRepository;
import com.andmicroservice.users.representation.UserDTO;
import com.andmicroservice.users.representation.mapper.UserMapper;
import com.andmicroservice.users.util.UserDTOUtils;
import com.andmicroservice.users.util.UserDomainUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        User domainUser = UserDomainUtils.aTestUser();
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
        User domainUser1 = UserDomainUtils.aTestUser();
        User domainUser2 = UserDomainUtils.aSecondTestUser();
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

    @Test
    public void deleteUser_whenPresent_findsAndDeletesAUser() {
        User domainUser1 = UserDomainUtils.aTestUser();
        String login1 = domainUser1.getLogin();
        Optional<User> domainUserMonad1 = Optional.of(UserDomainUtils.aTestUser());
        when(userRepository.findOneByLogin(login1)).thenReturn(domainUserMonad1);

        userService.deleteUser(login1);
        verify(userRepository).findOneByLogin(login1);
        verify(userRepository).delete(domainUser1);
    }

    @Test
    public void deleteUser_whenNotPresent_doesNotDeleteAUser() {
        User domainUser1 = UserDomainUtils.aTestUser();
        String login1 = domainUser1.getLogin();
        Optional<User> domainUserMonadEmpty = Optional.empty();
        when(userRepository.findOneByLogin(login1)).thenReturn(domainUserMonadEmpty);

        userService.deleteUser(login1);
        verify(userRepository).findOneByLogin(login1);
        verify(userRepository, Mockito.times(0)).delete(login1);


    }

}
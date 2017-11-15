package com.andmicroservice.users.service;

import com.andmicroservice.users.domain.User;
import com.andmicroservice.users.exception.EmailExistsException;
import com.andmicroservice.users.exception.IdProvidedException;
import com.andmicroservice.users.exception.LoginExistsException;
import com.andmicroservice.users.repository.UserRepository;
import com.andmicroservice.users.representation.UserDTO;
import com.andmicroservice.users.representation.mapper.UserMapper;
import com.andmicroservice.users.util.UserDTOUtils;
import com.andmicroservice.users.util.UserDomainUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    public void getAllUsers_whenNoUsers_returnsEmptyList() {
        assertThat(userService.getAllUsers(), is(empty()));
    }

    @Test
    public void getAllUsers_whenSingleUser_returnsListWithUserRepresentation() {
        User savedUser = UserDomainUtils.aTestUser();
        UserDTO expectedUserDTO = UserDTOUtils.aTestUser();
        when(userRepository.findAll()).thenReturn(Collections.singletonList(savedUser));
        when(userMapper.map(any(User.class))).thenReturn(expectedUserDTO);

        List<UserDTO> result = userService.getAllUsers();

        verify(userRepository).findAll();
        verify(userMapper).map(eq(savedUser));
        assertThat(result, equalTo(Collections.singletonList(expectedUserDTO)));
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
        verify(userRepository, times(0)).delete(login1);
    }


    @Test
    public void createUser_whenUserIdProvided_throwIdProvidedException() {
        UserDTO userDTO = UserDTOUtils.aTestUser();

        try {
            userService.createUser(userDTO);
            fail("Should throw IdProvidedException");
        } catch (EmailExistsException e) {
            fail("Should throw IdProvidedException, but threw EmailExistsException");
        } catch (LoginExistsException e) {
            fail("Should throw IdProvidedException, but threw LoginExistsException");
        } catch (IdProvidedException e) {
        } finally {
            verifyNoMoreInteractions(userRepository);
        }
    }

    @Test
    public void createUser_whenUserLoginExists_throwLoginExistsException() {
        UserDTO userDTO = UserDTOUtils.aTestUserBuilder()
                .withId(null)
                .build();
        when(userRepository.findOneByLogin(anyString())).thenReturn(Optional.of(UserDomainUtils.aTestUser()));

        try {
            userService.createUser(userDTO);
            fail("Should throw LoginExistsException");
        } catch (EmailExistsException e) {
            fail("Should throw LoginExistsException, but threw EmailExistsException");
        } catch (LoginExistsException e) {
        } catch (IdProvidedException e) {
            fail("Should throw LoginExistsException, but threw IdProvidedException");
        } finally {
            verify(userRepository).findOneByLogin(eq(userDTO.getLogin()));
            verifyNoMoreInteractions(userRepository);
        }
    }

    @Test
    public void createUser_whenUserEmailsExists_throwEmailExistsException() {
        UserDTO userDTO = UserDTOUtils.aTestUserBuilder()
                .withId(null)
                .build();
        when(userRepository.findOneByLogin(anyString())).thenReturn(Optional.empty());
        when(userRepository.findOneByEmail(anyString())).thenReturn(Optional.of(UserDomainUtils.aTestUser()));

        try {
            userService.createUser(userDTO);
            fail("Should throw EmailExistsException");
        } catch (EmailExistsException e) {
        } catch (LoginExistsException e) {
            fail("Should throw EmailExistsException, but threw LoginExistsException");
        } catch (IdProvidedException e) {
            fail("Should throw EmailExistsException, but threw IdProvidedException");
        } finally {
            verify(userRepository).findOneByLogin(eq(userDTO.getLogin()));
            verify(userRepository).findOneByEmail(eq(userDTO.getEmail()));
            verifyNoMoreInteractions(userRepository);
        }
    }

    @Test
    public void createUser_whenUserIsValid_thenSaveUserAndReturnUserDTO() {
        UserDTO userDTO = UserDTOUtils.aTestUserBuilder()
                .withId(null)
                .build();
        UserDTO expectedUserDTO = UserDTOUtils.aTestUser();
        when(userRepository.findOneByLogin(anyString())).thenReturn(Optional.empty());
        when(userRepository.findOneByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.map(any(User.class))).thenReturn(expectedUserDTO);

        UserDTO userResult = null;
        try {
            userResult = userService.createUser(userDTO);
        } catch (EmailExistsException | LoginExistsException | IdProvidedException e) {
            fail("Should not throw exceptions");
        }

        verify(userRepository).findOneByLogin(eq(userDTO.getLogin()));
        verify(userRepository).findOneByEmail(eq(userDTO.getEmail()));
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertThat(savedUser.getId(), isEmptyOrNullString());
        assertThat(savedUser.getLogin(), is(userDTO.getLogin()));
        assertThat(savedUser.getPassword(), is(userDTO.getPassword()));
        assertThat(savedUser.getEmail(), is(userDTO.getEmail()));
        assertThat(savedUser.getFirstName(), is(userDTO.getFirstName()));
        assertThat(savedUser.getLastName(), is(userDTO.getLastName()));
        verify(userMapper).map(eq(savedUser));
        assertThat(userResult, is(expectedUserDTO));
        verifyNoMoreInteractions(userRepository);
    }

}
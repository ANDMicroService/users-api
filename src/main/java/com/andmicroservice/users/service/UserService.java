package com.andmicroservice.users.service;

import com.andmicroservice.users.exception.EmailExistsException;
import com.andmicroservice.users.exception.IdProvidedException;
import com.andmicroservice.users.exception.LoginExistsException;
import com.andmicroservice.users.representation.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO createUser(UserDTO userDTO) throws EmailExistsException, LoginExistsException, IdProvidedException;

    void deleteUser(String login);
}

package com.andmicroservice.users.service;

import com.andmicroservice.users.representation.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO createUser(UserDTO userDTO);
}

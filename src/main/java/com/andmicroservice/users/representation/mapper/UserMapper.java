package com.andmicroservice.users.representation.mapper;

import com.andmicroservice.users.representation.UserDTO;

public interface UserMapper {

    UserDTO map(com.andmicroservice.users.domain.User user);
}

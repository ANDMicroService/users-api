package com.andmicroservice.users.service;

import com.andmicroservice.users.repository.UserRepository;
import com.andmicroservice.users.representation.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getAllUsers() {
        return null;
    }
}

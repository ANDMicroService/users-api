package com.andmicroservice.users.resource;


import com.andmicroservice.users.representation.User;
import com.andmicroservice.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class UserResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    @ResponseBody
    public List<User> getUsers() {


        return Collections.emptyList();
    }

    @PostMapping("/users")
    @ResponseBody
    public User createUser() {


        return null;
    }

    @PutMapping("/users")
    @ResponseBody
    public User updateUser() {


        return null;
    }
}

package com.andmicroservice.users.resource;


import com.andmicroservice.users.representation.UserDTO;
import com.andmicroservice.users.resource.util.HeaderUtil;
import com.andmicroservice.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ENTITY_NAME = "userManagement";

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    @ResponseBody
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    @ResponseBody
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        logger.info("REST request to create UserDTO : {}", userDTO.toString());

        if(userDTO.getId() != null) {
            ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user cannot already have an ID"))
                    .body(null);
        }
        UserDTO createdUser = userService.createUser(userDTO);

        logger.info("Created user: {}", createdUser.toString());
        return createdUser;
    }

    @PutMapping("/users")
    @ResponseBody
    public UserDTO updateUser() {


        return null;
    }
}

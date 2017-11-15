package com.andmicroservice.users.resource;


import com.andmicroservice.users.exception.EmailExistsException;
import com.andmicroservice.users.exception.IdProvidedException;
import com.andmicroservice.users.exception.LoginExistsException;
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
import java.net.URI;
import java.net.URISyntaxException;
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
    public ResponseEntity createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        logger.debug("REST request to create UserDTO : {}", userDTO.toString());

        UserDTO createdUser = null;
        try {
            createdUser = userService.createUser(userDTO);
        } catch (EmailExistsException | LoginExistsException | IdProvidedException e) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, e.getMessage()))
                    .body(null);
        }

        logger.debug("Created user: {}", createdUser.toString());
        return ResponseEntity.created(new URI("/users/" + createdUser.getLogin()))
                .headers(HeaderUtil.createAlert( "A user is created with identifier " + createdUser.getLogin(), createdUser.getLogin()))
                .body(createdUser);
    }

    @PutMapping("/users")
    @ResponseBody
    public UserDTO updateUser() {


        return null;
    }
}

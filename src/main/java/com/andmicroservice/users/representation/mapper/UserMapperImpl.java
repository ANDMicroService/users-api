package com.andmicroservice.users.representation.mapper;


import com.andmicroservice.users.representation.UserDTO;
import com.andmicroservice.users.representation.builder.UserBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO map(com.andmicroservice.users.domain.User user) {
        if(user == null) {
            return null;
        }
        return UserBuilder.aUser()
                .withId(user.getId())
                .withLogin(user.getLogin())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withEmail(user.getEmail())
                .build();
    }
}

package com.andmicroservice.users.builder;

import com.andmicroservice.users.representation.UserDTO;

public class UserDTOTestBuilder {
    private String id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;

    private UserDTOTestBuilder() {
    }

    public static UserDTOTestBuilder aUser() {
        return new UserDTOTestBuilder();
    }

    public UserDTOTestBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public UserDTOTestBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    public UserDTOTestBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserDTOTestBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserDTOTestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserDTO build() {
        return new UserDTO(id, login, firstName, lastName, email);
    }
}

package com.andmicroservice.users.representation.builder;

import com.andmicroservice.users.representation.UserDTO;

public class UserBuilder {

    private String id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;

    private UserBuilder() {
    }

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserDTO build() {
        return new UserDTO(id, login, firstName, lastName, email);
    }
}

package com.andmicroservice.users.builder;


import com.andmicroservice.users.domain.User;

public class UserDomainBuilder {

    private String id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    private UserDomainBuilder() {
    }

    public static UserDomainBuilder aUser() {
        return new UserDomainBuilder();
    }

    public UserDomainBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public UserDomainBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    public UserDomainBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserDomainBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserDomainBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserDomainBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public User build() {
        if(id == null || id.isEmpty()) {
            throw new IllegalArgumentException("User Id missing when building a UserDTO");
        }
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }
}

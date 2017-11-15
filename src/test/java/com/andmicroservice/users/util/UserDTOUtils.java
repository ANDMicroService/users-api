package com.andmicroservice.users.util;

import com.andmicroservice.users.representation.UserDTO;
import com.andmicroservice.users.representation.builder.UserBuilder;

import static com.andmicroservice.users.util.TestConstants.*;

public class UserDTOUtils {


    public static UserBuilder aTestUserBuilder() {
        return UserBuilder.aUser()
                .withId(USER_ID)
                .withLogin(USER_LOGIN)
                .withFirstName(USER_FIRSTNAME)
                .withLastName(USER_LASTNAME)
                .withEmail(USER_EMAIL);
    }

    public static UserDTO aTestUser() {
        return UserBuilder.aUser()
                .withId(USER_ID)
                .withLogin(USER_LOGIN)
                .withFirstName(USER_FIRSTNAME)
                .withLastName(USER_LASTNAME)
                .withEmail(USER_EMAIL)
                .build();
    }

    public static UserDTO aSecondTestUser() {
        return UserBuilder.aUser()
                .withId(USER_ID2)
                .withLogin(USER_LOGIN2)
                .withFirstName(USER_FIRSTNAME2)
                .withLastName(USER_LASTNAME2)
                .withEmail(USER_EMAIL2)
                .build();
    }

}

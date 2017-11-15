package com.andmicroservice.users.util;


import com.andmicroservice.users.builder.UserDomainBuilder;
import com.andmicroservice.users.domain.User;

import static com.andmicroservice.users.util.TestConstants.*;
import static com.andmicroservice.users.util.TestConstants.USER_EMAIL;

public class UserDomainUtils {

    public static UserDomainBuilder aTestUserBuilder() {
        return UserDomainBuilder.aUser()
                .withId(USER_ID)
                .withPassword(USER_PASSWORD)
                .withLogin(USER_LOGIN)
                .withFirstName(USER_FIRSTNAME)
                .withLastName(USER_LASTNAME)
                .withEmail(USER_EMAIL);
    }

    public static User aTestUser() {
        return UserDomainBuilder.aUser()
                .withId(USER_ID)
                .withPassword(USER_PASSWORD)
                .withLogin(USER_LOGIN)
                .withFirstName(USER_FIRSTNAME)
                .withLastName(USER_LASTNAME)
                .withEmail(USER_EMAIL)
                .build();
    }

    public static User aSecondTestUser() {
        return UserDomainBuilder.aUser()
                .withId(USER_ID2)
                .withPassword(USER_PASSWORD2)
                .withLogin(USER_LOGIN2)
                .withFirstName(USER_FIRSTNAME2)
                .withLastName(USER_LASTNAME2)
                .withEmail(USER_EMAIL2)
                .build();
    }
}

package com.andmicroservice.users.util;

import com.andmicroservice.users.builder.UserDTOTestBuilder;
import com.andmicroservice.users.representation.UserDTO;

import static com.andmicroservice.users.util.TestConstants.USER_EMAIL;
import static com.andmicroservice.users.util.TestConstants.USER_EMAIL2;
import static com.andmicroservice.users.util.TestConstants.USER_FIRSTNAME;
import static com.andmicroservice.users.util.TestConstants.USER_FIRSTNAME2;
import static com.andmicroservice.users.util.TestConstants.USER_ID;
import static com.andmicroservice.users.util.TestConstants.USER_ID2;
import static com.andmicroservice.users.util.TestConstants.USER_LASTNAME;
import static com.andmicroservice.users.util.TestConstants.USER_LASTNAME2;
import static com.andmicroservice.users.util.TestConstants.USER_LOGIN;
import static com.andmicroservice.users.util.TestConstants.USER_LOGIN2;

public class UserDTOUtils {


    public static UserDTOTestBuilder aTestUserBuilder() {
        return UserDTOTestBuilder.aUser()
                .withId(USER_ID)
                .withLogin(USER_LOGIN)
                .withFirstName(USER_FIRSTNAME)
                .withLastName(USER_LASTNAME)
                .withEmail(USER_EMAIL);
    }

    public static UserDTO aTestUser() {
        return UserDTOTestBuilder.aUser()
                .withId(USER_ID)
                .withLogin(USER_LOGIN)
                .withFirstName(USER_FIRSTNAME)
                .withLastName(USER_LASTNAME)
                .withEmail(USER_EMAIL)
                .build();
    }

    public static UserDTO aSecondTestUser() {
        return UserDTOTestBuilder.aUser()
                .withId(USER_ID2)
                .withLogin(USER_LOGIN2)
                .withFirstName(USER_FIRSTNAME2)
                .withLastName(USER_LASTNAME2)
                .withEmail(USER_EMAIL2)
                .build();
    }

}

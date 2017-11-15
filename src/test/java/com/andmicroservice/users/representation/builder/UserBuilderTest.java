package com.andmicroservice.users.representation.builder;

import com.andmicroservice.users.representation.UserDTO;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.andmicroservice.users.util.TestConstants.USER_EMAIL;
import static com.andmicroservice.users.util.TestConstants.USER_FIRSTNAME;
import static com.andmicroservice.users.util.TestConstants.USER_ID;
import static com.andmicroservice.users.util.TestConstants.USER_LASTNAME;
import static com.andmicroservice.users.util.TestConstants.USER_LOGIN;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class UserBuilderTest {

    @Test
    public void build_whenMissingId_throwsIllegalArgumentException() {
        try {
            UserBuilder.aUser().build();
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("User ID missing"));
        }
    }

    @Test
    public void build_whenUserWithIdOnly_throwExceptionDueToMissingLogin() throws Exception {
        try {
            UserBuilder.aUser().withId(USER_ID).build();
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), Matchers.is("User login missing"));
        }
    }

    @Test
    public void testMap_whenUserWithMinimumRequiredInfo_thenMapsExistingFields() throws Exception {

        UserDTO result = UserBuilder.aUser()
                .withId(USER_ID)
                .withLogin(USER_LOGIN)
                .build();

        assertThat(result.getId(), is(USER_ID));
        assertThat(result.getLogin(), is(USER_LOGIN));
        assertThat(result.getEmail(), is(nullValue()));
        assertThat(result.getFirstName(), is(nullValue()));
        assertThat(result.getLastName(), is(nullValue()));
        assertThat(result.getPassword(), is(nullValue()));
    }

    @Test
    public void build_returnsUserDTOWithAllFields() {

        UserDTO result = UserBuilder.aUser()
                .withId(USER_ID)
                .withLogin(USER_LOGIN)
                .withEmail(USER_EMAIL)
                .withFirstName(USER_FIRSTNAME)
                .withLastName(USER_LASTNAME)
                .build();

        assertThat(result.getId(), is(USER_ID));
        assertThat(result.getLogin(), is(USER_LOGIN));
        assertThat(result.getEmail(), is(USER_EMAIL));
        assertThat(result.getFirstName(), is(USER_FIRSTNAME));
        assertThat(result.getLastName(), is(USER_LASTNAME));
        assertThat(result.getPassword(), is(nullValue()));
    }
}
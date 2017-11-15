package com.andmicroservice.users.representation.mapper;

import com.andmicroservice.users.domain.User;
import com.andmicroservice.users.representation.UserDTO;
import com.andmicroservice.users.util.UserDomainUtils;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;


public class UserMapperImplTest {

    private UserMapper userMapper = new UserMapperImpl();

    @Test
    public void testMap_whenNull_returnNull() throws Exception {
        assertThat(userMapper.map(null), is(nullValue()));
    }

    @Test
    public void testMap_whenAllFields_returnAllExceptPassword() throws Exception {
        User user = UserDomainUtils.aTestUser();

        UserDTO result = userMapper.map(user);

        assertThat(result.getPassword(), is(nullValue()));
        assertThat(result.getId(), is(user.getId()));
        assertThat(result.getLogin(), is(user.getLogin()));
        assertThat(result.getEmail(), is(user.getEmail()));
        assertThat(result.getFirstName(), is(user.getFirstName()));
        assertThat(result.getLastName(), is(user.getLastName()));
    }

}
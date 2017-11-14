package com.andmicroservice.users.representation.mapper;

import org.junit.Test;


public class UserMapperImplTest {


    @Test
    public void testMap_whenNull_throwException() throws Exception {

    }

    @Test
    public void testMap_whenUserWithIdOnly_failDueToMissingLogin() throws Exception {

    }

    @Test
    public void testMap_whenUserWithLoginOnly_failDueToMissingId() throws Exception {

    }

    @Test
    public void testMap_whenUserWithMinimumRequiredInfo_thenMapsExistingFields() throws Exception {

    }

    @Test
    public void testMap_whenUserWithAllFields_thenMapsAllFieldsExceptPassword() throws Exception {

    }

}
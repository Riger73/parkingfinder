package com.pp1.parkingfinder.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void account_nameTest() throws Exception, IllegalAccessException
    {
        final String EXPECTED_FIRST_NAME = "FName";
        final String EXPECTED_LAST_NAME = "LName";

        User cust = new User();
        cust.setFirstname(EXPECTED_FIRST_NAME);
        cust.setLastname(EXPECTED_LAST_NAME);

        assertEquals(EXPECTED_FIRST_NAME, cust.getFirstname());
        assertEquals(EXPECTED_LAST_NAME, cust.getLastname());
    }

    @Test
    public void account_usernameTest() throws Exception, IllegalAccessException
    {

    }
}

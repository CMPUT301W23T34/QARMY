package com.example.QArmy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.QArmy.UI.profile.RegistrationActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;

public class TestRegistration {

    private FirebaseFirestore mockDb;

    @Before
    public void setUp() {
        mockDb = FirebaseFirestore.getInstance();
    }

    @Test
    public void testValidEmail() {
        assertTrue(RegistrationActivity.isValidEmail("example@gmail.com"));
    }

    @Test
    public void testInvalidEmail() {
        assertFalse(RegistrationActivity.isValidEmail("example@.com"));
    }

    @Test
    public void testValidPhoneNumber() {
        assertTrue(RegistrationActivity.isValidPhoneNumber("555-555-5555"));
    }

    @Test
    public void testInvalidPhoneNumber() {
        assertFalse(RegistrationActivity.isValidPhoneNumber("555-555-5"));
    }

}
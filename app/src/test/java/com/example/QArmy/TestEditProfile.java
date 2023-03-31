package com.example.QArmy;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.example.QArmy.UI.profile.EditProfileActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Provide tests for the EditProfileActivity.
 * @author Jessica Emereonye
 * @version 1.0
 * @see EditProfileActivity
 */
public class TestEditProfile {

    private EditProfileActivity editProfileActivity;

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra("name", "John Doe");
        intent.putExtra("email", "johndoe@example.com");
        intent.putExtra("phone", "1234567890");
    }
    
    /**
     * Test that user is entering valid input
     */
    @Test
    public void testValidInput() {
        // Find UI elements
        EditText edit_name = editProfileActivity.findViewById(R.id.edit_name);
        EditText edit_email = editProfileActivity.findViewById(R.id.edit_email);
        EditText edit_phone = editProfileActivity.findViewById(R.id.edit_phone);
        Button save_button = editProfileActivity.findViewById(R.id.save_button);

        edit_name.setText("Jane Doe");
        edit_email.setText("janedoe@example.com");
        edit_phone.setText("0987654321");

        save_button.performClick();
    }
    /**
     * Test for no input by user
     */
    
    @Test
    public void testEmptyInput() {
        EditText edit_name = editProfileActivity.findViewById(R.id.edit_name);
        EditText edit_email = editProfileActivity.findViewById(R.id.edit_email);
        EditText edit_phone = editProfileActivity.findViewById(R.id.edit_phone);
        Button save_button = editProfileActivity.findViewById(R.id.save_button);

        edit_name.setText("");
        edit_email.setText("janedoe@example.com");
        edit_phone.setText("0987654321");

        save_button.performClick();

        Assert.assertTrue(edit_email.getError() != null);
        Assert.assertEquals("Invalid email", edit_email.getError().toString());

        Assert.assertTrue(edit_name.getError() != null);
        Assert.assertEquals("Name is required", edit_name.getError().toString());

        Assert.assertTrue(edit_phone.getError() != null);
        Assert.assertEquals("Invalid phone number", edit_phone.getError().toString());
    }

}

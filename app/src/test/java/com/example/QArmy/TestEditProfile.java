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


public class TestEditProfile {
    private DatabaseReference mockDBReference;
    private FirebaseUser mockUser;

    private EditProfileActivity editProfileActivity;

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra("name", "John Doe");
        intent.putExtra("email", "johndoe@example.com");
        intent.putExtra("phone", "1234567890");
    }


    @Test
    public void testValidInput() {
        // Find UI elements
        EditText edit_name = editProfileActivity.findViewById(R.id.edit_name);
        EditText edit_email = editProfileActivity.findViewById(R.id.edit_email);
        EditText edit_phone = editProfileActivity.findViewById(R.id.edit_phone);
        Button save_button = editProfileActivity.findViewById(R.id.save_button);

        // Update user information in UI elements
        edit_name.setText("Jane Doe");
        edit_email.setText("janedoe@example.com");
        edit_phone.setText("0987654321");

        // Click Save button
        save_button.performClick();
    }
    @Test
    public void testEmptyInput() {
        // Find UI elements
        EditText edit_name = editProfileActivity.findViewById(R.id.edit_name);
        EditText edit_email = editProfileActivity.findViewById(R.id.edit_email);
        EditText edit_phone = editProfileActivity.findViewById(R.id.edit_phone);
        Button save_button = editProfileActivity.findViewById(R.id.save_button);

        // Update user information in UI elements with empty name
        edit_name.setText("");
        edit_email.setText("janedoe@example.com");
        edit_phone.setText("0987654321");

        // Click Save button
        save_button.performClick();

        // Verify that the email field has an error message
        Assert.assertTrue(edit_email.getError() != null);
        Assert.assertEquals("Invalid email", edit_email.getError().toString());

        // Verify that the name field has an error message
        Assert.assertTrue(edit_name.getError() != null);
        Assert.assertEquals("Name is required", edit_name.getError().toString());

        // Verify that the phone field has an error message
        Assert.assertTrue(edit_phone.getError() != null);
        Assert.assertEquals("Invalid phone number", edit_phone.getError().toString());
    }

}

package com.example.QArmy;

import android.content.Intent;
import android.widget.Button;

import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.UI.profile.EditProfileActivity;
import com.example.QArmy.UI.profile.UserProfileActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestUserProfileActivity {

    private UserProfileActivity activity;

    @Before
    public void setUp() {
        // Set up the activity
        Intent intent = new Intent();
        intent.putExtra("name", "John Doe");
        intent.putExtra("email", "johndoe@example.com");
        intent.putExtra("phone", "555-555-5555");
        activity = new UserProfileActivity();
        activity.setIntent(intent);
    }

    @Test
    public void testEditButton() {
        Button editButton = activity.findViewById(R.id.edit_button);

        // Perform click on the edit button
        editButton.performClick();

        // Check the intent for the EditProfileActivity
        Intent intent = activity.getIntent();
        Assert.assertEquals(EditProfileActivity.class.getName(), intent.getComponent().getClassName());
        Assert.assertEquals("John Doe", intent.getStringExtra("name"));
        Assert.assertEquals("johndoe@example.com", intent.getStringExtra("email"));
        Assert.assertEquals("555-555-5555", intent.getStringExtra("phone"));
    }

    @Test
    public void testHomeButton() {
        Button homeButton = activity.findViewById(R.id.home_button);

        // Perform click on the home button
        homeButton.performClick();

        // Check the intent for the MainActivity
        Intent intent = activity.getIntent();
        Assert.assertEquals(MainActivity.class.getName(), intent.getComponent().getClassName());
    }
}
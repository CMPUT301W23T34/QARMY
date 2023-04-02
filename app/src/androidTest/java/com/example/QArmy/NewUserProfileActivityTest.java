package com.example.QArmy;

import android.content.Intent;
import android.widget.Button;

import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.UI.profile.EditProfileActivity;
import com.example.QArmy.UI.profile.UserProfileActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NewUserProfileActivityTest {

    private UserProfileActivity activity;

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra("name", "John Doe");
        intent.putExtra("email", "johndoe@example.com");
        intent.putExtra("phone", "555-555-5555");
        activity = new UserProfileActivity();
        activity.setIntent(intent);
    }

    /**
     * Test that edit button is working
     */

    @Test
    public void testEditButton() {
        Button editButton = activity.findViewById(R.id.edit_button);

        editButton.performClick();

        Intent intent = activity.getIntent();
        Assert.assertEquals(EditProfileActivity.class.getName(), intent.getComponent().getClassName());
        Assert.assertEquals("John Doe", intent.getStringExtra("name"));
        Assert.assertEquals("johndoe@example.com", intent.getStringExtra("email"));
        Assert.assertEquals("555-555-5555", intent.getStringExtra("phone"));
    }
    /**
     * Test that home button is working
     */
    
    @Test
    public void testHomeButton() {
        Button homeButton = activity.findViewById(R.id.home_button);

        homeButton.performClick();

        Intent intent = activity.getIntent();
        Assert.assertEquals(MainActivity.class.getName(), intent.getComponent().getClassName());
    }
}

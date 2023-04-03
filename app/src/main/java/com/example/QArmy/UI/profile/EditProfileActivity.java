/*
 * EditProfileActivity
 *
 * Version: 1.2
 *
 * Date: 2023-03-23
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy.UI.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import com.example.QArmy.QArmy;
import com.example.QArmy.R;
import com.example.QArmy.model.AppContainer;
import com.example.QArmy.model.User;

/**
 * The activity that allows a user to edit their profile settings
 * @author Jessica Emereonye
 */
public class EditProfileActivity extends AppCompatActivity {


    // UI Elements
    private EditText edit_name;
    private EditText edit_email;
    private EditText edit_phone;
    private String name;
    private String email;
    private String phone;

    private UserController userController;

    /**
     * Initialize the activity
     * @param savedInstanceState The saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setSupportActionBar(findViewById(R.id.edit_profile_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        AppContainer model = ((QArmy) getApplication()).model;
        userController = new UserController(model.prefsController, model.db);

        // Retrieve user information from Intent
        User user = model.user;
        name = user.getName();
        email = user.getEmail();
        phone = user.getPhone();


        edit_name = findViewById(R.id.edit_name);
        edit_email = findViewById(R.id.edit_email);
        edit_phone = findViewById(R.id.edit_phone);
        Button save_button = findViewById(R.id.save_button);

        edit_name.setText(name);
        edit_email.setText(email);
        edit_phone.setText(phone);

        edit_name.setEnabled(false);

        // Set click listener for Save button
        save_button.setOnClickListener(v -> {
            name = edit_name.getText().toString().trim();
            email = edit_email.getText().toString().trim();
            phone = edit_phone.getText().toString().trim();


            if (TextUtils.isEmpty(email)) {
                edit_email.setError("Email is required");
                return;
            }

            if (!isValidEmail(email)) {
                edit_email.setError("Invalid email");
                return;
            }

            if (!TextUtils.isEmpty(phone) && !isValidPhoneNumber(phone)) {
                edit_phone.setError("Invalid phone");
                return;
            }

            // Update user info
            User updatedUser = new User(name, email, phone);

            // TODO: Add listener for success/failure
            userController.update(updatedUser);
            model.user = updatedUser;
            finish();

        });
    }

    /**
     * Checks whether a new phone number is valid
     *
     * @param target The new phone number the user is attempting to add
     * @return Whether or not this phone number already exists in the database
     */
    public boolean isValidPhoneNumber(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches());
    }

    /**
     * Checks whether a new email is valid
     *
     * @param target The new email the user is attempting to add
     * @return Whether or not this email already exists in the database
     */
    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    /**
     * Close activity and return to previous.
     * @return True - action has been taken
     */
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

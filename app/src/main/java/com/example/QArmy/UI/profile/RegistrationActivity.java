/*
 * RegistrationActivity
 *
 * Version: 1.2
 *
 * Date: 2023-04-02
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy.UI.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.QArmy.QArmy;
import com.example.QArmy.R;
import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.model.AppContainer;
import com.example.QArmy.model.User;


/**
 * Represents the registration page for new users
 * @version 1.2
 * @author Jessica Emereonye
 */
public class RegistrationActivity extends AppCompatActivity implements RegistrationListener {
    private EditText email_or_phone;
    private EditText username;

    private UserController userController;


    /**
     * Checks whether a new email is valid
     *
     * @param target The new email the user is attempting to add
     * @return Whether or not this email already exists in the database
     */
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    /**
     * Checks whether a new phone number is valid
     *
     * @param target The new phone number the user is attempting to add
     * @return Whether or not this phone number already exists in the database
     */
    public static boolean isValidPhoneNumber(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches());
    }

    /**
     * Initialize the activity
     *
     * @param savedInstanceState The saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        AppContainer model = ((QArmy) getApplication()).model;
        User user = model.user;
        Log.d("Registration", user.getName());
        if (!user.getName().equals("")) {
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        userController = new UserController(model.prefsController, model.db);

        // Initialize Views
        email_or_phone = findViewById(R.id.email_or_phone);
        username = findViewById(R.id.username);

        Button register_button = findViewById(R.id.register_button);

        // Set OnClickListener for Register button
        register_button.setOnClickListener(view -> {
            // Get user input
            String email_phoneInput = RegistrationActivity.this.email_or_phone.getText().toString().trim();
            String usernameInput = RegistrationActivity.this.username.getText().toString().trim();


            // Validate user input
            if (TextUtils.isEmpty(email_phoneInput)) {
                Toast.makeText(getApplicationContext(), "Please enter your email or phone number:", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidEmail(email_phoneInput) && !isValidPhoneNumber(email_phoneInput)) {
                Toast.makeText(getApplicationContext(), "Please enter a valid email or phone number:", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(usernameInput)) {
                Toast.makeText(getApplicationContext(), "Please choose a username:", Toast.LENGTH_SHORT).show();
                return;
            }

            userController.add(new User(usernameInput, email_phoneInput, "", 0), RegistrationActivity.this);

        });
    }

    /**
     * Start main activity if user added successfully
     * @param user The user that was created
     */
    @Override
    public void onAdded(User user) {
        ((QArmy) getApplication()).model.user = user;
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Display error message if username exists in database.
     */
    @Override
    public void onExists() {
        Toast.makeText(getApplicationContext(), "Username already taken. Please choose another one.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display error message if error occurs while adding user.
     * @param e The error that occurred
     */
    @Override
    public void onError(Exception e) {
        Log.e("RegistrationActivity", "Error writing document", e);
        Toast.makeText(getApplicationContext(), "Error: Invalid Username", Toast.LENGTH_SHORT).show();
    }
}

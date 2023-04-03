package com.example.QArmy.UI.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.QArmy.QArmy;
import com.example.QArmy.R;
import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.model.AppContainer;
import com.example.QArmy.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the registration page for new users
 *
 * @author Jessica Emereonye
 */
public class RegistrationActivity extends AppCompatActivity implements RegistrationListener {
    private EditText email_or_phone;
    private EditText username;
    //private EditText password;
    private Button register_button;

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
     * @param savedInstanceState
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

        register_button = findViewById(R.id.register_button);

        // Set OnClickListener for Register button
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

            }
        });
    }

    @Override
    public void onAdded(User user) {
        ((QArmy) getApplication()).model.user = user;
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onExists() {
        Toast.makeText(getApplicationContext(), "Username already taken. Please choose another one.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Exception e) {
        Log.e("RegistrationActivity", "Error writing document", e);
    }
}

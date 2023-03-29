package com.example.QArmy.UI.profile;

import android.annotation.SuppressLint;
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
import com.example.QArmy.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the registration page for new users
 * @author Jessica Emereonye
 */
public class RegistrationActivity extends AppCompatActivity {
    private EditText email_or_phone;
    private EditText username;
    private EditText password;
    private Button register_button;

    private FirebaseFirestore db;

    /**
     * Checks whether a new email is valid
     * @param target The new email the user is attempting to add
     * @return Whether or not this email already exists in the database
     */
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    /**
     * Checks whether a new phone number is valid
     * @param target The new phone number the user is attempting to add
     * @return Whether or not this phone number already exists in the database
     */
    public static boolean isValidPhoneNumber(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches());
    }

    /**
     * Initialize the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Initialize Firebase Auth and Database references

        db = FirebaseFirestore.getInstance();


        // TODO: get data from firebase for the Players
        // Get device id
        @SuppressLint("HardwareIds") String deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        // Initialize Views
        email_or_phone = findViewById(R.id.email_or_phone);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register_button = findViewById(R.id.register_button);

        // Set OnClickListener for Register button
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String email_phoneInput = RegistrationActivity.this.email_or_phone.getText().toString().trim();
                String usernameInput = RegistrationActivity.this.username.getText().toString().trim();
                String passwordInput = RegistrationActivity.this.password.getText().toString().trim();

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

                if (TextUtils.isEmpty(passwordInput)) {
                    Toast.makeText(getApplicationContext(), "Please enter a password:", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the device has already been registered
//                db.collection("Players")
//                        .whereEqualTo("deviceID", deviceID)
//                        .get()
//                        .addOnSuccessListener(queryDocumentSnapshots -> {
//                            if (!queryDocumentSnapshots.isEmpty()) {
//                                // Device already registered to another user
//                                Toast.makeText(getApplicationContext(), "This device is already registered to another account.", Toast.LENGTH_SHORT).show();
//                            } else {
                                //check if username is taken
                                db.collection("Players").document(usernameInput)
                                        .get()
                                        .addOnSuccessListener(documentSnapshot -> {
                                            if (documentSnapshot.exists()) {
                                                Toast.makeText(getApplicationContext(), "Username already taken. Please choose another one.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Map<String, Object> userObject = new HashMap<>();
                                                userObject.put("email", email_phoneInput);
                                                userObject.put("userName", usernameInput);
                                                userObject.put("password", passwordInput);
                                                userObject.put("deviceID", deviceID);
                                                ((QArmy) getApplication()).setUser(new User(usernameInput, email_phoneInput, "", "100", deviceID));


                                                db.collection("Players").document(usernameInput)
                                                        .set(userObject)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.e("RegistrationActivity", "DocumentSnapshot successfully written!");
                                                                // save created account to shared preferences
                                                                ((QArmy) getApplication()).setUser(new User(usernameInput, email_phoneInput, "", "100", deviceID));
                                                                MySharedPreferences.saveUserProfile(getApplicationContext(), new User(
                                                                        usernameInput,
                                                                        email_phoneInput,
                                                                        "",
                                                                        "100",
                                                                        deviceID
                                                                ));

//                                                                Intent intent = new Intent(RegistrationActivity.this, UserProfileActivity.class);
//                                                                intent.putExtra("name", usernameInput);
//                                                                intent.putExtra("email", email_phoneInput);
//                                                                intent.putExtra("password", passwordInput);
//                                                                intent.putExtra("id", deviceID);
//                                                                startActivity(intent);
                                                                finish();

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.e("RegistrationActivity", "Error writing document", e);
                                                            }
                                                        });
                                            }
                                        });
//                            }
//                        });
            }
            });


        }
}

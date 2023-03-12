package com.example.QArmy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // UI elements
    private EditText email_or_phone;
    private EditText password;
    private Button login_button;
    private Button register;

    private FirebaseAuth Auth;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Authentication
        Auth = FirebaseAuth.getInstance();

        // Get references to UI elements
        email_or_phone = findViewById(R.id.email_or_phone);
        password = findViewById(R.id.password);
        login_button = findViewById(R.id.login_button);
        register = findViewById(R.id.register);

        // Set click listener for login button
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                String email_or_phone = LoginActivity.this.email_or_phone.getText().toString();
                String password = LoginActivity.this.password.getText().toString();

                // Validate user inputs
                if (TextUtils.isEmpty(email_or_phone)) {
                    LoginActivity.this.email_or_phone.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    LoginActivity.this.password.setError("Password is required");
                    return;
                }

                // Sign in user with Firebase Authentication
                Auth.signInWithEmailAndPassword(email_or_phone, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Set click listener for register text view
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Initialize Firebase Auth state listener
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in, start the profile page activity
                    Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    // Start Firebase Auth state listener in onStart() method
    @Override
    public void onStart() {
        super.onStart();
        Auth.addAuthStateListener(authStateListener);
    }

    // Remove Firebase Auth state listener in onStop() method
    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            Auth.removeAuthStateListener(authStateListener);
        }
    }
}

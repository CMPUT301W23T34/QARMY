package com.example.QArmy.UI.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.QArmy.R;
import com.example.QArmy.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class EditProfileActivity extends AppCompatActivity {
    private DatabaseReference db_reference;
    private FirebaseUser current_user;


    // UI Elements
    private EditText edit_name;
    private EditText edit_email;
    private EditText edit_phone;
    private Button save_button;

    // User Info
    private String name;
    private String email;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Retrieve user information from Intent extras
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");

        // Initialize UI Elements
        edit_name = findViewById(R.id.edit_name);
        edit_email = findViewById(R.id.edit_email);
        edit_phone = findViewById(R.id.edit_phone);
        save_button = findViewById(R.id.save_button);

        // Display user information in UI elements
        edit_name.setText(name);
        edit_email.setText(email);
        edit_phone.setText(phone);

        // Set click listener for Save button
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get updated user info from UI elements
                name = edit_name.getText().toString().trim();
                email = edit_email.getText().toString().trim();
                phone = edit_phone.getText().toString().trim();

                // Validate user input
                if (TextUtils.isEmpty(name)) {
                    edit_name.setError("Name is required");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    edit_email.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    edit_phone.setError("Phone number is required");
                    return;
                }

                // Update user info in Firebase Realtime Database
                User updatedUser = new User(name, email, phone);
                db_reference.child("users").child(current_user.getUid()).setValue(updatedUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfileActivity.this, "User info updated", Toast.LENGTH_SHORT).show();
                                // go back to profile page
                                Intent intent = new Intent(EditProfileActivity.this, UserProfileActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfileActivity.this, "Failed to update user info", Toast.LENGTH_SHORT).show();
                                // go back to profile page
                                Intent intent = new Intent(EditProfileActivity.this, UserProfileActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
            }
        });
    }
}

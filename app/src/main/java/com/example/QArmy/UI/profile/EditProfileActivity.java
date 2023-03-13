package com.example.QArmy.UI.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.QArmy.R;
import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {
    private DatabaseReference db_reference;
    private FirebaseUser current_user;

    // UI Elements
    private EditText edit_name;
    private EditText edit_email;
    private EditText edit_phone;
    private Button save_button;
    private Button home_button1;

    // User Info
    private String name;
    private String email;
    private String phone;

    // SharedPreferences
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Retrieve user information
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");

        // Initialize UI Elements
        edit_name = findViewById(R.id.edit_name);
        edit_email = findViewById(R.id.edit_email);
        edit_phone = findViewById(R.id.edit_phone);
        save_button = findViewById(R.id.save_button);
        home_button1 = findViewById(R.id.home_button1);

        // Display user information in UI elements
        edit_name.setText(name);
        edit_email.setText(email);
        edit_phone.setText(phone);

        // Set click listener for Save button
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EditProfileActivity", "Edit button clicked");

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

                // Update user info in  Database
                User updatedUser = new User(name, email, phone);
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Players");
                dbRef.child(current_user.getUid()).setValue(updatedUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfileActivity.this, "User info updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditProfileActivity.this, UserProfileActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                intent.putExtra("phone", phone);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfileActivity.this, "Failed to update user info", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // if home button is clicked, go back to homepage
        home_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (db_reference == null) {
            db_reference = FirebaseDatabase.getInstance().getReference();
        }

        // Retrieve updated user information
        Intent intent = getIntent();
        String updatedName = intent.getStringExtra("name");
        String updatedEmail = intent.getStringExtra("email");
        String updatedPhone = intent.getStringExtra("phone");

        // Update user information
        edit_name.setText(updatedName);
        edit_email.setText(updatedEmail);
        edit_phone.setText(updatedPhone);

    }

}

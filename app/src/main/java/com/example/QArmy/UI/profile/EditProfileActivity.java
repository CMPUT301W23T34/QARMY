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

import com.example.QArmy.QArmy;
import com.example.QArmy.R;
import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * The activity that allows a user to edit their profile settings
 * @author Jessica Emereonye
 */
public class EditProfileActivity extends AppCompatActivity {
    private Database db;


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

    /**
     * Initialize the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        db = new Database();

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");

        edit_name = findViewById(R.id.edit_name);
        edit_email = findViewById(R.id.edit_email);
        edit_phone = findViewById(R.id.edit_phone);
        save_button = findViewById(R.id.save_button);
        home_button1 = findViewById(R.id.home_button1);

        edit_name.setText(name);
        edit_email.setText(email);
        edit_phone.setText(phone);

        edit_name.setEnabled(false);

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

                // Update user info database
                User updatedUser = new User(name, email, phone);
                db.addUser(updatedUser, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditProfileActivity.this, "User info updated", Toast.LENGTH_SHORT).show();
                            ((QArmy) getApplication()).setUser(updatedUser);
                            MySharedPreferences.saveUserProfile(EditProfileActivity.this, updatedUser);
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Failed to update user info", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                });
            }
        });

        // if home button is clicked, go back to user profile
        home_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}

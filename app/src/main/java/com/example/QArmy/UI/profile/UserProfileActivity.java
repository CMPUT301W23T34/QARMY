package com.example.QArmy.UI.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.QArmy.R;
import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.model.User;


public class UserProfileActivity extends AppCompatActivity {
   
    private User current_user;

    // UI Elements
    private TextView text_name;
    private TextView text_email;
    private TextView text_phone;
    private Button edit_button;
    private Button home_button;

    // User Info
    private String name;
    private String email;
    private String phone;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        deviceId = intent.getStringExtra("id");

        String score = "";
        User user = new User(name, email, phone, score, deviceId);

        // Initialize UI Elements
        text_name = findViewById(R.id.text_name);
        text_email = findViewById(R.id.text_email);
        text_phone = findViewById(R.id.text_phone);
        edit_button = findViewById(R.id.edit_button);

        // Load user info from Database
        text_name.setText(name);
        text_email.setText(email);
        text_phone.setText(phone);
        // Set click listener for Edit button
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start EditProfileActivity
                Intent intent = new Intent(UserProfileActivity.this, EditProfileActivity.class);

                // Pass the current user information to EditProfileActivity
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);

                startActivity(intent);
            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start EditProfileActivity
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}








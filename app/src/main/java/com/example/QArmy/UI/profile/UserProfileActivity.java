package com.example.QArmy.UI.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.QArmy.QArmy;
import com.example.QArmy.R;
import com.example.QArmy.model.User;


/**
 * Activity representing the user profile screen
 * @author Yasmin Ghaznavian
 * @author Kai Luedemann
 */
public class UserProfileActivity extends AppCompatActivity {
    // Firebase
    private User current_user;

    // UI Elements
    private TextView text_name;
    private TextView text_email;
    private TextView text_phone;
    private Button edit_button;

    // User Info
    private String name;
    private String email;
    private String phone;
    private String deviceId;

    private User currentUser;

    /**
     * Initialize the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setSupportActionBar(findViewById(R.id.user_profile_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initialize Firebase
//        db_reference = FirebaseDatabase.getInstance().getReference();
//        current_user = FirebaseAuth.getInstance().getCurrentUser();
//        Intent intent = getIntent();
//        name = intent.getStringExtra("name");
//        email = intent.getStringExtra("email");
//        phone = intent.getStringExtra("phone");
//        deviceId = intent.getStringExtra("id");
//
//        String score = "";
//        User user = new User(name, email, phone, score, deviceId);

        //User user = MySharedPreferences.loadUserProfile(this);

        // Initialize UI Elements
        text_name = findViewById(R.id.text_name);
        text_email = findViewById(R.id.text_email);
        text_phone = findViewById(R.id.text_phone);
        edit_button = findViewById(R.id.edit_button);

        updateValues();

        // Load user info from Firebase Realtime Database


        // Set click listener for Edit button
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start EditProfileActivity
                Intent intent = new Intent(UserProfileActivity.this, EditProfileActivity.class);

                // Pass the current user information to EditProfileActivity using Intent extras
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);

                startActivity(intent);
            }
        });
    }

    /**
     * Update the user profile with new values
     */
    public void updateValues() {
        User user = ((QArmy) getApplication()).getUser();
        name = user.getName();
        email = user.getEmail();
        phone = user.getPhone();

        text_name.setText(name);
        text_email.setText(email);
        text_phone.setText(phone);
    }

    /**
     * Resumes the activity, updating the values (after the EditProfileActivity ends)
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateValues();
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}


package com.example.QArmy.UI.qrcodes;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.QArmy.R;
import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;

import java.util.Date;

public class QRCodeScanActivity extends AppCompatActivity {
    //TODO: Add the image options
    //TODO: Write documentation and tests
    //TODO: Use a ToggleButton for the geolocation
    private Database db;
    private User user;
    private Button finishTrainingButton;
    private LocationManager locationManager;
    private String qrCodeText;
    private ToggleButton geolocationToggle;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        db = new Database();
        Bundle inputData = getIntent().getExtras();
        qrCodeText = inputData.getString("qrCodeText");
        user = (User) inputData.getSerializable("user");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setSupportActionBar(findViewById(R.id.scan_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        geolocationToggle = findViewById(R.id.geolocation_toggle);

        finishTrainingButton = findViewById(R.id.finish_training_button);
        finishTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location = null;
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                        && (ActivityCompat.checkSelfPermission(QRCodeScanActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        && (!geolocationToggle.isChecked())){
                    try {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    } catch (SecurityException e) {

                    }
                }

                // Create the new QRCode object
                QRCode code = new QRCode(qrCodeText, user, location, new Date());
                if (code.getScore() > user.getScore()) {
                    user.setScore(code.getScore());
                }
                db.addQRCode(code, task -> {
                    if (task.isSuccessful()) {
                        //Log.d("Main", "Error adding QR code");
                    }
                });
                db.addUser(user, task -> {

                });

                // Close the activity after making the new QRCode
                finish();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}

package com.example.QArmy.UI.qrcodes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.QArmy.ImageUtils;
import com.example.QArmy.R;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;

import java.util.Date;


/**
 * Activity created after a QR code is scanned.
 * Enables the user to take a picture of the QR code location
 * and toggle geolocation settings.
 * @author Brett Merkosky
 * @author Japkirat Kaur
 * @version 1.0
 */
public class QRCodeScanActivity extends AppCompatActivity {
    private Database db;
    private User user;
    private Button finishTrainingButton;

    private Button takePictureButton;
    private ImageView scanView;
    private LocationManager locationManager;
    private String qrCodeText;
    private ToggleButton geolocationToggle;
    private Bitmap image;

    /**
     * Initialize the activity.
     *
     * @param savedInstanceState
     */
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

        takePictureButton = findViewById(R.id.take_picture_button);
        scanView = findViewById(R.id.scan_image);

        // Open up the camera app to take a picture of the QR code location
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraResultLauncher.launch(cameraIntent);

            }
        });

        // Create the new QRCode object and close the activity
        finishTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location = null;
                // Check if geolocation is enabled
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
                if(image != null){
                    code.setImage(ImageUtils.encodeToBase64(image));
                }
                if (code.getScore() > user.getScore()) {
                    user.setScore(code.getScore());
                }
                db.addQRCode(code, task -> {
                    if (task.isSuccessful()) {
                    }
                });
                db.addUser(user, task -> {

                });

                // Close the activity after making the new QRCode
                finish();
            }
        });
    }

    /**
     * Close the activity when the back button is pressed.
     *
     * @return Whether the activity was successfully closed
     */
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    // Open up the camera activity and take the picture
    // after the clicks the button to take a picture of the QR code location
    ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        image = (Bitmap) result.getData().getExtras().get("data");
                        scanView.setImageBitmap(image);
                    }
                }
            });


}

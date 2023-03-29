package com.example.QArmy.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.QArmy.GPSLocation;
import com.example.QArmy.R;
import com.example.QArmy.UI.profile.MySharedPreferences;
import com.example.QArmy.UI.profile.RegistrationActivity;
import com.example.QArmy.UI.qrcodes.QRListFragment;
import com.example.QArmy.UI.rank.RankFragment;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Controls the ViewPager's behaviour, allowing us to switch between fragments on the main activity
 * @author Japkirat Kaur
 */
public class FragmentPagerAdapter extends FragmentStateAdapter {
    public FragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    /**
     * Create the fragment which we will move to
     * @param position The number of the fragment we will create
     * @return The fragment which we have created
     * @return Null if the position was invalid
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new MapFragment();
            case 1:
                return new QRListFragment();
            case 2:
                return new RankFragment();
        }
        return null;
    }

    /**
     * Get the number of fragments controlled by the ViewPager
     * @return The number of fragments controlled by the ViewPager
     */
    @Override
    public int getItemCount() {
        return 3;
    }

    /**

     This activity allows the user to fetch their current location and take a picture using the device's camera. It uses the device's location
     services and camera hardware to provide a seamless experience for the user.
     The activity includes UI elements for displaying the current location of the user, capturing a picture, and submitting the location and
     picture to a remote server.
     To use this activity, you must first request the necessary permissions from the user for accessing the device's location and camera hardware.
     Note that this activity is intended as an example and should be customized to meet the specific requirements of your application.

     The FetchLocationAndPictureActivity class is an Android Activity that allows the user to fetch their current location and take a picture using the device's camera. It includes UI elements for displaying the current location, capturing a picture, and submitting the location and picture to a remote server.

     The activity requires the necessary permissions from the user for accessing the device's location and camera hardware. The specific implementation details of this activity will depend on the requirements of your application and may include actions such as uploading the image to a cloud service, sending a notification, or updating a database record.

     */

    public static class FetchLocationAndPictureActivity extends AppCompatActivity implements View.OnClickListener {

        private static final String TAG = "FetchLocationAndPicture";

        private User user;
        private Database db;

        private Button takePictureButton;
        private Button geolocationButton;
        private Button finishTrainingButton;

        private ImageView currentImageView;

        private QRCode qrCode;

        private Location location;

        private TextView geolocationTextView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fetch_location_and_picture);

            takePictureButton = findViewById(R.id.takePictureButton);
            takePictureButton.setOnClickListener(this);

            geolocationButton = findViewById(R.id.geolocationButton);
            geolocationButton.setOnClickListener(this);

            finishTrainingButton = findViewById(R.id.finishTrainingButton);
            finishTrainingButton.setOnClickListener(this);

            currentImageView = findViewById(R.id.currentImageView);
            geolocationTextView = findViewById(R.id.locationTextView);

            // Shared Preferences
            user = MySharedPreferences.loadUserProfile(this);
            Log.d("Main", user.getName());
            if (user.getName().equals("")) {
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                user = null;
            }

            db = new Database();

            qrCode = (QRCode) getIntent().getSerializableExtra("QR_CODE");
        }


        @SuppressLint("MissingPermission")
        private void getLocation(Context context) {
            GPSLocation gpsLocation = new GPSLocation(context);

            location = gpsLocation.getLocation();
            if (location != null) {

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String addressString = getAddress(context, latitude, longitude);
                Log.i(TAG, "getLocation: " + addressString);
                geolocationTextView.setText("Geolocation:\nLatitude: " + latitude + "\nLongitude: " + longitude + "\nAddress: " + addressString);

            } else {
                Toast.makeText(context, "unable to fetch location", Toast.LENGTH_SHORT).show();
            }
        }


        /**
         * Gets address.
         *
         * @param context   the context
         * @param latitude  the latitude
         * @param longitude the longitude
         * @return the address
         */
        public static String getAddress(Context context, double latitude, double longitude) {
            StringBuilder stringBuilder = new StringBuilder();

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.getDefault());

            if (latitude == 0.0 && longitude == 0.0)
                return "Unknown Location";

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String address = addresses.get(0).getAddressLine(0);
                String state = addresses.get(0).getAdminArea();
                stringBuilder.append(address).append(" ").append(" ").append(state);
                return stringBuilder.toString();
            } catch (IOException | IndexOutOfBoundsException e) {
                e.printStackTrace();
                return stringBuilder.append("Unknown location ").append(latitude).append(" , ").append(longitude).toString();
            }
        }

        @Override
        public void onClick(View v) {
            if (v == takePictureButton) {
                ImagePicker.with(this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
            if (v == geolocationButton) {
                getLocation(this);
            }
            if (v == finishTrainingButton) {
                if(location != null) {
                    QRCode code = new QRCode(qrCode.getName(), user, location, new Date());
                    if (code.getScore() > user.getScore()) {
                        user.setScore(code.getScore());
                    }
                    db.addQRCode(code, null);
                    db.addUser(user, null);
                    finish();
                }else {
                    Toast.makeText(this, "please fetch location first", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                currentImageView.setImageURI(uri);
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

package com.example.QArmy.UI.qrcodes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.QArmy.ImageUtils;
import com.example.QArmy.R;
import com.example.QArmy.UI.ScannedByActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRCode;

/**
 * The type Qr code visual rep activity.
 *
 * @author Yasmin Ghaznavian
 */
public class QRCodeInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private TextView geoLocationTextView;
    private Button scoreButton;

    private ImageView userImageView;
    private ImageView commentsImageView;

    private TextView monsterTextView;

    private QRCode qrCode;

    private Database db = new Database();

    /**
     * Oncreate method is called when the activity is created so we can set
     * the content view
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_info);

        imageView = findViewById(R.id.currentImageView);
        geoLocationTextView = findViewById(R.id.emailTextView);
        scoreButton = findViewById(R.id.button);
        userImageView = findViewById(R.id.users_image_view);
        userImageView.setOnClickListener(this);
        commentsImageView = findViewById(R.id.comments_image_view);
        monsterTextView = findViewById(R.id.textView4);

        setSupportActionBar(findViewById(R.id.qr_code_representation_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        qrCode = (QRCode) getIntent().getSerializableExtra("QRCode");
        setTitle(qrCode.getName());
        updateData();
    }

    private void updateData() {

        scoreButton.setText("Score: " + qrCode.getScore());
        geoLocationTextView.setText("Geolocation:\nLatitude: " + qrCode.getLat() + "\nLongitude: " + qrCode.getLon());
        if (qrCode.getImage() != null) {
            imageView.setImageBitmap(ImageUtils.decodeFromBase64(qrCode.getImage()));
        }
        StringBuilder stringBuilder = createMonster();
        monsterTextView.setText(stringBuilder.toString());

        commentsImageView.setOnClickListener(view -> {
            Intent intent = new Intent(QRCodeInfoActivity.this, CommentsActivity.class);
            intent.putExtra("QRCode", qrCode);
            startActivity(intent);
        });
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    @NonNull
    private StringBuilder createMonster() {
        StringBuilder stringBuilder = new StringBuilder();
        String hashOfData = qrCode.getHash();

        boolean bit0 = strToBoolean(Integer.toBinaryString(hashOfData.charAt(0)));


        boolean bit1 =strToBoolean(Integer.toBinaryString(hashOfData.charAt(0)));
        boolean bit2 = strToBoolean(Integer.toBinaryString(hashOfData.charAt(1)));
        boolean bit3 = strToBoolean(Integer.toBinaryString(hashOfData.charAt(2)));
        boolean bit4 = strToBoolean(Integer.toBinaryString(hashOfData.charAt(3)));
        boolean bit5 = strToBoolean(Integer.toBinaryString(hashOfData.charAt(4)));


        if (bit2) // hat instead of round face or square face
            stringBuilder.append("+  ' ' ' ' ' ' '  +");
        else
            stringBuilder.append("              ");

        if (bit1) // eyebrows
            stringBuilder.append("\n     --   --");
        else
            stringBuilder.append("\n            ");

        //bit5 ears or no ears
        if (bit5) {
            if (bit0) // eyes with ears
                stringBuilder.append("\n (  @  @   )");
            else
                stringBuilder.append("\n (  -  -   )");
        } else {
            if (bit0) //eyes without ears
                stringBuilder.append("\n   @  @   ");
            else
                stringBuilder.append("\n   -  -   ");
        }

        if (bit3) // nose
            stringBuilder.append("\n        ^     ");
        else
            stringBuilder.append("\n            ");

        //bit4 smiling face or frown
        if (bit4)
            stringBuilder.append("\n     \\\\_//   ");
        else
            stringBuilder.append("\n        __   ");
        return stringBuilder;
    }

    private boolean strToBoolean(String toBinaryString) {

        int count=countOccurrences(toBinaryString,'1');
        return count%2==0;
    }

    public static int countOccurrences(String str, char c) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }
    /**
     * This method takes a character as an input and returns
     * true or false if the character is 0 it means the bit is false
     * if the character is 1 then it means its true.
     *
     * @param ch the character
     * @return true or false
     */
    private boolean charToBoolean(char ch) {
        return ch != '0';
    }

    @Override
    public void onClick(View v) {
        if (v == userImageView) {
            Intent intent = new Intent(this, ScannedByActivity.class);
            intent.putExtra("Object", qrCode.getHash());
            startActivity(intent);
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}


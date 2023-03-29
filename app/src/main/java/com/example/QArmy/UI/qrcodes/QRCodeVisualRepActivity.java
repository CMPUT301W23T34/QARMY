package com.example.QArmy.UI.qrcodes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.QArmy.R;
import com.example.QArmy.UI.UsersSameQrScanActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRCode;

/**
 * The type Qr code visual rep activity.
 * @author Yasmin Ghaznavian
 */
public class QRCodeVisualRepActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private TextView geoLocationTextView;
    private TextView textualRepresentationTextView;
    private Button scoreButton;

    private ImageView userImageView;
    private ImageView commentsImageView;

    private TextView monsterTextView;
    private TextView nameTextView;

    private QRCode qrCode;

    private Database db = new Database();

    /**
     * Oncreate method is called when the activity is created so we can set
     * the content view
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_visual_rep);

        qrCode = (QRCode) getIntent().getSerializableExtra("Object");


        nameTextView = findViewById(R.id.name_textView);
        nameTextView.setText(qrCode.getName());

        imageView = findViewById(R.id.currentImageView);
        geoLocationTextView = findViewById(R.id.locationTextView);
        textualRepresentationTextView = findViewById(R.id.textView4);
        scoreButton = findViewById(R.id.button);

        scoreButton.setText("Score: " + qrCode.getScore());

        userImageView = findViewById(R.id.users_image_view);
        userImageView.setOnClickListener(this);
        commentsImageView = findViewById(R.id.comments_image_view);

        geoLocationTextView.setText("Geolocation:\nLatitude: " + qrCode.getLat() + "\nLongitude: " + qrCode.getLon());

        monsterTextView = findViewById(R.id.textView4);

        StringBuilder stringBuilder = new StringBuilder();
        String hashOfData = qrCode.getHash();

        boolean bit0 = charToBoolean(Integer.toBinaryString(hashOfData.charAt(0)).charAt(0));
        boolean bit1 = charToBoolean(Integer.toBinaryString(hashOfData.charAt(1)).charAt(0));
        boolean bit2 = charToBoolean(Integer.toBinaryString(hashOfData.charAt(2)).charAt(0));
        boolean bit3 = charToBoolean(Integer.toBinaryString(hashOfData.charAt(3)).charAt(0));
        boolean bit4 = charToBoolean(Integer.toBinaryString(hashOfData.charAt(4)).charAt(0));
        boolean bit5 = charToBoolean(Integer.toBinaryString(hashOfData.charAt(5)).charAt(0));


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


        monsterTextView.setText(stringBuilder.toString());

        commentsImageView.setOnClickListener(view -> {
            Intent intent = new Intent(QRCodeVisualRepActivity.this, CommentsActivity.class);
            intent.putExtra("Object", qrCode);
            startActivity(intent);
        });
    }
    /**
     * This method takes a character as an input and returns
     * true or false if the character is 0 it means the bit is false
     * if the character is 1 then it means its true.
     * @param ch the character
     * @return true or false
     */
    private boolean charToBoolean(char ch) {
        return ch != '0';
    }

    @Override
    public void onClick(View v) {
        if (v == userImageView) {
            Intent intent = new Intent(this, UsersSameQrScanActivity.class);
            intent.putExtra("Object", qrCode);
            startActivity(intent);
        }
    }
}

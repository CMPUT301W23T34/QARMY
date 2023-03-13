package com.example.QArmy.UI.qrcodes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.QArmy.R;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRCode;

/**
 * The type Qr code visual rep activity.
 * @author yasminghaznavian
 */
public class QRCodeVisualRepActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_visual_rep);

        String qrCodeId = getIntent().getStringExtra("Object");

        if (qrCode == null) {
            qrCode = new QRCode();
        }

        nameTextView = findViewById(R.id.name_textView);

        imageView = findViewById(R.id.imageView);
        geoLocationTextView = findViewById(R.id.textView);
        textualRepresentationTextView = findViewById(R.id.textView4);
        scoreButton = findViewById(R.id.button);

        userImageView = findViewById(R.id.users_image_view);
        commentsImageView = findViewById(R.id.comments_image_view);

        monsterTextView = findViewById(R.id.textView4);

        StringBuilder stringBuilder = new StringBuilder();
        String hashOfData = "0bfdbc53911679122cfa3aa87725668f689ed8945421bfd9bc0dede715deef9a";

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

        populateData();

        commentsImageView.setOnClickListener(view -> {
            Intent intent = new Intent(QRCodeVisualRepActivity.this, CommentsActivity.class);
            intent.putExtra("Object", qrCodeId);
            startActivity(intent);
        });
    }

    private boolean charToBoolean(char ch) {
        return ch != '0';
    }

    private void populateData() {
        nameTextView.setText("Lieutenant Maroon Petite Cocky Dog");
    }
}

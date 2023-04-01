package com.example.QArmy.UI.qrcodes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.QArmy.ImageUtils;
import com.example.QArmy.R;
import com.example.QArmy.UI.UsersSameQrScanActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.QRCode;

import java.util.List;

/**
 * The type Qr code visual rep activity.
 *
 * @author Yasmin Ghaznavian
 */
public class QRCodeVisualRepActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private TextView geoLocationTextView;
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
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_visual_rep);

        nameTextView = findViewById(R.id.name_textView);

        imageView = findViewById(R.id.currentImageView);
        geoLocationTextView = findViewById(R.id.emailTextView);
        scoreButton = findViewById(R.id.button);
        userImageView = findViewById(R.id.users_image_view);
        userImageView.setOnClickListener(this);
        commentsImageView = findViewById(R.id.comments_image_view);
        monsterTextView = findViewById(R.id.textView4);

        String qrCodeId = (String) getIntent().getStringExtra("Object");
        db.getCodesById(qrCodeId, new QueryListener<QRCode>() {
            @Override
            public void onSuccess(List<QRCode> data) {
                if (data != null && data.size() == 1) {
                    qrCode = data.get(0);
                    updateData();
                } else {
                    Toast.makeText(QRCodeVisualRepActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    ProgressBar progressBar = findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(QRCodeVisualRepActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void updateData() {

        nameTextView.setText(qrCode.getName());
        scoreButton.setText("Score: " + qrCode.getScore());
        geoLocationTextView.setText("Geolocation:\nLatitude: " + qrCode.getLat() + "\nLongitude: " + qrCode.getLon());
        imageView.setImageBitmap(ImageUtils.decodeFromBase64(qrCode.getImage()));
        StringBuilder stringBuilder = createMonster();
        monsterTextView.setText(stringBuilder.toString());

        commentsImageView.setOnClickListener(view -> {
            Intent intent = new Intent(QRCodeVisualRepActivity.this, CommentsActivity.class);
            intent.putExtra("Object", qrCode.getID());
            startActivity(intent);
        });
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    @NonNull
    private StringBuilder createMonster() {
        StringBuilder stringBuilder = new StringBuilder();
        String hashOfData = qrCode.getHash();

        // boolean bit0 = charToBoolean(Integer.toBinaryString(hashOfData.charAt(0)).charAt(0));
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
            Intent intent = new Intent(this, UsersSameQrScanActivity.class);
            intent.putExtra("Object", qrCode.getHash());
            startActivity(intent);
        }
    }
}


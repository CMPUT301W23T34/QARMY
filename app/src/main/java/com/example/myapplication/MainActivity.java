package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<QRCode> qrCodeDataList;
    private ListView qrCodeList;
    private QRCodeArrayAdapter qrCodeAdapter;
    private TextView total;
    private TextView max;

    public void addQRCode(QRCode qrCode) {
        qrCodeAdapter.add(qrCode);
        qrCodeAdapter.notifyDataSetChanged();
        updateSummaries();
    }

    public void removeQRCode(QRCode qrCode) {
        qrCodeAdapter.remove(qrCode);
        qrCodeAdapter.notifyDataSetChanged();
        updateSummaries();
    }

    private void updateSummaries() {
        //TODO: Implement this
        // This is where we would update our total, count, min, and max
        // after deleting or adding a QR code
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        qrCodeDataList = new ArrayList<QRCode>();

        qrCodeList = findViewById(R.id.qr_code_list);
        qrCodeAdapter = new QRCodeArrayAdapter(this, qrCodeDataList);
        qrCodeList.setAdapter(qrCodeAdapter);

        total = findViewById(R.id.sum_of_scores);
        max = findViewById(R.id.max_score);

        // TODO: Get rid of this
        // THIS IS A TEMPORARY LIST WE ARE MAKING FOR VERIFICATION
        // We won't actually want to create NEW qr codes here, since we should check if they already exist
        // (i.e. have been scanned by someone else) first
        addQRCode(new QRCode("ThisIsWhereTheHashWouldGo", null, null, null));
        addQRCode(new QRCode("ThisIsWhereTheHashWouldGo", null, null, null));
    }
}
package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class QRCodeArrayAdapter extends ArrayAdapter<QRCode> {
    public QRCodeArrayAdapter(Context context, ArrayList<QRCode> visits) {
        super(context, 0, visits);
    }

    // Creates a view to display the list of QR Codes
    // TODO: Clean up this function (I just copied it out of the MyCarFootprint app and changed the variable names)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.qr_code_data,
                    parent, false);
        } else {
            view = convertView;
        }
        QRCode qrCode = getItem(position);
        TextView qrCodeName = view.findViewById(R.id.qr_code_name);
        TextView qrCodeScore = view.findViewById(R.id.qr_code_score);

        qrCodeName.setText(qrCode.getName());
        qrCodeScore.setText("Score: "+Integer.toString(qrCode.getScore()));
        return view;
    }
}

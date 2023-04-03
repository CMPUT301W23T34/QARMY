/*
 * NoDeleteQRAdapter
 *
 * Version: 1.0
 *
 * Date: 2023-04-03
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 * - Brett Merkosky, MyCarFootprint
 */

package com.example.QArmy.UI.qrcodes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.QArmy.R;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.QRList;

import java.util.Locale;


/**
 * Provide views to represent QR codes in a ListView.
 * @author Brett Merkosky
 * @author Kai Luedemann
 * @author Yasmin Ghaznavian
 * @version 1.0
 */
public class NoDeleteQRAdapter extends QRCodeArrayAdapter {
    /**
     * Initialize the adapter.
     *
     * @param context
     * @param qrList  The list of QR codes
     * @param db      The database to query
     */
    public NoDeleteQRAdapter(Context context, QRList qrList, Database db) {
        super(context, qrList, db);
    }

    /** Creates a view to display the list of QR Codes
     *
     * Note: Code modified from CMPUT 301 Assignment 1 - MyCarFootprint
     *
     * @param position Index of item in list
     * @param convertView
     * @param parent
     * @return The view belonging to the QR code
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // TODO: Clean up this function (I just copied it out of the MyCarFootprint app and changed the variable names)
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.qr_code_data_others,
                    parent, false);
        } else {
            view = convertView;
        }
        QRCode qrCode = getItem(position);
        TextView qrCodeName = view.findViewById(R.id.qr_code_name);
        TextView qrCodeScore = view.findViewById(R.id.qr_code_score);

        qrCodeName.setText(qrCode.getName());
        qrCodeScore.setText(String.format(Locale.CANADA, "Score: %d", qrCode.getScore()));

        view.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), QRCodeInfoActivity.class);
            intent.putExtra("QRCode", qrCode);
            getContext().startActivity(intent);
        });

        return view;
    }
}

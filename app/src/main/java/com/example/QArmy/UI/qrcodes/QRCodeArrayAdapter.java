/*
 * QRCodeArrayAdapter
 *
 * Version: 1.0
 *
 * Date: 2023-03-08
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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRList;
import com.example.QArmy.R;
import com.example.QArmy.TView;
import com.example.QArmy.model.QRCode;

import java.util.Locale;

/**
 * Provide views to represent QR codes in a ListView.
 * @author Brett Merkosky
 * @author Kai Luedemann
 * @author Yasmin Ghaznavian
 * @version 1.0
 */
public class QRCodeArrayAdapter extends ArrayAdapter<QRCode> implements TView<QRList> {
    private final QRList qrList;
    private final Database db;


    /**
     * Initialize the adapter.
     * @param context
     * @param qrList The list of QR codes
     * @param db The database to query
     */
    public QRCodeArrayAdapter(Context context, QRList qrList, Database db) {
        super(context, 0, qrList.getList());
        this.db = db;
        this.qrList = qrList;
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
            view = LayoutInflater.from(getContext()).inflate(R.layout.qr_code_data,
                    parent, false);
        } else {
            view = convertView;
        }
        QRCode qrCode = getItem(position);
        TextView qrCodeName = view.findViewById(R.id.qr_code_name);
        TextView qrCodeScore = view.findViewById(R.id.qr_code_score);

        qrCodeName.setText(qrCode.getName());
        qrCodeScore.setText(String.format(Locale.CANADA, "Score: %d", qrCode.getScore()));

        ImageButton button = view.findViewById(R.id.deleteButton);
        // TODO: Convert to controller class
        button.setOnClickListener(view1 -> db.deleteQRCode(qrCode, task -> {
            if (task.isSuccessful()) {
                qrList.remove(qrCode);
            }
        }));

        view.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), QRCodeVisualRepActivity.class);
            intent.putExtra("Object", qrCode.getID());
            getContext().startActivity(intent);
        });

        return view;
    }

    /**
     * Notify the listview when the model state has changed.
     * @param model The model whose state has changed
     */
    @Override
    public void update(QRList model) {
        notifyDataSetChanged();
    }
}
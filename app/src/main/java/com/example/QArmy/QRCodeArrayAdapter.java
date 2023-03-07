package com.example.QArmy;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRCode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class QRCodeArrayAdapter extends ArrayAdapter<QRCode> implements TView<QRList>{
    private QRList qrList;
    private Database db;
    public QRCodeArrayAdapter(Context context, QRList qrList, Database db) {
        super(context, 0, qrList.getList());
        this.db = db;
        this.qrList = qrList;
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

        ImageButton button = view.findViewById(R.id.deleteButton);
        // TODO: Convert to controller class
        button.setOnClickListener(view1 -> db.deleteQRCode(qrCode, task -> {
            if (task.isSuccessful()) {
                qrList.remove(qrCode);
            }
        }));
        return view;
    }

    @Override
    public void update(QRList model) {
        notifyDataSetChanged();
    }
}

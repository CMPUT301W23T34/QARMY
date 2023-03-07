package com.example.QArmy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class QRListFragment extends Fragment {

    private ArrayList<QRCode> qrCodeDataList;
    private ListView qrCodeList;
    private QRCodeArrayAdapter qrCodeAdapter;
    private TextView total;
    private TextView max;

    private Database db;
    private QRListener listener;
    private User user;

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
    public QRListFragment(){
        db = new Database();
        listener = new QRListener();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        qrCodeDataList = new ArrayList<QRCode>();

        qrCodeList = getView().findViewById(R.id.qr_code_list);
        qrCodeAdapter = new QRCodeArrayAdapter(getContext(), qrCodeDataList, db);
        qrCodeList.setAdapter(qrCodeAdapter);

        total = getView().findViewById(R.id.sum_of_scores);
        max = getView().findViewById(R.id.max_score);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    class QRListener implements OnCompleteListener<Void>, QueryListener<QRCode> {
        @Override
        public void onComplete(@NonNull Task<Void> task) {

        }

        @Override
        public void onSuccess(List<QRCode> data) {
            qrCodeDataList.clear();
            qrCodeDataList.addAll(data);
            qrCodeAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Exception e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        db.getUserCodes(new User("kai"), listener);
    }
}
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

import java.util.ArrayList;

public class HomeFragment extends Fragment {

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
    public HomeFragment(){

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
        qrCodeAdapter = new QRCodeArrayAdapter(getContext(), qrCodeDataList);
        qrCodeList.setAdapter(qrCodeAdapter);

        total = getView().findViewById(R.id.sum_of_scores);
        max = getView().findViewById(R.id.max_score);

        // TODO: Get rid of this
        // THIS IS A TEMPORARY LIST WE ARE MAKING FOR VERIFICATION
        // We won't actually want to create NEW qr codes here, since we should check if they already exist
        // (i.e. have been scanned by someone else) first
        addQRCode(new QRCode("QRCode1", null, null, null));
        addQRCode(new QRCode("QRCode2", null, null, null));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}

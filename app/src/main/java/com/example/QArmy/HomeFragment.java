package com.example.QArmy;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<QRCode> qrCodeDataList;
    private ListView qrCodeList;
    private QRCodeArrayAdapter qrCodeAdapter;
    private TextView total;
    private TextView max;

    private Database db;

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
        db = new Database();
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

        db.registerQRCodes(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                qrCodeDataList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    String hash = doc.getId();
                    qrCodeDataList.add(new QRCode(hash, doc.getData()));
                }
                qrCodeAdapter.notifyDataSetChanged();
            }
        });

        total = getView().findViewById(R.id.sum_of_scores);
        max = getView().findViewById(R.id.max_score);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}

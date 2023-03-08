package com.example.QArmy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

public class QRListFragment extends Fragment {
    private Database db;
    private QRListener listener;
    private User user;
    private QRList qrList;
    private String newCode;

    private void updateSummaries() {
        //TODO: Implement this
        // This is where we would update our total, count, min, and max
        // after deleting or adding a QR code
    }
    public QRListFragment(){
        db = new Database();
        listener = new QRListener();
        user = new User("kai");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        qrList = new QRList();

        ListView qrCodeList = getView().findViewById(R.id.qr_code_list);
        QRCodeArrayAdapter qrCodeAdapter = new QRCodeArrayAdapter(getContext(), qrList, db);
        qrCodeList.setAdapter(qrCodeAdapter);
        qrList.addView(qrCodeAdapter);

        SummaryFragment fragment = new SummaryFragment();
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
        qrList.addView(fragment);
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    class QRListener implements QueryListener<QRCode> {

        @Override
        public void onSuccess(List<QRCode> data) {
            qrList.modify(data);
        }

        @Override
        public void onFailure(Exception e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        db.getUserCodes(user, listener);
    }
}

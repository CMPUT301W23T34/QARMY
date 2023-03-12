/*
 * QRListFragment
 *
 * Version: 1.0
 *
 * Date: 2023-03-08
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 * - Suragch, 2016-09-13, https://stackoverflow.com/a/39467807, Stack Overflow
 */
package com.example.QArmy.UI.qrcodes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.QArmy.model.QRList;
import com.example.QArmy.R;
import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;

import java.util.List;

/**
 * Holds a list of QR codes and a summary of their scores.
 * @author Kai Luedemann
 * @author Japkirat Kaur
 * @version 1.0
 */
public class QRListFragment extends Fragment {
    private Database db;
    private QRListener listener;
    private User user;
    private QRList qrList;

    /**
     * Create the root view for the fragment.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return The root view of the fragment
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    /**
     * Setup the list of QR codes when the view is created.
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new Database();
        listener = new QRListener();
        user = new User("kai", "", "");
        qrList = new QRList();

        ListView qrCodeList = view.findViewById(R.id.qr_code_list);
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

    /**
     * Initialize the fragment.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Listen for the completion of a query to the database and update the
     * model with the new data.
     */
    class QRListener implements QueryListener<QRCode> {

        @Override
        public void onSuccess(List<QRCode> data) {
            qrList.modify(data);
        }

        @Override
        public void onFailure(Exception e) {

        }
    }

    /**
     * Refresh the QR codes when the fragment comes into view.
     */
    @Override
    public void onResume() {
        super.onResume();
        db.getUserCodes(user, listener);
    }
}

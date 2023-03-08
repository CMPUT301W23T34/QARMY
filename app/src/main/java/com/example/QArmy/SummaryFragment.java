package com.example.QArmy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SummaryFragment extends Fragment implements TView<QRList> {

    private TextView maxView;
    private TextView minView;
    private TextView totalView;
    private TextView countView;


    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        maxView = getView().findViewById(R.id.max_view);
        minView = getView().findViewById(R.id.min_view);
        countView = getView().findViewById(R.id.count_view);
        totalView = getView().findViewById(R.id.total_view);
    }

    @Override
    public void update(QRList model) {
        updateTotal(model.getTotal());
        updateCount(model.getCount());
        updateMax(model.getMax());
        updateMin(model.getMin());
    }

    private void updateTotal(int total) {
        totalView.setText(String.format(Locale.CANADA, "Total: %d", total));
    }

    private void updateCount(int count) {
        countView.setText(String.format(Locale.CANADA, "Count: %d", count));
    }

    private void updateMin(int min) {
        if (min > 0) {
            minView.setText(String.format(Locale.CANADA, "Min: %d", min));
        } else {
            minView.setText("Min: n/a");
        }
    }

    private void updateMax(int max) {
        if (max > 0) {
            maxView.setText(String.format(Locale.CANADA, "Max: %d", max));
        } else {
            maxView.setText("Max: n/a");
        }
    }
}
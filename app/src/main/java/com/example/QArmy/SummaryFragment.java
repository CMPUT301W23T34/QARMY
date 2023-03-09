/*
 * SummaryFragment
 *
 * Version: 1.0
 *
 * Date: 2023-03-08
 *
 * Sources:
 */

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
 * Displays the total, min, max, and number of scores from a list of QR codes.
 * @author Kai Luedemann
 * @version 1.0
 */
public class SummaryFragment extends Fragment implements TView<QRList> {

    private TextView maxView;
    private TextView minView;
    private TextView totalView;
    private TextView countView;

    /**
     * Initialize the fragment.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Create the root view
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return The root view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    /**
     * Get the text views when the fragment view is created.
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        maxView = view.findViewById(R.id.max_view);
        minView = view.findViewById(R.id.min_view);
        countView = view.findViewById(R.id.count_view);
        totalView = view.findViewById(R.id.total_view);
    }

    /**
     * Update the values from the QR code list
     * @param model The list of QR codes
     */
    @Override
    public void update(QRList model) {
        updateTotal(model.getTotal());
        updateCount(model.getCount());
        updateMax(model.getMax());
        updateMin(model.getMin());
    }

    /**
     * Set the total text
     * @param total The sum of scores
     */
    private void updateTotal(int total) {
        totalView.setText(String.format(Locale.CANADA, "Total: %d", total));
    }

    /**
     * Set the count text
     * @param count The number of QR codes in the list
     */
    private void updateCount(int count) {
        countView.setText(String.format(Locale.CANADA, "Count: %d", count));
    }

    /**
     * Set the min text
     * @param min The minimum score in the list
     */
    private void updateMin(int min) {
        if (min > 0) {
            minView.setText(String.format(Locale.CANADA, "Min: %d", min));
        } else {
            minView.setText(R.string.min_na_text);
        }
    }

    /**
     * Set the max text
     * @param max The maximum score in the list
     */
    private void updateMax(int max) {
        if (max > 0) {
            maxView.setText(String.format(Locale.CANADA, "Max: %d", max));
        } else {
            maxView.setText(R.string.max_na_text);
        }
    }
}
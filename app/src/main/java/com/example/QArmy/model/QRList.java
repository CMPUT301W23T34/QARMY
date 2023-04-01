/*
 * QRList
 *
 * Version: 1.0
 *
 * Date: 2023-03-08
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy.model;

import com.example.QArmy.TModel;
import com.example.QArmy.TView;
import com.example.QArmy.model.QRCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class storing the list of QR codes and summary information.
 * @author Kai Luedemann
 * @version 1.0
 */
public class QRList extends TModel<TView> {
    private final ArrayList<QRCode> qrCodes;
    private int total;
    private int max;
    private int min;

    /**
     * Initialize an empty list.
     */
    public QRList() {
        qrCodes = new ArrayList<>();
        update();
    }

    /**
     * Add a QR code to the list
     * @param code The code to add
     */
    public void add(QRCode code) {
        qrCodes.add(code);
        update();
        notifyViews();
    }

    /**
     * Refresh the list from a list of QR codes.
     * @param newCodes The queried list of QR codes
     */
    public void modify(List<QRCode> newCodes) {
        qrCodes.clear();
        qrCodes.addAll(newCodes);
        update();
        notifyViews();
    }

    /**
     * Remove a QR code from the list
     * @param code The QR code to remove
     */
    public void remove(QRCode code) {
        qrCodes.remove(code);
        update();
        notifyViews();
    }

    /**
     * Recalculate the summary values.
     */
    private void update() {
        total = 0;
        min = -1;
        max = -1;
        int score;
        for (QRCode code : qrCodes) {
            score = code.getScore();
            total += score;
            if (min < 0 || score < min) {
                min = score;
            }
            if (score > max) {
                max = score;
            }
        }
    }

    /* ************************** Getters *************************************/

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return qrCodes.size();
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public ArrayList<QRCode> getList() {
        return qrCodes;
    }
}


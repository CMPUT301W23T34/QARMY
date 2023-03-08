package com.example.QArmy;

import com.example.QArmy.model.QRCode;

import java.util.ArrayList;
import java.util.List;

public class QRList extends TModel<TView> {
    private ArrayList<QRCode> qrCodes;
    private int total;
    private int max;
    private int min;

    public QRList() {
        qrCodes = new ArrayList<>();
        update();
    }

    public ArrayList<QRCode> getList() {
        return qrCodes;
    }

    public void modify(List<QRCode> newCodes) {
        qrCodes.clear();
        qrCodes.addAll(newCodes);
        update();
        notifyViews();
    }

    public void remove(QRCode code) {
        qrCodes.remove(code);
        update();
        notifyViews();
    }

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
}

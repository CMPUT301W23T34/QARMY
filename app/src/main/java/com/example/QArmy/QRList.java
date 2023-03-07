package com.example.QArmy;

import com.example.QArmy.model.QRCode;

import java.util.ArrayList;
import java.util.List;

public class QRList extends TModel<TView> {
    private ArrayList<QRCode> qrCodes;

    public QRList() {
        qrCodes = new ArrayList<>();
    }

    public ArrayList<QRCode> getList() {
        return qrCodes;
    }

    public void modify(List<QRCode> newCodes) {
        qrCodes.clear();
        qrCodes.addAll(newCodes);
        notifyViews();
    }

    public void remove(QRCode code) {
        qrCodes.remove(code);
        notifyViews();
    }
}

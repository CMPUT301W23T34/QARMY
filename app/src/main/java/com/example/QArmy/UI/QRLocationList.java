package com.example.QArmy.UI;

import com.example.QArmy.TModel;
import com.example.QArmy.TView;
import com.example.QArmy.model.QRCode;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

public class QRLocationList extends TModel<TView> {
    private ArrayList<QRCode> qrCodes;
    private ArrayList<OverlayItem> itemList;

    public QRLocationList () {
        qrCodes = new ArrayList<>();
        itemList = new ArrayList<>();
    }

    public void modify(List<QRCode> newCodes) {
        qrCodes.clear();
        qrCodes.addAll(newCodes);
        updateQrLocations();
        notifyViews();
    }

    private void updateQrLocations() {
        itemList.clear();
        for (QRCode qr : qrCodes) {
            if (qr.getLat() != null || qr.getLon() != null) {
                GeoPoint g = new GeoPoint(qr.getLat(), qr.getLon());
                itemList.add(new OverlayItem(qr.getHash(), qr.getName(), g));
            }
        }
    }

    public ArrayList<QRCode> getQrCodes() {
        return qrCodes;
    }

    public ArrayList<OverlayItem> getItemList() {
        return itemList;
    }
}

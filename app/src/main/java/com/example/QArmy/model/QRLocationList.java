package com.example.QArmy.model;

import com.example.QArmy.TModel;
import com.example.QArmy.TView;

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
        for (QRCode qr : newCodes) {
            if (qr.getLat() != null || qr.getLon() != null) {
                qrCodes.add(qr);
            }
        }
        updateQrLocations();
        notifyViews();
    }

    private void updateQrLocations() {
        itemList.clear();
        for (QRCode qr : qrCodes) {
            GeoPoint g = new GeoPoint(qr.getLat(), qr.getLon());
            itemList.add(new OverlayItem(qr.getHash(), qr.getName(), g));
        }
    }

    public ArrayList<QRCode> getQrCodes() {
        return qrCodes;
    }

    public ArrayList<OverlayItem> getItemList() {
        return itemList;
    }
}

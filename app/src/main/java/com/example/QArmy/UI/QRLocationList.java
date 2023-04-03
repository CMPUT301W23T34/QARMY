package com.example.QArmy.UI;

import com.example.QArmy.TModel;
import com.example.QArmy.TView;
import com.example.QArmy.model.QRCode;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class storing arraylist of QRCodes and corresponding OverlayItems for the map
 * @author Japkirat Kaur
 */
public class QRLocationList extends TModel<TView> {
    private ArrayList<QRCode> qrCodes;
    private ArrayList<OverlayItem> itemList;

    /**
     * Initialize empty arrayLists
     */
    public QRLocationList () {
        qrCodes = new ArrayList<>();
        itemList = new ArrayList<>();
    }

    /**
     * Resets qr and overlay item lists
     * @param newCodes
     */
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

    /**
     * Updates list of OverlayItems
     */
    private void updateQrLocations() {
        itemList.clear();
        for (QRCode qr : qrCodes) {
            GeoPoint g = new GeoPoint(qr.getLat(), qr.getLon());
            itemList.add(new OverlayItem(qr.getHash(), qr.getName(), g));

        }
    }

    /**
     * Get the list of QRCodes
     * @return the ArrayList of QRCodes
     */
    public ArrayList<QRCode> getQrCodes() {
        return qrCodes;
    }

    /**
     * Get the list of OverlayItems
     * @return the ArrayList of OverlayItems
     */
    public ArrayList<OverlayItem> getItemList() {
        return itemList;
    }
}

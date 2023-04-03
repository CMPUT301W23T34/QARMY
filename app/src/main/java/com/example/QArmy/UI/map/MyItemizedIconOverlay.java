/*
 * MyItemizedIconOverlay
 *
 * Version: 1.1
 *
 * Date: 2023-04-03
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */
package com.example.QArmy.UI.map;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

/**
 * Custom ItemizedIconOverlay used to show QR Codes on map
 * @author Japkirat Kaur
 * @version 1.1
 * @param <OverlayItem>
 */
public class MyItemizedIconOverlay<OverlayItem> extends ItemizedIconOverlay{
    protected List<OverlayItem> mItemList;
    public MyItemizedIconOverlay(List pList, Drawable pDefaultMarker, OnItemGestureListener pOnItemGestureListener, Context pContext) {
        super(pList, pDefaultMarker, pOnItemGestureListener, pContext);
    }

    @Override
    public void onDetach(MapView mapView) {
        if (mItemList != null)
            mItemList.clear();
    }

}

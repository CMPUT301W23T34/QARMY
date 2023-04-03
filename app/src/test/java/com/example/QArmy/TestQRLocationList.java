package com.example.QArmy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.location.Location;
import android.location.LocationManager;

import com.example.QArmy.UI.QRLocationList;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;


/**
 * Provides tests for QRLocationList  class
 * @author Japkirat Kaur
 */
public class TestQRLocationList {

    /**
     * Create a mock QR code
     * @return Empty QR code
     */
    public QRCode MockEmptyCode() {
        return new QRCode();
    }

    /**
     * Create a mock QR code
     * @return  QRCode
     */
    public QRCode MockCode() {
        return new QRCode("http://en.m.wikipedia.org",new User("test"),new Location(LocationManager.NETWORK_PROVIDER),new Date());
    }

    /**
     * Create a mock QR list
     * @return Empty QR list
     */
    public QRLocationList MockQRLocationList() {
        return new QRLocationList();
    }

    /**
     * Test the modify method
     */
    @Test
    public void testModify() {
        QRLocationList list = MockQRLocationList();
        ArrayList<QRCode> codes = new ArrayList<QRCode>();

        codes.add(MockEmptyCode());
        list.modify(codes);
        assertEquals(0, list.getQrCodes().size());
        assertEquals(0, list.getItemList().size());

        codes.add(MockCode());
        list.modify(codes);
        assertEquals(1, list.getQrCodes().size());
        assertEquals(1, list.getItemList().size());
        assertTrue(list.getQrCodes().contains(codes.get(1)));

        codes.clear();
        list.modify(codes);
        assertEquals(0, list.getQrCodes().size());
        assertEquals(0, list.getItemList().size());
    }

}

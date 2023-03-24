package com.example.QArmy;

import static org.junit.Assert.assertEquals;

import com.example.QArmy.UI.qrcodes.Constants;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;

import org.junit.Test;

import java.util.Date;

public class TestConstants {
    @Test
    public void testQRCodeConstants() {
        QRCode testQRCode = new QRCode("BFG5DGW54\n", new User(), null, new Date());
        Constants.setQrCode(testQRCode);
        assertEquals(testQRCode, Constants.getQrCode());
    }
}

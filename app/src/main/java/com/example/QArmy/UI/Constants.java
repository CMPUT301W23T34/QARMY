package com.example.QArmy.UI;

import com.example.QArmy.model.QRCode;

public class Constants {

    private static QRCode qrCode;

    public static QRCode getQrCode() {
        return qrCode;
    }

    public static void setQrCode(QRCode qrCode) {
        Constants.qrCode = qrCode;
    }
}

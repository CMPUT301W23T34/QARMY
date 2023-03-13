package com.example.QArmy.UI.qrcodes;

import com.example.QArmy.model.QRCode;

/**
 * This class provides a way to store and retrieve a single {@link QRCode} object
 * The {@link QRCode} object can be set using the {@link Constants#setQrCode(QRCode)} method, and retrieved using
 * the {@link Constants#getQrCode()} method.
 * @author yasminghaznavian
 */
public class Constants {

    private static QRCode qrCode;

    /**
     * Returns the {@link QRCode} object that is stored in this class
     */
    public static QRCode getQrCode() {
        return qrCode;
    }

    /**
     * Sets the {@link QRCode} object that is stored in this class.
     *
     * @param qrCode The {@link QRCode} object to store in this class.
     */
    public static void setQrCode(QRCode qrCode) {
        Constants.qrCode = qrCode;
    }
}

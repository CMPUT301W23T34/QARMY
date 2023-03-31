package com.example.QArmy.UI.qrcodes;

import com.example.QArmy.model.QRCode;

/**
 * This class provides a way to store and retrieve a single {@link QRCode} object
 * The {@link QRCode} object can be set using the {@link Constants#setQrCode(QRCode)} method, and retrieved using
 * the {@link Constants#getQrCode()} method.
 * @author yasminghaznavian
 * A class for handling constants, treating variables as if they were global.
 * @author Yasmin Ghaznavian
 */
public class Constants {

    private static QRCode qrCode;

    /**
     * Returns the {@link QRCode} object that is stored in this class
     * Gets the QRCode stored as a constant
     * @return The QR Code stored as a constant
     */
    public static QRCode getQrCode() {
        return qrCode;
    }

    /**
     * Sets the {@link QRCode} object that is stored in this class.
     *
     * @param qrCode The {@link QRCode} object to store in this class.
     * Sets the QRCode to be stored as a constant
     * @param qrCode The QR Code to be stored as a constant
     */
    public static void setQrCode(QRCode qrCode) {
        Constants.qrCode = qrCode;
    }
}

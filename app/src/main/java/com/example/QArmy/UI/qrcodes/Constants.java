package com.example.QArmy.UI.qrcodes;

import com.example.QArmy.model.QRCode;

/**
 * A class for handling constants, treating variables as if they were global.
 * @author Yasmin Ghaznavian
 */
public class Constants {

    private static QRCode qrCode;

    /**
     * Gets the QRCode stored as a constant
     * @return The QR Code stored as a constant
     */
    public static QRCode getQrCode() {
        return qrCode;
    }

    /**
     * Sets the QRCode to be stored as a constant
     * @param qrCode The QR Code to be stored as a constant
     */
    public static void setQrCode(QRCode qrCode) {
        Constants.qrCode = qrCode;
    }
}

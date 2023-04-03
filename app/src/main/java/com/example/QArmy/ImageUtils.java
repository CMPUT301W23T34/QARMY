package com.example.QArmy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Utility class for encoding and decoding images using Base64 encoding.
 */
public class ImageUtils {
    /**
     * Encodes a Bitmap image to a Base64-encoded String.
     *
     * @param image the Bitmap image to encode
     * @return a Base64-encoded String representing the image
     */
    public static String encodeToBase64(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * Decodes a Base64-encoded image String to a Bitmap.
     *
     * @param encodedString the Base64-encoded image String
     * @return the decoded Bitmap image
     */
    public static Bitmap decodeFromBase64(String encodedString) {
        byte[] decodedByteArray = Base64.decode(encodedString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

}


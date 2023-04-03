package com.example.QArmy.UI.profile;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.QArmy.model.User;

/**
 * Represents the user data that is stored in the phone.
 * This data allows the user to skip the registration page after they have already registered.
 * @author Jessica Emereonye
 * @author Kai Luedemann
 * @author Brett Merkosky
 */
public class MySharedPreferences {
    private static final String USER_PROFILE_PREFS = "user_profile_prefs";
    private static final String QR_CODE_PREFS = "qr_code_prefs";
    private static final String DEVICE_ID_PREFERENCE = "device_id_prefs";

    private static final String COMMENTS_PREFS = "comments_prefs";


    /**
     * Saves the users profile to their phone
     *
     * @param context The context of the application
     * @param user    The (current) User who's data will be saved to the phone
     */
    public static void saveUserProfile(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PROFILE_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("phone", user.getPhone());
        editor.putString("score", String.valueOf(user.getScore()));

        editor.apply();
    }

    /**
     * Loads the user's profile from the phone
     *
     * @param context The context of the application
     * @return The User who's data is stored in the phone
     */
    public static User loadUserProfile(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PROFILE_PREFS, Context.MODE_PRIVATE);

        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        String phone = sharedPreferences.getString("phone", "");
        int score = sharedPreferences.getInt("score", -1);
        return new User(name, email, phone, score);
    }

    /**
     * Saves a QRCode to the phone (currently unused)
     *
     * @param context
     * @param qrCode
     */
    public static void saveQRCode(Context context, String qrCode) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(QR_CODE_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("qrCode", qrCode);
        editor.apply();
    }

    /**
     * Loads a QRCode from the phone (currently unused)
     *
     * @param context
     * @return qrcode
     */
    public static String loadQRCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(QR_CODE_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString("qrCode", "");
    }

    /**
     * Gets the device ID of the phone
     *
     * @param applicationContext The context of the application
     * @return The (String) deviceID of the phone
     */
    public static Object getDeviceID(Context applicationContext) {
        return applicationContext.getSharedPreferences(DEVICE_ID_PREFERENCE, Context.MODE_PRIVATE).getString("deviceId", "");
    }

    /**
     * Loads the user's name from the phone
     *
     * @param context The context of the application
     * @return The name of the current user stored in the phone
     */
    public static String loadUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PROFILE_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", "");
    }
}

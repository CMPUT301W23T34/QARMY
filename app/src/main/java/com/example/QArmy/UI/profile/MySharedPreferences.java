package com.example.QArmy.UI.profile;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.QArmy.model.User;

public class MySharedPreferences {
    private static final String USER_PROFILE_PREFS = "user_profile_prefs";
    private static final String DEVICE_ID_PREFERENCE = "device_id_prefs";

    public static void saveUserProfile(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PROFILE_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("phone", user.getPhone());
        editor.putString("score", String.valueOf(user.getScore()));
        editor.putString("uniqueID", user.getUniqueID());

        editor.apply();
    }

    public static User loadUserProfile(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PROFILE_PREFS, Context.MODE_PRIVATE);

        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        String phone = sharedPreferences.getString("phone", "");
        String score = sharedPreferences.getString("score", "");
        String uniqueID = sharedPreferences.getString("uniqueID", "");
        return new User(name, email, phone, score, uniqueID);
    }

    public static Object getDeviceID(Context applicationContext) {
        return applicationContext.getSharedPreferences(DEVICE_ID_PREFERENCE, Context.MODE_PRIVATE).getString("deviceId", "");
    }
}

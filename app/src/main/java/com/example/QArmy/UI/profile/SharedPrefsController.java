/*
 * SharedPrefsController
 *
 * Version: 1.1
 *
 * Date: 2023-03-23
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */
package com.example.QArmy.UI.profile;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.QArmy.model.User;

public class SharedPrefsController {
    private static final String USER_PROFILE_PREFS = "user_profile_prefs";

    private SharedPreferences sharedPrefs;

    public SharedPrefsController(Context context) {
        if (context != null) {
            sharedPrefs = context.getSharedPreferences(USER_PROFILE_PREFS, Context.MODE_PRIVATE);
        }
    }

    /**
     * Saves the users profile to their phone
     * @param user The (current) User who's data will be saved to the phone
     */
    public void saveUser(User user) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("phone", user.getPhone());
        editor.putInt("newScore", user.getScore());
        editor.apply();
    }

    /**
     * Loads the user's profile from the phone
     * @return The User who's data is stored in the phone
     */
    public User loadUser() {
        String name = sharedPrefs.getString("name", "");
        String email = sharedPrefs.getString("email", "");
        String phone = sharedPrefs.getString("phone", "");
        int score = sharedPrefs.getInt("newScore", -1);
        return new User(name, email, phone, score);
    }
}

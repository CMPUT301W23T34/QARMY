package com.example.QArmy;

import android.app.Application;

import com.example.QArmy.UI.profile.MySharedPreferences;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.User;

/**
 * Represents the entire app
 * @author Brett Merkosky
 * @author Kai Luedemann
 * @author Jessica Emereonye
 * @author Yasmin Ghaznavian
 * @author Japkirat Kaur
 * @author Nicholas Mellon
 */
public class QArmy extends Application {
    private User user;
    private Database database;

    @Override
    public void onCreate() {
        super.onCreate();
        user = MySharedPreferences.loadUserProfile(this);
        database = new Database();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Database getDatabase() {
        return database;
    }
}

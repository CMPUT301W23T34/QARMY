package com.example.QArmy;

import android.app.Application;

import com.example.QArmy.UI.profile.MySharedPreferences;
import com.example.QArmy.model.User;

public class QArmy extends Application {
    private User user;

    @Override
    public void onCreate() {
        super.onCreate();
        user = MySharedPreferences.loadUserProfile(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.example.QArmy;

import android.app.Application;

import com.example.QArmy.UI.profile.MySharedPreferences;
import com.example.QArmy.model.AppContainer;
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
    //private User user;
    public AppContainer model;

    @Override
    public void onCreate() {
        super.onCreate();
        model = createModel();
        //user = MySharedPreferences.loadUserProfile(this);
    }

    public AppContainer createModel() {
        return new AppContainer();
    }

    public User getUser() {
        return model.user;
    }

    public void setUser(User user) {
        model.user = user;
    }
}

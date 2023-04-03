package com.example.QArmy.UI.profile;

import com.example.QArmy.model.User;

public interface RegistrationListener {
    void onAdded(User user);
    void onExists();
    void onError(Exception e);
}

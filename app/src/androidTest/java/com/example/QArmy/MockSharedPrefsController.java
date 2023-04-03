package com.example.QArmy;

import com.example.QArmy.UI.profile.SharedPrefsController;
import com.example.QArmy.model.User;

public class MockSharedPrefsController extends SharedPrefsController {
    public MockSharedPrefsController() {
        super(null);
    }

    @Override
    public User loadUser() {
        return new User("test");
    }

    @Override
    public void saveUser(User user) {

    }
}

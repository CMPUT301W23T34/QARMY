package com.example.QArmy;

import com.example.QArmy.UI.profile.SharedPrefsController;
import com.example.QArmy.model.User;

public class MockSharedPrefsController extends SharedPrefsController {

    private User user = new User("test");
    public MockSharedPrefsController() {
        super(null);
    }

    @Override
    public User loadUser() {
        return user;
    }

    @Override
    public void saveUser(User user) {
        this.user = user;
    }
}

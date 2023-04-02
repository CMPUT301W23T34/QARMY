package com.example.QArmy;

import com.example.QArmy.db.Database;
import com.example.QArmy.model.AppContainer;
import com.example.QArmy.model.User;

public class MockContainer extends AppContainer {
    public MockContainer() {
        user = new User("test");
        prefsController = new MockSharedPrefsController();
        db = new Database(true);
    }
}

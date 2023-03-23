package com.example.QArmy;

import com.example.QArmy.model.AppContainer;

public class MockApp extends QArmy {

    @Override
    public AppContainer createModel() {
        return new MockContainer();
    }
}

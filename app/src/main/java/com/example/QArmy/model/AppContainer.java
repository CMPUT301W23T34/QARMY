/*
 * AppContainer
 *
 * Version: 1.0
 *
 * Date: 2023-03-23
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */
package com.example.QArmy.model;


import com.example.QArmy.UI.profile.SharedPrefsController;
import com.example.QArmy.db.Database;

/**
 * Store the model classes for the app.
 * Enable dependency injection for android components.
 * @version 1.0
 * @author Kai Luedemann
 */
public class AppContainer {
    public User user = new User("kai");
    public Database db = new Database();
    public QRList qrList = new QRList();
    public PlayerList playerList = new PlayerList();
    public SharedPrefsController prefsController;
}

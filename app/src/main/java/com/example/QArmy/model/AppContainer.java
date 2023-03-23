package com.example.QArmy.model;


import com.example.QArmy.db.Database;

public class AppContainer {
    public User user = new User("kai");
    public Database db = new Database();
    public QRList qrList = new QRList();
    public PlayerList playerList = new PlayerList();
}

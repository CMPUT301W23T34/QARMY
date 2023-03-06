package com.example.QArmy.model;

public class User extends Model {

    public static final String SCORE_FIELD = "score";
    private String name;

    public User(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public String getID() {
        return this.name;
    }

    public int getScore() {
        return 100;
    }
}

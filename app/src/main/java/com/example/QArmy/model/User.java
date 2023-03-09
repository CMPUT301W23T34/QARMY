/*
 * User
 *
 * Version: 1.0
 *
 * Date: 2023-03-09
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy.model;

/**
 * Represent a user of the app.
 * @author Kai Luedemann
 * @version 1.0
 */
public class User extends Entity {

    public static final String SCORE_FIELD = "score";
    private String name;

    /**
     * Initialize a user
     * @param name The unique username
     */
    public User(String name) {
        this.name = name;
    }

    /* *********************************** Getters ************************************************/

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

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

import java.util.HashMap;
import java.util.Map;

/**
 * Represent a user of the app.
 * @author Kai Luedemann
 * @version 1.0
 */
public class User extends Entity {

    public static final String SCORE_FIELD = "score";

    private int score;
    private String name;
    private String email;
    private String phone;

    public User() {

    }

    public User(String testUsername) {
        this.name = testUsername;
    }

    /**
     * Initialize a user
     * @param name The unique username
     */

    public User(String name, String email, String phone) {
        this.name = name;

        this.email = email;

        this.phone = phone;

        this.score = 0;

    }

    public User(String name, String email, String phone, int score) {
        this.name = name;

        this.email = email;

        this.phone = phone;

        this.score = score;

    }

    /* *********************************** Getters ************************************************/


    public String getName() {
        return this.name;
    }

    public String getID() {
        return this.name;
    }

    public int getScore() {
        return score;
    }
    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    // Add this method to get the User object as a Map
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("email", email);
        result.put("phone", phone);
        return result;
    }

    // Add this method to create a User object from a Map
    public static User fromMap(Map<String, Object> map) {
        String name = (String) map.get("name");
        String email = (String) map.get("email");
        String phone = (String) map.get("phone");
        return new User(name, email, phone);
    }

    public void setScore(int score) {
        this.score = score;
    }
}
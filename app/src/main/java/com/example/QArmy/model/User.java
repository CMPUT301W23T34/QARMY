package com.example.QArmy.model;

import java.util.HashMap;
import java.util.Map;

public class User extends Entity {

    public static final String SCORE_FIELD = "score";
    private String name;
    private String email;
    private String phone;
    private String score;
    private String uniqueID;


    public User(String name, String email, String phone) {
        this.name = name;

        this.email = email;

        this.phone = phone;

    }

    public User(String name, String email, String phone, String score, String uniqueID) {
        this.name = name;

        this.email = email;

        this.phone = phone;

        this.score = score;

        this.uniqueID = uniqueID;

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
    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUniqueID() {return uniqueID;}

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
}
package com.example.QArmy.model;

public class UserComments {

    public UserComments(String username, String textMessage) {
        this.username = username;
        this.textMessage = textMessage;
    }

    private String username;
    private String textMessage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}

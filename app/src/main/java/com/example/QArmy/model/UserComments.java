package com.example.QArmy.model;

/**
 * Represent a comment

 * Contains username and message of the comment, and timestamp
 * @author Yasmin Ghaznavian
 * @author Jessica Emereonye
 */
public class UserComments extends Entity {
    private String username;
    private String textMessage;
    private String id;

    public UserComments(String username, String textMessage, String id) {
        this.username = username;
        this.textMessage = textMessage;
        this.id = id;
    }

    /**
     * Get the username of the person who made a comment
     *
     * @return The user who made the comment
     */
    public String getUser() {
        return username;
    }

    /**
     * Set the username of a person who made a comment
     *
     * @param username The new username of the person who wrote the comment
     */
    public void setUser(String username) {
        this.username = username;
    }

    /**
     * Get the text of a comment
     *
     * @return The text of the comment
     */
    public String getText() {
        return textMessage;
    }

    /**
     * Set the text of a comment
     *
     * @param textMessage The text the comment should be set to
     */
    public void setText(String textMessage) {
        this.textMessage = textMessage;
    }

    /**
     * Get the id of a comment
     *
     * @return The id of the comment
     */
    public String getID(){return id;}

    public void setID(String id) {
        this.id = id;
    }

    public UserComments() {
        // empty constructor required for deserialization
    }
}
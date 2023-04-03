/*
 * CommentList
 *
 * Version: 1.0
 *
 * Date: 2023-04-02
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy.model;

import com.example.QArmy.TModel;
import com.example.QArmy.TView;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class storing a list of comments belonging to a QRCode.
 * @author Kai Luedemann
 * @version 1.0
 */
public class CommentList extends TModel<TView> {
    private final ArrayList<Comment> comments;

    /**
     * Initialize an empty list.
     */
    public CommentList() {
        comments = new ArrayList<>();
    }

    /**
     * Add a comment to the list.
     * @param comment The comment to add
     */
    public void add(Comment comment) {
        comments.add(0, comment);
        notifyViews();
    }

    /**
     * Remove a comment from the list.
     * @param comment The comment to remove
     */
    public void remove(Comment comment) {
        comments.remove(comment);
        notifyViews();
    }

    /**
     * Refresh the list from a list of comments.
     * @param newComments The queried list of comments
     */
    public void modify(List<Comment> newComments) {
        comments.clear();
        comments.addAll(newComments);
        notifyViews();
    }

    /**
     * Return a reference to the list of comments.
     * @return The list of comments.
     */
    public ArrayList<Comment> getList() {
        return comments;
    }
}

/*
 * Database
 *
 * Version: 1.0
 *
 * Date: 2023-03-05
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 *  - Google Developers, 2023-03-03, Get Data with Cloud Firestore,
 * https://firebase.google.com/docs/firestore/query-data/get-data
 */

package com.example.QArmy.db;

import com.example.QArmy.model.Comment;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * This class provides access to the Firestore database and performs queries.
 * @author Kai Luedemann
 * @version 1.0
 * @see FirebaseFirestore
 */
public class Database {

    private final CollectionReference QR_CODES;
    private final CollectionReference PLAYERS;
    private final CollectionReference COMMENTS;

    /**
     * Initialize the database.
     */
    public Database() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        QR_CODES = db.collection("QRCodes");
        PLAYERS = db.collection("Players");
        COMMENTS = db.collection("Comments");
    }

    // ************************* QR Code Queries *******************************

    /**
     * Get the QR codes scanned by a given user.
     * @param user - the user to query QR codes for
     * @param listener - provides a callback when query is complete
     */
    public void getUserCodes(User user, DBListener<QRCode> listener) {
        QR_CODES.whereEqualTo(QRCode.USER_FIELD, user.getName())
                .get()
                .addOnCompleteListener(new DBHelper<>(listener, QRCode.class));
    }

    /**
     * Get QR codes around a given location
     * NOT YET IMPLEMENTED!
     * @param lat
     * @param lon
     * @param listener
     */
    public void getNearbyCodes(double lat, double lon, DBListener<QRCode> listener) {
        // TODO: Implement location queries
    }

    /**
     * Add a QR code to the database
     * @param qrCode - the QR code to add
     * @param listener - provides a callback when query is complete
     */
    public void addQRCode(QRCode qrCode, DBListener<QRCode> listener) {
        QR_CODES.document(qrCode.getHash())
                .set(qrCode)
                .addOnSuccessListener(new DBHelper<>(listener, QRCode.class));
    }

    /**
     * Delete a QR code from the database
     * @param qrCode - the QR code to add
     * @param listener - provides a callback when query is complete
     */
    public void deleteQRCode(QRCode qrCode, DBListener<QRCode> listener) {
        QR_CODES.document(qrCode.getID())
                .delete()
                .addOnSuccessListener(new DBHelper<>(listener, QRCode.class));
    }

    // ************************* Comment Queries *******************************

    /**
     * Add a comment to the database.
     * @param comment - the comment to add
     * @param listener - provides a callback when query is complete
     */
    public void addComment(Comment comment, DBListener<Comment> listener) {
        COMMENTS.document(comment.getID())
                .set(comment)
                .addOnSuccessListener(new DBHelper<>(listener, Comment.class));
    }

    /**
     * Delete a comment to the database.
     * @param comment - the comment to delete
     * @param listener - provides a callback when query is complete
     */
    public void deleteComment(Comment comment, DBListener<Comment> listener) {
        COMMENTS.document(comment.getID())
                .delete()
                .addOnSuccessListener(new DBHelper<>(listener, Comment.class));
    }

    /**
     * Get the comments posted to a given QR code.
     * @param qrCode - the QR code to get comments for
     * @param listener - provides a callback when query is complete
     */
    public void getQRComments(QRCode qrCode, DBListener<Comment> listener) {
        COMMENTS.whereEqualTo(Comment.CODE_FIELD, qrCode.getHash())
                .get()
                .addOnCompleteListener(new DBHelper<>(listener, Comment.class));
    }

    // ************************* User Queries **********************************

    /**
     * Get users who have scanned a given QR code.
     * @param qrCode - the QR code to get users from
     * @param listener - provides a callback when query is complete
     */
    public void getQRUsers(QRCode qrCode, DBListener<User> listener) {
        // Query QR codes that match hash
        QR_CODES.whereEqualTo(QRCode.CODE_FIELD, qrCode.getHash())
                .get()
                .addOnCompleteListener(task -> {
                    // Collect user IDs
                    ArrayList<String> users = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        users.add((String) doc.get(QRCode.USER_FIELD));
                    }

                    // Query users from DB
                    PLAYERS.whereIn(User.ID_FIELD, users)
                            .get()
                            .addOnCompleteListener(new DBHelper<>(listener, User.class));
                });
    }

    /**
     * Add a user to the database.
     * @param user - the user to add
     * @param listener - provides a callback when query is complete
     */
    public void addUser(User user, DBListener<User> listener) {
        PLAYERS.document(user.getID())
                .set(user)
                .addOnSuccessListener(new DBHelper<>(listener, User.class));
    }
}

/*
 * Database
 *
 * Version: 1.3
 *
 * Date: 2023-03-06
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 *  - Google Developers, 2023-03-03, Get Data with Cloud Firestore,
 * https://firebase.google.com/docs/firestore/query-data/get-data
 * - Jon Skeet, 2008-11-12, https://stackoverflow.com/a/285184, Stack Overflow
 */

package com.example.QArmy.db;

import static java.lang.Math.min;

import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * This class provides access to the Firestore database and performs queries.
 *
 * @author Kai Luedemann
 * @version 1.3
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
        this(false);
    }

    public Database(boolean isTest) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (!isTest) {
            QR_CODES = db.collection("QRCodes");
            PLAYERS = db.collection("Players");
            COMMENTS = db.collection("Comments");
        } else {
            QR_CODES = db.collection("TestQRCodes");
            PLAYERS = db.collection("TestPlayers");
            COMMENTS = db.collection("TestComments");
        }
    }

    // ************************* QR Code Queries *******************************

    /**
     * Get the QR codes scanned by a given user.
     *
     * @param user     - the user to query QR codes for
     * @param listener - provides a callback when query is complete
     */
    public void getUserCodes(User user, QueryListener<QRCode> listener) {
        QR_CODES.whereEqualTo(QRCode.USER_FIELD, user.getName())
                .orderBy(QRCode.TIME_FIELD, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new QueryHelper<>(listener, QRCode.class));
    }

    /**
     * Get QR codes around a given location
     *
     * @param listener
     */
    public void getNearbyCodes(QueryListener<QRCode> listener) {
        QR_CODES.get()
                .addOnCompleteListener(new QueryHelper<>(listener, QRCode.class));
    }

    /**
     * Add a QR code to the database
     *
     * @param qrCode   - the QR code to add
     * @param listener - provides a callback when query is complete
     */
    public void addQRCode(QRCode qrCode, OnCompleteListener<Void> listener) {
        QR_CODES.document(qrCode.getID())
                .set(qrCode)
                .addOnCompleteListener(listener);
    }

    /**
     * Delete a QR code from the database
     *
     * @param qrCode   - the QR code to add
     * @param listener - provides a callback when query is complete
     */
    public void deleteQRCode(QRCode qrCode, OnCompleteListener<Void> listener) {
        QR_CODES.document(qrCode.getID())
                .delete()
                .addOnCompleteListener(listener);
    }

    // ************************* User Queries **********************************

    /**
     * Get users who have scanned a given QR code.
     * If more than 10 users have scanned the code, only the first 10 are returned
     *
     * @param listener - provides a callback when query is complete
     */
    public void getQRUsersByHash(String qrCodeHash, QueryListener<User> listener) {
        // Query QR codes that match hash
        QR_CODES.whereEqualTo(QRCode.CODE_FIELD, qrCodeHash)
                .get()
                .addOnCompleteListener(task -> {
                    // Collect user IDs
                    ArrayList<String> users = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        users.add((String) doc.get(QRCode.USER_FIELD));
                    }

                    // Query users from DB
                    // We cannot query on a list of more than 10 users
                    PLAYERS.whereIn(User.ID_FIELD, users.subList(0, min(users.size(), 10)))
                            .get()
                            .addOnCompleteListener(new QueryHelper<>(listener, User.class));

                    // This will solve the crash even if list is empty
                    if (!users.isEmpty())
                        // Query users from DB
                        PLAYERS.whereIn(User.ID_FIELD, users)
                                .get()
                                .addOnCompleteListener(new QueryHelper<>(listener, User.class));
                });
    }

    /**
     * Add a user to the database.
     *
     * @param user     - the user to add
     * @param listener - provides a callback when query is complete
     */
    public void addUser(User user, OnCompleteListener<Void> listener) {
        PLAYERS.document(user.getID())
                .set(user)
                .addOnCompleteListener(listener);
    }

    /**
     * Delete a user from the database
     * @param listener - provides a callback when the query is complete
     */
    public void deleteUser(User user, OnCompleteListener<Void> listener) {
        PLAYERS.document(user.getID())
                .delete()
                .addOnCompleteListener(listener);
    }

    public void getUser(User user, QueryListener<User> listener) {
        PLAYERS.whereEqualTo(User.ID_FIELD, user.getID())
                .get()
                .addOnCompleteListener(new QueryHelper<>(listener, User.class));
    }

    /**
     * Get the list of users ordered by rank.
     *
     * @param listener - provides a callback when the query is complete
     */
    public void getRankedUsers(QueryListener<User> listener) {
        PLAYERS.whereGreaterThanOrEqualTo(User.SCORE_FIELD, 0)
                .orderBy(User.SCORE_FIELD, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new QueryHelper<>(listener, User.class));
    }

    /**
     * Return the rank of the user based on total score.
     *
     * @param user     - the user to get the rank of
     * @param listener - provides a callback when the query is complete
     */
    public void getRank(User user, AggregateListener listener) {
        // TODO: Change to max score
        PLAYERS.whereGreaterThanOrEqualTo(User.SCORE_FIELD, user.getScore())
                .count()
                .get(AggregateSource.SERVER)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess(task.getResult().getCount());
                    } else {
                        listener.onFailure(task.getException());
                    }
                });
    }
}

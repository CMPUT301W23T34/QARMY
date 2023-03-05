package com.example.QArmy.db;


import com.example.QArmy.Comment;
import com.example.QArmy.QRCode;
import com.example.QArmy.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Database {

    private final CollectionReference QR_CODES;
    private final CollectionReference PLAYERS;
    private final CollectionReference COMMENTS;

    public Database() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        QR_CODES = db.collection("QRCodes");
        PLAYERS = db.collection("Players");
        COMMENTS = db.collection("Comments");
    }

    public void getUserCodes(User user, DBListener<QRCode> listener) {
        QR_CODES.whereEqualTo("user", user.getName())
                .get()
                .addOnCompleteListener(new DBHelper<>(listener, QRCode.class));
    }

    public void addQRCode(QRCode qrCode, DBListener<QRCode> listener) {
        QR_CODES.document(qrCode.getHash())
                .set(qrCode)
                .addOnSuccessListener(new DBHelper<>(listener, QRCode.class));
    }

    public void deleteQRCode(QRCode qrCode, DBListener<QRCode> listener) {
        QR_CODES.document(qrCode.getID())
                .delete()
                .addOnSuccessListener(new DBHelper<>(listener, QRCode.class));
    }

    public void addComment(Comment comment, DBListener<Comment> listener) {
        COMMENTS.document(comment.getID())
                .set(comment)
                .addOnSuccessListener(new DBHelper<>(listener, Comment.class));
    }

    public void deleteComment(Comment comment, DBListener<Comment> listener) {
        COMMENTS.document(comment.getID())
                .delete()
                .addOnSuccessListener(new DBHelper<>(listener, Comment.class));
    }

    public void getQRComments(QRCode qrCode, DBListener<Comment> listener) {
        COMMENTS.whereEqualTo("code", qrCode.getHash())
                .get()
                .addOnCompleteListener(new DBHelper<>(listener, Comment.class));
    }

    public void getNearbyCodes(double lat, double lon, DBListener<QRCode> listener) {
        // TODO: Implement location queries
    }

    public void getQRUsers(QRCode qrCode, DBListener<User> listener) {
        QR_CODES.whereEqualTo("code", qrCode.getHash())
                .get()
                .addOnCompleteListener(task -> {
            ArrayList<String> users = new ArrayList<>();
            for (QueryDocumentSnapshot doc : task.getResult()) {
                users.add((String) doc.get("user"));
            }
            PLAYERS.whereIn("__name__", users)
                    .get()
                    .addOnCompleteListener(new DBHelper<>(listener, User.class));
        });
    }

    public void addUser(User user, DBListener<User> listener) {
        PLAYERS.document(user.getID())
                .set(user)
                .addOnSuccessListener(new DBHelper<>(listener, User.class));
    }
}

/*
 * CommentController
 *
 * Version: 1.0
 *
 * Date: 2023-04-02
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy.UI.qrcodes;

import androidx.annotation.NonNull;

import com.example.QArmy.db.Database;
import com.example.QArmy.model.Comment;
import com.example.QArmy.model.CommentList;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Manage comments for a given QR Code.
 * @version 1.0
 * @author Kai Luedemann
 */
public class CommentController {

    private final Database db;
    private final CommentList comments;
    private final QRCode qrCode;
    private final User user;
    private final CommentListener listener;

    /**
     * Initialize the CommentController.
     * @param db The database to query
     * @param comments The list of comments
     * @param qrCode The QRCode that the comments belong to
     * @param user The user who is in the app
     */
    public CommentController(Database db, CommentList comments, QRCode qrCode, User user) {
        this.db = db;
        this.comments = comments;
        this.qrCode = qrCode;
        this.user = user;
        listener = new CommentListener();
    }

    /**
     * Query the comments from the database and update the CommentList.
     */
    public void getComments() {
        db.getComments(qrCode, listener);
    }

    /**
     * Add a comment to the database.
     * @param comment The comment to add
     */
    public void addComment(Comment comment) {
        db.addComment(qrCode, comment, task -> {
            if (task.isSuccessful()) {
                comment.setID(task.getResult().getId());
                comments.add(comment);
            }
        });
    }

    /**
     * Delete a comment from the database.
     * @param comment The comment to delete.
     */
    public void deleteComment(Comment comment) {
        if (comment.getUser().equals(user.getName())) {
            db.deleteComment(qrCode, comment, task -> {
                if (task.isSuccessful()) {
                    comments.remove(comment);
                }
            });
        }
    }

    /**
     * Listen for query completion and add comments to the list.
     */
    class CommentListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                ArrayList<Comment> commentsList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Comment comment = doc.toObject(Comment.class);
                    comment.setID(doc.getId());
                    commentsList.add(comment);
                }
                Collections.reverse(commentsList);
                comments.modify(commentsList);
            }
        }
    }


}

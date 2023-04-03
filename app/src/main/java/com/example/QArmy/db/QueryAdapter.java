/*
 * QueryAdapter
 *
 * Version: 1.0
 *
 * Date: 2023-03-09
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy.db;

import androidx.annotation.NonNull;

import com.example.QArmy.model.Entity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Intermediate listener to convert query result into list of entities
 * @param <T> The entity returned from the query
 * @author Kai Luedemann
 * @version 1.0
 * @see QueryListener
 */
public class QueryAdapter<T extends Entity> implements OnCompleteListener<QuerySnapshot> {

    private final QueryListener<T> listener;
    private final Class<T> type;

    /**
     * Initialize the QueryAdapter
     * @param listener The QueryListener to call when the query returns
     * @param type The type of entity returned from the query
     */
    public QueryAdapter(QueryListener<T> listener, Class<T> type) {
        this.listener = listener;
        this.type = type;
    }

    /**
     * Convert the query snapshot to list of entities and perform callback on listener.
     * @param task The query result obtained from the Firestore database
     */
    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            List<DocumentSnapshot> docs = task.getResult().getDocuments();
            List<T> objs = new ArrayList<>();
            for (DocumentSnapshot doc : docs) {
                objs.add(doc.toObject(type));
            }
            listener.onSuccess(objs);
        } else {
            listener.onFailure(task.getException());
        }
    }
}
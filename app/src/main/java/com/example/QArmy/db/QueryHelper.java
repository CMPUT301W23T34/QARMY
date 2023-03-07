package com.example.QArmy.db;

import androidx.annotation.NonNull;

import com.example.QArmy.model.Entity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class QueryHelper<T extends Entity> implements OnCompleteListener<QuerySnapshot> {

    private final QueryListener<T> listener;
    private final Class<T> type;

    public QueryHelper(QueryListener<T> listener, Class<T> type) {
        this.listener = listener;
        this.type = type;
    }
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
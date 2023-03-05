package com.example.QArmy.db;

import androidx.annotation.NonNull;

import com.example.QArmy.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBHelper<T extends Model> implements OnCompleteListener<QuerySnapshot>, OnSuccessListener<Void> {

    private final DBListener<T> listener;
    private final Class<T> type;

    public DBHelper(DBListener<T> listener, Class<T> type) {
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
            listener.onListQuery(objs);
        } else {
            listener.onFailure(task.getException());
        }
    }

    @Override
    public void onSuccess(Void unused) {
        listener.onSuccess();
    }
}
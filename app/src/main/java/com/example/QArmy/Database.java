package com.example.QArmy;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class Database {

    private final CollectionReference QR_CODES;
    private final CollectionReference SCANS;
    private final CollectionReference PLAYERS;
    private final CollectionReference COMMENTS;

    public Database() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        QR_CODES = db.collection("QRCodes");
        SCANS = db.collection("Scans");
        PLAYERS = db.collection("Players");
        COMMENTS = db.collection("Comments");
    }

    public void registerUserCodes(String user, OnSuccessListener<QuerySnapshot> listener) {

        SCANS.whereEqualTo("user", user).addSnapshotListener((value, error) -> {
            ArrayList<String> ids = new ArrayList<>();
            for (QueryDocumentSnapshot doc : value) {
                ids.add((String) doc.get("code"));
            }
            if (!ids.isEmpty()) {
                QR_CODES.whereIn("__name__", ids).get().addOnSuccessListener(listener);
            } else {
                listener.onSuccess(value);
            }
        });
    }

    public void addQRCode(QRCode qrCode, OnSuccessListener<Void> success, OnFailureListener failure) {
        QR_CODES.document(qrCode.getHash())
                .set(qrCode.getHashMap())
                .addOnSuccessListener(success)
                .addOnFailureListener(failure);
    }

    public void deleteScan(String user, QRCode qrCode) {
        SCANS.whereEqualTo("user", user)
                .whereEqualTo("code", qrCode.getHash()).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        doc.getReference().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("Database", "DELETED");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Database", "Error deleting!");
                            }
                        });
                    }
                });
    }
}

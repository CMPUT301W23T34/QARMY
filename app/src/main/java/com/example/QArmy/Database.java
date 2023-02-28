package com.example.QArmy;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Database {

    private static final String QR_CODE = "QRCodes";
    private FirebaseFirestore db;

    public Database() {
        db = FirebaseFirestore.getInstance();
    }

    public void registerQRCodes(EventListener<QuerySnapshot> listener) {
        db.collection(QR_CODE).addSnapshotListener(listener);
    }

    public void addQRCode(QRCode qrCode, OnSuccessListener<Void> success, OnFailureListener failure) {
        db.collection(QR_CODE).document(qrCode.getHash())
                .set(qrCode.getHashMap())
                .addOnSuccessListener(success)
                .addOnFailureListener(failure);
    }
}

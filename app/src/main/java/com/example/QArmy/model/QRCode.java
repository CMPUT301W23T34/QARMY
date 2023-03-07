package com.example.QArmy.model;

import android.media.Image;
import android.util.Log;

import com.example.QArmy.PlayerProfile;
import com.example.QArmy.QrVisual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QRCode extends Model {

    public static final String CODE_FIELD = "code";
    public static final String USER_FIELD = "user";
    private ArrayList<Float> geoLocations;
    private ArrayList<Image> images;
    private String qrName;
    private QrVisual qrMonster;
    private int qrScore;
    private ArrayList<Comment> qrComments;
    private ArrayList<PlayerProfile> qrScanners;
    private String hash;
    private String ID;

    private String user;

    public QRCode(String qrHash, ArrayList<Float> geoLocations, ArrayList<Image> images, PlayerProfile currentScanner) {
        this.geoLocations = geoLocations;
        this.images = images;
        this.qrName = generateName(qrHash);
        this.qrMonster = generateVisual(qrHash);
        this.qrScore = generateScore(qrHash);
        this.qrComments = new ArrayList<Comment>();
        this.qrScanners = new ArrayList<PlayerProfile>();
        qrScanners.add(currentScanner);
        this.hash = qrHash;
    }

    public QRCode(String qrHash, Map<String, Object> data) {
        this.hash = qrHash;
        this.qrName = (String) data.get("name");
        this.qrScore = Math.toIntExact((Long) data.get("score"));
    }

    public QRCode() {

    }

    private String generateName(String qrHash) {
        // TODO: Implement this
        // The rank should be based on the score of the QR code
        Log.d("QRCODE", "CALLED");
        return qrHash;
    }
    private QrVisual generateVisual(String qrHash) {
        // TODO: Implement this
        Log.d("QRCODE", "CALLED");
        return null;
    }
    private int generateScore(String qrHash) {
        // TODO: Implement this
        Log.d("QRCODE", "CALLED");
        return 100;
    }


    private void seeComments() {
        // TODO: Implement this
        return;
    }
    private void seeScanners() {
        // TODO: Implement this
        return;
    }

    // TODO: Make getters and setters for everything
    public String getName() {
        return this.qrName;
    }
    public int getScore() {
        return this.qrScore;
    }

    public HashMap getHashMap() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", qrName);
        data.put("score", qrScore);
        return data;
    }

    public String getHash() {
        return hash;
    }

    public String getID() {
        // TODO: CAN'T be hash if we switch model
        return qrName;
    }

    public String getUser() {
        return user;
    }

    public void setName(String name) {
        this.qrName = name;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setScore(int score) {
        this.qrScore = score;
    }


}

package com.example.QArmy;

import android.media.Image;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QRCode {
    private ArrayList<Float> geoLocations;
    private ArrayList<Image> images;
    private String qrName;
    private QrVisual qrMonster;
    private int qrScore;
    private ArrayList<Comment> qrComments;
    private ArrayList<PlayerProfile> qrScanners;
    private String hash;

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
        this.hash = hash;
        this.qrName = (String) data.get("name");
        this.qrScore = Math.toIntExact((Long) data.get("score"));
    }

    private String generateName(String qrHash) {
        // TODO: Implement this
        // The rank should be based on the score of the QR code
        return qrHash;
    }
    private QrVisual generateVisual(String qrHash) {
        // TODO: Implement this
        return null;
    }
    private int generateScore(String qrHash) {
        // TODO: Implement this
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
}

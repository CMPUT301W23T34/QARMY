package com.example.QArmy.model;

import android.media.Image;
import android.util.Log;

import com.example.QArmy.PlayerProfile;
import com.example.QArmy.QrVisual;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Map;

import java.security.NoSuchAlgorithmException;

public class QRCode extends Model {

    public static final String CODE_FIELD = "code";
    public static final String USER_FIELD = "user";
    private String qrHashHex;
    private ArrayList<Float> geoLocations;
    private ArrayList<Image> images;
    private int qrScore;
    private String qrName;
    private QrVisual qrMonster;
    private ArrayList<Comment> qrComments;
    private ArrayList<PlayerProfile> qrScanners;
    private String ID;

    private String user;


    public QRCode(String qrData, ArrayList<Float> geoLocations, ArrayList<Image> images, PlayerProfile currentScanner) {
        try {
            this.qrHashHex = convertByteArrayToHexString(hashStringToBytes(qrData));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }

        this.geoLocations = geoLocations;
        this.images = images;
        this.qrScore = generateScore(qrHashHex.toCharArray());
        this.qrName = generateName(qrHashHex.toCharArray(), qrScore);
        this.qrMonster = generateVisual(qrHashHex);
        this.qrComments = new ArrayList<Comment>();
        this.qrScanners = new ArrayList<PlayerProfile>();
        qrScanners.add(currentScanner);
    }

    public QRCode(String qrHash, Map<String, Object> data) {
        this.qrHashHex = qrHash;
        this.qrName = (String) data.get("name");
        this.qrScore = Math.toIntExact((Long) data.get("score"));
    }

    public QRCode() {

    }


    // THE FOLLOWING TWO FUNCTIONS COME FROM: https://www.geeksforgeeks.org/sha-256-hash-in-java/ and https://www.baeldung.com/sha-256-hashing-java
    // TODO: Deal with all the licensing stuff for these functions
    private byte[] hashStringToBytes(String inputString) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(inputString.getBytes(StandardCharsets.UTF_8));
    }

    private String convertByteArrayToHexString(byte[] byteArray) {
        StringBuilder hexString = new StringBuilder(2 * byteArray.length);
        for (int i = 0; i < byteArray.length; i++) {
            String hex = Integer.toHexString(0xff & byteArray[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private String generateName(char[] qrHashHex, int qrScore) {
        String newName = "";
        // The military rank is based on the score of the QR code
        if (qrScore < 10) {
            newName += "Private ";
        } else if (qrScore < 20) {
            newName += "Corporal ";
        } else if (qrScore < 50) {
            newName += "Sergeant ";
        } else if (qrScore < 100) {
            newName += "Lieutenant ";
        } else if (qrScore < 250) {
            newName += "Captain ";
        } else if (qrScore < 500) {
            newName += "Major ";
        } else if (qrScore < 1000) {
            newName += "Colonel ";
        } else {
            newName += "General ";
        }

        // The 2,4,...14 split up the 16 possible values evenly into 8 options for the rest of the name.
        int nextHex = Integer.parseInt(String.valueOf(qrHashHex[0]), 16);
        if (nextHex < 2) {
            newName += "Purple";
        } else if (nextHex < 4) {
            newName += "Maroon";
        } else if (nextHex < 6) {
            newName += "Orange";
        } else if (nextHex < 8) {
            newName += "Cyan";
        } else if (nextHex < 10) {
            newName += "Green";
        } else if (nextHex < 12) {
            newName += "Blue";
        } else if (nextHex < 14) {
            newName += "Yellow";
        } else {
            newName += "Red";
        }

        nextHex = Integer.parseInt(String.valueOf(qrHashHex[1]), 16);
        if (nextHex < 2) {
            newName += "Little";
        } else if (nextHex < 4) {
            newName += "Petite";
        } else if (nextHex < 6) {
            newName += "Hefty";
        } else if (nextHex < 8) {
            newName += "Big";
        } else if (nextHex < 10) {
            newName += "Thicc";
        } else if (nextHex < 12) {
            newName += "Tall";
        } else if (nextHex < 14) {
            newName += "Lanky";
        } else {
            newName += "Stubby";
        }

        nextHex = Integer.parseInt(String.valueOf(qrHashHex[2]), 16);
        if (nextHex < 2) {
            newName += "Kind";
        } else if (nextHex < 4) {
            newName += "Humble";
        } else if (nextHex < 6) {
            newName += "Cocky";
        } else if (nextHex < 8) {
            newName += "Noble";
        } else if (nextHex < 10) {
            newName += "Malicious";
        } else if (nextHex < 12) {
            newName += "Sleepy";
        } else if (nextHex < 14) {
            newName += "Angry";
        } else {
            newName += "Sad";
        }

        nextHex = Integer.parseInt(String.valueOf(qrHashHex[3]), 16);
        if (nextHex < 2) {
            newName += "Dog";
        } else if (nextHex < 4) {
            newName += "Panda";
        } else if (nextHex < 6) {
            newName += "Ant";
        } else if (nextHex < 8) {
            newName += "Feline";
        } else if (nextHex < 10) {
            newName += "Slug";
        } else if (nextHex < 12) {
            newName += "Sloth";
        } else if (nextHex < 14) {
            newName += "Salmon";
        } else {
            newName += "Bird";
        }
        return newName;
    }

    private QrVisual generateVisual(String qrHash) {
        // TODO: Implement this
        Log.d("QRCODE", "CALLED");
        return null;
    }

    private int generateScore(char[] qrHashHex) {
        // TODO: Implement this

        int prevHex = -1;
        int newHex = -1;
        int score = 0;
        int addToScore = 0;
        for (char hexChar : qrHashHex) {
            newHex = Integer.parseInt(String.valueOf(hexChar), 16);
            if (newHex == prevHex) {
                // Same digit as before: Get rid of the score we just added (if the number repeats 3+ times)
                score -= addToScore;
                // First time the number has been repeated
                if (addToScore == 0) {
                    addToScore = 1;
                }
                if (newHex == 0) {
                    score -= 1; // Remove the 1 we added when we assumed it was the only 0
                    addToScore *= 20;
                } else {
                    addToScore *= newHex;
                }
                // Add to the score, assuming this is the last time the number appears
                score += addToScore;

            } else {
                // We changed digits
                addToScore = 0;
            }
            prevHex = newHex;
        }
        return score;
    }


    private void seeComments() {
        // TODO: Implement this
        return;
    }

    private void seeScanners() {
        // TODO: Implement this
        return;
    }

    // Setters
    // TODO: Decide whether we need to keep all of these getters (ex getHash())
    public String getHash() {
        return this.qrHashHex;
    }
    public ArrayList<Float> getGeoLocations() {
        return this.geoLocations;
    }
    public int getScore() {
        return this.qrScore;
    }

    public String getID() {
        return this.qrName;
    }

    public String getName() {
        return this.qrName;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setName(String name) {
        this.qrName = name;
    }

    public void setScore(int score) {
        this.qrScore = score;
    }
    public QrVisual getVisual() {
        return this.qrMonster;
    }
    public ArrayList<Comment> getComments() {
        return this.qrComments;
    }
    public ArrayList<PlayerProfile> getScanners() {
        return this.qrScanners;
    }

    // Setters
    public void addGeoLocation(Float geolocation) {
        this.geoLocations.add(geolocation);
    }
    public void removeGeoLocation(Float geolocation) {
        this.geoLocations.remove(geolocation);
    }
    public void addImage(Image image) {
        this.images.add(image);
    }
    public void removeImage(Image image) {
        this.images.remove(image);
    }
    public void addComment(Comment comment) {
        this.qrComments.add(comment);
    }
    public void removeComment(Comment comment) {
        this.qrComments.remove(comment);
    }
    public void addScanner(PlayerProfile scanner) {
        this.qrScanners.add(scanner);
    }
    public void removeScanner(PlayerProfile scanner) {
        this.qrScanners.remove(scanner);
    }
}


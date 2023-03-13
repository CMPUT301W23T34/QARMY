/*
 * QRCode
 *
 * Version: 1.0
 *
 * Date: 2023-03-09
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 * - Bilal Hungund, 2022-04-29, https://www.geeksforgeeks.org/sha-256-hash-in-java/, Geeks for Geeks
 * - baeldung, 2022-02-28, https://www.baeldung.com/sha-256-hashing-java, Baeldung
 */

package com.example.QArmy.model;

import android.location.Location;
import android.media.Image;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;

import java.security.NoSuchAlgorithmException;

/**
 * Represent a QR Code instance.
 * Contains user, scan info, and code info.
 * @author Brett Merkosky
 * @author Kai Luedemann
 * @version 1.0
 */
public class QRCode extends Entity {
    // TODO: Improve cohesion
    public static final String CODE_FIELD = "hash";
    public static final String USER_FIELD = "user";

    public static final String TIME_FIELD = "timestamp";
    private String hash;
    private double lat;
    private double lon;
    private Image image;
    private int score;
    private String name;
    private QrVisual qrMonster;
    private long timestamp;
    private String user;

    /**
     * Construct a new QR Code upon scanning.
     * @param qrData The hex string representing the code
     * @param user The name of the user who scanned it
     * @param location The location where it was scanned
     * @param timestamp The timestamp when it was scanned
     */
    public QRCode(String qrData, User user, Location location, Date timestamp) {
        try {
            this.hash = convertByteArrayToHexString(hashStringToBytes(qrData));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }

        this.score = generateScore(hash.toCharArray());
        this.name = generateName(hash.toCharArray(), score);
        this.qrMonster = generateVisual(hash);
        this.user = user.getName();
        if (location != null) {
            this.lat = location.getLatitude();
            this.lon = location.getLongitude();
        }
        this.timestamp = timestamp.getTime();
    }

    /**
     * Initialize an empty QR code.
     * Called by Firestore toObject()
     */
    public QRCode() {

    }

    /**
     * Hash a string to a byte array using SHA256
     *
     * From: <a href="https://www.geeksforgeeks.org/sha-256-hash-in-java/">...</a>
     * and <a href="https://www.baeldung.com/sha-256-hashing-java">...</a>
     *
     * @param inputString The string to hash
     * @return The hashed byte array
     * @throws NoSuchAlgorithmException Invalid hash algorithm
     */
    private byte[] hashStringToBytes(String inputString) throws NoSuchAlgorithmException {
        // TODO: Deal with all the licensing stuff for these functions
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(inputString.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Convert byte array to hex string.
     *
     * From: <a href="https://www.geeksforgeeks.org/sha-256-hash-in-java/">...</a>
     * and <a href="https://www.baeldung.com/sha-256-hashing-java">...</a>
     *
     * @param byteArray The byte array to convert
     * @return The hex string
     */
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

    /**
     * Generate name from hash hex.
     * Not guaranteed to be unique.
     * @param qrHashHex The hash of the QR code
     * @param qrScore The score of the QR code
     * @return The name of the QR code
     */
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

    /**
     * Create the visual representation of the QR code.
     * Not yet implemented.
     * @param qrHash The QR code hash string
     * @return The visual representation of the QR code
     */
    private QrVisual generateVisual(String qrHash) {
        // TODO: Implement this
        //Log.d("QRCODE", "CALLED");
        return null;
    }

    /**
     * Calculate the score for the QR code.
     * Based on the algorithm presented in the project description.
     * @param qrHashHex The QR code hash
     * @return The score
     */
    private int generateScore(char[] qrHashHex) {

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

    /* ************************************* Getters **********************************************/
    // Anything stored in the database needs a getter and a setter
    // TODO: Decide whether we need to keep all of these getters (ex getHash())
    public String getHash() {
        return this.hash;
    }
    public int getScore() {
        return this.score;
    }

    @Override
    public String getID() {
        return this.user+this.hash;
    }

    public String getName() {
        return this.name;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public double getLat() {
        return this.lat;
    }

    public QrVisual getVisual() {
        return this.qrMonster;
    }

    public String getUser() {
        return this.user;
    }

    public double getLon() {
        return this.lon;
    }

    // TODO: Refactor tests to not require this. e.g. MockCode
    public void setScore(int score) {this.score = score;}
}


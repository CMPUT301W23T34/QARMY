package com.example.QArmy.model;

import java.util.ArrayList;
import java.util.Observable;

public class QRList {
    private ArrayList<QRCode> codes;
    private int total;
    private int count;
    private int max;
    private int min;


    public QRList() {
        super();
    }

    public ArrayList<QRCode> getList() {
        return codes;
    }


}

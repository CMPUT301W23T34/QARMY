package com.example.QArmy;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestQRCode {
    @Test
    public void testGenerateScore(){
        QRCode testQRCode = new QRCode("BFG5DGW54\n", null, null, null);
        System.out.println("Final score:");
        System.out.println(testQRCode.getScore());

        for (int i = 0; i < 100; i++) {
            String s = String.valueOf(i);
            testQRCode = new QRCode(s, null, null, null);
            System.out.println(s+": Hash: "+ testQRCode.getQrHashHex());
            System.out.println("Name: "+testQRCode.getName());
            System.out.println("Score: "+testQRCode.getScore()+"\n");

        }
    }
}
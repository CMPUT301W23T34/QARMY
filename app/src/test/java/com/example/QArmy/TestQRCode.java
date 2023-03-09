package com.example.QArmy;

import static org.junit.Assert.assertEquals;

import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestQRCode {

    @Test
    public void testGenerateHash() {
        QRCode testQRCode = new QRCode("BFG5DGW54\n", new User(""));
        // The hash should be
        assertEquals("696ce4dbd7bb57cbfe58b64f530f428b74999cb37e2ee60980490cd9552de3a6", testQRCode.getHash());
    }
    @Test
    public void testGenerateScore(){
        QRCode testQRCode = new QRCode("BFG5DGW54\n", new User(""));
        // Based on the proposed scoring system, this QRCode should have a score of 111
        assertEquals(111,testQRCode.getScore());
    }
    @Test
    public void testGenerateName() {
        QRCode testQRCode = new QRCode("BFG5DGW54\n", new User(""));
        // Based on the naming system and hash, this QRCode should have the following name
        String intendedName = "Captain CyanThiccNobleSalmon";
        assertEquals(intendedName,testQRCode.getName());
    }
    @Test
    public void testGenerateID() {
        User testUser = new User("TestUsername");
        QRCode testQRCode = new QRCode("BFG5DGW54\n", testUser);
        // Based on the hash and user, the QRCode should have the following ID:
        String codeHash = testQRCode.getHash();
        String intendedID = testUser.getName() + codeHash;
        assertEquals(intendedID, testQRCode.getID());
    }
}
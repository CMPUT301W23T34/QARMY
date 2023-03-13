package com.example.QArmy;

import static org.junit.Assert.assertEquals;

import com.example.QArmy.model.PlayerList;
import com.example.QArmy.model.User;

import org.junit.Test;

public class TestPlayerList {
    @Test
    public void testAddPlayer() {
        PlayerList testPlayerList = new PlayerList();
        User testPlayer = new User("Test", "", "");
        assertEquals(0, testPlayerList.getList().size());
        testPlayerList.addPlayer(testPlayer);
        assertEquals(1, testPlayerList.getList().size());
    }

}

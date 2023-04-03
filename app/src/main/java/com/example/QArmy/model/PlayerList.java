package com.example.QArmy.model;

import com.example.QArmy.TModel;
import com.example.QArmy.TView;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class storing the list of users.
 * @author Nicholas Mellon
 */
public class PlayerList extends TModel<TView> {
    private ArrayList<User> players;

    /**
     * Construct a new (empty) PlayerList
     */
    public PlayerList() {

        players = new ArrayList<>();
    }

    /**
     * Get the ArrayList of players in the PlayerList
     * @return The ArrayList of players in the list
     */
    public ArrayList<User> getList() {
        return players;
    }

    /**
     * Add a user to the PlayerList
     * @param player The player to be added
     */
    public void addPlayer(User player) {
        players.add(player);
        notifyViews();
    }

    /**
     * Reset the players in the PlayerList to a new list of users
     * @param newPlayers The new ArrayList of players to be stored in the PlayerList
     */
    public void modifyPlayer(List<User> newPlayers) {
        players.clear();
        players.addAll(newPlayers);
        notifyViews();
    }

    /**
     * Remove a user from the PlayerList
     * @param player The player to be removed
     */
    public void removePlayer(User player) {
        players.remove(player);
        notifyViews();
    }

}

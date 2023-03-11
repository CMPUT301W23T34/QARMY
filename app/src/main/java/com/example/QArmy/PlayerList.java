package com.example.QArmy;

import com.example.QArmy.TModel;
import com.example.QArmy.TView;
import com.example.QArmy.model.User;
import java.util.ArrayList;
import java.util.List;

public class PlayerList extends TModel<TView> {
    private ArrayList<User> players;


    public PlayerList() {

        players = new ArrayList<>();
    }

    public ArrayList<User> getList() {
        return players;
    }


    public void addPlayer(User player) {
        players.add(player);
        notifyViews();
    }
    public void modifyPlayer(List<User> newPlayers) {
        players.clear();
        players.addAll(newPlayers);
        notifyViews();
    }

    public void removePlayer(User player) {
        players.remove(player);
        notifyViews();
    }

}

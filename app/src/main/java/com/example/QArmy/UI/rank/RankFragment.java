package com.example.QArmy.UI.rank;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.QArmy.QArmy;
import com.example.QArmy.UI.OtherUserProfileActivity;
import com.example.QArmy.model.AppContainer;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.PlayerList;
import com.example.QArmy.R;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.User;

import java.util.ArrayList;
import java.util.List;

public class RankFragment extends Fragment{


    private Database db;
    private User user;
    private RankListener listener;

    private PlayerList playerList;

    private PlayerList userList;

    private PlayerList searchedList;

    private SearchView searchView;



    public RankFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppContainer appContainer = ((QArmy) getActivity().getApplication()).model;
        playerList = appContainer.playerList;
        db = appContainer.db;

        listener = new RankListener();
        user = appContainer.user;

        userList = new PlayerList();

        // list when search is being made
        searchedList = new PlayerList();
        searchView = getView().findViewById(R.id.search_text);

        // player list from database
        ListView rankList = getView().findViewById(R.id.rank_list);
        PlayerArrayAdapter playerArrayAdapter = new PlayerArrayAdapter(getContext(), playerList, db);
        rankList.setAdapter(playerArrayAdapter);
        playerList.addView(playerArrayAdapter);


        // for user at bottom
        ListView userRank = getView().findViewById(R.id.user_rank);
        PlayerArrayAdapter userArrayAdapter = new PlayerArrayAdapter(getContext(), userList, db);
        userRank.setAdapter(userArrayAdapter);
        userList.addView(userArrayAdapter);
        userList.addPlayer(user);

        rankList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                User userClicked = (User)adapterView.getItemAtPosition(position);
                Intent otherUserProfileIntent = new Intent(getContext(), OtherUserProfileActivity.class);
                otherUserProfileIntent.putExtra("user", userClicked);
                startActivity(otherUserProfileIntent);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {

                searchedList.getList().clear();
                ArrayList<User> players = playerList.getList();
                for (int i = 0; i < players.size(); i++ )
                {
                    User u = players.get(i);
                    if(u.getName().toLowerCase().contains(s.toLowerCase())){
                        searchedList.addPlayer(u);
                        //u.setRank(i+1);
                    }
                }

                PlayerArrayAdapter searchedArrayAdapter = new PlayerArrayAdapter(getContext(), searchedList, db);
                rankList.setAdapter(searchedArrayAdapter);

                return false;
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();
        db.getRankedUsers(listener);
    }

    class RankListener implements QueryListener<User> {

        @Override
        public void onSuccess(List<User> data) {
            playerList.modifyPlayer(data);
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setRank(i+1);
                if (data.get(i).getID().equals(user.getID())) {
                    user.setRank(i+1);
                }
            }
            userList.removePlayer(user);
            userList.addPlayer(user);
        }

        @Override
        public void onFailure(Exception e) {

        }
    }

}

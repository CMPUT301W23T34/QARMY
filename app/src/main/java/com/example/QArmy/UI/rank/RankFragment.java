package com.example.QArmy.UI.rank;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.QArmy.QArmy;
import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.model.AppContainer;
import com.example.QArmy.model.PlayerList;
import com.example.QArmy.R;
import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.User;

import java.util.List;

public class RankFragment extends Fragment{


    private Database db;
    private User user;
    private RankListener listener;
    private PlayerList playerList;

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

//        playerList = new PlayerList();
//        db = new Database();
//        listener = new RankListener();
//        user = ((MainActivity) getActivity()).getUser();

        ListView rankList = getView().findViewById(R.id.rank_list);
        PlayerArrayAdapter playerArrayAdapter = new PlayerArrayAdapter(getContext(), playerList, db);
        rankList.setAdapter(playerArrayAdapter);
        playerList.addView(playerArrayAdapter);



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
        }

        @Override
        public void onFailure(Exception e) {

        }
    }

}

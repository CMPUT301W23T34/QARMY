package com.example.QArmy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;


import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.User;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class RankFragment extends Fragment{


    private Database db;
    private User user;
    private RankListener listener;
    private PlayerList playerList;

    public RankFragment(){

        db = new Database();
        listener = new RankListener();
        user = new User("kai");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerList = new PlayerList();

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

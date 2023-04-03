/*
 * PlayerArrayAdapter
 *
 * Version: 1.1
 *
 * Date: 2023-04-03
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 * - Brett Merkosky, MyCarFootprint
 */
package com.example.QArmy.UI.rank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.QArmy.db.Database;
import com.example.QArmy.db.AggregateListener;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.PlayerList;
import com.example.QArmy.R;
import com.example.QArmy.TView;
import com.example.QArmy.model.User;

import java.util.List;
import java.util.Locale;

/**
 * Provide views to represent Players in a ListView.
 * @author Nicholas Mellon
 * @version 1.1
 */
public class PlayerArrayAdapter extends ArrayAdapter<User> implements TView<PlayerList> {
    private PlayerList playerList;
    private UserRankListener listener;
    private long rank;
    private Database db;

    /**
     * Initialize the array adapter
     * @param context
     * @param playerList the list of players
     * @param db the database to query
     */
    public PlayerArrayAdapter(Context context, PlayerList playerList, Database db) {
        super(context, 0, playerList.getList());
        this.db = db;
        this.playerList = playerList;
        listener = new UserRankListener();
    }

    /**
     * Creates a view to display the list of Players
     * Note: Code modified from CMPUT 301 Assignment 1 - MyCarFootprint
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.player_data,
                    parent, false);
        } else {
            view = convertView;
        }
        User player = getItem(position);
        TextView playerName = view.findViewById(R.id.player_name);
        TextView playerScore = view.findViewById(R.id.player_score);

        if (player.getScore() == -1) {
            playerName.setText("N/A. "+player.getName());
            playerScore.setText("Score: N/A");
        } else {
            playerName.setText(String.format(Locale.CANADA, "%d. %s", player.getRank(), player.getName()));
            playerScore.setText("Score: " + Integer.toString(player.getScore()));
        }


        return view;
    }

    /**
     * Listen for the completion of a query to the database and update the
     * model with the new data.
     */
    class UserRankListener implements AggregateListener {
        @Override
        public void onSuccess(long data) {
            rank = data;
        }

        @Override
        public void onFailure(Exception e) {

        }
    }

    /**
     * Notify the listview when the model state has changed.
     * @param model The model whose state has changed
     */
    @Override
    public void update(PlayerList model) {
        notifyDataSetChanged();
    }
}

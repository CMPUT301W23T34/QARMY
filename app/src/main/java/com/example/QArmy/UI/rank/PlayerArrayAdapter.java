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


public class PlayerArrayAdapter extends ArrayAdapter<User> implements TView<PlayerList> {
    private PlayerList playerList;
    private UserRankListener listener;
    private long rank;
    private Database db;
    public PlayerArrayAdapter(Context context, PlayerList playerList, Database db) {
        super(context, 0, playerList.getList());
        this.db = db;
        this.playerList = playerList;
        listener = new UserRankListener();
    }

    // Creates a view to display the list of Players
    // TODO: Clean up this function (I just copied it out of the MyCarFootprint app and changed the variable names)
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

    class UserRankListener implements AggregateListener {
        @Override
        public void onSuccess(long data) {
            rank = data;
        }

        @Override
        public void onFailure(Exception e) {

        }
    }

    @Override
    public void update(PlayerList model) {
        notifyDataSetChanged();
    }
}

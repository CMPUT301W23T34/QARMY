package com.example.QArmy;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class PlayerArrayAdapter extends ArrayAdapter<User> implements TView<PlayerList>{
    private PlayerList playerList;
    private Database db;
    public PlayerArrayAdapter(Context context, PlayerList playerList, Database db) {
        super(context, 0, playerList.getList());
        this.db = db;
        this.playerList = playerList;
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

        playerName.setText(player.getName());
        playerScore.setText("Score: "+Integer.toString(player.getScore()));

        return view;
    }

    @Override
    public void update(PlayerList model) {
        notifyDataSetChanged();
    }
}

package com.example.QArmy.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QArmy.R;
import com.example.QArmy.model.User;

import java.util.List;

/**
 * The type User view adapter.
 */
public class UserViewAdapter extends RecyclerView.Adapter<UserViewAdapter.ViewHolder> {

    private List<User> mData;
    private LayoutInflater mInflater;
    private Context context;

    /**
     * Instantiates a new User view adapter.
     *
     * @param context the context
     * @param data    the data
     */
// data is passed into the constructor
    public UserViewAdapter(Context context, List<User> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_users, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.nameTextView.setText(mData.get(position).getName());
        holder.emailTextView.setText(mData.get(position).getEmail());
        holder.phoneTextView.setText(mData.get(position).getPhone());
        holder.scoreTextView.setText("Score: " + mData.get(position).getScore());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        else return 0;
    }

    /**
     * Gets item.
     *
     * @param id the id
     * @return the item
     */
// convenience method for getting data at click position
    public User getItem(int id) {
        return mData.get(id);
    }


    /**
     * The type View holder.
     */
// stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * The Name text view.
         */
        TextView nameTextView;
        /**
         * The Email text view.
         */
        TextView emailTextView;
        /**
         * The Phone text view.
         */
        TextView phoneTextView;
        /**
         * The Score text view.
         */
        TextView scoreTextView;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }
}
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
import com.example.QArmy.model.UserComments;

import java.util.ArrayList;

/**
 * The type Services recycler view adapter.
 * @author yasminghaznavian
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private ArrayList<UserComments> mData;
    private LayoutInflater mInflater;
    private Context context;

    /**
     * Instantiates a new Event recycler view adapter.
     *
     * @param context the context
     * @param data    the data
     */
// data is passed into the constructor
    public CommentsAdapter(Context context, ArrayList<UserComments> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.user_comments_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.titleTextView.setText(mData.get(position).getUsername());
        holder.descTextView.setText(mData.get(position).getTextMessage());

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
    public UserComments getItem(int id) {
        return mData.get(id);
    }


    /**
     * The type View holder.
     */
// stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {


        /**
         * The Title text view.
         */
        TextView titleTextView;
        /**
         * The Desc text view.
         */
        TextView descTextView;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descTextView = itemView.findViewById(R.id.descTextView);
        }
    }
}

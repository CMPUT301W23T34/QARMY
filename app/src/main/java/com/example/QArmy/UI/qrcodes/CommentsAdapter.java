/*
 * CommentsAdapter
 *
 * Version: 1.1
 *
 * Date: 2023-04-02
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy.UI.qrcodes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QArmy.R;
import com.example.QArmy.TView;
import com.example.QArmy.model.Comment;
import com.example.QArmy.model.CommentList;
import com.example.QArmy.model.User;

import java.util.ArrayList;

/**
 * The type Services recycler view adapter.
 * @author Yasmin Ghaznavian
 * @author Jessica Emereonye
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> implements TView<CommentList> {

    private final ArrayList<Comment> mData;
    private final LayoutInflater mInflater;
    private final CommentController controller;
    private final User user;


    /**
     * Instantiates a new Event recycler view adapter.
     *
     * @param context the context
     * @param data    the data
     * @param controller The CommentController
     */


   // data is passed into the constructor
    public CommentsAdapter(Context context, ArrayList<Comment> data, CommentController controller, User user) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.controller = controller;
        this.user = user;
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

        holder.titleTextView.setText(mData.get(position).getUser());
        holder.descTextView.setText(mData.get(position).getText());

        Comment comment = mData.get(position);

        if (user.getName().equals(comment.getUser())) {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setOnClickListener(v -> controller.deleteComment(comment));
        } else {
            holder.deleteButton.setVisibility(View.GONE);
        }


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
    public Comment getItem(int id) {
        return mData.get(id);
    }


    /**
     * The type View holder.
     */
// stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * The delete Button.
         */
        ImageButton deleteButton;

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
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }
    }

    /**
     * Update listview when model changes.
     * @param model The model whose state has changed
     */
    @Override
    public void update(CommentList model) {
        notifyDataSetChanged();
    }
}

package com.example.QArmy.UI.qrcodes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.QArmy.R;
import com.example.QArmy.UI.profile.MySharedPreferences;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.UserComments;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

/**
 * The type Services recycler view adapter.
 * @author Yasmin Ghaznavian
 * @author Jessica Emereonye
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private ArrayList<UserComments> mData;
    private LayoutInflater mInflater;
    private Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference qrCodesCollection = db.collection("QRCodes");
    private String qrCodeID;


    /**
     * Instantiates a new Event recycler view adapter.
     *
     * @param context the context
     * @param data    the data
     */

   // data is passed into the constructor
    public CommentsAdapter(Context context, ArrayList<UserComments> data, String id) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        this.qrCodeID = id;
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
         * The delete Button.
         */
        Button deleteButton;

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
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        UserComments comment = mData.get(position);
                        String currentUser = MySharedPreferences.loadUserName(context);
                        deleteComment(comment, currentUser);
                    }
                }
            });

        }
    }

    // allow users to delete their comments
    public void deleteComment(UserComments comment, String currentUser) {
        if (comment != null) {
            if (comment.getUsername().equals(currentUser)) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference qrCodesCollection = db.collection("QRCodes").document(qrCodeID).collection("Comments");
                qrCodesCollection.document(comment.getId()).delete();
                mData.remove(comment);
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "You can only delete your own comments.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Cannot delete null comment.", Toast.LENGTH_SHORT).show();
        }
    }
}

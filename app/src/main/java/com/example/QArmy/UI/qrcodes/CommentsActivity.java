package com.example.QArmy.UI.qrcodes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QArmy.R;
import com.example.QArmy.model.UserComments;

import java.util.ArrayList;

/**
 * The type Comments activity.
 */
public class CommentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ArrayList<UserComments> comments = new ArrayList<>();

        // the three comments
        comments.add(new UserComments("Username", "Wow! This is a great QR code. Very easy to find" +
                "and worth tons of points"));
        comments.add(new UserComments("abramhindle301", "Only 542 points. Not worth your time"));

        CommentsAdapter adapter = new CommentsAdapter(getApplicationContext(), comments);
        recyclerView.setAdapter(adapter);

    }
}


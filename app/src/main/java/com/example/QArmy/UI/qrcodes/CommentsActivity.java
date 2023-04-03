/*
 * CommentsActivity
 *
 * Version: 1.2
 *
 * Date: 2023-04-02
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy.UI.qrcodes;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QArmy.QArmy;
import com.example.QArmy.R;
import com.example.QArmy.model.AppContainer;
import com.example.QArmy.model.Comment;
import com.example.QArmy.model.CommentList;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;


/**
 * The type Comments activity.
 * @author Yasmin Ghaznavian
 * @author Jessica Emereonye
 * @version 1.2
 */
public class CommentsActivity extends AppCompatActivity {
    private EditText add_comment;
    private Button submit_button;
    private User user;
    private QRCode qrCode;
    private CommentList commentList;
    private CommentController controller;

    /**
     * Oncreate method is called when the activity is created so we can set
     * the content view
     * @param savedInstanceState The saved state
     */ 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        setSupportActionBar(findViewById(R.id.comments_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        AppContainer model = ((QArmy) getApplication()).model;
        user = model.user;

        add_comment = findViewById(R.id.commentEditText);
        submit_button = findViewById(R.id.submitButton);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        commentList = new CommentList();

        Intent intent = getIntent();
        qrCode = (QRCode) intent.getSerializableExtra("QRCode");

        controller = new CommentController(model.db, commentList, qrCode, user);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        CommentsAdapter adapter = new CommentsAdapter(getApplicationContext(), commentList.getList(), controller, user);
        recyclerView.setAdapter(adapter);
        commentList.addView(adapter);

        controller.getComments();

        submit_button.setOnClickListener(view -> {
            String commentText = add_comment.getText().toString();
            if (!TextUtils.isEmpty(commentText)) {
                Comment comment = new Comment(user.getName(), commentText, null);
                controller.addComment(comment);
                add_comment.setText("");
            }
        });

    }

    /**
     * Finish activity and return to previous.
     * @return True - event handled.
     */
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

package com.example.QArmy.UI.qrcodes;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.QArmy.R;
import com.example.QArmy.UI.profile.MySharedPreferences;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.UserComments;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Comments activity.
 * @author Yasmin Ghaznavian
 * @author Jessica Emereonye
 */
public class CommentsActivity extends AppCompatActivity {
    private EditText add_comment;
    private Button submit_button;
    private Button back_button;
    private FirebaseFirestore db;
    private CollectionReference qrCodesCollection;
    private DocumentReference qrCodesDocument;

    /**
     * Oncreate method is called when the activity is created so we can set
     * the content view
     * @param savedInstanceState
     */ 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        add_comment = findViewById(R.id.commentEditText);
        submit_button = findViewById(R.id.submitButton);
        back_button = findViewById(R.id.backButton);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String data = intent.getStringExtra("Object");
        qrCodesDocument = db.collection("QRCodes").document(data);
        qrCodesCollection = qrCodesDocument.collection("Comments");

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<UserComments> comments = new ArrayList<>();
        CommentsAdapter adapter = new CommentsAdapter(getApplicationContext(), comments, qrCodesDocument.getId());
        recyclerView.setAdapter(adapter);

        // get current comments from database and display
        qrCodesCollection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<UserComments> comments = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String user = document.getString("user");
                                String text = document.getString("text");
                                String id = document.getId();
                                comments.add(new UserComments(user, text, id));
                            }
                            Collections.reverse(comments);
                            CommentsAdapter adapter = new CommentsAdapter(getApplicationContext(), comments, qrCodesDocument.getId());
                            recyclerView.setAdapter(adapter);
                        } else {
                            // error
                        }
                    }
                });

        qrCodesDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Context context = getApplicationContext();
                        String username = MySharedPreferences.loadUserName(context);
                        submit_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String commentText = add_comment.getText().toString();
                                if (!TextUtils.isEmpty(commentText)) {
                                    UserComments comment = new UserComments(username, commentText,null);
                                    comments.add(0, comment);
                                    adapter.notifyItemInserted(0);
                                    recyclerView.scrollToPosition(0);
                                    add_comment.setText("");
                                    Map<String, Object> commentData = new HashMap<>();
                                    commentData.put("user", username);
                                    commentData.put("text", commentText);

                                    qrCodesCollection.add(commentData);
                                    qrCodesCollection.get()

                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    ArrayList<UserComments> comments = new ArrayList<>();
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        String user = document.getString("user");
                                                        String text = document.getString("text");
                                                        String id = document.getId();
                                                        comments.add(new UserComments(user, text, id));
                                                    }
                                                    Collections.reverse(comments);

                                                    CommentsAdapter adapter = new CommentsAdapter(getApplicationContext(), comments, qrCodesDocument.getId());
                                                    recyclerView.setAdapter(adapter);
                                                } else {
                                                    // error
                                                }
                                            }
                                        });
                                }
                            }
                        });
                    }
                }
            }
        });

        // set the back button for users
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

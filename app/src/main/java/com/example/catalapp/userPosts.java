package com.example.catalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class userPosts extends AppCompatActivity {

    FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase dbs = FirebaseDatabase.getInstance();
    DatabaseReference mRef = dbs.getReference();

    RecyclerView rViewMyPost;
    rViewMyPostAdapt rViewMyPostAdapt;
    List<Details> detailsList;

    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v-> finish());

        Bundle ext = getIntent().getExtras();
        if (ext != null){
            userName = ext.getString("user");
        }

        rViewMyPost = findViewById(R.id.rViewMyPost);
        detailsList = new ArrayList<>();
        rViewMyPostAdapt = new rViewMyPostAdapt(this, detailsList, userName);
        rViewMyPost.setAdapter(rViewMyPostAdapt);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rViewMyPost.setLayoutManager(linearLayoutManager);
        if (userName != null){
            mRef.child("contents").addValueEventListener(new ValueEventListener() {
                String username;
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    detailsList.clear();
                    rViewMyPostAdapt.notifyDataSetChanged();

                    for (DataSnapshot datasnapshot : snapshot.getChildren()){
                        Details details = datasnapshot.getValue(Details.class);
                        assert details != null;
                        username = details.getUserName();

                        if(userName.equals(username)) {
                            detailsList.add(details);
                        }

                        rViewMyPostAdapt.notifyDataSetChanged();
                        linearLayoutManager.smoothScrollToPosition(rViewMyPost,null,rViewMyPost.getAdapter().getItemCount());

                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }else if (fbUser != null){
            mRef.child("contents").addValueEventListener(new ValueEventListener() {
                final String userID = fbUser.getUid();
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    detailsList.clear();
                    rViewMyPostAdapt.notifyDataSetChanged();

                    for (DataSnapshot datasnapshot : snapshot.getChildren()){
                        Details details = datasnapshot.getValue(Details.class);
                        assert details != null;
                        String contentUID = details.getFbUserId();

                        if(userID.equals(contentUID)) {
                            detailsList.add(details);
                        }

                        rViewMyPostAdapt.notifyDataSetChanged();
                        linearLayoutManager.smoothScrollToPosition(rViewMyPost,null,rViewMyPost.getAdapter().getItemCount());

                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }
}
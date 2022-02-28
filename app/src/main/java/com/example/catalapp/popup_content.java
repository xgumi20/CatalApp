package com.example.catalapp;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class popup_content extends AppCompatActivity {

    FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase adb = FirebaseDatabase.getInstance();
    DatabaseReference mRef = adb.getReference();

    TextView authName, userNamePop ,caption, category;

    String userName, contentID, currentDP;
    EditText commentInput;
    ImageView sendInputComment;

    List<commentSend> commentSendList;

    ConstraintLayout rlayout;
    rViewComments rViewComments;
    RecyclerView rViewCom;
    Boolean commentShow = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_content);

        Bundle ext = getIntent().getExtras();
        userName = ext.getString("currentUser");
        contentID = ext.getString("contentID");
        currentDP = ext.getString("currentDisplayName");
        String name = ext.getString("author");
        String capt = ext.getString("capt");
        String categ = ext.getString("categ");
        String uNamePop = ext.getString("uname");
        String key = ext.getString("key");
        String uID = null;

        if (currentDP == null){
            mRef.child("login").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot loginsnapshot) {
                    currentDP = loginsnapshot.child("user").child(userName).child("dpname").getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

        if (fbUser != null) {
            uID = fbUser.getUid();
        }
        ImageView back = findViewById(R.id.back);
        ImageView like = findViewById(R.id.likeContentPopup);
        ImageView dislike = findViewById(R.id.dislikeContentPopup);
        ImageView comment = findViewById(R.id.commentsContentPopup);

        rlayout = findViewById(R.id.commentDropDisplay);
        commentInput = findViewById(R.id.commentData);
        sendInputComment = findViewById(R.id.submitData);

        String finalUID = uID;

        back.setOnClickListener(v-> {finish();});

        like.setOnClickListener(v->{
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (fbUser==null) {
                        if (snapshot.child("contentsReact").child(key).child("likes").hasChild(userName)) {
                            like.setImageResource(R.drawable.ic_baseline_thumb_up_24_black);
                            mRef.child("contentsReact").child(key).child("likes").child(userName).removeValue();
                        }else{
                            mRef.child("contentsReact").child(key).child("dislikes").child(userName).removeValue();
                            mRef.child("contentsReact").child(key).child("likes").child(userName).child(userName).setValue(userName);
                            like.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                            dislike.setImageResource(R.drawable.ic_baseline_thumb_down_24_black);
                        }

                    }else{
                        if (snapshot.child("contentsReact").child(key).child("likes").hasChild(finalUID)) {
                            mRef.child("contentsReact").child(key).child("likes").child(finalUID).removeValue();
                            like.setImageResource(R.drawable.ic_baseline_thumb_up_24_black);
                        }else{
                            mRef.child("contentsReact").child(key).child("dislikes").child(finalUID).removeValue();
                            mRef.child("contentsReact").child(key).child("likes").child(finalUID).child(userName).setValue(userName);
                            like.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                            dislike.setImageResource(R.drawable.ic_baseline_thumb_down_24_black);
                        }


                    }


                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        });

        dislike.setOnClickListener(v->{
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (fbUser==null) {
                        if (snapshot.child("contentsReact").child(key).child("dislikes").hasChild(userName)) {
                            dislike.setImageResource(R.drawable.ic_baseline_thumb_down_24_black);
                            mRef.child("contentsReact").child(key).child("dislikes").child(userName).removeValue();
                        }else{
                            mRef.child("contentsReact").child(key).child("likes").child(userName).removeValue();
                            mRef.child("contentsReact").child(key).child("dislikes").child(userName).child(userName).setValue(userName);
                            like.setImageResource(R.drawable.ic_baseline_thumb_up_24_black);
                            dislike.setImageResource(R.drawable.ic_baseline_thumb_down_24);
                        }

                    }else{
                        if (snapshot.child("contentsReact").child(key).child("dislikes").hasChild(finalUID)) {
                            mRef.child("contentsReact").child(key).child("dislikes").child(finalUID).removeValue();
                            dislike.setImageResource(R.drawable.ic_baseline_thumb_down_24_black);
                        }else{
                            mRef.child("contentsReact").child(key).child("likes").child(finalUID).removeValue();
                            mRef.child("contentsReact").child(key).child("dislikes").child(finalUID).child(userName).setValue(userName);
                            like.setImageResource(R.drawable.ic_baseline_thumb_up_24_black);
                            dislike.setImageResource(R.drawable.ic_baseline_thumb_down_24);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        });

        comment.setOnClickListener(v->{

            if (commentShow){
                rlayout.setVisibility(View.INVISIBLE);
                commentShow = false;
            }else{
                rlayout.setVisibility(View.VISIBLE);
                commentShow = true;
            }

        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.child("contentsReact").child(contentID).exists()){
                    if (snapshot.child("contentsReact").child(contentID).child("likes").child(userName).exists()){
                        like.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                        dislike.setImageResource(R.drawable.ic_baseline_thumb_down_24_black);
                    }else if (snapshot.child("contentsReact").child(contentID).child("dislikes").child(userName).exists()){
                        like.setImageResource(R.drawable.ic_baseline_thumb_up_24_black);
                        dislike.setImageResource(R.drawable.ic_baseline_thumb_down_24);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        authName = findViewById(R.id.authorNamePopup);
        caption = findViewById(R.id.captionPopup);
        category = findViewById(R.id.categTextPopup);
        userNamePop = findViewById(R.id.userNamePopup);

        authName.setText(name);
        caption.setText(capt);
        category.setText(categ);
        if (uNamePop.equals("Facebook User")){
            userNamePop.setText(uNamePop + " • ");
        }else {
            userNamePop.setText("@" + uNamePop + " • ");
        }
        caption.setMovementMethod(new ScrollingMovementMethod());

        sendInputComment.setOnClickListener(v->{
            String commentInputData = commentInput.getText().toString().replace('\n', 's').trim();
            if (commentInput.equals("")){
            }else{
                String commentPush = mRef.push().getKey();
                commentSend commentSend = new commentSend(currentDP, userName, commentInputData);
                mRef.child("contentsComment").child(key).child(commentPush).setValue(commentSend);

                commentInput.setText("");
            }
        });

        rViewCom = findViewById(R.id.commentDrop);
        commentSendList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rViewCom.setLayoutManager(linearLayoutManager);

        rViewComments = new rViewComments(this,commentSendList, userName);
        rViewCom.setAdapter(rViewComments);

        mRef.child("contentsComment").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                commentSendList.clear();
                rViewComments.notifyDataSetChanged();

                for (DataSnapshot datasnapshot : snapshot.getChildren()){
                    commentSend commentSend = datasnapshot.getValue(commentSend.class);
                    commentSendList.add(commentSend);
                }

                rViewComments.notifyDataSetChanged();
                linearLayoutManager.smoothScrollToPosition(rViewCom,null,rViewCom.getAdapter().getItemCount());

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




    }

}
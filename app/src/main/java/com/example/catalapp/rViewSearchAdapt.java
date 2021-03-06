package com.example.catalapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class rViewSearchAdapt extends RecyclerView.Adapter<rViewSearchAdapt.MyViewHolder> {

    FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase adb = FirebaseDatabase.getInstance();
    DatabaseReference mRef = adb.getReference();
    DatabaseReference fRef = adb.getReference();


    Context context;
    String userName;
    List<Details> detailsList;

    public rViewSearchAdapt(Context context, List<Details> detailsList, String userName){
        this.context = context;
        this.detailsList = detailsList;
        this.userName = userName;

    }
    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View noimageview = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.no_img_content_card_layout,
                parent, false
        );
        return new MyViewHolder(noimageview);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Details details = detailsList.get(position);
        holder.authName.setText(details.getAuthName());
        String fbVerif = details.getFbUserId();
        if (fbVerif.equals("nonFB")){
            holder.userName.setText("@" + details.getUserName()+ " ??? ");
        }else{
            holder.userName.setText(details.getUserName()+ " ??? ");
        }
        holder.caption.setText(details.getCaption());
        holder.category.setText(details.getCategory());
        holder.dateTime.setText(details.getDateTime());

        String contentUserName = details.getUserName();
        String key = details.getPushKey();
        String fbID = null;

        if (fbUser != null){
            fbID = fbUser.getUid();
        }

        String finalFbID = fbID;

        fRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.child("contentsReact").exists()) {
                    if (fbUser == null) {
                        if (snapshot.child("contentsReact").child(key).child("likes").hasChild(userName)) {
                            holder.likeImg.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                        } else if (snapshot.child("contentsReact").child(key).child("dislikes").hasChild(userName)) {
                            holder.dislikeImg.setImageResource(R.drawable.ic_baseline_thumb_down_24);
                        }
                    } else {
                        if (snapshot.child("contentsReact").child(key).child("likes").hasChild(finalFbID)) {
                            holder.likeImg.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                        } else if (snapshot.child("contentsReact").child(key).child("dislikes").hasChild(finalFbID)) {
                            holder.dislikeImg.setImageResource(R.drawable.ic_baseline_thumb_down_24);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        holder.userName.setOnClickListener(v->{
            if (fbUser == null && userName.equals(contentUserName)){
                Intent intent = new Intent(context, profile.class);
                intent.putExtra("user", userName);
                context.startActivity(intent);
            }else if (fbUser == null && !userName.equals(contentUserName) && !contentUserName.equals("Facebook User")){
                Intent intent = new Intent(context, viewUserProfile.class);
                intent.putExtra("user", contentUserName);
                context.startActivity(intent);
            }else if (fbUser != null && !contentUserName.equals("Facebook User")){
                Intent intent = new Intent(context, viewUserProfile.class);
                intent.putExtra("user", contentUserName);
                context.startActivity(intent);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Profiles")
                        .setMessage("Exclusive only for non-Facebook User")
                        .setNeutralButton("Cancel", (dialog, which) -> {});
                builder.create();
                builder.show();
            }
        });

        holder.likeImg.setOnClickListener(v -> {
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    String pushKey = mRef.push().getKey();
                    if (fbUser==null) {
                        if (snapshot.child("contentsReact").child(key).child("likes").hasChild(userName)) {
                            mRef.child("contentsReact").child(key).child("likes").child(userName).removeValue();
                            holder.likeImg.setImageResource(R.drawable.ic_baseline_thumb_up_24_black);
                        }else{
                            mRef.child("contentsReact").child(key).child("dislikes").child(userName).removeValue();
                            mRef.child("contentsReact").child(key).child("likes").child(userName).child(pushKey).setValue("likes");
                            holder.likeImg.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                            holder.dislikeImg.setImageResource(R.drawable.ic_baseline_thumb_down_24_black);
                        }
                    }else{
                        if (snapshot.child("contentsReact").child(key).child("likes").hasChild(finalFbID)) {
                            mRef.child("contentsReact").child(key).child("likes").child(finalFbID).removeValue();
                            holder.likeImg.setImageResource(R.drawable.ic_baseline_thumb_up_24_black);
                        }else{
                            mRef.child("contentsReact").child(key).child("dislikes").child(finalFbID).removeValue();
                            mRef.child("contentsReact").child(key).child("likes").child(finalFbID).child(pushKey).setValue("likes");
                            holder.likeImg.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                            holder.dislikeImg.setImageResource(R.drawable.ic_baseline_thumb_down_24_black);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        });

        holder.dislikeImg.setOnClickListener(v-> {
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    String pushKey = mRef.push().getKey();
                    if (fbUser==null) {
                        if (snapshot.child("contentsReact").child(key).child("dislikes").hasChild(userName)) {
                            mRef.child("contentsReact").child(key).child("dislikes").child(userName).removeValue();
                            holder.dislikeImg.setImageResource(R.drawable.ic_baseline_thumb_down_24_black);
                        }else{
                            mRef.child("contentsReact").child(key).child("likes").child(userName).removeValue();
                            mRef.child("contentsReact").child(key).child("dislikes").child(userName).child(pushKey).setValue("likes");
                            holder.likeImg.setImageResource(R.drawable.ic_baseline_thumb_up_24_black);
                            holder.dislikeImg.setImageResource(R.drawable.ic_baseline_thumb_down_24);
                        }
                    }else{
                        if (snapshot.child("contentsReact").child(key).child("dislikes").hasChild(finalFbID)) {
                            mRef.child("contentsReact").child(key).child("dislikes").child(finalFbID).removeValue();
                            holder.dislikeImg.setImageResource(R.drawable.ic_baseline_thumb_down_24_black);
                        }else{
                            mRef.child("contentsReact").child(key).child("likes").child(finalFbID).removeValue();
                            mRef.child("contentsReact").child(key).child("dislikes").child(finalFbID).child(pushKey).setValue("likes");
                            holder.likeImg.setImageResource(R.drawable.ic_baseline_thumb_up_24_black);
                            holder.dislikeImg.setImageResource(R.drawable.ic_baseline_thumb_down_24);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        });


        holder.option.setOnClickListener(v-> {
            if (contentUserName.equals(userName)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("My Posts")
                        .setMessage("What will you do to this post?")
                        .setPositiveButton("Edit Post", (dialog, which) -> {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setTitle("Editing Content")
                                    .setMessage("Feature under development, Development Progress v0.0.3.0")
                                    .setNeutralButton("Cancel", (dialog1, which1) -> {

                                    });
                            builder1.create();
                            builder1.show();
                        })
                        .setNegativeButton("Delete Post", (dialog, which) -> {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setTitle("Delete Post")
                                    .setMessage("Are you sure to delete this post?")
                                    .setPositiveButton("Delete", (dialog1, which1) -> {
                                        mRef.child("contents").child(key).removeValue();
                                        mRef.child("contentsReact").child(key).removeValue();
                                        Toast.makeText(context, "Content deleted...", Toast.LENGTH_SHORT).show();
                                    })
                                    .setNeutralButton("Cancel", (dialog1, which1) -> {

                                    });
                            builder1.create();
                            builder1.show();
                        })
                        .setNeutralButton("Cancel", (dialog, which) -> {
                        });
                builder.create();
                builder.show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Option")
                        .setMessage("What will you do to this post?")

                        .setPositiveButton("Report", (dialog, which) -> {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setTitle("Editing Content")
                                    .setMessage("Feature under development, Development Progress v0.0.3.0")
                                    .setNeutralButton("Cancel", (dialog1, which1) -> {

                                    });
                            builder1.create();
                            builder1.show();
                        })
                        .setNeutralButton("Cancel", (dialog, which) -> {
                        });
                builder.create();
                builder.show();

            }
        });

        holder.itemView.setOnClickListener(v->{
            Intent i = new Intent(context, popup_content.class);
            String authorName = details.getAuthName();
            String caption = details.getCaption();
            String category = details.getCategory();
            String conTuserName = details.getUserName();
            String contentID = details.getPushKey();
            i.putExtra("author", authorName);
            i.putExtra("capt", caption);
            i.putExtra("categ", category);
            i.putExtra("uname", conTuserName);
            i.putExtra("currentUser", userName);
            i.putExtra("contentID", contentID);
            i.putExtra("key", key);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return detailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView authName;
        TextView userName;
        TextView caption;
        TextView category;
        TextView dateTime;
        ImageView option;
        ImageView likeImg;
        ImageView dislikeImg;
        public MyViewHolder(@Nullable View itemView){
            super(itemView);
            likeImg = itemView.findViewById(R.id.likeContent);
            dislikeImg = itemView.findViewById(R.id.dislikeContent);
            authName = itemView.findViewById(R.id.authorName);
            userName = itemView.findViewById(R.id.userNameContent);
            caption = itemView.findViewById(R.id.caption);
            category = itemView.findViewById(R.id.categText);
            dateTime = itemView.findViewById(R.id.dateTimeCard);
            option = itemView.findViewById(R.id.postoptionContent);
        }
    }
}
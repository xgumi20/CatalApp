package com.example.catalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class rViewComments extends RecyclerView.Adapter<rViewComments.MyViewHolder> {

    Context context;
    List<commentSend> commentSendList;
    String userName;

    public rViewComments(Context context, List<commentSend> commentSendList, String userName) {
        this.context = context;
        this.commentSendList = commentSendList;
        this.userName = userName;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View noimageview = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.commentscards,
                parent, false
        );
        return new MyViewHolder(noimageview);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        commentSend commentSend = commentSendList.get(position);
        String commentUsername = commentSend.getUserNameComment();
        holder.commentDisplay.setText(commentSend.getCommentDisplay());
        holder.commentDP.setText(commentSend.getDisplayNameComment());
    }

    @Override
    public int getItemCount() {
        return commentSendList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView commentDP;
        TextView commentDisplay;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            commentDP = itemView.findViewById(R.id.commentDP);
            commentDisplay = itemView.findViewById(R.id.commentDisplay);
        }
    }
}

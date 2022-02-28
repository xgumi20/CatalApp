package com.example.catalapp;

public class commentSend {
    String displayNameComment;
    String userNameComment;
    String commentDisplay;

    public commentSend(){

    }

    public commentSend(String displayNameComment, String userNameComment, String commentDisplay) {
        this.displayNameComment = displayNameComment;
        this.userNameComment = userNameComment;
        this.commentDisplay = commentDisplay;
    }

    public String getDisplayNameComment() {
        return displayNameComment;
    }

    public String getUserNameComment() {
        return userNameComment;
    }

    public String getCommentDisplay() {
        return commentDisplay;
    }
}

package com.example.catalapp;

public class Details {

    private String authName;
    private String userName;
    private String caption;
    private String category;
    private String dateTime;
    private String fbUserId;
    private String pushKey;
    public Details(){

    }

    public Details(String authName, String userName, String category, String caption, String dateTime, String fbUserId, String pushKey){
        this.authName = authName;
        this.userName = userName;
        this.category = category;
        this.caption = caption;
        this.dateTime = dateTime;
        this.fbUserId = fbUserId;
        this.pushKey = pushKey;
    }

    public String getAuthName(){
        return authName;
    }

    public String getCaption(){
        return caption;
    }

    public String getCategory() {
        return category;
    }

    public String getUserName() {
        return userName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getFbUserId() {
        return fbUserId;
    }

    public String getPushKey() {
        return pushKey;
    }
}
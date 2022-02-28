package com.example.catalapp;

public class DetailsPrf {

    private String displayName;
    private String usernameProfile;
    private String eMail;
    private String mContact;
    private String mLocation;
    private String mEducation;
    private String mHobbies;

    private String eMailPriv;
    private String mContactPriv;
    private String mLocationPriv;
    private String mEducationPriv;
    private String mHobbiesPriv;


    public DetailsPrf(String displayName, String usernameProfile,String eMail, String mContact, String mLocation, String mEducation, String mHobbies, String eMailPriv, String mContactPriv, String mLocationPriv, String mEducationPriv, String mHobbiesPriv) {
        this.displayName = displayName;
        this.usernameProfile = usernameProfile;
        this.eMail = eMail;
        this.mContact = mContact;
        this.mLocation = mLocation;
        this.mEducation = mEducation;
        this.mHobbies = mHobbies;
        this.eMailPriv = eMailPriv;
        this.mContactPriv = mContactPriv;
        this.mLocationPriv = mLocationPriv;
        this.mEducationPriv = mEducationPriv;
        this.mHobbiesPriv = mHobbiesPriv;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUsernameProfile() {
        return usernameProfile;
    }

    public String geteMail() {
        return eMail;
    }

    public String getmContact() {
        return mContact;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmEducation() {
        return mEducation;
    }

    public String getmHobbies() {
        return mHobbies;
    }

    public String geteMailPriv() {
        return eMailPriv;
    }

    public String getmContactPriv() {
        return mContactPriv;
    }

    public String getmLocationPriv() {
        return mLocationPriv;
    }

    public String getmEducationPriv() {
        return mEducationPriv;
    }

    public String getmHobbiesPriv() {
        return mHobbiesPriv;
    }
}
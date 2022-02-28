package com.example.catalapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

public class preference {
    public static final String DATA_LOGIN = "status_login", DATA_TYPEUSER = "typeuser";

    private static SharedPreferences getSharedReferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setDataTypeuser(Context context, String data) {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_TYPEUSER,data);
        editor.apply();
    }

    public static String getDataTypeuser(Context context) {
        return getSharedReferences(context).getString(DATA_TYPEUSER, "");
    }

    public static void setDataLogin(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putBoolean(DATA_LOGIN, status);
        editor.apply();
    }

    public static boolean getDataLogin(Context context){
        return getSharedReferences(context).getBoolean(DATA_LOGIN, false);
    }

    public static void clearData(Context context){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.remove(DATA_LOGIN);
        editor.remove(DATA_TYPEUSER);
        editor.apply();
    }
}

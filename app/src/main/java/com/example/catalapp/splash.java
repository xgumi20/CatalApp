package com.example.catalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler= new Handler();
        handler.postDelayed(()->{
           Intent intent = new Intent(splash.this, MainActivity.class);
           startActivity(intent);
           finish();
        },3000);

    }
}
package com.example.catalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class viewUserProfile extends AppCompatActivity {

    TextView profileName, profileUsername, otherProfile;
    EditText profileEmail, profileLoc, profileContact, profileEduc, profileHobbies;

    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profile);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v-> {finish();});

        Bundle ext = getIntent().getExtras();
        if (ext != null){
            userName = ext.getString("user");
        }

        otherProfile = findViewById(R.id.checkOtherUser);
        profileName = findViewById(R.id.profileDisplayOther);
        profileUsername = findViewById(R.id.profileUserOther);
        profileEmail = findViewById(R.id.emailProfileOther);
        profileLoc = findViewById(R.id.locationProfileOther);
        profileContact = findViewById(R.id.contactProfileOther);
        profileEduc = findViewById(R.id.educationalProfileOther);
        profileHobbies = findViewById(R.id.hobbiesProfileOther);

        DatabaseReference fRef = FirebaseDatabase.getInstance().getReference();
        if (userName == null){
            Toast.makeText(this, "No Username", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            fRef.child("login").addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    otherProfile.setText(userName+"'s Profile ");
                    profileName.setText(snapshot.child("user").child(userName).child("dpname").getValue(String.class));
                    profileUsername.setText("@" + userName);
                    profileEmail.setText(snapshot.child("user").child(userName).child("email").getValue(String.class));

                    if (snapshot.child("user").child(userName).child("emailPriv").exists()) {
                        if (snapshot.child("user").child(userName).child("emailPriv").getValue(String.class).equals("f")) {
                            profileEmail.setText(snapshot.child("user").child(userName).child("email").getValue(String.class));
                        } else {
                            profileEmail.setText("Information hidden by user");
                        }
                    }

                    if (snapshot.child("user").child(userName).child("location").exists()) {
                        if (snapshot.child("user").child(userName).child("locPriv").getValue(String.class).equals("f")) {
                            profileLoc.setText(snapshot.child("user").child(userName).child("location").getValue(String.class));
                        } else {
                            profileLoc.setText("Information hidden by user");
                        }
                    }

                    if (snapshot.child("user").child(userName).child("contact").exists()) {
                        if (snapshot.child("user").child(userName).child("contactPriv").getValue(String.class).equals("f")) {
                            profileContact.setText(snapshot.child("user").child(userName).child("contact").getValue(String.class));
                        } else {
                            profileContact.setText("Information hidden by user");
                        }
                    }

                    if (snapshot.child("user").child(userName).child("education").exists()) {
                        if (snapshot.child("user").child(userName).child("educPriv").getValue(String.class).equals("f")) {
                            profileEduc.setText(snapshot.child("user").child(userName).child("education").getValue(String.class));
                        } else {
                            profileEduc.setText("Information hidden by user");
                        }
                    }

                    if (snapshot.child("user").child(userName).child("hobbies").exists()) {
                        if (snapshot.child("user").child(userName).child("hobbiesPriv").getValue(String.class).equals("f")) {
                            profileHobbies.setText(snapshot.child("user").child(userName).child("hobbies").getValue(String.class));
                        } else {
                            profileHobbies.setText("Information hidden by user");
                        }
                    }

                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                }
            });
        }
    }
}
package com.example.catalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class profile extends AppCompatActivity {

    FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    TextView profileName, profileUsername;
    EditText profileEmail, profileLoc, profileContact, profileEduc, profileHobbies;
    String privEmail, privLoc, privCont, privEduc, privHob;

    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v-> {finish();});

        profileName = findViewById(R.id.profileDisplay);
        profileUsername = findViewById(R.id.profileUser);
        profileEmail = findViewById(R.id.emailProfile);
        profileLoc = findViewById(R.id.locationProfile);
        profileContact = findViewById(R.id.contactProfile);
        profileEduc = findViewById(R.id.educationalProfile);
        profileHobbies = findViewById(R.id.hobbiesProfile);

        Bundle ext = getIntent().getExtras();
        if (ext != null) {
            userName = ext.getString("user");
        }

        DatabaseReference fRef = FirebaseDatabase.getInstance().getReference();
        if (userName == null){
            Toast.makeText(this, "No Username", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            fRef.child("login").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    profileName.setText(snapshot.child("user").child(userName).child("dpname").getValue().toString());
                    profileUsername.setText("@" + userName);
                    profileEmail.setText(snapshot.child("user").child(userName).child("email").getValue().toString());
                    if (snapshot.child("user").child(userName).child("location").exists()) {
                        profileLoc.setText(snapshot.child("user").child(userName).child("location").getValue().toString());
                    }else{
                        profileLoc.setText("");
                    }
                    if (snapshot.child("user").child(userName).child("contact").exists()) {
                        profileContact.setText(snapshot.child("user").child(userName).child("contact").getValue().toString());
                    }else{
                        profileContact.setText("");
                    }

                    if (snapshot.child("user").child(userName).child("education").exists()) {
                        profileEduc.setText(snapshot.child("user").child(userName).child("education").getValue().toString());
                    }else{
                        profileEduc.setText("");
                    }

                    if (snapshot.child("user").child(userName).child("hobbies").exists()) {
                        profileHobbies.setText(snapshot.child("user").child(userName).child("hobbies").getValue().toString());
                    }else{
                        profileHobbies.setText("");
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }



        ImageView editProfile = findViewById(R.id.gotoEdit);
        editProfile.setOnClickListener(v-> {
            Intent intent = new Intent(profile.this, profile_edit.class);
            intent.putExtra("disName", profileName.getText().toString());
            intent.putExtra("userName", userName);
            intent.putExtra("email", profileEmail.getText().toString());
            intent.putExtra("loc", profileLoc.getText().toString());
            intent.putExtra("contact", profileContact.getText().toString());
            intent.putExtra("educ", profileEduc.getText().toString());
            intent.putExtra("hobbies", profileHobbies.getText().toString());
            startActivity(intent);
        });


    }
}
package com.example.catalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class profile_edit extends AppCompatActivity {

    EditText dpName, email, loc, contact, educ, hobbies;
    TextView profileUserNameEdit;
    Switch emailHide, locHide, contactHide, educHide, hobbiesHide;
    ImageView saveProfile;

    String disName, userName, emailStr, locStr, contStr, educStr, hobbiesStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v-> {finish();});

        Bundle ext = getIntent().getExtras();
        if (ext != null){
            disName = ext.getString("disName");
            userName = ext.getString("userName");
            emailStr = ext.getString("email");
            locStr = ext.getString("loc");
            contStr = ext.getString("contact");
            educStr = ext.getString("educ");
            hobbiesStr = ext.getString("hobbies");
        }


        dpName = findViewById(R.id.editProfileDisplay);
        email = findViewById(R.id.emailEdit);
        loc = findViewById(R.id.locationEdit);
        contact = findViewById(R.id.contactEdit);
        educ = findViewById(R.id.educationalEdit);
        hobbies = findViewById(R.id.hobbiesEdit);
        profileUserNameEdit = findViewById(R.id.profileUser);

        dpName.setText(disName);
        profileUserNameEdit.setText("@"+userName);
        email.setText(emailStr);
        loc.setText(locStr);
        contact.setText(contStr);
        educ.setText(educStr);
        hobbies.setText(hobbiesStr);

        emailHide = findViewById(R.id.editEmailPrivacy);
        locHide = findViewById(R.id.editLocationPrivacy);
        contactHide = findViewById(R.id.editContactPrivacy);
        educHide = findViewById(R.id.editEducPrivacy);
        hobbiesHide = findViewById(R.id.editHobbiesPrivacy);

        saveProfile = findViewById(R.id.saveProfile);

        saveProfile.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Save Profile?")
                    .setMessage("Are you sure to save this profile?")
                    .setPositiveButton("Save Profile", (dialog, which) -> {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("login").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                final String newDpName = dpName.getText().toString().trim();
                                final String newEmail =  email.getText().toString().trim();
                                final String newLoc = loc.getText().toString().trim();
                                final String newContact = contact.getText().toString().trim();
                                final String newEduc = educ.getText().toString().trim();
                                final String newHobbies = hobbies.getText().toString().trim();

                                databaseReference.child("login").child("user").child(userName).child("dpname").setValue(newDpName);
                                databaseReference.child("login").child("user").child(userName).child("email").setValue(newEmail);
                                databaseReference.child("login").child("user").child(userName).child("location").setValue(newLoc);
                                databaseReference.child("login").child("user").child(userName).child("contact").setValue(newContact);
                                databaseReference.child("login").child("user").child(userName).child("education").setValue(newEduc);
                                databaseReference.child("login").child("user").child(userName).child("hobbies").setValue(newHobbies);


                                if (emailHide.isChecked()){
                                    databaseReference.child("login").child("user").child(userName).child("emailPriv").setValue("t");
                                }else{
                                    databaseReference.child("login").child("user").child(userName).child("emailPriv").setValue("f");
                                }

                                if (locHide.isChecked()){
                                    databaseReference.child("login").child("user").child(userName).child("locPriv").setValue("t");
                                }else{
                                    databaseReference.child("login").child("user").child(userName).child("locPriv").setValue("f");
                                }

                                if (contactHide.isChecked()){
                                    databaseReference.child("login").child("user").child(userName).child("contactPriv").setValue("t");
                                }else{
                                    databaseReference.child("login").child("user").child(userName).child("contactPriv").setValue("f");
                                }

                                if (educHide.isChecked()){
                                    databaseReference.child("login").child("user").child(userName).child("educPriv").setValue("t");
                                }else{
                                    databaseReference.child("login").child("user").child(userName).child("educPriv").setValue("f");
                                }

                                if (hobbiesHide.isChecked()){
                                    databaseReference.child("login").child("user").child(userName).child("hobbiesPriv").setValue("t");
                                }else {
                                    databaseReference.child("login").child("user").child(userName).child("hobbiesPriv").setValue("f");
                                }

                                finish();

                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    })
                    .setNeutralButton("Cancel", (dialog, which) -> {});
            builder.create();
            builder.show();

        });
    }
}
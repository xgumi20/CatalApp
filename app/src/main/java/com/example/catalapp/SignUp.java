package com.example.catalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class SignUp extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://catalapp-7b9c3-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText dpname = findViewById(R.id.displayName);
        final EditText uname = findViewById(R.id.username);
        final EditText pword = findViewById(R.id.password);
        final EditText cpword = findViewById(R.id.confirmpassword);
        final EditText email = findViewById(R.id.email);


        Button signup = findViewById(R.id.signup);
        TextView clsignin = findViewById(R.id.clsignin);

        clsignin.setOnClickListener(v-> finish());

        signup.setOnClickListener(v->{
            final String dpnametxt = dpname.getText().toString().trim();
            final String unametxt = uname.getText().toString().trim();
            final String pwordtxt = pword.getText().toString().trim();
            final String cpwordtxt = cpword.getText().toString().trim();
            final String emailtxt = email.getText().toString().trim();

            if (dpnametxt.isEmpty() || emailtxt.isEmpty() || unametxt.isEmpty() || pwordtxt.isEmpty() || cpwordtxt.isEmpty()){
                Toast.makeText(SignUp.this, "Fill all missing fields", Toast.LENGTH_SHORT).show();
            }else if(!pwordtxt.equals(cpwordtxt)){
                Toast.makeText(SignUp.this, "Password does not match", Toast.LENGTH_SHORT).show();
                pword.setText("");
                cpword.setText("");
            }else{
                databaseReference.child("login").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.child("user").child(unametxt).exists()){
                            Toast.makeText(SignUp.this, "Username is taken", Toast.LENGTH_SHORT).show();
                        }else{
                            databaseReference.child("login").child("user").child(unametxt).child("dpname").setValue(dpnametxt);
                            databaseReference.child("login").child("user").child(unametxt).child("username").setValue(unametxt);
                            databaseReference.child("login").child("user").child(unametxt).child("password").setValue(pwordtxt);
                            databaseReference.child("login").child("user").child(unametxt).child("email").setValue(emailtxt);
                            databaseReference.child("login").child("user").child(unametxt).child("keepLogin").setValue("f");
                            databaseReference.child("login").child("user").child(unametxt).child("typeuser").setValue("user");
                            databaseReference.child("login").child("user").child(unametxt).child("emailPriv").setValue("f");
                            databaseReference.child("login").child("user").child(unametxt).child("location").setValue("");
                            databaseReference.child("login").child("user").child(unametxt).child("locPriv").setValue("f");
                            databaseReference.child("login").child("user").child(unametxt).child("contact").setValue("");
                            databaseReference.child("login").child("user").child(unametxt).child("contactPriv").setValue("f");
                            databaseReference.child("login").child("user").child(unametxt).child("education").setValue("");
                            databaseReference.child("login").child("user").child(unametxt).child("educPriv").setValue("f");
                            databaseReference.child("login").child("user").child(unametxt).child("hobbies").setValue("");
                            databaseReference.child("login").child("user").child(unametxt).child("hobbiesPriv").setValue("f");


                            Toast.makeText(SignUp.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

            }

        });
    }
}
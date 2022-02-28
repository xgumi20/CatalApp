package com.example.catalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private EditText username, password; // EditText user inputs
    private Button login; // login Button for database

    private static final String TAG = "FacebookLogin";

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private CallbackManager mCallbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signup = findViewById(R.id.sign_up);
        signup.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        });

        Bundle bundle = new Bundle();

        login = findViewById(R.id.login_account);

        username = findViewById(R.id.usernameLogin);
        password = findViewById(R.id.passwordLogin);

        login.setOnClickListener(v->{
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("login").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userinput = username.getText().toString().trim();
                    String passinput = password.getText().toString().trim();

                    Intent feedd = new Intent(MainActivity.this, feed.class);
                    feedd.putExtra("userName", userinput);


                    if (userinput.equals("") || passinput.equals("")) {
                        Toast.makeText(MainActivity.this, "You can't Login with Blank Format", Toast.LENGTH_SHORT).show();
                    } else {
                        if (snapshot.child("user").child(userinput).exists()) {
                            if (snapshot.child("user").child(userinput).child("password").getValue(String.class).equals(passinput) && snapshot.child("user").child(userinput).child("password").getValue(String.class) != null) {

                                if (snapshot.child("user").child(userinput).child("typeuser").getValue(String.class).equals("admin")) {

                                    preference.setDataLogin(MainActivity.this, false);
                                    startActivity(feedd);
                                } else if (snapshot.child("user").child(userinput).child("typeuser").getValue(String.class).equals("user")) {
                                    preference.setDataLogin(MainActivity.this, false);
                                    startActivity(feedd);
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Password Incorrect... Try Again.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Account not registered...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START initialize_fblogin]
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
        // [END initialize_fblogin]
    }



    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();

        if (preference.getDataLogin(this)){
            if (preference.getDataTypeuser(this).equals("admin")){
                startActivity(new Intent(MainActivity.this, feed.class));
                finish();
            }else if (preference.getDataTypeuser(this).equals("user")){
                startActivity(new Intent(MainActivity.this, feed.class));
                finish();
            }
        }
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    // [START on_activity_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    // [END on_activity_result]

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    // [END auth_with_facebook]

    private void updateUI(FirebaseUser user) {
         user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Intent intent = new Intent(MainActivity.this, feed.class);
            startActivity(intent);
        }
    }
}
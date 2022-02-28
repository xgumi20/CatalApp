package com.example.catalapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class feed extends AppCompatActivity {


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        private TabLayout tabLayout;
        private ViewPager viewPager;
        ImageView toSearch;

        String userNameStr;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_feed);

                Bundle ext = getIntent().getExtras();
                if (ext != null) {
                        userNameStr = ext.getString("userName");
                }


                tabLayout = findViewById(R.id.tabLayout);
                viewPager = findViewById(R.id.viewpage);

                tabLayout.setupWithViewPager(viewPager);

                vpAdapt vpAdapter = new vpAdapt(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT );

                vpAdapter.addFragmen(new homeFrag(), "HOME");
                //vpAdapter.addFragmen(new tdrFrag(), "TRENDS");
                vpAdapter.addFragmen(new settingsFrag(), "SETTINGS");

                viewPager.setAdapter(vpAdapter);

                toSearch = findViewById(R.id.toSearch);
                toSearch.setOnClickListener(v-> {
                        Intent intent = new Intent(feed.this, search_area.class);
                        intent.putExtra("user", userNameStr);
                        startActivity(intent);
                });

        }


        public void onBackPressed(){
                back();
                //super.onBackPressed();
        }

        private void back() {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Logout?")
                        .setMessage("Do you want to logout?")
                        .setNegativeButton("Logout", (dialog, which) -> {
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();
                                startActivity(new Intent(feed.this, MainActivity.class));
                                finish();
                        })
                        .setNeutralButton("Cancel", (dialog, which) -> {});
                builder.create();
                builder.show();
        }

}
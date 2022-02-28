package com.example.catalapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class settingsFrag extends Fragment {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        feed fd = (feed) getActivity();
        userName = fd.userNameStr;

        Button profile = view.findViewById(R.id.profile);
        profile.setOnClickListener(v->{
            if (user != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Profiles")
                        .setMessage("Exclusive only for non-Facebook User")
                        .setNeutralButton("Cancel", (dialog, which) -> {});
                builder.create();
                builder.show();
            }else{
                Intent prof = new Intent(getContext(), profile.class);
                prof.putExtra("user", userName);
                startActivity(prof);
            }
        });

        Button myposts = view.findViewById(R.id.myposts);
        myposts.setOnClickListener(v->{
            Intent prof = new Intent(getContext(), userPosts.class);
            prof.putExtra("user", userName);
            startActivity(prof);
        });


        Button logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Logout?")
                    .setMessage("Do you want to logout?")
                    .setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            LoginManager.getInstance().logOut();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        }
                    })
                    .setNeutralButton("Cancel", (dialog, which) -> {});
            builder.create();
            builder.show();
        });

        return view;
    }
}
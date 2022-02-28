package com.example.catalapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Calendar;

public class homeFrag extends Fragment {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase dbs = FirebaseDatabase.getInstance();
    DatabaseReference mRef = dbs.getReference().child("contents");

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;


    private AlertDialog.Builder adBuilder;
    private AlertDialog dialog;
    private EditText newCaption;
    private Spinner categorized;
    private Button addPost, cancelPost;

    RecyclerView rView;
    List<Details> detailsList;
    rViewAdapt rAdapter;


    String userName;
    String disName;

    String fbname;

    //int likeCatch;
    //int dislikeCatch;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Details detailsget = new Details();
        //likeCatch = Integer.parseInt(detailsget.getLike());
        //dislikeCatch = Integer.parseInt(detailsget.getDislike());


        feed fd = (feed) getActivity();
        userName = fd.userNameStr;

        DatabaseReference fRef = FirebaseDatabase.getInstance().getReference();
        if (userName == null){
        }else {
            fRef.child("login").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    disName = snapshot.child("user").child(userName).child("dpname").getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }


        if (user != null){
            fbname = user.getDisplayName();
            userName = "Facebook User";
        }


        // Inflate the layout for this fragment

        FloatingActionButton fabPost = view.findViewById(R.id.post);
        fabPost.setOnClickListener(v->{
            createPost();
        });


        rView = view.findViewById(R.id.rview);

        detailsList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rView.setLayoutManager(linearLayoutManager);

        rAdapter = new rViewAdapt(getActivity(),detailsList, userName);
        rView.setAdapter(rAdapter);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                detailsList.clear();
                rAdapter.notifyDataSetChanged();

                for (DataSnapshot datasnapshot : snapshot.getChildren()){

                    Details details = datasnapshot.getValue(Details.class);
                    detailsList.add(details);
                }
                rAdapter.notifyDataSetChanged();
                linearLayoutManager.smoothScrollToPosition(rView,null,rView.getAdapter().getItemCount());

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return view;
    }

    @SuppressLint("SimpleDateFormat")
    public void createPost(){
        adBuilder = new AlertDialog.Builder(getActivity());
        final View postAdded = getLayoutInflater().inflate(R.layout.popup, null);

        newCaption = postAdded.findViewById(R.id.captionPost);
        categorized = postAdded.findViewById(R.id.categoryPost);
        addPost = postAdded.findViewById(R.id.addPost);
        cancelPost = postAdded.findViewById(R.id.cancelPost);

        adBuilder.setView(postAdded);
        dialog = adBuilder.create();
        dialog.show();

        addPost.setOnClickListener(v->{
            if (disName != null && !newCaption.getText().toString().isEmpty()){
                    calendar = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm a");
                    final String dateTime = simpleDateFormat.format(calendar.getTime());
                    final String author = disName;
                    final String userNameToDSP = userName;
                    final String category = categorized.getSelectedItem().toString();
                    final String capt = newCaption.getText().toString().trim();
                    final String fbUserId = "nonFB";

                    //DatabaseReference pushPost = mRef.push();
                    String key = mRef.push().getKey();
                    Details details = new Details(author, userNameToDSP ,category ,capt, dateTime, fbUserId, key);
                    mRef.child(key).setValue(details);
                    //pushPost.setValue(details);

                    Toast.makeText(getActivity(), "Post Added", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
            }else if(fbname != null && !newCaption.getText().toString().isEmpty()){
                calendar = Calendar.getInstance();
                simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm a");
                final String dateTime = simpleDateFormat.format(calendar.getTime());
                final String author = fbname;
                final String userNameToDSP = userName;
                final String category = categorized.getSelectedItem().toString();
                final String capt = newCaption.getText().toString().replace('\n', 's').trim();
                final String fbUserId = user.getUid();



                //DatabaseReference pushPost = mRef.push();
                String key = mRef.push().getKey();
                Details details = new Details(author, userNameToDSP ,category ,capt, dateTime, fbUserId,key);


                assert key != null;
                mRef.child(key).setValue(details);
                //pushPost.setValue(details);


                Toast.makeText(getActivity(), "Post Added", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }else {
                Toast.makeText(getActivity(), "Can't post empty content", Toast.LENGTH_SHORT).show();
            }


        });

        cancelPost.setOnClickListener(v->{
            dialog.dismiss();
        });

    }

}
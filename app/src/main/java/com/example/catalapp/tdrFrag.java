package com.example.catalapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class tdrFrag extends Fragment {
/*
    FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase adb = FirebaseDatabase.getInstance();
    DatabaseReference mRef = adb.getReference().child("contents");
    DatabaseReference crRef = adb.getReference().child("contentsReact");

    String userName;
    String disName;
    String fbID;

    RecyclerView rViewTDR;
    List<Details> detailsList;
    rViewTDRAdapt rViewTDRAdapt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tdr, container, false);

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


        if (fbUser != null){
            fbID = fbUser.getUid();
            userName = "Facebook User";
        }

        rViewTDR = view.findViewById(R.id.trendsRecycler);

        detailsList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rViewTDR.setLayoutManager(linearLayoutManager);

        rViewTDRAdapt = new rViewTDRAdapt(getActivity(), detailsList, userName);
        rViewTDR.setAdapter(rViewTDRAdapt);

        ArrayList<String> contentID = new ArrayList<>();
        ArrayList<Integer> contentLike = new ArrayList<>();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                for (DataSnapshot datasnapshot : snapshot.getChildren()){
                    Details details = datasnapshot.getValue(Details.class);
                    assert details != null;
                    String pushID = details.getPushKey();

                    crRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot cRsnapshot) {
                            if (cRsnapshot.child(pushID).exists()) {
                                contentID.add(pushID);
                                contentLike.add((int) cRsnapshot.child(pushID).child("likes").getChildrenCount());
                                Toast.makeText(fd, contentID.get(contentID.indexOf(pushID)) + "---" + contentLike.get(contentID.indexOf(pushID)), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                    String tempString;
                    int temp;
                    for (int i = 0; i < contentID.size(); i++){
                        for (int j=i; j < contentID.size(); j++){
                            if (contentLike.get(i) > contentLike.get(j)){
                                temp = contentLike.get(i);
                                contentLike.set(i, contentLike.get(j));
                                contentLike.set(j, temp);

                                tempString = contentID.get(i);
                                contentID.set(i, contentID.get(j));
                                contentID.set(j, tempString);
                            }
                        }
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot fRsnapshot) {

                detailsList.clear();
                rViewTDRAdapt.notifyDataSetChanged();

                for (DataSnapshot dataSnapshot1 : fRsnapshot.getChildren()) {
                    Details details1 = dataSnapshot1.getValue(Details.class);
                    assert details1 != null;
                    String pushIDFinal = details1.getPushKey();
                    if (contentID.contains(pushIDFinal)){
                        if (fRsnapshot.child(pushIDFinal).exists())
                            if (contentLike.get(contentID.indexOf(pushIDFinal)) > 0) {
                                detailsList.add(details1);
                            }
                    }
                }

                rViewTDRAdapt.notifyDataSetChanged();
                linearLayoutManager.smoothScrollToPosition(rViewTDR,null,rViewTDR.getAdapter().getItemCount());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        return view;
    }

 */
}
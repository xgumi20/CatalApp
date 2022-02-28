package com.example.catalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class search_area extends AppCompatActivity {
    FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase dbs = FirebaseDatabase.getInstance();
    DatabaseReference mRef = dbs.getReference().child("contents");

    Spinner categorySpin;
    ImageView srch;
    EditText seText;

    String userName;

    RecyclerView rViewSearch;
    rViewSearchAdapt rViewSearchAdapt;
    List<Details> detailsList;

    String category,textContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_area);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v-> {finish();});

        Bundle ext = getIntent().getExtras();
        if (fbUser == null){
            if (ext != null){
                userName = ext.getString("user");
            }
        }else{
            userName = fbUser.getUid();
        }


        srch = findViewById(R.id.searchImgBtn);
        categorySpin = findViewById(R.id.category);
        seText = findViewById(R.id.mainSearch);

        ArrayAdapter adaptSpin = ArrayAdapter.createFromResource(this, R.array.spinner_category, R.layout.spinner_item);
        adaptSpin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorySpin.setAdapter(adaptSpin);

        rViewSearch = findViewById(R.id.rViewSearch);
        detailsList = new ArrayList<>();
        rViewSearchAdapt = new rViewSearchAdapt(this,detailsList, userName);
        rViewSearch.setAdapter(rViewSearchAdapt);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rViewSearch.setLayoutManager(linearLayoutManager);

        srch.setOnClickListener(v->{

            textContent = seText.getText().toString();
            category = categorySpin.getSelectedItem().toString();


            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    detailsList.clear();
                    rViewSearchAdapt.notifyDataSetChanged();

                    for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                        String caption;
                        String author;
                        Details details = datasnapshot.getValue(Details.class);
                        assert details != null;
                        caption = details.getCaption();
                        author = details.getAuthName();

                        if(category.equals("Person") && author.toLowerCase().contains(textContent.toLowerCase())) {
                            detailsList.add(details);
                        }else if(category.equals("All") && caption.isEmpty()){
                            detailsList.add(details);
                        }else if(category.equals("All") && caption.toLowerCase().contains(textContent.toLowerCase())){
                            detailsList.add(details);
                        }else if (category.equals(details.getCategory()) && caption.isEmpty() ) {
                            detailsList.add(details);
                        }else if (category.equals(details.getCategory()) && caption.toLowerCase().contains(textContent.toLowerCase())) {
                            detailsList.add(details);
                        }

                    }

                    rViewSearchAdapt.notifyDataSetChanged();
                    linearLayoutManager.smoothScrollToPosition(rViewSearch,null, Objects.requireNonNull(rViewSearch.getAdapter()).getItemCount());

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        });


    }

    public void readMyData(MyCallBack myCallBack){
        mRef = FirebaseDatabase.getInstance().getReference("contents");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String DbValue = snapshot.getValue(String.class);

                myCallBack.ocCallBack(DbValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public interface MyCallBack {
        void ocCallBack(String value);
    }
}
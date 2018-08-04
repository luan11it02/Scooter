package com.app.lfc.scooter.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.app.lfc.scooter.Adapter.YeuCauAdapter;
import com.app.lfc.scooter.Model.YeuCau;
import com.app.lfc.scooter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanLyActivity extends AppCompatActivity {

    ImageButton btnBack;
    RecyclerView recycler;
    YeuCauAdapter adapter;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    ArrayList<YeuCau> listYeuCau = new ArrayList<>();
    ArrayList<YeuCau> list = new ArrayList<>();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("yeucau");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);
        initView();
        recycler.setLayoutManager(layoutManager);
        adapter = new YeuCauAdapter(this, list);
        recycler.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_click));
                onBackPressed();
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataYeuCau();
    }

    private void getDataYeuCau(){
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listYeuCau.clear();
                list.clear();
                for (DataSnapshot data: dataSnapshot.getChildren())
                    listYeuCau.add(data.getValue(YeuCau.class));
                for (int i = 0; i < listYeuCau.size(); i++)
                    if (!listYeuCau.get(i).isDuyet())
                        list.add(listYeuCau.get(i));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initView() {
        btnBack = findViewById(R.id.btn_back_quanly);
        recycler = findViewById(R.id.recycler_quanly);
    }
}

package com.app.lfc.scooter.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.lfc.scooter.Adapter.ChiNhanhAdapter;
import com.app.lfc.scooter.Adapter.PostAdapter;
import com.app.lfc.scooter.Model.ChiNhanh;
import com.app.lfc.scooter.Model.Post;
import com.app.lfc.scooter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerPost, recyclerChiNhanh;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Post> listPost = new ArrayList<>();
    ArrayList<ChiNhanh> listAddress = new ArrayList<>();
    PostAdapter adapterPost;
    ChiNhanhAdapter adapterChiNhanh;
    DatabaseReference database;
    ImageButton btnBack;
    AppCompatEditText edtSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        adapterPost = new PostAdapter(this, listPost);
        layoutManager = new LinearLayoutManager(this);
        recyclerPost.setLayoutManager(layoutManager);
        recyclerPost.setItemAnimator(new SlideInUpAnimator());
        getDataPost();
        adapterChiNhanh = new ChiNhanhAdapter(this, listAddress);
        layoutManager = new LinearLayoutManager(this);
        recyclerChiNhanh.setLayoutManager(layoutManager);
        recyclerChiNhanh.setItemAnimator(new SlideInUpAnimator());
        getDataAddress();

        recyclerPost.setNestedScrollingEnabled(false);
        recyclerChiNhanh.setNestedScrollingEnabled(false);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Post> filtermodelistPost = filterPost(listPost, String.valueOf(s));
                adapterPost.setfilter(filtermodelistPost);
                recyclerPost.setAdapter(adapterPost);

                List<ChiNhanh> filtermodelistAddress = filterAddress(listAddress, String.valueOf(s));
                adapterChiNhanh.setfilter(filtermodelistAddress);
                recyclerChiNhanh.setAdapter(adapterChiNhanh);
                Toast.makeText(SearchActivity.this, "" + recyclerChiNhanh.getChildCount(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(SearchActivity.this, R.anim.anim_click));
                finish();
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

    }



    private static List<Post> filterPost(List<Post> list, String query){
        query = covertStringToURL(query);
        final List<Post> fiterModeList = new ArrayList<>();
        if (query.length() > 0) {
            for (Post model : list) {
                final String text = covertStringToURL(model.getTitle());
                if (text.contains(query)) {
                    fiterModeList.add(model);
                }
            }
        } else fiterModeList.clear();
        return fiterModeList;
    }

    private static List<ChiNhanh> filterAddress(List<ChiNhanh> list, String query){
        query = covertStringToURL(query);
        final List<ChiNhanh> fiterModeList = new ArrayList<>();
        if (query.length() > 0) {
            for (ChiNhanh model : list) {
                final String text = covertStringToURL(model.getAds());
                if (text.contains(query)) {
                    fiterModeList.add(model);
                }
            }
        } else fiterModeList.clear();
        return fiterModeList;
    }


    private static String covertStringToURL(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase()
                    .replaceAll(" ", "-").replaceAll("đ", "d");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void getDataPost(){
        database = FirebaseDatabase.getInstance().getReference("baiviet");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPost.clear();
                for (int i = (int) (dataSnapshot.getChildrenCount()-1); i >= 0; i--){
                    Post post = dataSnapshot.child(String.valueOf(i)).getValue(Post.class);
                    post.setId(i);
                    listPost.add(post);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getDataAddress(){
        database = FirebaseDatabase.getInstance().getReference("diachi");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listAddress.clear();
                for (DataSnapshot data: dataSnapshot.child("main").getChildren())
                    listAddress.add(data.getValue(ChiNhanh.class));
                for (DataSnapshot data: dataSnapshot.child("miennam").getChildren())
                    listAddress.add(data.getValue(ChiNhanh.class));
                for (DataSnapshot data: dataSnapshot.child("mientrung").getChildren())
                    listAddress.add(data.getValue(ChiNhanh.class));
                for (DataSnapshot data: dataSnapshot.child("mienbac").getChildren())
                    listAddress.add(data.getValue(ChiNhanh.class));
                for (DataSnapshot data: dataSnapshot.child("miendong").getChildren())
                    listAddress.add(data.getValue(ChiNhanh.class));
                Toast.makeText(SearchActivity.this, "" + listAddress.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void initView() {
        recyclerPost        = findViewById(R.id.list_post_search);
        recyclerChiNhanh    = findViewById(R.id.list_address_search);
        btnBack             = findViewById(R.id.btn_back_search);
        edtSearch           = findViewById(R.id.edt_search);
    }
}

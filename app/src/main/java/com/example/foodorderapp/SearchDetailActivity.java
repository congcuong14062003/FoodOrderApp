package com.example.foodorderapp;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchDetailActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_detail);
        initView();
    }
    public void initView() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.detailRecycle);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<SearchItem> items = new ArrayList<>();
        items.add(new SearchItem("che do den","thach den, do den",5,R.drawable.logopanda ));
        items.add(new SearchItem("che do den","thach den, do den",5,R.drawable.logopanda ));
        items.add(new SearchItem("che do den","thach den, do den",5,R.drawable.logopanda ));

        SearchDetailAdapter searchDetailAdapter = new SearchDetailAdapter(getApplicationContext(), items);
        recyclerView.setAdapter(searchDetailAdapter);
    };

}

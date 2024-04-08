//package com.example.foodorderapp;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//public class OrderHistory extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        requestWindowFeature(Window.FEATURE_NO_TITLE);
////        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_order_history);
//        initView();
//
//        };
//        public void initView() {
//            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view_history);
//            recyclerView.setHasFixedSize(true);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//            recyclerView.setLayoutManager(linearLayoutManager);
//            ArrayList<ItemOrderHistory> arrayList = new ArrayList<>();
//            arrayList.add(new ItemOrderHistory(R.drawable.testitem, "thịt kho tàu", "thịt lợn, đường", 150, 400, imgUrl, orderDatetime, totalPrice));
//            arrayList.add(new ItemOrderHistory(R.drawable.testitem, "thịt kho tàu", "thịt lợn, đường", 150, 600, imgUrl, orderDatetime, totalPrice));
//            arrayList.add(new ItemOrderHistory(R.drawable.testitem, "thịt kho tàu", "thịt lợn, đường", 150, 150, imgUrl, orderDatetime, totalPrice));
//            arrayList.add(new ItemOrderHistory(R.drawable.testitem, "thịt kho tàu", "thịt lợn, đường", 150, 200, imgUrl, orderDatetime, totalPrice));
//            arrayList.add(new ItemOrderHistory(R.drawable.testitem, "thịt kho tàu", "thịt lợn, đường", 150, 200, imgUrl, orderDatetime, totalPrice));
//            arrayList.add(new ItemOrderHistory(R.drawable.testitem, "thịt kho tàu", "thịt lợn, đường", 150, 200, imgUrl, orderDatetime, totalPrice));
//            arrayList.add(new ItemOrderHistory(R.drawable.testitem, "thịt kho tàu", "thịt lợn, đường", 150, 200, imgUrl, orderDatetime, totalPrice));
//            arrayList.add(new ItemOrderHistory(R.drawable.testitem, "thịt kho tàu", "thịt lợn, đường", 150, 200, imgUrl, orderDatetime, totalPrice));
//            arrayList.add(new ItemOrderHistory(R.drawable.testitem, "thịt kho tàu", "thịt lợn, đường", 150, 200, imgUrl, orderDatetime, totalPrice));
//            ItemOrderHistoryAdapter historyAdapter = new ItemOrderHistoryAdapter(arrayList, getApplicationContext());
//            recyclerView.setAdapter(historyAdapter);
//        };
//
//    }

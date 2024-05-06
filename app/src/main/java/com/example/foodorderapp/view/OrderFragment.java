package com.example.foodorderapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.OrderAdapter;
import com.example.foodorderapp.viewmodal.OrderViewModel;

import java.util.List;

public class OrderFragment extends Fragment {
    private OrderViewModel orderViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);


    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_order_history, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_history);
        final OrderAdapter adapter = new OrderAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderViewModel.getOrderList().observe(getViewLifecycleOwner(), orderDTOs -> {
            adapter.setOrderList(orderDTOs);
        });

        return view;
    }
}

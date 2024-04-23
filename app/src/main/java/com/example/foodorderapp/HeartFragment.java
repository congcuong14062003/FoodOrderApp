package com.example.foodorderapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorderapp.adapter.FavorAdapter;
import com.example.foodorderapp.adapter.NotiAdapter;
import com.example.foodorderapp.viewmodal.FoodViewModel;
import com.example.foodorderapp.viewmodal.NotiViewModel;

public class HeartFragment extends Fragment {

    // Inflate the layout for this fragment
    private FoodViewModel foodViewModel;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favourite, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.favorRecycle);
        final FavorAdapter adapter = new FavorAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        foodViewModel.getFoodList().observe(getViewLifecycleOwner(), orderDTOS -> {
            adapter.setFoodList(orderDTOS);
        });

//        TextView cancelBtn = view.findViewById(R.id.backBtn);
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Đóng FoodFragment và hiển thị lại HomeFragment
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                fragmentManager.popBackStack();
//            }
//        });

        return view;
    }
}
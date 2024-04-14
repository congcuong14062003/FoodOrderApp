package com.example.foodorderapp.view;

import static com.example.foodorderapp.R.id.list_food_recycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.ListFoodAdapter;
import com.example.foodorderapp.adapter.OrderAdapter;
import com.example.foodorderapp.viewmodal.FoodViewModel;
import com.example.foodorderapp.viewmodal.OrderViewModel;

public class FoodFragment extends Fragment {
    private FoodViewModel foodViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_detail, container, false);

        RecyclerView recyclerView = view.findViewById(list_food_recycle);
        final ListFoodAdapter adapter = new ListFoodAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        foodViewModel.getFoodList().observe(getViewLifecycleOwner(), orderDTOs -> {
            adapter.setFoodList(orderDTOs);
        });

        return view;
    }
}

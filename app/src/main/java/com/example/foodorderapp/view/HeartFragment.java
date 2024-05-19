package com.example.foodorderapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.foodorderapp.LoadingManager;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.FavorAdapter;
import com.example.foodorderapp.view.DetailActivity;
import com.example.foodorderapp.view.MainActivity;
import com.example.foodorderapp.viewmodal.FoodViewModel;

public class HeartFragment extends BaseFragment implements FavorAdapter.InFoodItemClickListener {

    // Inflate the layout for this fragment
    private FoodViewModel foodViewModel;
    private RecyclerView recyclerView;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favourite, container, false);
        LoadingManager.showLoading(requireActivity());
        RecyclerView recyclerView = view.findViewById(R.id.favorRecycle);
        final FavorAdapter adapter = new FavorAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.SetOnFoodItemClickListener(this);

        foodViewModel.getFoodList().observe(getViewLifecycleOwner(), orderDTOS -> {
            adapter.setFoodList(orderDTOS);
            LoadingManager.hideLoading();
        });

        ImageView cancelBtn = view.findViewById(R.id.backFavorBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);

                startActivity(intent);
            }
        });

       

        return view;
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }


    public void onFoodItemClick(int foodId) {
//         Handle item click event here, e.g., navigate to the detail fragment with foodId
        Intent intent = new Intent(requireContext(), DetailActivity.class);
        intent.putExtra("foodId", foodId); // Truyền ID của thức ăn qua Intent
        startActivity(intent);
    }
}
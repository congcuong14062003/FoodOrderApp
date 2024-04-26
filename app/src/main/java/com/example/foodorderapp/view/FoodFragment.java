package com.example.foodorderapp.view;

import static com.example.foodorderapp.R.id.list_food_recycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.ListFoodAdapter;
import com.example.foodorderapp.viewmodal.FoodViewModel;

public class FoodFragment extends Fragment implements ListFoodAdapter.OnFoodItemClickListener {
    private FoodViewModel foodViewModel;
    private EditText searchFood;
    private HomeFragment homeFragment;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    @Nullable
// Trong FoodFragment
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_detail, container, false);



        searchFood = view.findViewById(R.id.search_food);
        // Yêu cầu focus cho EditText trong FoodFragment
        searchFood.requestFocus();

        RecyclerView recyclerView = view.findViewById(list_food_recycle);
        final ListFoodAdapter adapter = new ListFoodAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnFoodItemClickListener(this);
        foodViewModel.getFoodList().observe(getViewLifecycleOwner(), orderDTOs -> {
            adapter.setFoodList(orderDTOs);
        });

        // Lắng nghe sự kiện click của nút "Cancel"
        TextView cancelBtn = view.findViewById(R.id.cancelbtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đóng FoodFragment và hiển thị lại HomeFragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    @Override
    public void onFoodItemClick(int foodId) {
//         Handle item click event here, e.g., navigate to the detail fragment with foodId
        Intent intent = new Intent(requireContext(), DetailActivity.class);
        intent.putExtra("foodId", foodId); // Truyền ID của thức ăn qua Intent
        startActivity(intent);
    }
}

package com.example.foodorderapp.view;

import static com.example.foodorderapp.R.id.list_food_recycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.HomeFragment;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.ListFoodAdapter;
import com.example.foodorderapp.adapter.OrderAdapter;
import com.example.foodorderapp.viewmodal.FoodViewModel;
import com.example.foodorderapp.viewmodal.OrderViewModel;

public class FoodFragment extends Fragment {
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

}

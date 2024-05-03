package com.example.foodorderapp.view;

import static com.example.foodorderapp.R.id.list_food_recycle;
import static com.example.foodorderapp.R.id.resultRecycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.ListFoodAdapter;
import com.example.foodorderapp.adapter.ResultAdapter;
import com.example.foodorderapp.object.FoodDTO;
import com.example.foodorderapp.retrofit.ListFoodResponsive;
import com.example.foodorderapp.viewmodal.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment implements ResultAdapter.InFoodItemClickListener {

    private FoodViewModel foodViewModel;
    private FoodFragment foodFragment;
    private String userInput;



    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_result, container, false);

        RecyclerView recyclerView = view.findViewById(resultRecycle);
        final ResultAdapter adapter = new ResultAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.SetOnFoodItemClickListener((ResultAdapter.InFoodItemClickListener) this);

        Bundle args = getArguments();
        if (args != null && args.containsKey("userInput")) {
             userInput = args.getString("userInput");
            TextView txtResult =view.findViewById(R.id.textResult);
            txtResult.setText("Result for "+ userInput);
        }

        TextView message = view.findViewById(R.id.message);
        message.setText("Không tìm thấy món ăn");
        foodViewModel.getfoodList(userInput).observe(getViewLifecycleOwner(), orderDTOs -> {
                if (orderDTOs.isEmpty()==true) {
                    recyclerView.setVisibility(View.GONE);
                    message.setVisibility(View.VISIBLE);
                }
                else{
                    message.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.setFoodList(orderDTOs);
                }
        });

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

       ImageView backBtn = view.findViewById(R.id.backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

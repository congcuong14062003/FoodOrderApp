package com.example.foodorderapp.view;

import static com.example.foodorderapp.R.id.list_food_recycle;
import static com.example.foodorderapp.R.id.resultRecycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.HomeFragment;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.ListFoodAdapter;
import com.example.foodorderapp.adapter.ResultAdapter;
import com.example.foodorderapp.object.FoodDTO;
import com.example.foodorderapp.viewmodal.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment {

    private FoodViewModel foodViewModel;
    private FoodFragment foodFragment;


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    @Nullable
// Trong FoodFragment
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_result, container, false);

        RecyclerView recyclerView = view.findViewById(resultRecycle);
        final ResultAdapter adapter = new ResultAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



//        foodViewModel.getFoodList().observe(getViewLifecycleOwner(), orderDTOs -> {
//               adapter.setFoodList(orderDTOs);
//            });


        Bundle args = getArguments();
        if (args != null && args.containsKey("userInput")) {
            String userInput = args.getString("userInput");

            TextView txtResult =view.findViewById(R.id.textResult);
            txtResult.setText("Result for "+ userInput);

            foodViewModel.getFoodList(userInput).observe(getViewLifecycleOwner(), orderDTOs -> {
                adapter.setFoodList(orderDTOs);
            });


//            adapter.setFoodList(newList);
//            adapter.notifyDataSetChanged();
        }
//
//         Lắng nghe sự kiện click của nút "Cancel"
        TextView backBtn = view.findViewById(R.id.backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
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

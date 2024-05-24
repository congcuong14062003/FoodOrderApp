package com.example.foodorderapp.view;

import static com.example.foodorderapp.R.id.list_food_recycle;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.LoadingManager;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.ListFoodAdapter;
import com.example.foodorderapp.adapter.OrderAdapter;
import com.example.foodorderapp.object.FoodDTO;
import com.example.foodorderapp.viewmodal.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends BaseFragment implements ListFoodAdapter.OnFoodItemClickListener {
    private FoodViewModel foodViewModel;
    private EditText searchFood;
    private HomeFragment homeFragment;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    private void handleSubmit() {
        String userInput = searchFood.getText().toString(); // Lấy dữ liệu người dùng nhập vào EditText

        Fragment resultFragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString("userInput", userInput);
        resultFragment.setArguments(args);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, resultFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        // Xử lý dữ liệu userInput ở đây, ví dụ: tìm kiếm, lưu trữ, xử lý, vv.


    }

    @Nullable
// Trong FoodFragment
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_detail, container, false);
//        LoadingManager.showLoading(requireActivity());

        searchFood = view.findViewById(R.id.search_food);
        // Yêu cầu focus cho EditText trong FoodFragment
        searchFood.requestFocus();

        // Hiển thị màn hình loading khi bắt đầu tải dữ liệu
        searchFood.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ) {
                // Xử lý khi người dùng nhập liệu xong và nhấn Enter trên bàn phím ảo hoặc trên thiết bị thật
                // Gọi hàm xử lý submit
                handleSubmit();
                return true;
            }
            return false;
        });


        RecyclerView recyclerView = view.findViewById(R.id.list_food_recycle);
        final ListFoodAdapter adapter = new ListFoodAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnFoodItemClickListener(this);
        foodViewModel.getFoodList().observe(getViewLifecycleOwner(), orderDTOs -> {
            adapter.setFoodList(orderDTOs);
//            LoadingManager.hideLoading();
        });

        // thay đổi theo sự kiện onchange của input
        searchFood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().toLowerCase();
                List<FoodDTO> newList = new ArrayList<>();

                for (FoodDTO food : foodViewModel.getFoodList().getValue() ){
                    if(food.getName().toLowerCase().startsWith(newText) || food.getDescription().toLowerCase().contains(newText) || food.getIngredients().toLowerCase().contains(newText)){
                        newList.add(food);
                    }
                }

                adapter.setFoodList(newList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
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

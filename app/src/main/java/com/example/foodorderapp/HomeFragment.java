package com.example.foodorderapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodorderapp.databinding.ActivityMainBinding;
import com.example.foodorderapp.view.FoodFragment;

public class HomeFragment extends Fragment {
    private EditText searchHome;
    ActivityMainBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Ánh xạ view
        searchHome = view.findViewById(R.id.search_home);

        // Thêm sự kiện focus change listener vào EditText
        searchHome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Kiểm tra xem EditText có focus không
                if (hasFocus) {
                    Log.d("Search", "Has click search"); // Log success message
                    replaceFragment(new FoodFragment());

                } else {

                }
            }
        });


        return view;
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}


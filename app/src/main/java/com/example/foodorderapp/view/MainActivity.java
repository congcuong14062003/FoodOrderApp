package com.example.foodorderapp.view;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodorderapp.HeartFragment;
import com.example.foodorderapp.R;
import com.example.foodorderapp.databinding.ActivityMainBinding;

import com.example.foodorderapp.view.HomeFragment;
import com.example.foodorderapp.view.NotificationFragment;
import com.example.foodorderapp.view.OrderFragment;
import com.example.foodorderapp.view.ProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

<<<<<<< HEAD:app/src/main/java/com/example/foodorderapp/MainActivity.java
        replaceFragment(new HomeFragment());
=======
        // Khởi tạo Fragment Home mặc định khi Activity được khởi chạy
>>>>>>> b9ad88c6b6543931e462649936d63b3c31c58bcd:app/src/main/java/com/example/foodorderapp/view/MainActivity.java
        binding.bottomNavigation.getMenu().findItem(R.id.navigation_home).setChecked(true);

        String fragmentToShow = getIntent().getStringExtra("fragment");
        if (fragmentToShow != null && fragmentToShow.equals("order")) {
            replaceFragment(new OrderFragment());
            binding.bottomNavigation.getMenu().findItem(R.id.navigation_receipt).setChecked(true);
        } else {
            // Nếu không có intent hoặc không phải là order fragment, hiển thị fragment mặc định
            replaceFragment(new HomeFragment());
        }

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        replaceFragment(new HomeFragment());
                        return true;
                    case R.id.navigation_profile:
                        replaceFragment(new ProfileFragment());
                        return true;
                    case R.id.navigation_heart:
                        replaceFragment(new HeartFragment());
                        return true;
                    case R.id.navigation_notification:
                        replaceFragment(new NotificationFragment());
                        return true;
                    case R.id.navigation_receipt:
                        replaceFragment(new OrderFragment());
                        return true;
                }
                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}


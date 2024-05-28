package com.example.foodorderapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodorderapp.R;
import com.example.foodorderapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavigation();
        handleIntent(getIntent().getStringExtra("fragment"));
    }

    private void setupNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.navigation_profile:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.navigation_heart:
                        fragment = new HeartFragment();
                        break;
                    case R.id.navigation_notification:
                        fragment = new NotificationFragment();
                        break;
                    case R.id.navigation_receipt:
                        fragment = new OrderFragment();
                        break;
                    default:
                        return false;
                }
                replaceFragment(fragment);
                return true;
            }
        });
    }

    private void handleIntent(String fragmentToShow) {
        if (fragmentToShow != null) {
            switch (fragmentToShow) {
                case "order":
                    binding.bottomNavigation.getMenu().findItem(R.id.navigation_receipt).setChecked(true);
                    replaceFragment(new OrderFragment());
                    break;
                case "profile":
                    binding.bottomNavigation.getMenu().findItem(R.id.navigation_profile).setChecked(true);
                    replaceFragment(new ProfileFragment());
                    break;
                default:
                    binding.bottomNavigation.getMenu().findItem(R.id.navigation_home).setChecked(true);
                    replaceFragment(new HomeFragment());
                    break;
            }
        } else {
            binding.bottomNavigation.getMenu().findItem(R.id.navigation_home).setChecked(true);
            replaceFragment(new HomeFragment());
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String fragmentTag = fragment.getClass().getSimpleName();

        // Avoid adding the same fragment multiple times
        if (fragmentManager.findFragmentByTag(fragmentTag) == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, fragmentTag);
            fragmentTransaction.addToBackStack(fragmentTag);
            fragmentTransaction.commit();
        }
    }
    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 1) {
            getSupportFragmentManager().popBackStackImmediate();

            // Lấy Fragment trước đó từ ngăn xếp
            FragmentManager.BackStackEntry backStackEntry = getSupportFragmentManager().getBackStackEntryAt(backStackEntryCount - 2);
            String previousFragmentTag = backStackEntry.getName();
            Fragment previousFragment = getSupportFragmentManager().findFragmentByTag(previousFragmentTag);

            // Chuyển đổi BottomNavigationView để chọn mục tương ứng với Fragment trước đó
            if (previousFragment != null) {
                int selectedItemId = getSelectedItemId(previousFragment);
                binding.bottomNavigation.setSelectedItemId(selectedItemId);
            }
        } else {
            // Nếu chỉ có một Fragment trong ngăn xếp, hoặc không có Fragment nào, thực hiện hành động mặc định
            super.onBackPressed();
        }
    }

    // Phương thức để xác định id của mục trong BottomNavigationView tương ứng với Fragment
    private int getSelectedItemId(Fragment fragment) {
        if (fragment instanceof HomeFragment) {
            return R.id.navigation_home;
        } else if (fragment instanceof ProfileFragment) {
            return R.id.navigation_profile;
        } else if (fragment instanceof HeartFragment) {
            return R.id.navigation_heart;
        } else if (fragment instanceof NotificationFragment) {
            return R.id.navigation_notification;
        } else if (fragment instanceof OrderFragment) {
            return R.id.navigation_receipt;
        }
        return R.id.navigation_home;
    }

}

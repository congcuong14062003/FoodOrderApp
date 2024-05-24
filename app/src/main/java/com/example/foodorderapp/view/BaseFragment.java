package com.example.foodorderapp.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.foodorderapp.NetworkUtils;
import com.google.android.material.snackbar.Snackbar;

public abstract class BaseFragment extends Fragment {

    private boolean isActivityVisible = false;

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isActivityVisible && !NetworkUtils.isNetworkAvailable(context)) {
                showNetworkSnackbar();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(networkReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        isActivityVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isActivityVisible = false;
    }

    private void showNetworkSnackbar() {
        Snackbar snackbar = Snackbar.make(requireActivity().findViewById(android.R.id.content), "Mất kết nối mạng", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", view -> {
            snackbar.dismiss();
        });
        snackbar.show();

        new Handler().postDelayed(() -> {
            if (isActivityVisible) {
                Intent noNetworkIntent = new Intent(requireContext(), NoNetworkActivity.class);
                startActivity(noNetworkIntent);
            }
        }, 5000); // 5000 milliseconds = 5 seconds
    }
}

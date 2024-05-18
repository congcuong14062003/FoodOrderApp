package com.example.foodorderapp.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderapp.NetworkUtils;
import com.google.android.material.snackbar.Snackbar;

public abstract class BaseActivity extends AppCompatActivity {
    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!NetworkUtils.isNetworkAvailable(context)) {
                showNetworkSnackbar();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReceiver);
    }
    private void showNetworkSnackbar() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Mất kết nối mạng", Snackbar.LENGTH_LONG);
        snackbar.show();

        new Handler().postDelayed(() -> {
            Intent noNetworkIntent = new Intent(this, NoNetworkActivity.class);
            startActivity(noNetworkIntent);
            finish();
        }, 5000); // 5000 milliseconds = 5 seconds
    }
}

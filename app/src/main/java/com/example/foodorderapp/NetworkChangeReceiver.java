package com.example.foodorderapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.foodorderapp.view.NoNetworkActivity;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String PREFS_NAME = "AppPrefs";
    private static final String LAST_PAGE = "lastPage";

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            // Chuyển về trang cũ nếu có mạng
            Toast.makeText(context, "Connected to Internet", Toast.LENGTH_SHORT).show();
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String lastPage = prefs.getString(LAST_PAGE, null);
            if (lastPage != null) {
                try {
                    Class<?> lastActivity = Class.forName(lastPage);
                    Intent backIntent = new Intent(context, lastActivity);
                    backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(backIntent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Chuyển sang trang thông báo mất mạng
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(LAST_PAGE, intent.getComponent().getClassName());
            editor.apply();
            Intent noConnectionIntent = new Intent(context, NoNetworkActivity.class);
            noConnectionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(noConnectionIntent);
        }
    }
}

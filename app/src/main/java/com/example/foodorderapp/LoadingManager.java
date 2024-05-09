package com.example.foodorderapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;

public class LoadingManager {
    private static Dialog dialog;

    public static void showLoading(Activity activity) {
        if (dialog == null) {
            dialog = new Dialog(activity);
            dialog.setContentView(R.layout.dialog_loading); // Tạo layout cho Dialog, có thể là một ProgressBar hoặc layout tùy chỉnh khác
            dialog.setCancelable(false); // Không cho phép bấm nút back để hủy bỏ Dialog
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Để nền trong suốt
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT); // Kích thước toàn màn hình
        }
        dialog.show();
    }

    public static void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}

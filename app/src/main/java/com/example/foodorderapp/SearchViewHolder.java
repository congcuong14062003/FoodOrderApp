package com.example.foodorderapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    ImageView itemImage;
    TextView itemName, itemDescription , itemReview;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
        itemName = (TextView) itemView.findViewById(R.id.itemName);
        itemDescription = (TextView) itemView.findViewById(R.id.itemDescription);
        itemReview = (TextView) itemView.findViewById(R.id.itemReview);

    }
}

package com.example.foodorderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchDetailAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    Context context;
    List <SearchItem> items;

    public SearchDetailAdapter(Context context, List<SearchItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_search_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.itemName.setText(items.get(position).getName());
        holder.itemImage.setImageResource(items.get(position).getImage());
        holder.itemDescription.setText(items.get(position).getDescription());
        holder.itemReview.setText(items.get(position).getCountReview());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

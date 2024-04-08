package com.example.foodorderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchDetailAdapter extends RecyclerView.Adapter<SearchDetailAdapter.SearchViewHolder> {

    Context context;
    ArrayList<SearchItem> items;

    public SearchDetailAdapter(Context context, ArrayList<SearchItem> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.activity_search_item, parent, false);
        return new SearchViewHolder(itemview);
//        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_search_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.itemName.setText(items.get(position).getName());
        holder.itemImage.setImageResource(items.get(position).getImage());
        holder.itemDescription.setText(items.get(position).getDescription());
        holder.itemReview.setText(String.valueOf(items.get(position).getCountReview()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

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
}



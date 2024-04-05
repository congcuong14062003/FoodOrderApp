package com.example.foodorderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemOrderHistoryAdapter extends RecyclerView.Adapter<ItemOrderHistoryAdapter.ViewHolder>  {
    ArrayList<ItemOrderHistory> orderHistories;
    Context context;

    public ItemOrderHistoryAdapter(ArrayList<ItemOrderHistory> orderHistories, Context context) {
        this.orderHistories = orderHistories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_order_history, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNameHistoryOrder.setText(orderHistories.get(position).getName());
        holder.imgHistoryOrder.setImageResource(orderHistories.get(position).getImageResource());
        holder.txtIngredientsHistoryOrder.setText(orderHistories.get(position).getIngredients());
        holder.txtPriceHistoryOrder.setText(String.valueOf(orderHistories.get(position).getPrice()));
        holder.txtQuantityHistoryOrder.setText(String.valueOf(orderHistories.get(position).getQuantity()));

    }

    @Override
    public int getItemCount() {
        return orderHistories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameHistoryOrder;
        TextView txtIngredientsHistoryOrder;
        TextView txtPriceHistoryOrder;
        TextView txtQuantityHistoryOrder;

        ImageView imgHistoryOrder;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameHistoryOrder = (TextView) itemView.findViewById(R.id.nameItemHistoryOrder);
            imgHistoryOrder = (ImageView) itemView.findViewById(R.id.imgItemHistoryOrder);
            txtIngredientsHistoryOrder = (TextView) itemView.findViewById(R.id.ingredientItemHistoryOrder);
            txtPriceHistoryOrder = (TextView) itemView.findViewById(R.id.priceItemHistoryOrder);
            txtQuantityHistoryOrder = (TextView) itemView.findViewById(R.id.quantityItemHistoryOrder);
        }
    }
}

package com.example.foodorderapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemOrderHistoryAdapter extends RecyclerView.Adapter<ItemOrderHistoryAdapter.ViewHolder> {

    private ArrayList<ItemOrderHistory> orderHistories;
    private Context context;
    private IOnItemClickListener listener;

    public ItemOrderHistoryAdapter(ArrayList<ItemOrderHistory> orderHistories, Context context) {
        this.orderHistories = orderHistories;
        this.context = context;
    }

    // Setter cho listener
    public void setOnItemClickListener(IOnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_order_history, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemOrderHistory orderHistory = orderHistories.get(position);

        // Gán dữ liệu vào các view trong item
        holder.txtNameHistoryOrder.setText(orderHistory.getName());
        holder.txtIngredientsHistoryOrder.setText(orderHistory.getIngredients());
        holder.txtPriceHistoryOrder.setText(String.valueOf(orderHistory.getPrice()));
        holder.txtTotalPriceHistoryOrder.setText(String.valueOf(orderHistory.getTotalPrice()));
        holder.txtQuantityHistoryOrder.setText(String.valueOf(orderHistory.getQuantity()));

        // Sử dụng Picasso để tải hình ảnh từ URL và hiển thị nó trong ImageView
        Picasso.get().load(orderHistory.getImageUrl()).into(holder.imgHistoryOrder);
        // Định dạng ngày mua
        String formattedDateTime = DateTimeHelper.formatDateTime(orderHistory.getPurchaseDate());
        holder.txtOrderDateTime.setText(formattedDateTime);
        // Xử lý sự kiện khi một mục trong RecyclerView được nhấp
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderHistories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameHistoryOrder;
        TextView txtIngredientsHistoryOrder;
        TextView txtPriceHistoryOrder;
        TextView txtTotalPriceHistoryOrder;
        TextView txtQuantityHistoryOrder;
        TextView txtOrderDateTime;
        ImageView imgHistoryOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameHistoryOrder = itemView.findViewById(R.id.nameItemHistoryOrder);
            imgHistoryOrder = itemView.findViewById(R.id.imgItemHistoryOrder);
            txtIngredientsHistoryOrder = itemView.findViewById(R.id.ingredientItemHistoryOrder);
            txtPriceHistoryOrder = itemView.findViewById(R.id.priceItemHistoryOrder);
            txtQuantityHistoryOrder = itemView.findViewById(R.id.quantityItemHistoryOrder);
            txtOrderDateTime = itemView.findViewById(R.id.purchaseDateTextView);
            txtTotalPriceHistoryOrder = itemView.findViewById(R.id.totalPriceTextView);
        }
    }
}

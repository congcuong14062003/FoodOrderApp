package com.example.foodorderapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.DateTimeHelper;
import com.example.foodorderapp.R;
import com.example.foodorderapp.object.OrderDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<OrderDTO> orderList = new ArrayList<>();

    public void setOrderList(List<OrderDTO> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_history, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderDTO order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameHistoryOrder;
        TextView txtIngredientsHistoryOrder;
        TextView txtPriceHistoryOrder;
        TextView txtTotalPriceHistoryOrder;
        TextView txtQuantityHistoryOrder;
        TextView txtOrderDateTime;
        ImageView imgHistoryOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameHistoryOrder = itemView.findViewById(R.id.nameItemHistoryOrder);
            imgHistoryOrder = itemView.findViewById(R.id.imgItemHistoryOrder);
            txtIngredientsHistoryOrder = itemView.findViewById(R.id.ingredientItemHistoryOrder);
            txtPriceHistoryOrder = itemView.findViewById(R.id.priceItemHistoryOrder);
            txtQuantityHistoryOrder = itemView.findViewById(R.id.quantityItemHistoryOrder);
            txtOrderDateTime = itemView.findViewById(R.id.purchaseDateTextView);
            txtTotalPriceHistoryOrder = itemView.findViewById(R.id.totalPriceTextView);
        }

        public void bind(OrderDTO order) {
            txtNameHistoryOrder.setText(order.getName());
            txtIngredientsHistoryOrder.setText(order.getIngredients());
            txtPriceHistoryOrder.setText(String.valueOf(order.getPrice()));
            txtQuantityHistoryOrder.setText(String.valueOf(order.getQuantity()));
            String formattedDateTime = DateTimeHelper.formatDateTime(order.getPurchaseDate());
            txtOrderDateTime.setText(formattedDateTime);
            txtTotalPriceHistoryOrder.setText(String.valueOf(order.getTotalPrice()));
            // Sử dụng Picasso để tải hình ảnh từ URL và hiển thị nó trong ImageView
            Picasso.get().load(order.getImageUrl()).into(imgHistoryOrder);

        }
    }
}

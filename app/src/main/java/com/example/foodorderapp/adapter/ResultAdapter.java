package com.example.foodorderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.object.FoodDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    private List<FoodDTO> foodDTOList = new ArrayList<>();

    public void setFoodList(List<FoodDTO> foodDTOList) {
        this.foodDTOList = foodDTOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultAdapter.ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actitvity_result_item, parent, false);
        return new ResultAdapter.ResultViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ResultAdapter.ResultViewHolder holder, int position) {
        FoodDTO foodDTO = foodDTOList.get(position);
        holder.bind(foodDTO);
    }

    @Override
    public int getItemCount() {
        return foodDTOList.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView nameFood;
        TextView ingredientFood;
        TextView totalReview;
        ImageView img_thumbnail;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            nameFood = itemView.findViewById(R.id.itemName);
            ingredientFood = itemView.findViewById(R.id.itemDescription);
            img_thumbnail = itemView.findViewById(R.id.itemImage);
        }

        public void bind(FoodDTO foodDTO) {
            nameFood.setText(foodDTO.getName());
            ingredientFood.setText(foodDTO.getIngredients());
            // Sử dụng Picasso để tải hình ảnh từ URL và hiển thị nó trong ImageView
            Picasso.get().load(foodDTO.getImageUrl()).into(img_thumbnail);
        }
    }
}

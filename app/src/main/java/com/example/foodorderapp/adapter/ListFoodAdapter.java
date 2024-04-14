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
import com.example.foodorderapp.object.FoodDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListFoodAdapter extends RecyclerView.Adapter<ListFoodAdapter.FoodViewHolder> {
    private List<FoodDTO> foodDTOList = new ArrayList<>();

    public void setFoodList(List<FoodDTO> foodDTOList) {
        this.foodDTOList = foodDTOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search_item, parent, false);
        return new FoodViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodDTO foodDTO = foodDTOList.get(position);
        holder.bind(foodDTO);
    }

    @Override
    public int getItemCount() {
        return foodDTOList.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView nameFood;
        TextView ingredientFood;
        TextView totalReview;
        ImageView img_thumbnail;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            nameFood = itemView.findViewById(R.id.foodName);
            ingredientFood = itemView.findViewById(R.id.foodIngredient);
            img_thumbnail = itemView.findViewById(R.id.foodImgList);
        }

        public void bind(FoodDTO foodDTO) {
            nameFood.setText(foodDTO.getName());
            ingredientFood.setText(foodDTO.getIngredients());
            // Sử dụng Picasso để tải hình ảnh từ URL và hiển thị nó trong ImageView
            Picasso.get().load(foodDTO.getImageUrl()).into(img_thumbnail);
        }
    }
}
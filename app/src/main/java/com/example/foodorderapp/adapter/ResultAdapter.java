package com.example.foodorderapp.adapter;

import android.content.Context;
import android.util.Log;
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
    private InFoodItemClickListener inFoodItemClickListener;

    public void setFoodList(List<FoodDTO> foodDTOList) {
        this.foodDTOList = foodDTOList;
        notifyDataSetChanged();
    }
    public void SetOnFoodItemClickListener(InFoodItemClickListener listener) {
        this.inFoodItemClickListener = listener;
    }


    @NonNull
    @Override
    public ResultAdapter.ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actitvity_result_item, parent, false);
        return new ResultAdapter.ResultViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ResultAdapter.ResultViewHolder holder, int position) {
        FoodDTO foodDTO = foodDTOList.get(position);
        holder.SetItemClickListeners(inFoodItemClickListener, foodDTO.getId());
        holder.bind(foodDTO);
    }

    @Override
    public int getItemCount() {
        return foodDTOList.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        TextView total_reviews;
        ImageView img_thumbnail;
        TextView average_rating;
        ImageView[] starImages;


        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            description = itemView.findViewById(R.id.itemDescription);
            img_thumbnail = itemView.findViewById(R.id.itemImage);
            total_reviews = itemView.findViewById(R.id.countReview);
            starImages = new ImageView[5];
            starImages[0]=itemView.findViewById(R.id.star1);
            starImages[1]=itemView.findViewById(R.id.star2);
            starImages[2]=itemView.findViewById(R.id.star3);
            starImages[3]=itemView.findViewById(R.id.star4);
            starImages[4]=itemView.findViewById(R.id.star5);
        }

        public void bind(FoodDTO foodDTO) {
            name.setText(foodDTO.getName());
            description.setText(foodDTO.getDescription());
            total_reviews.setText(String.valueOf(foodDTO.getTotal_reviews()));
            setRatingStars(foodDTO.getAverage_rating());
            // Sử dụng Picasso để tải hình ảnh từ URL và hiển thị nó trong ImageView
            Picasso.get().load(foodDTO.getImg_thumbnail()).into(img_thumbnail);
        }

        public void SetItemClickListeners(final InFoodItemClickListener listener, final int foodId) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onFoodItemClick(foodId);
                    }
                }
            });
        }

        private void setRatingStars(String averageRating){
            // Kiểm tra nếu averageRating là null hoặc rỗng, thì mặc định toàn bộ sẽ là sao trắng
            if (averageRating == null || averageRating.isEmpty()) {
                Log.d("ListFoodAdapter", "Món ăn có đánh giá null");
                setAllStarImage(false);
            } else {
                Log.d("ListFoodAdapter", "Món ăn có đánh giá " + averageRating);
                // Kiểm tra xem chuỗi có chứa dấu chấm không
                if (averageRating.contains(".")) {
                    // Nếu có, cắt bỏ phần thập phân trước khi chuyển đổi
                    averageRating = averageRating.split("\\.")[0];
                }
                try {
                    int avgNumberRating = Integer.parseInt(averageRating);
                    int minNumber = Math.min(avgNumberRating, 5);
                    for (int i = 0; i < minNumber; i++) {
                        starImages[i].setImageResource(R.drawable.pinkstar);
                    }
                } catch (NumberFormatException e) {
                    // Xử lý nếu không thể chuyển đổi chuỗi thành số nguyên
                    e.printStackTrace();
                }
            }

        }
        private void setAllStarImage(boolean isFilled) {
            int starResource = isFilled ? R.drawable.pinkstar : R.drawable.star;
            for (ImageView starImage : starImages) {
                starImage.setImageResource(starResource);
            }
        }
    
    }
    public interface InFoodItemClickListener {
        void onFoodItemClick(int foodId);
    }

}

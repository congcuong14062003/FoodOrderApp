package com.example.foodorderapp.adapter;

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
import com.example.foodorderapp.object.ReviewDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<ReviewDTO> reviewDTOS = new ArrayList<>();

    public void setReviewList(List<ReviewDTO> reviewDTOS) {
        this.reviewDTOS = reviewDTOS;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        ReviewDTO reviewDTO = reviewDTOS.get(position);
        holder.bind(reviewDTO);
    }

    @Override
    public int getItemCount() {
        return reviewDTOS.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView reviews_datetime;
        TextView comment;
        ImageView avatar_thumbnail;
        ImageView[] starImages;
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameUserReview);
            comment = itemView.findViewById(R.id.content_review);
            avatar_thumbnail = itemView.findViewById(R.id.avtUserReview);
            reviews_datetime = itemView.findViewById(R.id.date_review);
            starImages = new ImageView[5];
            starImages[0] = itemView.findViewById(R.id.rvstar1);
            starImages[1] = itemView.findViewById(R.id.rvstar2);
            starImages[2] = itemView.findViewById(R.id.rvstar3);
            starImages[3] = itemView.findViewById(R.id.rvstar4);
            starImages[4] = itemView.findViewById(R.id.rvstar5);
        }
        public void bind(ReviewDTO reviewDTO) {
            name.setText(reviewDTO.getName());
            comment.setText(reviewDTO.getComment());
            Picasso.get().load(reviewDTO.getAvatar_thumbnail()).into(avatar_thumbnail);
            reviews_datetime.setText(String.valueOf(reviewDTO.getReviews_datetime()));
            setRatingStars(reviewDTO.getRate());
        }
        private void setRatingStars(int averageRating) {
                try {
                    int minNumber = Math.min(averageRating, 5);
                    for (int i = 0; i < minNumber; i++) {
                        starImages[i].setImageResource(R.drawable.pinkstar);
                    }
                } catch (NumberFormatException e) {
                    // Xử lý nếu không thể chuyển đổi chuỗi thành số nguyên
                    e.printStackTrace();
                }
        }

        private void setAllStarImage(boolean isFilled) {
            int starResource = isFilled ? R.drawable.pinkstar : R.drawable.star;
            for (ImageView starImage : starImages) {
                starImage.setImageResource(starResource);
            }
        }
    }
}

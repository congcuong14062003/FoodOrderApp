package com.example.foodorderapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.DateTimeHelper;
import com.example.foodorderapp.R;
import com.example.foodorderapp.object.NotiDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.NotiViewHolder> {
    private List<NotiDTO> notiDTOList = new ArrayList<>();

    public void setNotiList(List<NotiDTO> notiDTOList) {
        this.notiDTOList=notiDTOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_noti_item, parent, false);
        return new NotiViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull NotiViewHolder holder, int position) {
        NotiDTO notiDTO = notiDTOList.get(position);
        holder.bind(notiDTO);
    }

    @Override
    public int getItemCount() {
        return notiDTOList.size();
    }

    static class NotiViewHolder extends RecyclerView.ViewHolder {
        TextView notiName;
        TextView notiQuantity;
        TextView notiTotal;
        TextView notiInsertTime;
        ImageView imgNoti;

        @SuppressLint("WrongViewCast")
        public NotiViewHolder(@NonNull View itemView) {
            super(itemView);
            notiName = itemView.findViewById(R.id.notiName);
//            notiQuantity = itemView.findViewById(R.id.notiQuantity);
            notiTotal = itemView.findViewById(R.id.notiTotal);
            notiInsertTime = itemView.findViewById(R.id.notiInsertTime);
            imgNoti = itemView.findViewById(R.id.notiImg);
        }

        public void bind(NotiDTO notiDTO) {
            notiName.setText( "Món ăn "+notiDTO.getName() + "(x"+String.valueOf(notiDTO.getQuantity())+")"+ " đang trên đường giao đến bạn." );
//           notiQuantity.setText(String.valueOf(notiDTO.getQuantity()));
            notiTotal.setText(  "Vui lòng thanh toán "+"$" + String.valueOf(notiDTO.getTotal_price())+ " cho người giao hàng." );
            String formattedDateTime = DateTimeHelper.formatDateTime(notiDTO.getOrder_datetime());
            notiInsertTime.setText(formattedDateTime);
            // Sử dụng Picasso để tải hình ảnh từ URL và hiển thị nó trong ImageView
//            Picasso.get().load(notiDTO.getImgNoti()).into(imgNoti);
        }
    }
}

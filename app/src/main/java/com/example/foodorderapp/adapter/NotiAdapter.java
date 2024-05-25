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
        TextView title_notifi ;
        TextView notices_message;
        TextView notices_datetime;

        @SuppressLint("WrongViewCast")
        public NotiViewHolder(@NonNull View itemView) {
            super(itemView);
            title_notifi = itemView.findViewById(R.id.title_notifi);
//
            notices_message = itemView.findViewById(R.id.notices_message);
            notices_datetime = itemView.findViewById(R.id.notices_datetime);

        }

        public void bind(NotiDTO notiDTO) {
            title_notifi.setText( notiDTO.getTitle_notifi()  );
            notices_message.setText(  notiDTO.getNotices_message() );
            String formattedDateTime = DateTimeHelper.formatDateTime(notiDTO.getNotices_datetime());
            notices_datetime.setText(formattedDateTime);

        }
    }
}

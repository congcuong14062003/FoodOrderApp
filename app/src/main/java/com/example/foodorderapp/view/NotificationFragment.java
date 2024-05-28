package com.example.foodorderapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.LoadingManager;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.NotiAdapter;
import com.example.foodorderapp.viewmodal.NotiViewModel;

public class NotificationFragment extends BaseFragment {
    private NotiViewModel notiViewModel;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notiViewModel = new ViewModelProvider(this).get(NotiViewModel.class);
    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        LoadingManager.showLoading(requireActivity());
        RecyclerView recyclerView = view.findViewById(R.id.notiRecycle);
        final NotiAdapter adapter = new NotiAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView message = view.findViewById(R.id.message);

        notiViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), isError -> {
            if (isError != null && isError) {
                message.setVisibility(View.VISIBLE);
                LoadingManager.hideLoading();
            }
        });

        notiViewModel.getNotiList().observe(getViewLifecycleOwner(), notiDTOS -> {
            adapter.setNotiList(notiDTOS);
            LoadingManager.hideLoading();
        });

        ImageView cancelBtn = view.findViewById(R.id.backBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

    return view;
    }
}

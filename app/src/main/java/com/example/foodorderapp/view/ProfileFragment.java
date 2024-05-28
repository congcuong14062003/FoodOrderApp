package com.example.foodorderapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodorderapp.LoadingManager;
import com.example.foodorderapp.R;
import com.example.foodorderapp.UserManager;
import com.example.foodorderapp.object.UserDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.UserResponsive;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

    public class ProfileFragment extends BaseFragment {
        TextView textName;
        TextView textAddress;
        ImageView avtUser;

        LinearLayout next_history_order;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
            LoadingManager.showLoading(requireActivity());
            ImageView nextToUpdateUser = rootView.findViewById(R.id.avatar_user);
            int userId = UserManager.getInstance().getUserId();
            textName = rootView.findViewById(R.id.text_Name);
            textAddress = rootView.findViewById(R.id.text_address);
            avtUser = rootView.findViewById(R.id.avatar_user);
            LinearLayout nextUpdateUser = rootView.findViewById(R.id.next_update_user);
            nextUpdateUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAdded() && getActivity() != null) {
                        Intent intent = new Intent(getActivity(), UserUpdateActivity.class);
                        intent.putExtra("USER_ID", userId);
                        startActivity(intent);
                    } else {
                        Log.e("ProfileFragment", "Fragment not added or Activity is null");
                    }
                }
            });




            next_history_order = rootView.findViewById(R.id.next_history_order);
            next_history_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("fragment", "order");
                    startActivity(intent);
                }
            });
            LinearLayout logoutButton = rootView.findViewById(R.id.logout);
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserManager.getInstance().clearUserId();
                    UserManager.getInstance().setUserId(-1);
                    Intent intent = new Intent(rootView.getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            fetchUserInfo(userId);
            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            fetchUserInfo(UserManager.getInstance().getUserId());
        }

        private void fetchUserInfo(int userId) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<UserResponsive> call = apiService.getUserInfo(userId);
            call.enqueue(new Callback<UserResponsive>() {
                @Override
                public void onResponse(Call<UserResponsive> call, Response<UserResponsive> response) {
                    if (response.isSuccessful()) {
                        UserDTO userData = response.body().getData();
                        if (userData != null) {
                            textName.setText(userData.getName());
                            textAddress.setText(userData.getAddress());
                            String avatarUser = userData.getAvatar_thumbnail();
                            Picasso.get().load(avatarUser).into(avtUser, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.d("Picasso", "Hình ảnh đã được tải thành công.");
                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.e("Picasso", "Lỗi khi tải hình ảnh: " + e.getMessage());
                                }
                            });
                            Log.d("Màn Home", "ảnh đại diện: " + avatarUser); // Log success message
                        }
                        LoadingManager.hideLoading();
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch user info", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<UserResponsive> call, Throwable t) {
                    Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
                    LoadingManager.hideLoading();
                }
            });
        }
    }


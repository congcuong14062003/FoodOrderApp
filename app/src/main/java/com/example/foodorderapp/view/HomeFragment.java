package com.example.foodorderapp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.example.foodorderapp.LoadingManager;
import com.example.foodorderapp.NetworkUtils;
import com.example.foodorderapp.R;
import com.example.foodorderapp.UserManager;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.UserResponsive;
import com.example.foodorderapp.object.UserDTO;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private EditText searchHome;
    private FoodFragment foodFragment;
    TextView userName;
    ImageView  avtUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Hiển thị màn hình loading khi bắt đầu tải dữ liệu
            LoadingManager.showLoading(requireActivity());

//        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
//            LoadingManager.showLoading(requireActivity());
//            Toast.makeText(requireContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
//        } else {
//            LoadingManager.hideLoading();
//
//        }
        searchHome = view.findViewById(R.id.search_home);
        // Lấy id người dùng từ bất kỳ đâu trong ứng dụng
        int userId = UserManager.getInstance().getUserId();
        userName = view.findViewById(R.id.nameUser);
        avtUser = view.findViewById(R.id.avtUser);
        // ấn vào nút search
        searchHome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Thay thế HomeFragment bằng FoodFragment
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, new FoodFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
        fetchUserInfo(userId);
        return view;
    }
    private void fetchUserInfo(int userId) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<UserResponsive> call = apiService.getUserInfo(userId);
        call.enqueue(new Callback<UserResponsive>() {
            @Override
            public void onResponse(Call<UserResponsive> call, Response<UserResponsive> response) {
                if(response.isSuccessful()) {

                    UserDTO userData = response.body().getData();
                    if(userData != null) {
                        userName.setText("Xin chào, " + userData.getName());
                        String avatarUser = userData.getAvatar_thumbnail();
                        Picasso.get().load(avatarUser).into(avtUser, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("Màn Home", "Hình ảnh đã được tải thành công.");
                            }
                            @Override
                            public void onError(Exception e) {
                                Log.e("Màn Home", "Lỗi khi tải hình ảnh: " + e.getMessage());
                            }
                        });
                        Log.d("Màn Home", "ảnh đại diện: " + avatarUser); // Log success message
                    }
                    LoadingManager.hideLoading();

                } else {
                    LoadingManager.hideLoading();
                    Toast.makeText(requireView().getContext(), "Failed to fetch user info", Toast.LENGTH_SHORT).show();
                }
            }
            // khi không thành công
            @Override
            public void onFailure(Call<UserResponsive> call, Throwable t) {
                Toast.makeText(requireView().getContext(), "Lỗi mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

package com.example.foodorderapp.viewmodal;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderapp.object.PostNotiDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.PostNoticeResponsive;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostNoticeViewModel extends ViewModel {
    private final MutableLiveData<Boolean> postNoticeStatus = new MutableLiveData<>();

    public LiveData<Boolean> getPostNoticeStatus() {
        return postNoticeStatus;
    }

    public void postNotices(int user_id, String title_notifi, String notices_message) {
        PostNotiDTO notiDTO = new PostNotiDTO(user_id, title_notifi, notices_message);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<PostNoticeResponsive> call = apiService.postNotice(notiDTO.getUser_id(), notiDTO.getTitle_notifi(), notiDTO.getNotices_message());
        call.enqueue(new Callback<PostNoticeResponsive>() {
            @Override
            public void onResponse(Call<PostNoticeResponsive> call, Response<PostNoticeResponsive> response) {
                Log.d("PostNoticeViewModel", "Response nè: " + response.code()); // Log response code
                if (response.isSuccessful() && response.body() != null) {
                    postNoticeStatus.setValue(true);
                    Log.d("PostNoticeViewModel", "API call successful.");
                } else {
                    postNoticeStatus.setValue(false);
                    String errorMessage;
                    if (response.errorBody() != null) {
                        try {
                            String errorBodyString = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(errorBodyString);
                            errorMessage = jsonObject.getString("message");
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                            errorMessage = "Unknown error";
                        }
                    } else {
                        errorMessage = "Server error";
                    }
                    Log.e("PostNoticeViewModel", "API call failed: " + errorMessage); // Log error message
                }
            }

            @Override
            public void onFailure(Call<PostNoticeResponsive> call, Throwable t) {
                postNoticeStatus.setValue(false);
                Log.e("PostNoticeViewModel", "API call failed: " + t.getMessage()); // Log failure message
            }
        });
    }
}
